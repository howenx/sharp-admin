package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import com.fasterxml.jackson.databind.JsonNode;
import domain.ID;
import domain.Refund;
import domain.RefundTemp;
import domain.User;
import domain.order.Order;
import domain.order.OrderLine;
import domain.order.OrderShip;
import domain.order.OrderSplit;
import filters.UserAuth;
import modules.NewScheduler;
import order.GetLogistics;
import play.Configuration;
import play.Logger;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by tiffany on 16/1/8.
 */
public class OrderCtrl extends Controller {

    @Inject
    private ItemService service;

    @Inject
    private OrderService orderService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    private OrderLineService orderLineService;

    @Inject
    private OrderShipService orderShipService;

    @Inject
    private IDService idService;

    @Inject
    private RefundService refundService;

    @Inject
    private ActorSystem system;

    @Inject
    private Configuration configuration;


    @Inject
    private NewScheduler newScheduler;


    public static final Timeout TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);

    /**
     * 订单列表     Added by Tiffany Zhu
     *
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderList(String lang) {
        Order order_temp = new Order();
        order_temp.setPageSize(-1);
        order_temp.setOffset(-1);

        int countNum = orderService.getOrdersAll().size();
        int pageCount = countNum / ThemeCtrl.PAGE_SIZE;
        if (countNum % ThemeCtrl.PAGE_SIZE != 0) {
            pageCount = countNum / ThemeCtrl.PAGE_SIZE + 1;
        }
        order_temp.setPageSize(ThemeCtrl.PAGE_SIZE);
        order_temp.setOffset(0);

        //订单列表
        List<Object[]> orList = new ArrayList<>();
        List<Order> orderList = orderService.getOrderPage(order_temp);
        for (Order order : orderList) {
            Object[] object = new Object[10];
            object[0] = order.getOrderId();
            object[1] = order.getUserId();
            object[2] = order.getOrderCreateAt();
            object[3] = order.getPayTotal();
            //支付方式
            if ("JD".equals(order.getPayMethod())) {
                object[4] = "京东";
            }
            if ("APAY".equals(order.getPayMethod())) {
                object[4] = "支付宝";
            }
            if ("WEIXIN".equals(order.getPayMethod())) {
                object[4] = "微信";
            }
            //订单状态
            //当前时间减去24小时
            Timestamp time = new Timestamp(System.currentTimeMillis() - 1 * 24 * 3600 * 1000L);
            if (order.getOrderCreateAt().before(time) && "I".equals(order.getOrderStatus())) {
                object[5] = "订单已超时";
            } else {
                if ("I".equals(order.getOrderStatus())) {
                    object[5] = "未支付";
                }
                if ("S".equals(order.getOrderStatus())) {
                    object[5] = "支付成功";
                }
                if ("C".equals(order.getOrderStatus())) {
                    object[5] = "订单取消";
                }
                if ("F".equals(order.getOrderStatus())) {
                    object[5] = "支付失败";
                }
                if ("R".equals(order.getOrderStatus())) {
                    object[5] = "已签收";
                }
                if ("D".equals(order.getOrderStatus())) {
                    object[5] = "已发货";
                }
                if ("J".equals(order.getOrderStatus())) {
                    object[5] = "拒收";
                }
                if ("N".equals(order.getOrderStatus())) {
                    object[5] = "已删除";
                }
                if ("T".equals(order.getOrderStatus())) {
                    object[5] = "已退款";
                }
                if ("PI".equals(order.getOrderStatus())) {
                    object[5] = "拼购未支付";
                }
                if (("PS").equals(order.getOrderStatus())) {
                    object[5] = "拼购支付成功";
                }
                if (("PF").equals(order.getOrderStatus())) {
                    object[5] = "拼团失败未退款";
                }
            }
            //手机号码
            ID userInfo = idService.getID(Integer.parseInt(order.getUserId().toString()));
            object[6] = userInfo.getPhoneNum();
            //订单类型
            if (order.getOrderType() == 1) {
                object[7] = "普通";
            }
            if (order.getOrderType() == 2) {
                object[7] = "拼购";
            }
            //拼购团ID
            object[8] = order.getPinActiveId();
            //订单状态
            object[9] = order.getOrderStatus();

            orList.add(object);

        }
        return ok(views.html.order.ordersearch.render(lang, ThemeCtrl.PAGE_SIZE, countNum, pageCount, orList, (User) ctx().args.get("user")));

    }

    /**
     * 订单Ajax查询     Added by Tiffany Zhu
     *
     * @param lang
     * @param pageNum
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderSearchAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        Order order = new Order();
        if (json.has("order")) {
            order = Json.fromJson(json.get("order"), Order.class);
        }
        if (json.has("userPhone")) {
            String userPhone = json.get("userPhone").toString();
            userPhone = userPhone.substring(1, userPhone.length() - 1);
            if (!userPhone.equals("")) {
                ID userTemp = idService.getIDByPhoneNum(userPhone);
                //Logger.error(userTemp.toString());
                if (userTemp != null && userTemp.getUserId() != null) {
                    if (order.getUserId() != null && !(order.getUserId().toString().equals(userTemp.getUserId().toString()))) {
                        order.setUserId(Long.valueOf(0));
                    } else {
                        order.setUserId(Long.valueOf(userTemp.getUserId()));
                    }
                }
            }
        }
        if (pageNum >= 1) {
            //计算从第几条开始取数据
            int offset = (pageNum - 1) * ThemeCtrl.PAGE_SIZE;
            order.setPageSize(-1);
            order.setOffset(-1);
            //取总数
            int countNum = orderService.getOrderPage(order).size();
            //共分几页
            int pageCount = countNum / ThemeCtrl.PAGE_SIZE;

            if (countNum % ThemeCtrl.PAGE_SIZE != 0) {
                pageCount = countNum / ThemeCtrl.PAGE_SIZE + 1;
            }
            order.setPageSize(ThemeCtrl.PAGE_SIZE);
            order.setOffset(offset);
            List<Order> orderList = orderService.getOrderPage(order);
            List<Object> resultList = new ArrayList<>();
            for (Order orderTemp : orderList) {
                Object[] object = new Object[10];
                object[0] = orderTemp.getOrderId();
                object[1] = orderTemp.getUserId();
                DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String startDate = sdf.format(orderTemp.getOrderCreateAt());
                object[2] = startDate;
                object[3] = orderTemp.getPayTotal().toString();
                object[4] = orderTemp.getPayMethod();
                object[5] = orderTemp.getOrderStatus();
                ID userInfo = idService.getID(Integer.parseInt(orderTemp.getUserId().toString()));
                object[6] = null != userInfo ? userInfo.getPhoneNum() : "";
                //object[6] = userInfo.getPhoneNum();
                //订单类型
                if (orderTemp.getOrderType() == 1) {
                    object[7] = "普通";
                }
                if (orderTemp.getOrderType() == 2) {
                    object[7] = "拼购";
                }
                //拼购团ID
                if (orderTemp.getPinActiveId() != null) {
                    object[8] = orderTemp.getPinActiveId();
                } else {
                    object[8] = "";
                }
                //订单状态
                object[9] = orderTemp.getOrderStatus();

                resultList.add(object);
            }
            //组装返回数据
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("topic", resultList);
            returnMap.put("pageNum", pageNum);
            returnMap.put("countNum", countNum);
            returnMap.put("pageCount", pageCount);
            returnMap.put("pageSize", ThemeCtrl.PAGE_SIZE);
            return ok(Json.toJson(returnMap));
        } else {
            return badRequest();
        }
    }

    /**
     * 订单详情     Added by Tiffany Zhu
     *
     * @param lang
     * @param id
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderDetail(String lang, Long id) {
        //获取订单
        Order order = orderService.getOrderById(id);
        Object[] orderArray = new Object[9];
        orderArray[0] = order.getOrderId();       //订单Id
        orderArray[1] = order.getOrderCreateAt(); //订单创建时间
        orderArray[2] = order.getPayTotal();      //订单总费用
        orderArray[3] = order.getDiscount();      //优惠
        orderArray[4] = order.getTotalFee();      //支付的总费用
        //支付方式
        if ("JD".equals(order.getPayMethod())) {
            orderArray[5] = "京东";
        }
        if ("APAY".equals(order.getPayMethod())) {
            orderArray[5] = "支付宝";
        }
        if ("WEIXIN".equals(order.getPayMethod())) {
            orderArray[5] = "微信";
        }
        //支付状态
        Timestamp time = new Timestamp(System.currentTimeMillis() - 1 * 24 * 3600 * 1000L);
        if (order.getOrderCreateAt().before(time) && "I".equals(order.getOrderStatus())) {
            orderArray[6] = "订单已超时";

        } else {
            if ("I".equals(order.getOrderStatus())) {
                orderArray[6] = "未支付";
            }
            if ("S".equals(order.getOrderStatus())) {
                orderArray[6] = "成功";
            }
            if ("C".equals(order.getOrderStatus())) {
                orderArray[6] = "取消";
            }
            if ("F".equals(order.getOrderStatus())) {
                orderArray[6] = "失败";
            }
            if ("R".equals(order.getOrderStatus())) {
                orderArray[6] = "已收货";
            }
            if ("D".equals(order.getOrderStatus())) {
                orderArray[6] = "已发货";
            }
            if ("J".equals(order.getOrderStatus())) {
                orderArray[6] = "拒收";
            }
            if ("N".equals(order.getOrderStatus())) {
                orderArray[6] = "已删除";
            }
            if ("T".equals(order.getOrderStatus())) {
                orderArray[6] = "已退款";
            }
            if ("PI".equals(order.getOrderStatus())) {
                orderArray[6] = "拼购未支付";
            }
            if (("PS").equals(order.getOrderStatus())) {
                orderArray[6] = "拼购成功";
            }
            if (("PF").equals(order.getOrderStatus())) {
                orderArray[6] = "拼团失败未退款";
            }
        }
        orderArray[7] = order.getShipFee();     //邮费
        orderArray[8] = order.getPostalFee();   //行邮税
        //获取订单收货信息
        OrderShip orderShip = orderShipService.getShipByOrderId(id);
        //遮挡身份证号中间8位
        if (orderShip.getDeliveryCardNum() != null && !orderShip.getDeliveryCardNum().equals("")) {
            String subCardNum = orderShip.getDeliveryCardNum().substring(6, 14);
            String rstCardNum = orderShip.getDeliveryCardNum().replace(subCardNum, "********");
            orderShip.setDeliveryCardNum(rstCardNum);
        }
        //获取子订单
        List<OrderSplit> orderSplitList = orderSplitService.getSplitByOrderId(id);
        //返回的结果集
        List<List<List<Object[]>>> subOrdersAll = new ArrayList<>();
        //子订单序号
        int subOrderNum = 0;
        for (OrderSplit orderSplit : orderSplitList) {
            //含有报关和产品的子订单
            List<List<Object[]>> subOrderList = new ArrayList<>();
            //子订单基本信息
            List<Object[]> subOrderPart1 = new ArrayList<>();
            Object[] object1 = new Object[10];
            object1[0] = orderSplit.getSplitId();   //子订单编号
            //子订单报关状态
            if ("I".equals(orderSplit.getState())) {
                object1[1] = "初始化";
            }
            if ("Y".equals(orderSplit.getState())) {
                object1[1] = "报关成功";
            }
            if ("N".equals(orderSplit.getState())) {
                object1[1] = "报关失败";
            }
            object1[2] = orderSplit.getCustomsReturnCode(); //子订单支付报关状态
            object1[3] = orderSplit.getExpressNm(); //快递名称
            object1[4] = orderSplit.getExpressNum();//快递编号
            object1[5] = orderSplit.getShipFee();   //邮费
            object1[6] = orderSplit.getPostalFee(); //行邮税
            object1[7] = orderSplit.getTotalFee();  //商品总价
            object1[8] = orderSplit.getTotalPayFee();//支付费用总计
            subOrderNum = subOrderNum + 1;          //子订单序号
            object1[9] = subOrderNum;
            subOrderPart1.add(object1);
            subOrderList.add(subOrderPart1);

            //子订单的全部商品
            List<OrderLine> orderLineList = orderLineService.getLineBySplitId(orderSplit.getSplitId().longValue());
            //包含商品名的子订单商品
            List<Object[]> subOrderPart2 = new ArrayList<>();
            for (OrderLine orderLine : orderLineList) {
                Object[] object2 = new Object[8];
                //Item item = service.getItem(orderLine.getItemId());
                object2[0] = orderLine.getSkuTitle();    //名称
                JsonNode urlJson = Json.parse(orderLine.getSkuImg());
                object2[1] = urlJson.get("url").asText();  //图片url
                object2[2] = orderLine.getSkuSize(); //尺码
                object2[3] = orderLine.getSkuColor();//颜色
                object2[4] = orderLine.getPrice();   //价格
                object2[5] = orderLine.getAmount();  //数量
                object2[6] = 0;                      //优惠
                BigDecimal amount = new BigDecimal(orderLine.getAmount());
                object2[7] = orderLine.getPrice().multiply(amount);//总价
                subOrderPart2.add(object2);
            }
            subOrderList.add(subOrderPart2);

            //子订单物流
            JsonNode json = GetLogistics.sendGet("12837698789", "jd");
            //JsonNode json = GetLogistics.sendGet(orderSplit.getExpressNum(),"jd");
            if (json != null) {
                Logger.error(json.toString());
                //subOrderList.add();
            }

            //全部的子订单信息
            subOrdersAll.add(subOrderList);
        }

        //用户信息
        ID ClientUser = idService.getID(Integer.parseInt(order.getUserId().toString()));
        Object[] userObject = new Object[2];
        userObject[0] = order.getUserId();
        if (ClientUser != null && ClientUser.getPhoneNum() != null) {
            userObject[1] = ClientUser.getPhoneNum();
        } else {
            userObject[1] = "";
        }

        return ok(views.html.order.orderdetail.render(lang, orderArray, orderShip, subOrdersAll, ThemeCtrl.IMAGE_URL, userObject, (User) ctx().args.get("user")));
    }

    /**
     * 取消订单     Added by Tiffany Zhu 2016.01.04
     *
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderCancel(String lang) {
        JsonNode json = request().body().asJson();
        Logger.error("取消订单:" + json.toString());
        Long ids[] = new Long[json.size()];
        for (int i = 0; i < json.size(); i++) {
            ids[i] = (json.get(i)).asLong();
        }
        orderService.orderCancel(ids);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)), "message.save.success")));
    }

    /**
     * 未支付超时订单列表 Added by Tiffany Zhu 2016.01.07
     *
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result unpaidOrders(String lang) {
        List<Object[]> orList = new ArrayList<>();
        List<Order> orderList = orderService.getOutTimeOrders();
        for (Order order : orderList) {
            Object[] object = new Object[6];
            object[0] = order.getOrderId();
            object[1] = order.getUserId();
            object[2] = order.getOrderCreateAt();
            object[3] = order.getPayTotal();
            //支付方式
            if ("JD".equals(order.getPayMethod())) {
                object[4] = "京东";
            }
            if ("APAY".equals(order.getPayMethod())) {
                object[4] = "支付宝";
            }
            if ("WEIXIN".equals(order.getPayMethod())) {
                object[4] = "微信";
            }
            Timestamp time = new Timestamp(System.currentTimeMillis() - 1 * 24 * 3600 * 1000L);
            if (order.getOrderCreateAt().before(time) && "I".equals(order.getOrderStatus())) {
                object[5] = "订单已超时";
            }
            orList.add(object);

        }
        return ok(views.html.order.outTimeUnpaidOrders.render(lang, orList, (User) ctx().args.get("user")));
    }

    /**
     * 退款申请列表       Added by Tiffany Zhu 2016.04.14
     *
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result refundList(String lang) {
        RefundTemp refund_Temp_condition = new RefundTemp();
        refund_Temp_condition.setPageSize(-1);
        refund_Temp_condition.setOffset(-1);
        int countNum = refundService.getRefundOrders().size();
        int pageCount = countNum / ThemeCtrl.PAGE_SIZE;
        if (countNum % ThemeCtrl.PAGE_SIZE != 0) {
            pageCount = countNum / ThemeCtrl.PAGE_SIZE + 1;
        }
        refund_Temp_condition.setPageSize(ThemeCtrl.PAGE_SIZE);
        refund_Temp_condition.setOffset(0);

        //订单列表
        List<RefundTemp> refundTempList = refundService.getRefundOrderPage(refund_Temp_condition);
        Logger.error(refundTempList.toString());
        List<Object[]> resultList = new ArrayList<>();
        for (RefundTemp refundTemp : refundTempList) {
            Object[] object = new Object[7];
            object[0] = refundTemp.getId();
            object[1] = refundTemp.getOrderId();
            object[2] = refundTemp.getUserId();
            //手机号码
            if (refundTemp.getUserId() != null) {
                ID userInfo = idService.getID(Integer.parseInt(refundTemp.getUserId().toString()));
                object[3] = userInfo.getPhoneNum();
            } else {
                object[3] = "";
            }
            if (refundTemp.getCreateAt() != null) {
                object[4] = refundTemp.getCreateAt().toString().substring(0,16);
            } else {
                object[4] = "";
            }

            switch (refundTemp.getState()) {
                case "":
                    object[5] = "";
                    break;
                case "I":
                    object[5] = "申请中";
                    break;
                case "A":
                    object[5] = "同意退货";
                    break;
                case "R":
                    object[5] = "拒绝退货";
                    break;
                case "Y":
                    object[5] = "退款成功";
                    break;
                case "N":
                    object[5] = "退款失败";
                    break;
                default:
                    object[5] = "";
            }
            object[6] = refundTemp.getReason();
            resultList.add(object);
        }

        return ok(views.html.order.refund.render(lang, ThemeCtrl.PAGE_SIZE, countNum, pageCount, resultList, (User) ctx().args.get("user")));

    }

    /**
     * 退款申请Ajax查询       Added by Tiffany Zhu 2016.04.14
     *
     * @param lang
     * @param pageNum
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result refundSearchAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        RefundTemp refund = new RefundTemp();
        if (json.has("refund")) {
            refund = Json.fromJson(json.get("refund"), RefundTemp.class);
        }
        if (json.has("userPhone")) {
            String userPhone = json.get("userPhone").asText();
            if (!userPhone.equals("")) {
                ID userTemp = idService.getIDByPhoneNum(userPhone);
                if (userTemp != null && userTemp.getUserId() != null) {
                    if (refund.getUserId() != null && !(refund.getUserId().toString().equals(userTemp.getUserId().toString()))) {
                        refund.setUserId(Long.valueOf(0));
                    } else {
                        refund.setUserId(Long.valueOf(userTemp.getUserId()));
                    }
                }
            }
        }
        Logger.error("查询条件:" + refund.toString());
        if (pageNum >= 1) {
            //计算从第几条开始取数据
            int offset = (pageNum - 1) * ThemeCtrl.PAGE_SIZE;
            refund.setPageSize(-1);
            refund.setOffset(-1);
            //取总数
            int countNum = refundService.getRefundOrderPage(refund).size();
            //共分几页
            int pageCount = countNum / ThemeCtrl.PAGE_SIZE;

            if (countNum % ThemeCtrl.PAGE_SIZE != 0) {
                pageCount = countNum / ThemeCtrl.PAGE_SIZE + 1;
            }
            refund.setPageSize(ThemeCtrl.PAGE_SIZE);
            refund.setOffset(offset);
            List<RefundTemp> refundTempList = refundService.getRefundOrderPage(refund);
            List<Object[]> resultList = new ArrayList<>();
            for (RefundTemp refundTempTemp : refundTempList) {
                Object[] object = new Object[7];
                object[0] = refundTempTemp.getId();
                object[1] = refundTempTemp.getOrderId();
                if (refundTempTemp.getUserId() != null) {
                    object[2] = refundTempTemp.getUserId();
                } else {
                    object[2] = "";
                }

                //手机号码
                if (refundTempTemp.getUserId() != null) {
                    ID userInfo = idService.getID(Integer.parseInt(refundTempTemp.getUserId().toString()));
                    object[3] = userInfo.getPhoneNum();
                } else {
                    object[3] = "";
                }
                if (refundTempTemp.getCreateAt() != null) {
                    object[4] = refundTempTemp.getCreateAt().toString().substring(0, 16);
                } else {
                    object[4] = "";
                }
                switch (refundTempTemp.getState()) {
                    case "":
                        object[5] = "";
                        break;
                    case "I":
                        object[5] = "申请中";
                        break;
                    case "A":
                        object[5] = "同意退货";
                        break;
                    case "R":
                        object[5] = "拒绝退货";
                        break;
                    case "Y":
                        object[5] = "退款成功";
                        break;
                    case "N":
                        object[5] = "退款失败";
                        break;
                    default:
                        object[5] = "";
                }
                object[6] = refundTempTemp.getReason();
                resultList.add(object);
            }
            //组装返回数据
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("topic", resultList);
            returnMap.put("pageNum", pageNum);
            returnMap.put("countNum", countNum);
            returnMap.put("pageCount", pageCount);
            returnMap.put("pageSize", ThemeCtrl.PAGE_SIZE);
            return ok(Json.toJson(returnMap));
        } else {
            return badRequest();
        }
    }

    /**
     * 退货详情     Added by Tiffany Zhu 2016.04.14
     *
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result refundDetail(String lang, Long id) {
        RefundTemp refundTemp = refundService.getRefundById(id);
        Order order = orderService.getOrderById(refundTemp.getOrderId());
        if ("JD".equals(order.getPayMethod())) {
            order.setPayMethod("京东");
        }
        if ("APAY".equals(order.getPayMethod())) {
            order.setPayMethod("支付宝");
        }
        if ("WEIXIN".equals(order.getPayMethod())) {
            order.setPayMethod("微信");
        }

        //订单状态
        //当前时间减去24小时
        Timestamp time = new Timestamp(System.currentTimeMillis() - 1 * 24 * 3600 * 1000L);
        if (order.getOrderCreateAt().before(time) && "I".equals(order.getOrderStatus())) {
            order.setOrderStatus("订单已超时");
        } else {
            if ("I".equals(order.getOrderStatus())) {
                order.setOrderStatus("未支付");
            }
            if ("S".equals(order.getOrderStatus())) {
                order.setOrderStatus("支付成功");
            }
            if ("C".equals(order.getOrderStatus())) {
                order.setOrderStatus("订单取消");
            }
            if ("F".equals(order.getOrderStatus())) {
                order.setOrderStatus("支付失败");
            }
            if ("R".equals(order.getOrderStatus())) {
                order.setOrderStatus("已签收");
            }
            if ("D".equals(order.getOrderStatus())) {
                order.setOrderStatus("已发货");
            }
            if ("J".equals(order.getOrderStatus())) {
                order.setOrderStatus("拒收");
            }
            if ("N".equals(order.getOrderStatus())) {
                order.setOrderStatus("已删除");
            }
            if ("T".equals(order.getOrderStatus())) {
                order.setOrderStatus("已退款");
            }
            if ("PI".equals(order.getOrderStatus())) {
                order.setOrderStatus("拼购未支付");
            }
            if (("PS").equals(order.getOrderStatus())) {
                order.setOrderStatus("拼购支付成功");
            }
            if (("PF").equals(order.getOrderStatus())) {
                order.setOrderStatus("拼团失败未退款");
            }
        }
        List<OrderLine> orderLineList = orderLineService.getLineByOrderId(order.getOrderId());
        List<Object[]> resultOrderLineList = new ArrayList<>();
        for (OrderLine orderLine : orderLineList) {
            String imgUrl = Json.parse(orderLine.getSkuImg()).get("url").asText();
            Object[] object = new Object[8];
            object[0] = orderLine.getSkuTitle();    //名称
            object[1] = imgUrl;  //图片url
            object[2] = orderLine.getSkuSize(); //尺码
            object[3] = orderLine.getSkuColor();//颜色
            object[4] = orderLine.getPrice();   //价格
            object[5] = orderLine.getAmount();  //数量
            object[6] = 0;                      //优惠
            BigDecimal amount = new BigDecimal(orderLine.getAmount());
            object[7] = orderLine.getPrice().multiply(amount);//总价

            resultOrderLineList.add(object);

        }
        OrderShip orderShip = orderShipService.getShipByOrderId(refundTemp.getOrderId());
        ID userInfo = new ID();
        if (refundTemp.getUserId() != null) {
            userInfo = idService.getID(Integer.parseInt(refundTemp.getUserId().toString()));
        }

        Logger.error("支付公司返回码:" + refundTemp.getPgCode());
        Logger.error("支付公司消息:" + refundTemp.getPgMessage());
        return ok(views.html.order.refundDetail.render(lang, refundTemp, order, resultOrderLineList, userInfo, orderShip, ThemeCtrl.IMAGE_URL, (User) ctx().args.get("user")));
    }


    /**
     * 处理退货申请       Added by Tiffany Zhu 2016.04.14
     *
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result refundDeal(String lang) {
        JsonNode json = request().body().asJson();
        Long id = json.get("refundId").asLong();
        String refuseReason = json.get("reasonContent").asText();
        String refundState = json.get("response").asText();
        RefundTemp refundTemp = refundService.getRefundById(id);
        Refund refund = refundService.getRefundServiceById(id);

        refundTemp.setState(refundState);
        refund.setState(refundState);
        if (refundState.equals("R")) {
            refundTemp.setRejectReason(refuseReason);
            refund.setRejectReason(refuseReason);
        }

        system.actorSelection(configuration.getString("shopping.refundActor")).tell(refund, ActorRef.noSender());

        refundService.updRefund(refundTemp);
        return ok("success");

    }

    /**
     * 已签收订单列表      Added by Tiffany Zhu 2016.04.15
     *
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result signedOrderList(String lang) {
        List<Order> orderList = orderService.getSignedOrders();
        List<Order> resultList = new ArrayList<>();
        for (Order order : orderList) {
            switch (order.getPayMethod()) {
                case "JD":
                    order.setPayMethod("京东");
                    break;
                case "APAY":
                    order.setPayMethod("支付宝");
                    break;
                case "WEIXIN":
                    order.setPayMethod("微信");
                    break;
                default:
                    order.setPayMethod("");
            }
            //订单状态
            //当前时间减去24小时
            Timestamp time = new Timestamp(System.currentTimeMillis() - 1 * 24 * 3600 * 1000L);
            if (order.getOrderCreateAt().before(time) && "I".equals(order.getOrderStatus())) {
                order.setOrderStatus("订单已超时");
            } else {
                switch (order.getOrderStatus()) {
                    case "I":
                        order.setOrderStatus("未支付");
                        break;
                    case "S":
                        order.setOrderStatus("支付成功");
                        break;
                    case "C":
                        order.setOrderStatus("订单取消");
                        break;
                    case "F":
                        order.setOrderStatus("支付失败");
                        break;
                    case "R":
                        order.setOrderStatus("已签收");
                        break;
                    case "D":
                        order.setOrderStatus("已发货");
                        break;
                    case "J":
                        order.setOrderStatus("拒收");
                        break;
                    case "N":
                        order.setOrderStatus("已删除");
                        break;
                    case "T":
                        order.setOrderStatus("已退款");
                        break;
                    case "PI":
                        order.setOrderStatus("拼购未支付");
                        break;
                    case "PS":
                        order.setOrderStatus("拼购支付成功");
                        break;
                    case "PF":
                        order.setOrderStatus("拼团失败未退款");
                        break;
                    default:
                        order.setOrderStatus("");
                }
            }
            resultList.add(order);
        }
        return ok(views.html.order.signedOrders.render(lang, resultList, (User) ctx().args.get("user")));
    }

    /**
     * 确认收货     Added by Tiffany Zhu 2016.04.15
     *
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderConfirmReceive(String lang) {
        JsonNode json = request().body().asJson();
        Long ids[] = new Long[json.size()];
        for (int i = 0; i < json.size(); i++) {
            ids[i] = (json.get(i)).asLong();
        }
        orderService.orderConfirmReceive(ids);
        return ok("success");
    }
}

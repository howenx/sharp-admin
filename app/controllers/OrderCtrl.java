package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.*;
import entity.order.Order;
import entity.order.OrderLine;
import entity.order.OrderShip;
import entity.order.OrderSplit;
import filters.UserAuth;
import order.GetLogistics;
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
import java.util.*;

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




    /**
     * 订单列表     Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderList(String lang){
        Order order_temp = new Order();
        order_temp.setPageSize(-1);
        order_temp.setOffset(-1);

        int countNum = orderService.getOrdersAll().size();
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;
        if(countNum%ThemeCtrl.PAGE_SIZE != 0){
            pageCount =  countNum/ThemeCtrl.PAGE_SIZE + 1;
        }
        order_temp.setPageSize(ThemeCtrl.PAGE_SIZE);
        order_temp.setOffset(0);

        //订单列表
        List<Object[]> orList = new ArrayList<>();
        List<Order> orderList = orderService.getOrderPage(order_temp);
        for(Order order : orderList){
            Object[] object = new Object[10];
            object[0] = order.getOrderId();
            object[1] = order.getUserId();
            object[2] = order.getOrderCreateAt();
            object[3] = order.getPayTotal();
            //支付方式
            if("JD".equals(order.getPayMethod())) {
                object[4] =  "京东";
            }
            if("APAY".equals(order.getPayMethod())) {
                object[4] =  "支付宝";
            }
            if("WEIXIN".equals(order.getPayMethod())) {
                object[4] =  "微信";
            }
            //订单状态
            //当前时间减去24小时
            Timestamp time = new Timestamp(System.currentTimeMillis() - 1*24*3600*1000L);
            if(order.getOrderCreateAt().before(time) && "I".equals(order.getOrderStatus())){
                object[5] = "订单已超时";
            }
            else{
                if("I".equals(order.getOrderStatus())){
                    object[5] = "未支付";
                }
                if("S".equals(order.getOrderStatus())){
                    object[5] = "支付成功";
                }
                if("C".equals(order.getOrderStatus())){
                    object[5] = "订单取消";
                }
                if("F".equals(order.getOrderStatus())){
                    object[5] = "支付失败";
                }
                if("R".equals(order.getOrderStatus())){
                    object[5] = "已签收";
                }
                if("D".equals(order.getOrderStatus())){
                    object[5] = "已发货";
                }
                if("J".equals(order.getOrderStatus())){
                    object[5] = "拒收";
                }
                if ("N".equals(order.getOrderStatus())) {
                    object[5] =  "已删除";
                }
                if ("T".equals(order.getOrderStatus())) {
                    object[5] =  "已退款";
                }
                if ("PI".equals(order.getOrderStatus())) {
                    object[5] =  "拼购未支付";
                }
                if (("PS").equals(order.getOrderStatus())) {
                    object[5] =  "拼购支付成功";
                }
                if (("PF").equals(order.getOrderStatus())) {
                    object[5] =  "拼团失败未退款";
                }
            }
            //手机号码
            ID userInfo = idService.getID(Integer.parseInt(order.getUserId().toString()));
            object[6] = userInfo.getPhoneNum();
            //订单类型
            if(order.getOrderType() == 1){
                object[7] = "普通";
            }
            if(order.getOrderType() == 2){
                object[7] = "拼购";
            }
            //拼购团ID
            object[8] = order.getPinActiveId();
            //订单状态
            object[9] = order.getOrderStatus();

            orList.add(object);

        }
        return ok(views.html.order.ordersearch.render(lang,ThemeCtrl.PAGE_SIZE,countNum,pageCount,orList,(User) ctx().args.get("user")));

    }

    /**
     * 订单Ajax查询     Added by Tiffany Zhu
     * @param lang
     * @param pageNum
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderSearchAjax(String lang,int pageNum){
        JsonNode json = request().body().asJson();
        Order order = new Order();
        if(json.has("order")){
            order = Json.fromJson(json.get("order"),Order.class);
        }
        if(json.has("userPhone")){
            String userPhone = json.get("userPhone").toString();
            userPhone = userPhone.substring(1,userPhone.length()-1);
            if(!userPhone.equals("")){
                ID userTemp = idService.getIDByPhoneNum(userPhone);
                //Logger.error(userTemp.toString());
                if(userTemp != null && userTemp.getUserId() != null){
                    if(order.getUserId() != null && !(order.getUserId().toString().equals(userTemp.getUserId().toString()))){
                        order.setUserId(Long.valueOf(0));
                    }else{
                        order.setUserId(Long.valueOf(userTemp.getUserId()));
                    }
                }
            }
        }
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            order.setPageSize(-1);
            order.setOffset(-1);
            //取总数
            int countNum = orderService.getOrderPage(order).size();
            //共分几页
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
            order.setPageSize(ThemeCtrl.PAGE_SIZE);
            order.setOffset(offset);
            List<Order> orderList = orderService.getOrderPage(order);
            List<Object> resultList = new ArrayList<>();
            for(Order orderTemp: orderList){
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
                object[6] = null!=userInfo?userInfo.getPhoneNum():"";
                //object[6] = userInfo.getPhoneNum();
                //订单类型
                if(orderTemp.getOrderType() == 1){
                    object[7] = "普通";
                }
                if(orderTemp.getOrderType() == 2){
                    object[7] = "拼购";
                }
                //拼购团ID
                if(orderTemp.getPinActiveId() != null){
                    object[8] = orderTemp.getPinActiveId();
                }else{
                    object[8] = "";
                }
                //订单状态
                object[9] = orderTemp.getOrderStatus();

                resultList.add(object);
            }
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",resultList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",ThemeCtrl.PAGE_SIZE);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 订单详情     Added by Tiffany Zhu
     * @param lang
     * @param id
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderDetail(String lang,Long id){
        //获取订单
        Order order = orderService.getOrderById(id);
        Object[] orderArray = new Object[9];
        orderArray[0] = order.getOrderId();       //订单Id
        orderArray[1] = order.getOrderCreateAt(); //订单创建时间
        orderArray[2] = order.getPayTotal();      //订单总费用
        orderArray[3] = order.getDiscount();      //优惠
        orderArray[4] = order.getTotalFee();      //支付的总费用
        //支付方式
        if("JD".equals(order.getPayMethod())) {
            orderArray[5] =  "京东";
        }
        if("APAY".equals(order.getPayMethod())) {
            orderArray[5] =  "支付宝";
        }
        if("WEIXIN".equals(order.getPayMethod())) {
            orderArray[5] =  "微信";
        }
        //支付状态
        Timestamp time = new Timestamp(System.currentTimeMillis() - 1*24*3600*1000L);
        if(order.getOrderCreateAt().before(time) && "I".equals(order.getOrderStatus())){
            orderArray[6] = "订单已超时";

        }else{
            if ("I".equals(order.getOrderStatus())) {
                orderArray[6] =  "未支付";
            }
            if ("S".equals(order.getOrderStatus())) {
                orderArray[6] =  "成功";
            }
            if ("C".equals(order.getOrderStatus())) {
                orderArray[6] =  "取消";
            }
            if ("F".equals(order.getOrderStatus())) {
                orderArray[6] =  "失败";
            }
            if ("R".equals(order.getOrderStatus())) {
                orderArray[6] =  "已收货";
            }
            if ("D".equals(order.getOrderStatus())) {
                orderArray[6] = "已发货";
            }
            if ("J".equals(order.getOrderStatus())) {
                orderArray[6] =  "拒收";
            }
            if ("N".equals(order.getOrderStatus())) {
                orderArray[6] =  "已删除";
            }
            if ("T".equals(order.getOrderStatus())) {
                orderArray[6] =  "已退款";
            }
            if ("PI".equals(order.getOrderStatus())) {
                orderArray[6] =  "拼购未支付";
            }
            if (("PS").equals(order.getOrderStatus())) {
                orderArray[6] =  "拼购成功";
            }
            if (("PF").equals(order.getOrderStatus())) {
                orderArray[6] =  "拼团失败未退款";
            }
        }
        orderArray[7] = order.getShipFee();     //邮费
        orderArray[8] = order.getPostalFee();   //行邮税
        //获取订单收货信息
        OrderShip orderShip = orderShipService.getShipByOrderId(id);
        //遮挡身份证号中间8位
        if(orderShip.getDeliveryCardNum() != null && !orderShip.getDeliveryCardNum().equals("")){
            String subCardNum = orderShip.getDeliveryCardNum().substring(6,14);
            String rstCardNum = orderShip.getDeliveryCardNum().replace(subCardNum,"********");
            orderShip.setDeliveryCardNum(rstCardNum);
        }
        //获取子订单
        List<OrderSplit> orderSplitList = orderSplitService.getSplitByOrderId(id);
        //返回的结果集
        List<List<List<Object[]>>> subOrdersAll = new ArrayList<>();
        //子订单序号
        int subOrderNum = 0;
        for(OrderSplit orderSplit : orderSplitList){
            //含有报关和产品的子订单
            List<List<Object[]>>  subOrderList = new ArrayList<>();
            //子订单基本信息
            List<Object[]> subOrderPart1 = new ArrayList<>();
            Object[] object1 = new Object[10];
            object1[0] = orderSplit.getSplitId();   //子订单编号
            //子订单报关状态
            if("I".equals(orderSplit.getState())){
                object1[1] = "初始化";
            }
            if("Y".equals(orderSplit.getState())){
                object1[1] = "报关成功";
            }
            if("N".equals(orderSplit.getState())){
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
            for(OrderLine orderLine : orderLineList){
                Object[] object2 = new Object[8];
                Item item = service.getItem(orderLine.getItemId());
                object2[0] = item.getItemTitle();    //名称
                JsonNode urlJson = Json.parse(orderLine.getSkuImg());
                String url = urlJson.get("url").toString();
                url = url.substring(1,url.length()-1);
                object2[1] = url;  //图片url
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
            //subOrderList.add(GetLogistics.sendGet(""));

            //全部的子订单信息
            subOrdersAll.add(subOrderList);
        }

        //用户信息
        ID ClientUser = idService.getID(Integer.parseInt(order.getUserId().toString()));
        Object[] userObject = new Object[2];
        userObject[0] = order.getUserId();
        userObject[1] = ClientUser.getPhoneNum();
        return ok(views.html.order.orderdetail.render(lang,orderArray,orderShip,subOrdersAll,ThemeCtrl.IMAGE_URL,userObject,(User) ctx().args.get("user")));
    }

    /**
     * 取消订单     Added by Tiffany Zhu 2016.01.04
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderCancel(String lang){
        JsonNode json = request().body().asJson();
        Logger.error(json.toString());
        Long ids[] = new Long[json.size()];
        for(int i=0;i<json.size();i++){
            ids[i] = (json.get(i)).asLong();
            Logger.error(ids[i].toString());
        }
        orderService.orderCancel(ids);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 未支付超时订单列表 Added by Tiffany Zhu 2016.01.07
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result unpaidOrders(String lang){
        List<Object[]> orList = new ArrayList<>();
        List<Order> orderList = orderService.getOutTimeOrders();
        for(Order order : orderList) {
            Object[] object = new Object[6];
            Logger.error(order.toString());
            Logger.error(order.getOrderId().toString());
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
        return ok(views.html.order.outTimeUnpaidOrders.render(lang,orList,(User) ctx().args.get("user")));
    }

    /**
     * 获取物流信息   Added by Tiffany Zhu 2016.03.29
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result getLogistics(String lang){
        JsonNode json = request().body().asJson();
        String nu = json.asText();
        String show = "0";
        String order = "";
        String logisticsJson = GetLogistics.sendGet(nu,show,order);
        return ok(Json.toJson(logisticsJson));
    }

}

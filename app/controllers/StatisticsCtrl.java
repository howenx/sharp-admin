package controllers;

import domain.User;
import domain.order.Order;
import domain.order.OrderLine;
import domain.statistics.SID;
import filters.UserAuth;
import org.apache.commons.collections.map.HashedMap;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.OrderLineService;
import service.OrderService;
import service.OrderSplitService;
import service.SIDService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class StatisticsCtrl extends Controller {

    @Inject
    private SIDService sidService;

    @Inject
    private OrderService orderService;

    @Inject
    private OrderLineService orderLineService;

    @Inject
    private OrderSplitService orderSplitService;

//    @Inject
//    private SOrderService sOrderService;
//
//    @Inject
//    private SOrderLineService sOrderLineService;

    /**
     * 首页统计数据       停止使用    Modified by Tiffany Zhu 2016.06.24
     * @return views
     */
//    @Security.Authenticated(UserAuth.class)
//    public Result summary(String lang) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        //当前日期
//        Date today = new Date();
//        String stoday = sdf.format(new Timestamp(today.getTime()));
//        //昨天日期
//        Calendar yestoday = Calendar.getInstance();
//        yestoday.setTime(today);
//        yestoday.set(Calendar.DATE, yestoday.get(Calendar.DATE)-1);
//        String syestoday = sdf.format(yestoday.getTime());
//        //根据当前日期查询统计表今日新增用户userNNum
//        SID sid = new SID();
//        sid.setsDate(stoday);
//        List<SID> sidList = sidService.getSIDBy(sid);
//        Integer userNum = sidList.size();
//        //根据当前日期查询统计表今日新增订单orderNum
//        SOrder sOrder = new SOrder();
//        sOrder.setsDate(stoday);
//        List<SOrder> sOrderList = sOrderService.getSOrderBy(sOrder);
//        Integer orderNum = sOrderList.size();
//        //根据当前日期查询今日订单的销售量soldNum
//        Integer soldNum = 0;
//        //销售额
//        BigDecimal salesTotalFee = new BigDecimal(0.00);
//        for(SOrder so : sOrderList) {
//            Long orderId = so.getOrderId();
//            SOrderLine sOrderLine = new SOrderLine();
//            sOrderLine.setOrderId(orderId);
//            List<SOrderLine> sOrderLineList = sOrderLineService.getSOrderLineBy(sOrderLine);
//            for(SOrderLine sol : sOrderLineList) {
//                soldNum += sol.getAmount();
//            }
//            BigDecimal payTotal = so.getPayTotal();
//            salesTotalFee = salesTotalFee.add(payTotal);
//        }
//        //昨日订单
//        SOrder sOrder1 = new SOrder();
//        sOrder1.setsDate(syestoday);
//        List<SOrder> sOrderList1 = sOrderService.getSOrderBy(sOrder1);
//        Integer yes_order = sOrderList1.size();
//        //超时订单
//        SOrder sOrder2 = new SOrder();
//        sOrder2.setOrderStatus("I");
//        List<SOrder> sOrderList2 = sOrderService.getSOrderBy(sOrder2);
//        Integer unPaidOrder = sOrderList2.size();
//        //已发货订单
//        SOrder sOrder3 = new SOrder();
//        sOrder3.setOrderStatus("D");
//        List<SOrder> sOrderList3 = sOrderService.getSOrderBy(sOrder3);
//        Integer deliverOrder = sOrderList3.size();
////        Logger.error("新增用户:"+userNum+", 新增订单:"+orderNum+", 销售量:"+soldNum+", 销售额:"+salesTotalFee);
//        return ok(views.html.summary.summary.render(lang, (User) ctx().args.get("user"), userNum, orderNum, yes_order, unPaidOrder,deliverOrder,soldNum));
//    }


    /**
     * 首页统计数据     Added by Tiffany Zhu 2016.06.24   Modified By Sunny Wu 2016.09.07
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result summary(String lang) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //当前日期
        Date today = new Date();
        String stoday = sdf.format(new Timestamp(today.getTime()));
        //昨天日期
        Calendar yestoday = Calendar.getInstance();
        yestoday.setTime(today);
        yestoday.set(Calendar.DATE, yestoday.get(Calendar.DATE)-1);
        String syestoday = sdf.format(yestoday.getTime());

        //返回数据
        Map<String, Object> returnMap = new HashMap<>();

        //今日新用户
        SID sid = new SID();
        sid.setsDate(stoday);
        List<SID> sidList = sidService.getSIDBy(sid);
        Integer userNum = sidList.size();

        //今日订单
        Order order = new Order();
        order.setOrderCreateAt(new Timestamp(today.getTime()));
        List<Order> todayOrders = orderService.getOrder(order);
        int todayOrderCount = todayOrders.size();  //今日新订单数
        //今日付款订单(根据订单状态过滤)
        List<Order> todayPayOrders = new ArrayList<>();
        //今日销售额
        BigDecimal todayIncome = new BigDecimal(0.00);
        for(Order o : todayOrders) {
            if (!"I".equals(o.getOrderStatus()) && !"C".equals(o.getOrderStatus()) && !"PI".equals(o.getOrderStatus())) {
                todayPayOrders.add(o);
            }
            if ("S".equals(o.getOrderStatus())) {
                BigDecimal payTotal = orderSplitService.getSplitByOrderId(o.getOrderId()).get(0).getTotalPayFee();//订单总支付费用
                todayIncome = todayIncome.add(payTotal);
            }
        }
        int todayPayOrdersCount = todayPayOrders.size();  //今日付款订单数

        //昨日订单
        Order yesOrder = new Order();
        yesOrder.setOrderCreateAt(new Timestamp(yestoday.getTimeInMillis()));
        List<Order> yesOrders = orderService.getOrder(yesOrder);
        int yesOrderCount = yesOrders.size();//昨日订单数
        //昨日付款订单(根据订单状态过滤)
        List<Order> yesPayOrders = new ArrayList<>();
        //昨日销售额
        BigDecimal yesIncome = new BigDecimal(0.00);
        for(Order o : yesOrders) {
            if (!"I".equals(o.getOrderStatus()) && !"C".equals(o.getOrderStatus()) && !"PI".equals(o.getOrderStatus())) {
                yesPayOrders.add(o);
            }
            if ("S".equals(o.getOrderStatus())) {
                BigDecimal payTotal = orderSplitService.getSplitByOrderId(o.getOrderId()).get(0).getTotalPayFee();//订单总支付费用
                yesIncome = yesIncome.add(payTotal);
            }
        }
        int yesPayOrdersCount = yesPayOrders.size();  //昨日付款订单数

        //2天前订单
        List<Order> formerOrders = orderService.getFormerOrder(yesOrder);
        int formerOrdersCount = formerOrders.size();//2天前订单数
        //2天前付款订单(根据订单状态过滤)
        List<Order> formerPayOrders = new ArrayList<>();
        //2天前销售额
        BigDecimal formerIncome = new BigDecimal(0.00);
        for(Order o : formerOrders) {
            if (!"I".equals(o.getOrderStatus()) && !"C".equals(o.getOrderStatus()) && !"PI".equals(o.getOrderStatus())) {
                formerPayOrders.add(o);
            }
            if ("S".equals(o.getOrderStatus())) {
                BigDecimal payTotal = orderSplitService.getSplitByOrderId(o.getOrderId()).get(0).getTotalPayFee();//订单总支付费用
                formerIncome = formerIncome.add(payTotal);
            }
        }
        int formerPayOrdersCount = formerPayOrders.size();  //2天前付款订单数

        //今日普通商品订单数,付款订单数,销售额
        List<Order> todaySkuOrders = new ArrayList<>();     //今日普通商品订单
        List<Order> todayPaySkuOrders = new ArrayList<>(); //今日普通商品付款订单
        BigDecimal todaySkuIncome = new BigDecimal(0.00);  //今日普通商品销售额
        for(Order o : todayOrders) {
            if (o.getOrderType()==1) {
                todaySkuOrders.add(o);
            }
            if (o.getOrderType()==1 && !"I".equals(o.getOrderStatus()) && !"C".equals(o.getOrderStatus()) && !"PI".equals(o.getOrderStatus())) {
                todayPaySkuOrders.add(o);
            }
            if (o.getOrderType()==1 && "S".equals(o.getOrderStatus())) {
                BigDecimal payTotal = orderSplitService.getSplitByOrderId(o.getOrderId()).get(0).getTotalPayFee();//订单总支付费用
                todaySkuIncome = todaySkuIncome.add(payTotal);
            }
        }
        int todaySkuOrdersCount = todaySkuOrders.size();  //今日普通商品订单数
        int todayPaySkuOrdersCount = todayPaySkuOrders.size();  //今日普通商品付款订单数

        //今日拼购商品订单数,付款订单数,销售额
        List<Order> todayPinOrders = new ArrayList<>();    //今日拼购商品订单
        List<Order> todayPayPinOrders = new ArrayList<>(); //今日拼购商品付款订单
        BigDecimal todayPinIncome = new BigDecimal(0.00);  //今日拼购商品销售额
        for(Order o : todayOrders) {
            if (o.getOrderType()==2) {
                todayPinOrders.add(o);
            }
            if (o.getOrderType()==2 && !"I".equals(o.getOrderStatus()) && !"C".equals(o.getOrderStatus()) && !"PI".equals(o.getOrderStatus())) {
                todayPayPinOrders.add(o);
            }
            if (o.getOrderType()==2 && "S".equals(o.getOrderStatus())) {
                BigDecimal payTotal = orderSplitService.getSplitByOrderId(o.getOrderId()).get(0).getTotalPayFee();//订单总支付费用
                todayPinIncome = todayPinIncome.add(payTotal);
            }
        }
        int todayPinOrdersCount = todayPinOrders.size();  //今日拼购商品订单数
        int todayPayPinOrdersCount = todayPayPinOrders.size();  //今日拼购商品付款订单数

        //昨日普通商品订单数,付款订单数,销售额
        List<Order> yesSkuOrders = new ArrayList<>();     //昨日普通商品订单
        List<Order> yesPaySkuOrders = new ArrayList<>(); //昨日普通商品付款订单
        BigDecimal yesSkuIncome = new BigDecimal(0.00);  //昨日普通商品销售额
        for(Order o : yesOrders) {
            if (o.getOrderType()==1) {
                yesSkuOrders.add(o);
            }
            if (o.getOrderType()==1 && !"I".equals(o.getOrderStatus()) && !"C".equals(o.getOrderStatus()) && !"PI".equals(o.getOrderStatus())) {
                yesPaySkuOrders.add(o);
            }
            if (o.getOrderType()==1 && "S".equals(o.getOrderStatus())) {
                BigDecimal payTotal = orderSplitService.getSplitByOrderId(o.getOrderId()).get(0).getTotalPayFee();//订单总支付费用
                yesSkuIncome = yesSkuIncome.add(payTotal);
            }
        }
        int yesSkuOrdersCount = yesSkuOrders.size();  //昨日普通商品订单数
        int yesPaySkuOrdersCount = yesPaySkuOrders.size();  //昨日普通商品付款订单数

        //昨日拼购商品订单数,付款订单数,销售额
        List<Order> yesPinOrders = new ArrayList<>();     //昨日拼购商品订单
        List<Order> yesPayPinOrders = new ArrayList<>(); //昨日拼购商品付款订单
        BigDecimal yesPinIncome = new BigDecimal(0.00);  //昨日拼购商品销售额
        for(Order o : yesOrders) {
            if (o.getOrderType()==2) {
                yesPinOrders.add(o);
            }
            if (o.getOrderType()==2 && !"I".equals(o.getOrderStatus()) && !"C".equals(o.getOrderStatus()) && !"PI".equals(o.getOrderStatus())) {
                yesPayPinOrders.add(o);
            }
            if (o.getOrderType()==2 && "S".equals(o.getOrderStatus())) {
                BigDecimal payTotal = orderSplitService.getSplitByOrderId(o.getOrderId()).get(0).getTotalPayFee();//订单总支付费用
                yesPinIncome = yesPinIncome.add(payTotal);
            }
        }
        int yesPinOrdersCount = yesPinOrders.size();  //昨日拼购商品订单数
        int yesPayPinOrdersCount = yesPayPinOrders.size();  //昨日拼购商品付款订单数

        //2天前普通商品订单数,付款订单数,销售额
        List<Order> formerSkuOrders = new ArrayList<>();  //2天前普通商品订单
        List<Order> formerPaySkuOrders = new ArrayList<>(); //2天前普通商品付款订单
        BigDecimal formerSkuIncome = new BigDecimal(0.00);  //2天前普通商品销售额
        for(Order o : formerOrders) {
            if (o.getOrderType()==1) {
                formerSkuOrders.add(o);
            }
            if (o.getOrderType()==1 && !"I".equals(o.getOrderStatus()) && !"C".equals(o.getOrderStatus()) && !"PI".equals(o.getOrderStatus())) {
                formerPaySkuOrders.add(o);
            }
            //两天前的销售额包含的订单状态有  成功,收货,发货,拒收,删除
            if (o.getOrderType()==1 && ("S".equals(o.getOrderStatus())||"R".equals(o.getOrderStatus())||"D".equals(o.getOrderStatus())||"J".equals(o.getOrderStatus())||"N".equals(o.getOrderStatus()))) {
                BigDecimal payTotal = orderSplitService.getSplitByOrderId(o.getOrderId()).get(0).getTotalPayFee();//订单总支付费用
                formerSkuIncome = formerSkuIncome.add(payTotal);
            }
        }
        int formerSkuOrdersCount = formerSkuOrders.size();  //2天前拼购商品订单数
        int formerPaySkuOrdersCount = formerPaySkuOrders.size();  //2天前拼购商品付款订单数

        //2天前拼购商品订单数,付款订单数,销售额
        List<Order> formerPinOrders = new ArrayList<>();  //2天前拼购商品订单
        List<Order> formerPayPinOrders = new ArrayList<>(); //2天前拼购商品付款订单
        BigDecimal formerPinIncome = new BigDecimal(0.00);  //2天前拼购商品销售额
        for(Order o : formerOrders) {
            if (o.getOrderType()==2) {
                formerPinOrders.add(o);
            }
            if (o.getOrderType()==2 && !"I".equals(o.getOrderStatus()) && !"C".equals(o.getOrderStatus()) && !"PI".equals(o.getOrderStatus())) {
                formerPayPinOrders.add(o);
            }
            //两天前的销售额包含的订单状态有  成功,收货,发货,拒收,删除
            if (o.getOrderType()==2 && ("S".equals(o.getOrderStatus())||"R".equals(o.getOrderStatus())||"D".equals(o.getOrderStatus())||"J".equals(o.getOrderStatus())||"N".equals(o.getOrderStatus()))) {
                BigDecimal payTotal = orderSplitService.getSplitByOrderId(o.getOrderId()).get(0).getTotalPayFee();//订单总支付费用
                formerPinIncome = formerPinIncome.add(payTotal);
            }
        }
        int formerPinOrdersCount = formerPinOrders.size();  //2天前拼购商品订单数
        int formerPayPinOrdersCount = formerPayPinOrders.size();  //2天前拼购商品付款订单数

        //组装返回数据
        returnMap.put("userNum", userNum);

        returnMap.put("todayOrderCount", todayOrderCount);
        returnMap.put("todayPayOrdersCount", todayPayOrdersCount);
        returnMap.put("todayIncome", todayIncome);

        returnMap.put("yesOrderCount", yesOrderCount);
        returnMap.put("yesPayOrdersCount", yesPayOrdersCount);
        returnMap.put("yesIncome", yesIncome);

        returnMap.put("formerOrdersCount", formerOrdersCount);
        returnMap.put("formerPayOrdersCount", formerPayOrdersCount);
        returnMap.put("formerIncome", formerIncome);

        returnMap.put("todaySkuOrdersCount", todaySkuOrdersCount);
        returnMap.put("todayPaySkuOrdersCount", todayPaySkuOrdersCount);
        returnMap.put("todaySkuIncome", todaySkuIncome);

        returnMap.put("todayPinOrdersCount", todayPinOrdersCount);
        returnMap.put("todayPayPinOrdersCount", todayPayPinOrdersCount);
        returnMap.put("todayPinIncome", todayPinIncome);

        returnMap.put("yesSkuOrdersCount", yesSkuOrdersCount);
        returnMap.put("yesPaySkuOrdersCount", yesPaySkuOrdersCount);
        returnMap.put("yesSkuIncome", yesSkuIncome);

        returnMap.put("yesPinOrdersCount", yesPinOrdersCount);
        returnMap.put("yesPayPinOrdersCount", yesPayPinOrdersCount);
        returnMap.put("yesPinIncome", yesPinIncome);

        returnMap.put("formerSkuOrdersCount", formerSkuOrdersCount);
        returnMap.put("formerPaySkuOrdersCount", formerPaySkuOrdersCount);
        returnMap.put("formerSkuIncome", formerSkuIncome);

        returnMap.put("formerPinOrdersCount", formerPinOrdersCount);
        returnMap.put("formerPayPinOrdersCount", formerPayPinOrdersCount);
        returnMap.put("formerPinIncome", formerPinIncome);

        //超时订单
        List<Order> timeOutOrders = orderService.getOutTimeOrders();
        int timeOutOrderCount = timeOutOrders.size();
        //已发货订单
        Order order2 = new Order();
        order2.setOrderStatus("D");
        List<Order> deliveriedOrders = orderService.getOrder(order2);
        int deliveryCount = deliveriedOrders.size();

        //销售额
        int soldCount = 0;
        BigDecimal salesTotalFee = new BigDecimal(0.00);
        for(Order so : todayOrders) {
            List<OrderLine> orderLineList = orderLineService.getLineByOrderId(so.getOrderId());
            for(OrderLine sol : orderLineList) {
                soldCount = soldCount + sol.getAmount();
            }
            BigDecimal payTotal = so.getPayTotal();
            salesTotalFee = salesTotalFee.add(payTotal);
        }

        //返回数据:   今日订单数, 今日付款订单数, 今日销售额, 昨日订单数, 昨日付款订单数, 昨日销售额,
        return ok(views.html.summary.summary.render(lang,(User) ctx().args.get("user"),returnMap));
    }
}

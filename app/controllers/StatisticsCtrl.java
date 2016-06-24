package controllers;

import com.google.inject.Inject;
import domain.User;
import domain.order.Order;
import domain.order.OrderLine;
import domain.statistics.SID;
import domain.statistics.SOrder;
import domain.statistics.SOrderLine;
import filters.UserAuth;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
     * 首页统计数据     Added by Tiffany Zhu 2016.06.24
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

        //今日新用户
        SID sid = new SID();
        sid.setsDate(stoday);
        List<SID> sidList = sidService.getSIDBy(sid);
        Integer userNum = sidList.size();

        //今日新订单
        Order order = new Order();
        order.setOrderCreateAt(new Timestamp(today.getTime()));
        List<Order> todayOrders = orderService.getOrder(order);
        int todayOrderCount = todayOrders.size();
        //昨日订单
        order.setOrderCreateAt(new Timestamp(yestoday.getTimeInMillis()));
        List<Order> yesterdayOrders = orderService.getOrder(order);
        int yesterdayOrderCount = yesterdayOrders.size();
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

        return ok(views.html.summary.summary.render(lang,(User) ctx().args.get("user"),userNum,todayOrderCount,yesterdayOrderCount,timeOutOrderCount,deliveryCount,soldCount));
    }
}

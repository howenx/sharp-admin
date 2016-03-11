package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import entity.order.Order;
import entity.order.OrderSplit;
import middle.ShopOrderMiddle;
import modules.LevelFactory;
import modules.NewScheduler;
import play.Logger;
import service.OrderService;
import service.OrderSplitService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * 往ERP推送订单的Actor
 * Created by Sunny Wu on 16/3/7.
 * kakao china.
 */
public class ShopOrderPushActor extends AbstractActor{

    @Inject
    private OrderService orderService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    private NewScheduler newScheduler;

    @Inject
    private LevelFactory levelFactory;

    @Inject
    @Named("salesOrderQueryActor")
    private ActorRef salesOrderQueryActor;

    @Inject
    public ShopOrderPushActor(ShopOrderMiddle shopOrderMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            Order order = orderService.getOrderById(orderId);
            String orderStatus = order.getOrderStatus();
            //支付成功的订单
            if ("S".equals(orderStatus) || "PS".equals(orderStatus)) {
                List<OrderSplit> orderSplitList = orderSplitService.getSplitByOrderId(orderId);
                for(OrderSplit orderSplit : orderSplitList) {
                    Long splitId = orderSplit.getSplitId();
                    String shopOrderNo = shopOrderMiddle.shopOrderPush(splitId);
                    Logger.debug("order"+shopOrderNo+":push to ERP");
                    //启动scheduler从erp查询订单的状态
//
                }
            }
        }).matchAny(s-> {
            Logger.error("push to ERP error!", s.toString());
            unhandled(s);
        }).build());
    }
}
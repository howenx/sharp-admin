package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import entity.order.Order;
import entity.order.OrderSplit;
import middle.ShopOrderPushMiddle;
import play.Logger;
import service.OrderService;
import service.OrderSplitService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/3/7.
 * kakao china.
 */
public class ShopOrderPushActor extends AbstractActor{

    @Inject
    private OrderService orderService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    public ShopOrderPushActor(ShopOrderPushMiddle shopOrderPushMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            Order order = orderService.getOrderById(orderId);
            String orderStatus = order.getOrderStatus();
            //支付成功的订单
            if ("S".equals(orderStatus) || "PS".equals(orderStatus)) {
                List<OrderSplit> orderSplitList = orderSplitService.getSplitByOrderId(orderId);
                for(OrderSplit orderSplit : orderSplitList) {
                    Long splitId = orderSplit.getSplitId();
                    shopOrderPushMiddle.shopOrderPush(splitId);
                    Logger.debug("order"+splitId+":push to ERP");
                }
            }
        }).matchAny(s-> {
            Logger.error("push to ERP error!", s.toString());
            unhandled(s);
        }).build());
    }
}

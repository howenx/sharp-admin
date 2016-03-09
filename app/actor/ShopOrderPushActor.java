package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import middle.ShopOrderPushMiddle;
import play.Logger;

import javax.inject.Inject;

/**
 * Created by Sunny Wu on 16/3/7.
 * kakao china.
 */
public class ShopOrderPushActor extends AbstractActor{

    @Inject
    public ShopOrderPushActor(ShopOrderPushMiddle shopOrderPushMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            shopOrderPushMiddle.shopOrderPush(orderId);
            Logger.debug("order"+orderId+":push to ERP");
        }).matchAny(s-> {
            Logger.error("push to ERP error!", s.toString());
            unhandled(s);
        }).build());
    }
}

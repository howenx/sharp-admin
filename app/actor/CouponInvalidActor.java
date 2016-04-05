package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import entity.Coupons;
import play.Logger;
import service.CouponsService;

import javax.inject.Inject;

/**
 * 优惠券自动失效
 *
 * Created by Sunny Wu on 16/4/5.
 * kakao china.
 */
public class CouponInvalidActor extends AbstractActor {
    @Inject
    CouponsService couponsService;

    public CouponInvalidActor() {
        receive(ReceiveBuilder.match(String.class, coupId -> {
            Coupons coupons = couponsService.getCoupons(coupId);
            coupons.setState("S");
            couponsService.updateCoupons(coupons);
            Logger.debug("coupon "+coupId+" auto invalid");
        }).matchAny(s -> {
            unhandled(s);
            Logger.error("" + s.toString());
        }).build());
    }
}

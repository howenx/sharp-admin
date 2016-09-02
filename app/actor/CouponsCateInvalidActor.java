package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import domain.Coupons;
import domain.CouponsCate;
import play.Logger;
import service.CouponsService;

import javax.inject.Inject;
import java.util.List;

/**
 * 优惠券类别自动失效
 *
 * Created by Sunny Wu on 16/4/5.
 * kakao china.
 */
public class CouponsCateInvalidActor extends AbstractActor {
    @Inject
    CouponsService couponsService;

    public CouponsCateInvalidActor() {
        receive(ReceiveBuilder.match(Long.class, coupCateId -> {
            //优惠券类别类型修改为3(不可用)
            CouponsCate couponsCate = couponsService.getCouponsCate(coupCateId);
            couponsCate.setCouponType(3);
            couponsService.updateCouponsCate(couponsCate);
            //更新该优惠类别的所有优惠券状态为"S"(已失效)
            List<Coupons> couponsList = couponsService.getCouponsByCateId(coupCateId);//查询所有该类别的优惠券
            for(Coupons c : couponsList) {
                c.setState("S");
                couponsService.updateCoupons(c);//更新优惠券状态为失效
            }
            Logger.info("CouponsCate "+coupCateId+" auto invalid");
        }).matchAny(s -> {
            unhandled(s);
            Logger.error("" + s.toString());
        }).build());
    }
}

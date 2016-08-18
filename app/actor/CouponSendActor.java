package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import domain.CouponRec;
import domain.Coupons;
import domain.CouponsCate;
import play.Logger;
import service.CouponsService;

import javax.inject.Inject;

/**
 * 优惠券发放 用户后台手动发放和前端用户主动领取
 * Created by Sunny Wu on 16/8/18.
 * kakao china.
 */
public class CouponSendActor extends AbstractActor {

    @Inject
    CouponsService couponsService;

    public CouponSendActor() {
        receive(ReceiveBuilder.match(CouponRec.class, couponRec -> {
            Logger.error("couponSendActor 接受数据:"+couponRec.toString());
            CouponsCate couponsCate = couponsService.getCouponsCate(couponRec.getCoupCateId());
            Coupons coupons = new Coupons();
            coupons.setLimitQuota(couponsCate.getLimitQuota());
            coupons.setDenomination(couponsCate.getDenomination());
            coupons.setStartAt(couponsCate.getStartAt());
            coupons.setEndAt(couponsCate.getEndAt());
            coupons.setCoupCateNm(couponsCate.getCoupCateNm());
            coupons.setCoupCateId(couponsCate.getCoupCateId());
            coupons.setUserId(couponRec.getUserId());
            coupons.setState("N");
            if (couponRec.getCouponType().equals(couponsCate.getCouponType())) {
                Logger.error("优惠券:"+coupons.toString());
                couponsService.couponsSave(coupons);
            }
        }).matchAny(s -> {
            unhandled(s);
            Logger.error("" + s.toString());
        }).build());
    }

}

package mapper;

import domain.CouponVo;

import java.util.List;

/**
 * Created by Sunny Wu on 16/4/25.
 * kakao china.
 */
public interface CouponVoMapper {

    /**
     * 录入一条优惠券信息
     * @param couponVo 优惠券
     */
    void insertCoupon(CouponVo couponVo);

    /**
     * 更新一条优惠券信息
     * @param couponVo 优惠券
     */
    int updateCoupon(CouponVo couponVo);

    /**
     * 获取优惠券信息
     * @param couponVo 优惠券
     * @return list of coupon
     */
    List<CouponVo> getCoupon(CouponVo couponVo);

    /**
     * 查询所有的优惠券信息
     * @return list of coupon
     */
    List<CouponVo> getAllCoupons();

    /**
     * 分页获取优惠券信息
     * @return list of coupon
     */
    List<CouponVo> getCouponsPage(CouponVo couponVo);
}

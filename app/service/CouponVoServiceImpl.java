package service;

import domain.CouponVo;
import mapper.CouponVoMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/4/25.
 * kakao china.
 */
public class CouponVoServiceImpl implements CouponVoService {

    @Inject
    private CouponVoMapper couponVoMapper;

    /**
     * 录入一条优惠券信息
     * @param couponVo 优惠券
     */
    @Override
    public void insertCoupon(CouponVo couponVo) {
        couponVoMapper.insertCoupon(couponVo);
    }

    /**
     * 更新一条优惠券信息
     * @param couponVo 优惠券
     */
    @Override
    public void updateCoupon(CouponVo couponVo) {
        couponVoMapper.updateCoupon(couponVo);
    }

    /**
     * 获取优惠券信息
     * @param couponVo 优惠券
     * @return list of couponVo
     */
    @Override
    public List<CouponVo> getCoupon(CouponVo couponVo) {
        return couponVoMapper.getCoupon(couponVo);
    }

    /**
     * 查询所有的优惠券信息
     * @return list of coupon
     */
    @Override
    public List<CouponVo> getAllCoupons() {
        return couponVoMapper.getAllCoupons();
    }

    /**
     * 分页获取优惠券信息
     * @return list of coupon
     */
    @Override
    public List<CouponVo> getCouponsPage(CouponVo couponVo) {
        return couponVoMapper.getCouponsPage(couponVo);
    }

}

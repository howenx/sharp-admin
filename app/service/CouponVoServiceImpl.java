package service;

import domain.CouponVo;
import mapper.CouponVoMapper;

import java.util.List;

/**
 * Created by Sunny Wu on 16/4/25.
 * kakao china.
 */
public class CouponVoServiceImpl implements CouponVoService {

//    @Inject   //注释 products 和 coupon库做的修改
    private CouponVoMapper couponVoMapper;

    //--------注释 products 和 coupon库做的修改-------     Modified By Sunny Wu 2016.09.01
    @Override
    public void insertCoupon(CouponVo couponVo) {
    }
    @Override
    public boolean updateCoupon(CouponVo couponVo) {
        return false;
    }
    @Override
    public List<CouponVo> getCoupon(CouponVo couponVo) {
        return null;
    }
    @Override
    public List<CouponVo> getAllCoupons() {
        return null;
    }
    @Override
    public List<CouponVo> getCouponsPage(CouponVo couponVo) {
        return null;
    }
    //--------注释 products 和 coupon库做的修改-------




//    /**
//     * 录入一条优惠券信息
//     * @param couponVo 优惠券
//     */
//    @Override
//    public void insertCoupon(CouponVo couponVo) {
//        couponVoMapper.insertCoupon(couponVo);
//    }
//
//    /**
//     * 更新一条优惠券信息
//     * @param couponVo 优惠券
//     */
//    @Override
//    public boolean updateCoupon(CouponVo couponVo) {
//        if(couponVoMapper.updateCoupon(couponVo) > 0){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    /**
//     * 获取优惠券信息
//     * @param couponVo 优惠券
//     * @return list of couponVo
//     */
//    @Override
//    public List<CouponVo> getCoupon(CouponVo couponVo) {
//        return couponVoMapper.getCoupon(couponVo);
//    }
//
//    /**
//     * 查询所有的优惠券信息
//     * @return list of coupon
//     */
//    @Override
//    public List<CouponVo> getAllCoupons() {
//        return couponVoMapper.getAllCoupons();
//    }
//
//    /**
//     * 分页获取优惠券信息
//     * @return list of coupon
//     */
//    @Override
//    public List<CouponVo> getCouponsPage(CouponVo couponVo) {
//        return couponVoMapper.getCouponsPage(couponVo);
//    }

}

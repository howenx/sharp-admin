package mapper;

import domain.Coupons;
import domain.CouponsCate;

import java.util.List;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public interface CouponsMapper {

    /**
     * 生成一张优惠券
     * @param coupons 优惠券
     */
    int insertCoupons(Coupons coupons);

    /**
     * 更新优惠券
     * @param coupons 优惠券  Added by Sunny Wu 2016.04.05
     */
    void updateCoupons(Coupons coupons);

    /**
     * 由id 获得一条优惠券信息
     * @param coupId 优惠券id
     * @return
     */
    Coupons getCoupons(String coupId);

    /**
     * 获取所有已使用的优惠券信息
     * @return list of Coupons
     */
    List<Coupons> getAllUsedCoupons();

    /**
     * 分页获取所有已使用优惠券信息
     * @param coupons 优惠券
     * @return list of Coupons
     */
    List<Coupons> getUsedCouponsPage(Coupons coupons);

    /**
     * 获取所有的优惠券类别       Added By Sunny Wu 2016.06.27
     * @return list of CouponsCate
     */
    List<CouponsCate> getAllCouponsCate();

    /**
     * 由cateId查询一条优惠券类别信息      Added By Sunny Wu 2016.06.27
     * @param cateId 优惠券类别id
     * @return CouponsCate
     */
    CouponsCate getCouponsCate(Long cateId);

}

package mapper;

import entity.Coupons;

import java.util.List;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public interface CouponsMapper {

    /**
     * 生成一张优惠券
     * @param coupons 优惠券
     */
    void insertCoupons(Coupons coupons);

    /**
     * 由id 获得一条优惠券信息
     * @param coupId 优惠券id
     * @return
     */
    Coupons getCoupons(String coupId);

    /**
     * 获取所有已使用的优惠券信息
     * @return list of CouponsService
     */
    List<Coupons> getAllUsedCoupons();

    /**
     * 分页获取所有已使用优惠券信息
     * @param coupons 优惠券
     * @return list of CouponsService
     */
    List<Coupons> getUsedCouponsPage(Coupons coupons);

}

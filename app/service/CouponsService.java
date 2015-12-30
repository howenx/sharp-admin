package service;

import entity.Coupons;

import java.util.List;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public interface CouponsService {

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
     * 获取所有的优惠券信息
     * @return list of CouponsService
     */
    List<Coupons> getAllCoupons();

    /**
     * 获得一页优惠券信息
     * @param coupons 优惠券
     * @return list of CouponsService
     */
    List<Coupons> getCouponsPage(Coupons coupons);

}

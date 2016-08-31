package service;

import com.fasterxml.jackson.databind.JsonNode;
import domain.Coupons;
import domain.CouponsCate;
import domain.CouponsMap;

import java.util.List;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public interface CouponsService {

    /**
     * 优惠券保存
     * @param coupons
     */
    void couponsSave(Coupons coupons);

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
     * 由coupCateId获取该类别的优惠券信息       Added By Sunny Wu 2016.08.19
     * @param coupCateId 优惠券类别id
     * @return
     */
    List<Coupons> getCouponsByCateId(Long coupCateId);

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
     * 获取所有可后台可发放的优惠券类别       Added By Sunny Wu 2016.06.27
     * @return list of CouponsCate
     */
    List<CouponsCate> getSendCouponsCate();

    /**
     * 获取所有的优惠券类别       Added By Sunny Wu 2016.08.19
     * @return list of CouponsCate
     */
    List<CouponsCate> getAllCouponsCate();

    /**
     * 由cateId查询一条优惠券类别信息      Added By Sunny Wu 2016.06.27
     * @param cateId 优惠券类别id
     * @return CouponsCate
     */
    CouponsCate getCouponsCate(Long cateId);

    /**
     * 新增优惠券类别                  Added by Sunny Wu 2016.08.18
     * @param jsonNode 优惠券类别及优惠券类别映射信息
     */
    void couponsCateSave(JsonNode jsonNode);

    /**
     * 更新优惠券类别
     * @param couponsCate 优惠券类别  Added by Sunny Wu 2016.08.18
     */
    void updateCouponsCate(CouponsCate couponsCate);

    /**
     * 录入一条优惠券类别映射信息        Added by Sunny Wu 2016.08.31
     * @param couponsMap 优惠券类别映射
     * @return
     *
     */
    boolean insertCouponsMap(CouponsMap couponsMap);

    /**
     * 更新一条优惠券类别映射信息        Added by Sunny Wu 2016.08.31
     * @param couponsMap 优惠券类别映射
     * @return
     */
    boolean updateCouponsMap(CouponsMap couponsMap);

    /**
     * 根据优惠券类别获取所有的优惠券类别映射信息    Added by Sunny Wu 2016.08.31
     * @param couponCateId 优惠券类别ID
     * @return list of couponsMap
     */
    List<CouponsMap> getCouponsMapByCateId(Long couponCateId);

}

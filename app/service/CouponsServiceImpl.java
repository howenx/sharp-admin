package service;

import entity.Coupons;
import mapper.CouponsMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class CouponsServiceImpl implements CouponsService {

    @Inject
    private CouponsMapper couponsMapper;

    /**
     * 生成一张优惠券
     * @param coupons 优惠券
     */
    @Override
    public void insertCoupons(Coupons coupons) {
        couponsMapper.insertCoupons(coupons);
    }

    /**
     * 由id 获得一条优惠券信息
     * @param coupId 优惠券id
     * @return
     */
    @Override
    public Coupons getCoupons(String coupId) {
        return couponsMapper.getCoupons(coupId);
    }

    /**
     * 获取所有的优惠券信息
     * @return list of CouponsService
     */
    @Override
    public List<Coupons> getAllCoupons() {
        return couponsMapper.getAllCoupons();
    }

    /**
     * 获得一页优惠券信息
     * @param coupons 优惠券
     * @return list of CouponsService
     */
    @Override
    public List<Coupons> getCouponsPage(Coupons coupons) {
        return couponsMapper.getCouponsPage(coupons);
    }

}

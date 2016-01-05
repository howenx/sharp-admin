package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Coupons;
import mapper.CouponsMapper;
import play.libs.Json;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class CouponsServiceImpl implements CouponsService {

    @Inject
    private CouponsMapper couponsMapper;

    /**
     * 优惠券保存
     * @param json
     */
    @Override
    public void couponsSave(JsonNode json) {
        for(JsonNode jsonNode : json) {
            Coupons coupons = Json.fromJson(jsonNode, Coupons.class);
            Long cateId = coupons.getCateId();
            String coupId = coupons.GetCode(cateId, 8);
            coupons.setCoupId(coupId);
            coupons.setState("N");
            couponsMapper.insertCoupons(coupons);
        }
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

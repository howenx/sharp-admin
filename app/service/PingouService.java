package service;

import entity.pingou.PinCoupon;
import entity.pingou.PinSku;

import java.util.List;

/**
 * Created by tiffany on 16/1/20.
 */
public interface PingouService {

    /**
     * 添加拼购商品和优惠券   Added by Tiffany Zhu 2016.01.20
     * @param pinSku
     *  @param pinCoupon
     */
    void insertPinSku(PinSku pinSku,PinCoupon pinCoupon);

    /**
     * 取得全部的拼购      Added by Tiffany Zhu 2016.01.21
     * @return
     */
    List<PinSku> getPinSkuAll();

    /**
     * ajax 分页查询    Added by Tiffany Zhu 2016.01.21
     * @param pinSku
     * @return
     */
    List<PinSku> getPinSkuPage(PinSku pinSku);

    /**
     * 通过ID获取拼购    Added by Tiffany Zhu 2016.01.22
     * @param pinId
     * @return
     */
    PinSku getPinSkuById(Long pinId);

    /**
     * 通过拼购ID获取拼购优惠券    Added by Tiffany Zhu 2016.01.22
     * @param pinId
     * @return
     */
    PinCoupon getCouponByPinId(Long pinId);

}

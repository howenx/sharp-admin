package service;

import entity.pingou.PinCoupon;
import entity.pingou.PinSku;

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
}

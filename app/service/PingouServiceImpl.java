package service;

import entity.pingou.PinCoupon;
import entity.pingou.PinSku;
import mapper.PinSkuMapper;
import play.Logger;

import javax.inject.Inject;

/**
 * Created by tiffany on 16/1/20.
 */
public class PingouServiceImpl implements PingouService {

    @Inject
    PinSkuMapper pinSkuMapper;
    /**
     * 添加拼购商品和优惠券   Added by Tiffany Zhu 2016.01.20
     * @param pinSku
     *  @param pinCoupon
     */
    @Override
    public void insertPinSku(PinSku pinSku,PinCoupon pinCoupon) {
        PinSku pinSku1 = pinSku;
        pinSkuMapper.insertPinSku(pinSku1);
        Logger.error(pinSku1.toString());
        pinCoupon.setPinId(pinSku1.getPinId());
        pinSkuMapper.insertPinCoupon(pinCoupon);
    }
}

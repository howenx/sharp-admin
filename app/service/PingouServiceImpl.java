package service;

import entity.pingou.PinCoupon;
import entity.pingou.PinSku;
import mapper.PinSkuMapper;
import play.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 16/1/20.
 */
public class PingouServiceImpl implements PingouService {

    @Inject
    PinSkuMapper pinSkuMapper;
    /**
     * 保存拼购商品和优惠券   Added by Tiffany Zhu 2016.01.20
     * @param pinSku
     *  @param pinCoupon
     */
    @Override
    public void insertPinSku(PinSku pinSku,PinCoupon pinCoupon) {
        PinSku pinSku1 = pinSku;
        pinSkuMapper.insertPinSku(pinSku1);
        Logger.error(pinSku1.toString());
        if(pinCoupon.getMasterCouponClass() != null || pinCoupon.getMemberCouponClass() != null){
            pinCoupon.setPinId(pinSku1.getPinId());
            pinSkuMapper.insertPinCoupon(pinCoupon);
        }

    }

    /**
     * 取得全部的拼购      Added by Tiffany Zhu 2016.01.21
     * @return
     */
    @Override
    public List<PinSku> getPinSkuAll() {
       return pinSkuMapper.getPinSkuAll();
    }

    /**
     * ajax 分页查询    Added by Tiffany Zhu 2016.01.21
     * @param pinSku
     * @return
     */
    @Override
    public List<PinSku> getPinSkuPage(PinSku pinSku) {
        return pinSkuMapper.getPinSkuPage(pinSku);
    }
}

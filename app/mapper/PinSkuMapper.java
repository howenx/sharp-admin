package mapper;

import entity.pingou.PinCoupon;
import entity.pingou.PinSku;

/**
 * Created by tiffany on 16/1/20.
 */
public interface PinSkuMapper {

    /**
     * 添加拼购商品   Added by Tiffany Zhu 2016.01.20
     * @param pinSku
     * @return
     */
    void insertPinSku(PinSku pinSku);

    /**
     * 添加拼购活动返回的优惠券     Added by Tiffany Zhu 2016.01.20
     * @param pinCoupon
     */
    void insertPinCoupon(PinCoupon pinCoupon);

}

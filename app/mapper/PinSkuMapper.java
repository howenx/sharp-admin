package mapper;

import entity.pingou.PinCoupon;
import entity.pingou.PinSku;
import java.util.List;

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

    /**
     * 取得全部的拼购      Added by Tiffany Zhu 2016.01.21
     * @return
     */
    List<PinSku> getPinSkuAll();

    /**
     * ajax分页查询     Added by Tiffany Zhu 2016.01.21
     * @param pinSku
     * @return
     */
    List<PinSku> getPinSkuPage(PinSku pinSku);

}

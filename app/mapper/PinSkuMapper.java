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

    /**
     *通过ID获取拼购    Added by Tiffany Zhu 2016.01.22
     * @param pinId
     * @return
     */
    PinSku getPinSkuById(Long pinId);

    /**
     *通过拼购ID获取拼购优惠券    Added by Tiffany Zhu 2016.01.22
     * @param pinId
     * @return
     */
    PinCoupon getCouponByPinId(Long pinId);

    /**
     * 更新拼购     Added by Tiffany Zhu 2016.01.22
     * @param pinSku
     */
    void updatePinSku(PinSku pinSku);

    /**
     * 更新拼购优惠券     Added by Tiffany Zhu 2016.01.22
     * @param pinCoupon
     */
    void updatePinCoupon(PinCoupon pinCoupon);

    /**
     * 删除拼购优惠券     Added by Tiffany Zhu 2016.01.22
     * @param pinId
     */
    void delPinCoupon(Long pinId);
}

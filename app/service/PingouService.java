package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.pingou.PinCoupon;
import entity.pingou.PinSku;
import entity.pingou.PinTieredPrice;

import java.util.List;

/**
 * Created by tiffany on 16/1/20.
 */
public interface PingouService {

    /**
     * 添加拼购商品和优惠券   Added by Tiffany Zhu 2016.01.20
     * @param json
     */
    void pinSkuSave(JsonNode json);

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
     * 通过PinId获取阶梯价格    Added by Tiffany Zhu 2016.01.28
     * @return
     */
    List<PinTieredPrice> getTieredPriceByPinId(Long pinId);

    /**
     * 添加主题ID       Added by Tiffany Zhu 2016.01.29
     * @param pinSku
     */
    void updPinThemeId(PinSku pinSku);

}

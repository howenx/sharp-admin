package mapper;

import entity.pingou.PinSku;
import entity.pingou.PinTieredPrice;
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
     * 更新拼购     Added by Tiffany Zhu 2016.01.22
     * @param pinSku
     */
    void updatePinSku(PinSku pinSku);

    /**
     * 添加阶梯价格       Added by Tiffany Zhu 2016.01.28
     * @param list
     */
    void addTieredPrice(List list);

    /**
     * 获取阶梯价格       Added by Tiffany Zhu 2016.01.28
     * @return
     */
    List<PinTieredPrice> getTieredPriceByPinId(Long pinId);

    /**
     * 更新阶梯价格       Added by Tiffany Zhu 2016.01.29
     * @param list
     */
    void updTieredPrice(List list);

    /**
     * 删除阶梯价格       Added by Tiffany Zhu 2016.01.29
     * @param list
     */
    void delTieredPrice(List list);

    /**
     * 添加主题ID       Added by Tiffany Zhu 2016.01.29
     * @param pinSku
     */
    void updPinThemeId(PinSku pinSku);

}

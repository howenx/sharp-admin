package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.pingou.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tiffany on 16/1/20.
 */
public interface PingouService {

    /**
     * 添加拼购商品和优惠券   Added by Tiffany Zhu 2016.01.20
     * @param pinSku
     */
    void pinSkuSave(PinSku pinSku);

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

    /**
     * 通过阶梯价格ID获取阶梯价格       Added by Tiffany Zhu 2016.02.15
     * @param id
     * @return
     */
    PinTieredPrice getTieredPriceByTieredId(Long id);

    /**
     * 手动添加拼购活动     Added by Tiffany Zhu 2016.02.15
     * @param pinActivity
     */
    void activityManualAdd(PinActivity pinActivity);

    /**
     * 手动添加拼购活动的优惠券     Added by Tiffany Zhu 2016.02.15
     * @param pinCoupon
     */
    void activityManualAddCoupon(PinCoupon pinCoupon);

    /**
     * 添加拼购用户        Added by Tiffany Zhu 2016.02.15
     * @param pinUser
     */
    void pinUserAdd(PinUser pinUser);

    /**
     * 获取全部的拼购活动        Added by Tiffany Zhu 2016.02.16
     * @return
     */
    List<PinActivity> getActivityAll();

    /**
     * 拼购活动 ajax分页查询        Added by Tiffany Zhu 2016.02.16
     * @param pinActivity
     * @return
     */
    List<PinActivity> getPinActivityPage(PinActivity pinActivity);

    /**
     * 通过ID获取拼购活动       Added by Tiffany Zhu 2016.02.17
     * @param id
     * @return
     */
    PinActivity getActivityById(Long id);

    /**
     * 通过拼购团ID获取优惠券     Added by Tiffany Zhu 2016.02.17
     * @param id
     * @return
     */
    PinCoupon getCouponByActivityId(Long id);

    /**
     * 通过拼购团ID获取参团团员     Added by Tiffany Zhu 2016.02.17
     * @param id
     * @return
     */
    List<PinUser> getUserByActivityId(Long id);

    /**
     * 批量添加拼购团用户        Added by Tiffany Zhu 2016.02.17
     * @param list
     */
    void pinUserAddList(List list);

    /**
     * 更新参加拼购活动的用户      Added by Tiffany Zhu 2016.02.18
     * @param hashMap
     */
    void updJoinPersonById(HashMap hashMap);

    /**
     * 通过拼购ID获取所有的拼购团       Added by Tiffany Zhu 2016.02.18
     * @param pinId
     * @return
     */
    List<PinActivity> getActivityByPinId(Long pinId);

    /**
     * 更新拼购商品状态         Added by Tiffany Zhu 2016.02.24
     */
    void updStatus();

    /**
     * 由invId获取拼购商品     Added By Sunny.Wu 2016.02.26
     * @param invId 库存Id
     * @return List of PinSku
     */
    List<PinSku> getPinSkuByInvId(Long invId);

    /**
     * 更新拼购             Added By Sunny.Wu 2016.02.26
     * @param pinSku
     */
    void updatePinSku(PinSku pinSku);

    /**
     * 更新拼购下架       Added by Tiffany Zhu 2016.02.29
     * @param pinSku
     */
    void updStatusById(PinSku pinSku);

    /**
     * 添加阶梯价格       Added by Tiffany Zhu 2016.01.28
     * @param list
     */
    void addTieredPrice(List list);

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
     * 获取状态为"正常"和"预售"的拼购    Added by Tiffany Zhu 2016.03.01
     * @return
     */
    List<PinSku> getAvailablePingou();

}

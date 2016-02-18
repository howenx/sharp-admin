package mapper;

import entity.pingou.PinActivity;
import entity.pingou.PinCoupon;
import entity.pingou.PinUser;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tiffany on 16/1/20.
 */
public interface PinActivityMapper {
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
     * 通过ID获取拼购团       Added by Tiffany Zhu 2016.02.17
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
}

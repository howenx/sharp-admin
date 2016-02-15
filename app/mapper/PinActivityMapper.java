package mapper;

import entity.pingou.PinActivity;
import entity.pingou.PinCoupon;
import entity.pingou.PinUser;

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

}

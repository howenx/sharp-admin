package service;

import domain.CouponVoDropLog;

/**
 * Created by tiffany on 16/5/20.
 */
public interface CouponVoDropLogService {
    /**
     * 添加优惠券废弃log       Added by Tiffany Zhu 2016.05.20
     * @param couponVoDropLog
     * @return
     */
    boolean addCouponVoDropLog(CouponVoDropLog couponVoDropLog);
}

package service;

import domain.CouponVoDropLog;
import mapper.CouponVoDropLogMapper;

import javax.inject.Inject;

/**
 * Created by tiffany on 16/5/20.
 */
public class CouponVoDropLogServiceImpl implements CouponVoDropLogService {
    @Inject
    private CouponVoDropLogMapper couponVoDropLogMapper;

    /**
     * 添加优惠券废弃log       Added by Tiffany Zhu 2016.05.20
     * @param couponVoDropLog
     * @return
     */
    @Override
    public boolean addCouponVoDropLog(CouponVoDropLog couponVoDropLog) {
        if(couponVoDropLogMapper.addCouponVoDropLog(couponVoDropLog) > 0){
            return true;
        }else {
            return false;
        }
    }
}

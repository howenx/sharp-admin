package service;

import domain.CouponVoDropLog;
import mapper.CouponVoDropLogMapper;

/**
 * Created by tiffany on 16/5/20.
 */
public class CouponVoDropLogServiceImpl implements CouponVoDropLogService {
//    @Inject  //注释 products 和 coupon库做的修改
    private CouponVoDropLogMapper couponVoDropLogMapper;

    //--------注释 products 和 coupon库做的修改-------     Modified By Sunny Wu 2016.09.01
    @Override
    public boolean addCouponVoDropLog(CouponVoDropLog couponVoDropLog) {
       return false;
    }
    //--------注释 products 和 coupon库做的修改-------




//    /**
//     * 添加优惠券废弃log       Added by Tiffany Zhu 2016.05.20
//     * @param couponVoDropLog
//     * @return
//     */
//    @Override
//    public boolean addCouponVoDropLog(CouponVoDropLog couponVoDropLog) {
//        if(couponVoDropLogMapper.addCouponVoDropLog(couponVoDropLog) > 0){
//            return true;
//        }else {
//            return false;
//        }
//    }


}

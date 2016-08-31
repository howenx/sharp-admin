package service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.google.common.base.Throwables;
import domain.*;
import mapper.CouponsMapper;
import modules.NewScheduler;
import play.Logger;
import scala.concurrent.duration.Duration;
import util.MsgTypeEnum;
import util.SysParCom;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class CouponsServiceImpl implements CouponsService {

    @Inject
    private CouponsMapper couponsMapper;

    @Inject
    private NewScheduler newScheduler;

    @Inject
    @Named("couponsCateInvalidActor")
    private ActorRef couponsCateInvalidActor;

    @Inject
    private ActorSystem system;

    /**
     * 优惠券保存
     * @param coupons
     */
    @Override
    public void couponsSave(Coupons coupons) {
//        for(JsonNode jsonNode : json) {
//            Coupons coupons = Json.fromJson(jsonNode, Coupons.class);
//            Long cateId = coupons.getCoupCateId();
//            String coupId = Coupons.GetCode(cateId, 8);
            String coupId = Coupons.CreateCouponCode(11);
            coupons.setCoupId(coupId);
//            coupons.setState("N");
//            Date now = new Date();
//            Long nowTimes = now.getTime();
//            Date endAt = new Date();
//            Long endTimes = 0l;
//            try {
//                endAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(coupons.getEndAt());
//                endTimes = endAt.getTime();
//            } catch (ParseException e) {
//                Logger.error(Throwables.getStackTraceAsString(e));
//            }
            //优惠券发送成功
            if (couponsMapper.insertCoupons(coupons) >0 ) {
                String content = "";
                if (coupons.getLimitQuota().compareTo(new BigDecimal(0.00)) == 0)
                    content = "优惠券金额"+coupons.getDenomination()+"元,无限额使用,快去用掉吧!";
                else if (coupons.getLimitQuota().compareTo(new BigDecimal(0.00)) > 0)
                    content = "优惠券金额"+coupons.getDenomination()+"元,满"+coupons.getLimitQuota()+"元可用,快去用掉吧!";
                //给用户推送消息
                PushMsg pushMsg = new PushMsg();
                pushMsg.setTitle(SysParCom.COUPON_MSG);    //标题
                pushMsg.setAlert(content);  //内容
                pushMsg.setAudience("alias");//给指定用户推送
                String[] tags = new String[1];
                tags[0] = coupons.getUserId().toString();
                pushMsg.setAliasOrTag(tags);
                pushMsg.setTargetType("V");//跳转的类型.优惠券
                Logger.error("推送的优惠券消息:"+pushMsg.toString());
                system.actorSelection(SysParCom.MSG_PUSH).tell(pushMsg, ActorRef.noSender());
                //给用户发送消息(消息盒子)
                MsgRec msgRec = new MsgRec();
                msgRec.setUserId(coupons.getUserId());
                msgRec.setMsgTitle(SysParCom.COUPON_MSG);  //标题
                msgRec.setMsgContent(content);//内容
                msgRec.setMsgRecType(1);
                msgRec.setMsgType(MsgTypeEnum.System.getMsgType());
                msgRec.setTargetType("C");//消息类型
                msgRec.setReadStatus(1);//1-未读 2-已读
                msgRec.setDelStatus(1);//1-未删 2-已删
                Logger.error("消息盒子优惠券消息:"+msgRec.toString());
                system.actorSelection(SysParCom.MSG_SEND).tell(msgRec, ActorRef.noSender());
//                //-- 创建Actor --//
//                //截止时间大于现在时间 启动自动失效scheduler
//                Logger.debug("coupon "+coupId+" auto invalid start...");
//                newScheduler.scheduleOnce(Duration.create(endTimes-nowTimes, TimeUnit.MILLISECONDS), couponInvalidActor, coupId);
            }
//        }
    }

    /**
     * 更新优惠券
     * @param coupons 优惠券  Added by Sunny Wu 2016.04.05
     */
    @Override
    public void updateCoupons(Coupons coupons) {
        couponsMapper.updateCoupons(coupons);
    }

    /**
     * 由id 获得一条优惠券信息
     * @param coupId 优惠券id
     * @return
     */
    @Override
    public Coupons getCoupons(String coupId) {
        return couponsMapper.getCoupons(coupId);
    }

    /**
     * 由coupCateId获取该类别的优惠券信息       Added By Sunny Wu 2016.08.19
     * @param coupCateId 优惠券类别id
     * @return
     */
    @Override
    public List<Coupons> getCouponsByCateId(Long coupCateId) {
        return couponsMapper.getCouponsByCateId(coupCateId);
    }

    /**
     * 获取所有已使用的优惠券信息
     * @return list of Coupons
     */
    @Override
    public List<Coupons> getAllUsedCoupons() {
        return couponsMapper.getAllUsedCoupons();
    }

    /**
     * 分页获取所有已使用优惠券信息
     * @param coupons 优惠券
     * @return list of Coupons
     */
    @Override
    public List<Coupons> getUsedCouponsPage(Coupons coupons) {
        return couponsMapper.getUsedCouponsPage(coupons);
    }

    /**
     * 获取所有可后台可发放的优惠券类别       Added By Sunny Wu 2016.06.27
     * @return list of CouponsCate
     */
    @Override
    public List<CouponsCate> getSendCouponsCate() {
        return couponsMapper.getSendCouponsCate();
    }

    /**
     * 获取所有的优惠券类别       Added By Sunny Wu 2016.08.19
     * @return list of CouponsCate
     */
    @Override
    public List<CouponsCate> getAllCouponsCate() {
        return couponsMapper.getAllCouponsCate();
    }

    /**
     * 由cateId查询一条优惠券类别信息      Added By Sunny Wu 2016.06.27
     * @param cateId 优惠券类别id
     * @return CouponsCate
     */
    @Override
    public CouponsCate getCouponsCate(Long cateId) {
        return couponsMapper.getCouponsCate(cateId);
    }

    /**
     * 新增优惠券类别                  Added by Sunny Wu 2016.08.18
     * @param couponsCate 优惠券类别
     */
    @Override
    public void couponsCateSave(CouponsCate couponsCate) {
        if (couponsMapper.insertCouponsCate(couponsCate) > 0) {
            Date now = new Date();
            Long nowTimes = now.getTime();
            Date endAt = new Date();
            Long endTimes = 0l;
            try {
                endAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(couponsCate.getEndAt());
                endTimes = endAt.getTime();
            } catch (ParseException e) {
                Logger.error(Throwables.getStackTraceAsString(e));
            }
            Long coupCateId = couponsCate.getCoupCateId();
            //-- 创建Actor --//
            //截止时间大于现在时间 启动优惠券类别自动失效scheduler
            Logger.debug("CouponsCate "+coupCateId+" auto invalid start...");
            newScheduler.scheduleOnce(Duration.create(endTimes-nowTimes, TimeUnit.MILLISECONDS), couponsCateInvalidActor, coupCateId);
        }
    }

    /**
     * 更新优惠券类别
     * @param couponsCate 优惠券类别  Added by Sunny Wu 2016.08.18
     */
    @Override
    public void updateCouponsCate(CouponsCate couponsCate) {
        couponsMapper.updateCouponsCate(couponsCate);
    }

    /**
     * 录入一条优惠券类别映射信息        Added by Sunny Wu 2016.08.31
     * @param couponsMap 优惠券类别映射
     * @return
     *
     */
    @Override
    public boolean insertCouponsMap(CouponsMap couponsMap) {
        return couponsMapper.insertCouponsMap(couponsMap) > 0;
    }

    /**
     * 更新一条优惠券类别映射信息        Added by Sunny Wu 2016.08.31
     * @param couponsMap 优惠券类别映射
     * @return
     */
    @Override
    public boolean updateCouponsMap(CouponsMap couponsMap) {
        return couponsMapper.updateCouponsMap(couponsMap) > 0;
    }

    /**
     * 根据优惠券类别获取所有的优惠券类别映射信息    Added by Sunny Wu 2016.08.31
     * @param couponCateId 优惠券类别ID
     * @return list of couponsMap
     */
    @Override
    public List<CouponsMap> getCouponsMapByCateId(Long couponCateId) {
        return couponsMapper.getCouponsMapByCateId(couponCateId);
    }

}

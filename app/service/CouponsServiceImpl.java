package service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Throwables;
import domain.Coupons;
import domain.MsgRec;
import mapper.CouponsMapper;
import modules.NewScheduler;
import play.Logger;
import play.libs.Json;
import scala.concurrent.duration.Duration;
import util.MsgTypeEnum;
import util.SysParCom;

import javax.inject.Inject;
import javax.inject.Named;
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
    @Named("couponInvalidActor")
    private ActorRef couponInvalidActor;

    @Inject
    private ActorSystem system;

    /**
     * 优惠券保存
     * @param json
     */
    @Override
    public void couponsSave(JsonNode json) {
        for(JsonNode jsonNode : json) {
            Coupons coupons = Json.fromJson(jsonNode, Coupons.class);
            Long cateId = coupons.getCateId();
            String coupId = Coupons.GetCode(cateId, 8);
            coupons.setCoupId(coupId);
            coupons.setState("N");
            Date now = new Date();
            Long nowTimes = now.getTime();
            Date endAt = new Date();
            Long endTimes = 0l;
            try {
                endAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(coupons.getEndAt());
                endTimes = endAt.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                Logger.error(Throwables.getStackTraceAsString(e));
            }
            //优惠券发送成功
            if (couponsMapper.insertCoupons(coupons) >0 ) {
                //给用户发送消息
                MsgRec msgRec = new MsgRec();
                msgRec.setUserId(coupons.getUserId());
                msgRec.setMsgRecType(1);
                String title = "您有一张新的优惠券";
                String content = "优惠券金额"+coupons.getDenomination()+",满"+coupons.getLimitQuota()+"元可用,快去用掉吧!";
                msgRec.setMsgTitle(title);
                msgRec.setMsgContent(content);
                msgRec.setMsgType(MsgTypeEnum.Coupon.getMsgType());

                system.actorSelection(SysParCom.MSG_SEND).tell(msgRec, ActorRef.noSender());

//                PushMsg pushMsg = new PushMsg();
//                pushMsg.setTitle(title);
//                pushMsg.setAlert(content);
//                pushMsg.setAudience("tag");
//                pushMsg.setUrl("");
//                pushMsg.setTargetType("");
//                system.actorSelection(SysParCom.MSG_PUSH).tell(pushMsg, ActorRef.noSender());

                //-- 创建Actor --//
                //截止时间大于现在时间 启动自动失效scheduler
                Logger.debug("coupon "+coupId+" auto invalid start...");
                newScheduler.scheduleOnce(Duration.create(endTimes-nowTimes, TimeUnit.MILLISECONDS), couponInvalidActor, coupId);
            }
        }
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
     * 获取所有已使用的优惠券信息
     * @return list of CouponsService
     */
    @Override
    public List<Coupons> getAllUsedCoupons() {
        return couponsMapper.getAllUsedCoupons();
    }

    /**
     * 分页获取所有已使用优惠券信息
     * @param coupons 优惠券
     * @return list of CouponsService
     */
    @Override
    public List<Coupons> getUsedCouponsPage(Coupons coupons) {
        return couponsMapper.getUsedCouponsPage(coupons);
    }

}

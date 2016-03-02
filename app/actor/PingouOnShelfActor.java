package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import entity.pingou.PinSku;
import modules.NewScheduler;
import play.Logger;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import service.PingouService;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by tiffany on 16/3/1.
 */
public class PingouOnShelfActor extends AbstractActor {

    @Inject
    private NewScheduler newScheduler;
    @Inject
    PingouService pingouService;
    @Inject
    @Named("pingouOffShelfActor")
    private ActorRef pingouOffShelfActor;


    public PingouOnShelfActor() {

        receive(ReceiveBuilder.match(Long.class, message -> {
            //预售-->正常
            PinSku pinSku = pingouService.getPinSkuById(message);
            pinSku.setStatus("Y");
            pingouService.updStatusById(pinSku);

            Thread.sleep(10000);

            //创建 Scheduled Actor   正常-->下架         ----Start
            //获取当前时间
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endAt = null;
            try{
                endAt = sdf.parse(pinSku.getEndAt());
            }catch(Exception e){
                e.printStackTrace();
            }
            if(endAt != null && (endAt.getTime() - now.getTime() > 0)){
                FiniteDuration duration = Duration.create(endAt.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
                newScheduler.scheduleOnce(duration,pingouOffShelfActor,pinSku.getPinId());
            }
            //创建 Scheduled Actor   正常-->下架         ----end
            Logger.error("" + message.toString());
        }).matchAny(s -> {
            unhandled(s);
            Logger.error("" + s.toString());
        }).build());
    }
}

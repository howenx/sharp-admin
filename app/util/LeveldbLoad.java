package util;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import com.google.common.base.Throwables;
import domain.Persist;
import modules.LevelFactory;
import modules.NewScheduler;
import play.Logger;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 用于加载所有的leveldb schedule
 * Created by hao on 16/5/9.
 */
@Singleton
public class LeveldbLoad {
    public static final Timeout TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);


    @Inject
    public LeveldbLoad(NewScheduler newScheduler, LevelFactory levelFactory, ActorSystem system) {
        Logger.error("Leveldb开始遍历....");
        List<Persist> persists;
        try {
            persists = levelFactory.iterator();
            if (persists != null && persists.size() > 0) {
                Logger.info("遍历所有持久化schedule---->\n" + persists);
                ExecutorService executorService = Executors.newFixedThreadPool(3);

                for (Persist p : persists) {
                    //----已经启动的schedule中不存在的actor --> 删除   Modified By Sunny.Wu 2016.09.02
                    if ("akka://application/user/couponInvalidActor".equals(p.getActorPath())) {
                        levelFactory.delete(p.getMessage());
                    }
                    //----已经启动的schedule中不存在的actor --> 删除

                    executorService.submit(() -> dealPersist(p, system, newScheduler, levelFactory));
                }
                executorService.shutdown();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error(Throwables.getStackTraceAsString(ex));
        }
    }

    private void dealPersist(Persist p, ActorSystem system, NewScheduler newScheduler, LevelFactory levelFactory) {
        try {
            ActorSelection sel = system.actorSelection(p.getActorPath());
            Future<ActorRef> fut = sel.resolveOne(TIMEOUT);
            ActorRef ref = Await.result(fut, TIMEOUT.duration());

            if (p.getType().equals("scheduleOnce")) {
                Long time = p.getDelay() - (new Date().getTime() - p.getCreateAt().getTime());
                Logger.info("重启后scheduleOnce执行时间---> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime() + time)));
                if (time > 0) {
                    newScheduler.scheduleOnce(Duration.create(time, TimeUnit.MILLISECONDS), ref, p.getMessage());
                } else {
                    levelFactory.delete(p.getMessage());
                    system.actorSelection(p.getActorPath()).tell(p.getMessage(), ActorRef.noSender());
                }
            } else if (p.getType().equals("schedule")) {
                newScheduler.schedule(Duration.create(p.getInitialDelay(), TimeUnit.MILLISECONDS), Duration.create(p.getDelay(), TimeUnit.MILLISECONDS), ref, p.getMessage());
                Logger.info("重启后schedule执行---> 每隔 " + Duration.create(p.getDelay(), TimeUnit.MILLISECONDS).toMinutes() + " 分钟执行一次");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error(Throwables.getStackTraceAsString(ex));
        }
    }

}

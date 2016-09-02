package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.japi.pf.ReceiveBuilder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Persist;
import domain.VersionVo;
import modules.LevelFactory;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import redis.clients.jedis.Jedis;
import util.RedisPool;

import javax.inject.Inject;
import java.util.List;

import static util.SysParCom.REDIS_CHANNEL;

/**
 * 用于处理自动上传
 * Created by howen on 16/04/18.
 */
public class AutoDeployActor extends AbstractActor {

    @Inject
    public AutoDeployActor(Configuration configuration, ActorSystem system) {
        receive(ReceiveBuilder.match(Object.class, message -> {

            if (message instanceof VersionVo) {

                String projectName = "style." + ((VersionVo) message).getProductType();

                List<String> actorPath = configuration.getStringList(projectName);

                if (actorPath != null && !actorPath.isEmpty()) {
                    if (((VersionVo) message).getProductType().equals("id") || ((VersionVo) message).getProductType().equals("web") || ((VersionVo) message).getProductType().equals("shopping") || ((VersionVo) message).getProductType().equals("services")) {
//                        for (String ap:actorPath) {
//                            system.actorSelection(ap).tell(message, ActorRef.noSender());
//                        }
                        Logger.error("频道--->"+"style-" + ((VersionVo) message).getProductType() + "-version");
                        Logger.error("版本vo---->" +Json.mapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).valueToTree((VersionVo) message).toString());
                        try (Jedis jedis = RedisPool.createPool().getResource()) {
                            if (((VersionVo) message).getProductType().equals("id") || ((VersionVo) message).getProductType().equals("web")){
                                jedis.publish("style-" + ((VersionVo) message).getProductType() + "-version", Json.mapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).valueToTree((VersionVo) message).toString());
                            }
                            else{
                                jedis.publish("style-" + ((VersionVo) message).getProductType() + "-version", ((VersionVo) message).getId().toString());
                            }
                        }
                    } else {
                        for (String ap : actorPath) {
                            system.actorSelection(ap).tell(((VersionVo) message).getId(), ActorRef.noSender());
                        }
                    }
                } else {
                    Logger.error("Configuration is not found,{}", "style." + ((VersionVo) message).getProductType());
                }
            }
        }).matchAny(s -> {
            Logger.error("AutoDeplayActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}

package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.VersionVo;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import redis.clients.jedis.Jedis;
import util.RedisPool;

import javax.inject.Inject;

/**
 * 用于处理自动上传
 * Created by howen on 16/04/18.
 */
public class AutoDeployActor extends AbstractActor {

    @Inject
    public AutoDeployActor(Configuration configuration, ActorSystem system) {
        receive(ReceiveBuilder.match(Object.class, message -> {

            if (message instanceof VersionVo) {

                String actorPath = configuration.getString("style." + ((VersionVo) message).getProductType());

                if (actorPath != null) {
                    system.actorSelection(actorPath).tell(((VersionVo) message).getId(), ActorRef.noSender());
                } else {
                    Logger.info("正在使用订阅发布", "style." + ((VersionVo) message).getProductType());
                    try (Jedis jedis = RedisPool.createPool().getResource()) {
                        if (((VersionVo) message).getProductType().equals("id") || ((VersionVo) message).getProductType().equals("web")) {
                            jedis.publish("style-" + ((VersionVo) message).getProductType() + "-version", Json.mapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).valueToTree((VersionVo) message).toString());
                        } else {
                            jedis.publish("style-" + ((VersionVo) message).getProductType() + "-version", ((VersionVo) message).getId().toString());
                        }
                    }
                }
            }
        }).matchAny(s -> {
            Logger.error("AutoDeplayActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}

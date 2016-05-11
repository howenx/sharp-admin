package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import play.Logger;
import play.mvc.WebSocket;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import util.RedisListener;

import static util.SysParCom.JEDIS_SUB;
import static util.SysParCom.REDIS_SUBSCRIBE;

/**
 * 用于produce消息到阿里云mns的Actor
 * Created by howen on 15/12/24.
 */
public class SubscribeActor extends AbstractActor {

    public SubscribeActor(JedisPool jedisPool, WebSocket.In<String> in, WebSocket.Out<String> out) {
        receive(ReceiveBuilder.match(String.class, message -> {
            try {

                Jedis jedis = jedisPool.getResource();

                if (message.equals("start")) {
                    for (String channel : REDIS_SUBSCRIBE) {
                        JedisPubSub listener = new RedisListener(out);
                        JEDIS_SUB.put(channel, listener);
                        jedis.psubscribe(listener, "hmm."+channel);
                    }
                } else if (message.equals("end")) {
                    for (String channel : REDIS_SUBSCRIBE) {
                        JedisPubSub listener = JEDIS_SUB.get(channel);
                        listener.unsubscribe();
                    }
                }

//                ExecutorService executor = Executors.newSingleThreadExecutor();
//                executor.submit(() -> jedis.psubscribe(listener, channel));

            } catch (Exception ignored) {
            }
        }).matchAny(s -> {
            Logger.error("MnsActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}

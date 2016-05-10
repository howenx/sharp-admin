package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import play.Logger;
import play.mvc.WebSocket;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import util.RedisListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于produce消息到阿里云mns的Actor
 * Created by howen on 15/12/24.
 */
public class SubscribeActor extends AbstractActor {

    public SubscribeActor(Jedis jedis, WebSocket.In<String> in, WebSocket.Out<String> out) {
        receive(ReceiveBuilder.match(String.class, channel -> {
            try {

                JedisPubSub listener = new RedisListener(out);
                jedis.psubscribe(listener, channel);
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

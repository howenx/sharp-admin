package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import play.Logger;
import play.mvc.WebSocket;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import util.RedisListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static util.SysParCom.JEDIS_SUB;
import static util.SysParCom.REDIS_SUBSCRIBE;

/**
 * 用于produce消息到阿里云mns的Actor
 * Created by howen on 15/12/24.
 */
public class SubscribeActor extends AbstractActor {

    private Callable<Boolean> callable(JedisPool jedisPool, WebSocket.Out<String> out, String channel) {
        return () -> {
            JedisPubSub listener = new RedisListener(out);
            JEDIS_SUB.put(channel, listener);
            System.out.println("hmm." + channel);
            jedisPool.getResource().psubscribe(listener, "hmm." + channel);
            return listener.isSubscribed();
        };
    }

    public SubscribeActor(JedisPool jedisPool, WebSocket.In<String> in, WebSocket.Out<String> out) {
        receive(ReceiveBuilder.match(String.class, message -> {
            try {
                ExecutorService executor = Executors.newFixedThreadPool(REDIS_SUBSCRIBE.size());
                List<Callable<Boolean>> callables = new ArrayList<>();
                System.err.println(REDIS_SUBSCRIBE);
                if (message.equals("start")) {
                    callables.addAll(REDIS_SUBSCRIBE.stream().map(channel -> callable(jedisPool, out, channel)).collect(Collectors.toList()));

                    executor.invokeAll(callables)
                            .stream()
                            .map(future -> {
                                try {
                                    return future.get();
                                } catch (Exception e) {
                                    throw new IllegalStateException(e);
                                }
                            })
                            .forEach(System.out::println);

                } else if (message.equals("end")) {
                    executor.shutdown();
                }

            } catch (Exception ignored) {
            }
        }).matchAny(s -> {
            Logger.error("MnsActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}

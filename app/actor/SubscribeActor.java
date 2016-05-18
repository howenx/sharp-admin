package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.google.common.base.Throwables;
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

import static util.SysParCom.*;

/**
 * 用于produce消息到阿里云mns的Actor
 * Created by howen on 15/12/24.
 */
public class SubscribeActor extends AbstractActor {

    private Callable<Boolean> callable(JedisPool jedisPool, WebSocket.Out<String> out, String channel) {
        return () -> {
            try {
                JedisPubSub listener = new RedisListener(out);
                JEDIS_SUB.put(channel, listener);
                jedisPool.getResource().psubscribe(listener, "hmm." + channel);
                return listener.isSubscribed();
            } catch (Exception ignore) {
                return false;
            }
        };
    }

    public SubscribeActor(JedisPool jedisPool, WebSocket.In<String> in, WebSocket.Out<String> out) {
        receive(ReceiveBuilder.match(String.class, message -> {
            try {
                ExecutorService executor = Executors.newFixedThreadPool(REDIS_SUBSCRIBE.size());
                List<Callable<Boolean>> callables = new ArrayList<>();
                if (message.equals("start")) {
                    REDIS_SUBSCRIBE.forEach(channel-> executor.submit(() -> {
                        try {
                            JedisPubSub listener = new RedisListener(out);
                            JEDIS_SUB.put(channel, listener);
                            jedisPool.getResource().psubscribe(listener, "hmm." + channel);
                            return listener.isSubscribed();
                        } catch (Exception ignore) {
                            Logger.error(Throwables.getStackTraceAsString(ignore));
                            return false;
                        }
                    }));

//                    callables.addAll(REDIS_SUBSCRIBE.stream().map(channel -> callable(jedisPool, out, channel)).collect(Collectors.toList()));

//                    executor.invokeAll(callables);
                    EXECUTOR_SERVICE.add(executor);

                } else if (message.equals("end")) {
                    System.out.println("----END---");
                    callables = null;
                    executor.shutdownNow();
                    JEDIS_SUB.forEach((k, v) -> {
                        if (v.isSubscribed()) {
                            System.out.println("----取消订阅---");
                            v.punsubscribe();
                        }
                    });
                    if (!EXECUTOR_SERVICE.isEmpty()) {
                        System.out.println("----关闭线程池---");
                        EXECUTOR_SERVICE.forEach(ExecutorService::shutdownNow);
                    }
                    JEDIS_SUB.clear();
                    EXECUTOR_SERVICE.clear();
                }

            } catch (Exception ignored) {
            }
        }).matchAny(s -> {
            Logger.error("MnsActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}

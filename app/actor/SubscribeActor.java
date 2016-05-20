package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.google.common.base.Throwables;
import play.Logger;
import play.mvc.WebSocket;
import redis.clients.jedis.Jedis;
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
                if (message.equals("start")) {
                    REDIS_SUBSCRIBE.forEach(channel -> executor.submit(() -> {
                        try {
                            JedisPubSub listener = new RedisListener(out);
                            JEDIS_SUB.put(channel, listener);
                            Jedis jedis = jedisPool.getResource();
                            JEDIS_COLLECT.add(jedis);
                            jedis.psubscribe(listener, "hmm." + channel);
                            return listener.isSubscribed();
                        } catch (Exception ignore) {
//                            Logger.error(Throwables.getStackTraceAsString(ignore));
                            return false;
                        }
                    }));
//                    EXECUTOR_SERVICE.add(executor);
                    executor.shutdown();
                } else if (message.equals("end")) {
                    System.out.println("----END---");
                    executor.shutdownNow();
                    JEDIS_SUB.forEach((k, v) -> {
                        if (v.isSubscribed()) {
                            System.out.println("----取消订阅---");
                            v.punsubscribe();
                        }
                    });

                    if (!JEDIS_COLLECT.isEmpty()) {
                        System.out.println("-----关闭Jedis------");
                        JEDIS_COLLECT.forEach(Jedis::close);
                    }
//                    if (!JEDIS_POOLS.isEmpty()) {
//                        System.out.println("-----destroy JedisPool------");
//                        JEDIS_POOLS.forEach(JedisPool::destroy);
//                    }
//
//                    if (!EXECUTOR_SERVICE.isEmpty()) {
//                        System.out.println("----关闭线程池---");
//                        EXECUTOR_SERVICE.forEach(executorService -> System.out.println("----当前状态线程池---"+executorService.isTerminated()+"----是否关闭---"+executorService.isShutdown()));
//                    }

                    JEDIS_SUB.clear();
                    EXECUTOR_SERVICE.clear();
                    JEDIS_COLLECT.clear();
                }

            } catch (Exception ignored) {
                Logger.error(Throwables.getStackTraceAsString(ignored));
            }
        }).matchAny(s -> {
            Logger.error("MnsActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}

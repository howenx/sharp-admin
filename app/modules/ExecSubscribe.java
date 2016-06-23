package modules;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import util.ConcurrentRedisListener;
import util.RedisPool;

import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.SysParCom.REDIS_SUBSCRIBE;

/**
 * 订阅
 * Created by howen on 16/6/22.
 */
@Singleton
public class ExecSubscribe {

    private static final ExecutorService executor = Executors.newFixedThreadPool(REDIS_SUBSCRIBE.size());

    public ExecSubscribe() {
        REDIS_SUBSCRIBE.forEach(channel -> executor.submit(() -> {
            try {
                JedisPubSub listener = new ConcurrentRedisListener();
                Jedis jedis = RedisPool.createPool().getResource();
                jedis.psubscribe(listener, "hmm." + channel);
                return listener.isSubscribed();
            } catch (Exception ignore) {
                return false;
            }
        }));
    }
}

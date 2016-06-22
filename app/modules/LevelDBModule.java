package modules;

import com.google.inject.AbstractModule;
import middle.ItemMiddle;
import middle.VersionMiddle;
import redis.clients.jedis.JedisPool;
import util.LeveldbLoad;
import util.LogUtil;
import util.RedisPool;
import util.SysParCom;
import util.cache.MemcachedConfiguration;

/**
 * 启动leveldb
 * Created by howen on 16/2/19.
 */
public class LevelDBModule extends AbstractModule {

    protected void configure() {
        bind(LevelFactory.class).asEagerSingleton();
        bind(NewScheduler.class);
        bind(ItemMiddle.class);
        bind(VersionMiddle.class);
        bind(SysParCom.class).asEagerSingleton();
        bind(MemcachedConfiguration.class).asEagerSingleton();
        bind(LogUtil.class).asEagerSingleton();
        bind(RedisPool.class).asEagerSingleton();
        bind(LeveldbLoad.class).asEagerSingleton();
        bind(ExecSubscribe.class).asEagerSingleton();
    }
}

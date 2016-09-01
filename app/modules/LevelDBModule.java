package modules;

import com.google.inject.AbstractModule;
import middle.ItemMiddle;
import middle.VersionMiddle;
import service.*;
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

        //--------注释 products 和 coupon库做的修改-------     Modified By Sunny Wu 2016.09.01
        bind(SaleService.class).to(SaleServiceImpl.class);
        bind(CouponVoService.class).to(CouponVoServiceImpl.class).asEagerSingleton();
        bind(CouponVoDropLogService.class).to(CouponVoDropLogServiceImpl.class).asEagerSingleton();
        bind(ImageService.class).to(ImageServiceImpl.class).asEagerSingleton();
        //--------注释 products 和 coupon库做的修改-------
    }

}

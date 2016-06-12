package modules;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import mapper.CouponVoDropLogMapper;
import mapper.CouponVoMapper;
import mapper.ImageMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.session.SqlSessionManagerProvider;
import play.db.DBApi;
import service.*;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

/**
 * Created by Sunny Wu on 16/4/25.
 * kakao china.
 */
public class CouponDBModule extends PrivateModule {

    @Override
    protected void configure() {

        install(new org.mybatis.guice.MyBatisModule() {
            @Override
            protected void initialize() {
                environmentId("coupon");
                //开启驼峰自动映射
                mapUnderscoreToCamelCase(true);

                bindDataSourceProviderType(CouponDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);
                addMapperClass(CouponVoMapper.class);
                addMapperClass(CouponVoDropLogMapper.class);
                addMapperClass(ImageMapper.class);
            }
        });

        /**
         * bind SQLsession to isolate the multiple datasources.
         */
        bind(SqlSession.class).annotatedWith(Names.named("coupon")).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
        expose(SqlSession.class).annotatedWith(Names.named("coupon"));

        /**
         * bind service for controller or other service inject.
         */
        bind(CouponVoService.class).to(CouponVoServiceImpl.class).asEagerSingleton();
        bind(CouponVoDropLogService.class).to(CouponVoDropLogServiceImpl.class).asEagerSingleton();
        bind(ImageService.class).to(ImageServiceImpl.class).asEagerSingleton();
        expose(CouponVoService.class);
        expose(CouponVoDropLogService.class);
        expose(ImageService.class);

    }

    @Singleton
    public static class CouponDataSourceProvider implements Provider<DataSource> {

        private final DBApi db;

        @Inject
        public CouponDataSourceProvider(final DBApi db) {
            this.db = db;
        }

        @Override
        public DataSource get() {
            return db.getDatabase("coupon").getDataSource();
        }
    }
}

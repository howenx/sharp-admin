package modules;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import mapper.PinActivityMapper;
import mapper.PinSkuMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.session.SqlSessionManagerProvider;
import play.db.DBApi;
import service.PingouService;
import service.PingouServiceImpl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

/**
 * 连接promotion 数据库
 * Created by tiffany on 16/1/20.
 */
public class PromotionDBModule extends PrivateModule {

    @Override
    protected void configure() {
        install(new org.mybatis.guice.MyBatisModule(){
            @Override
            protected void initialize() {
                environmentId("promotion");
                //开启驼峰式自动映射
                mapUnderscoreToCamelCase(true);
                bindDataSourceProviderType(PromotionDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);

                //只针对promotion数据库的Mapper,不可以将一个Mapper多Module进行Add
                addMapperClass(PinActivityMapper.class);
                addMapperClass(PinSkuMapper.class);

            }
        });

        /**
         * bind SQLsession to isolate the multiple datasources.无须更改
         * 如果需要取SqlSession:
         * @Named("promotion")
         * @Inject
         * private SqlSession sqlsession;
         */
        bind(SqlSession.class).annotatedWith(Names.named("promotion")).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
        expose(SqlSession.class).annotatedWith(Names.named("promotion"));

        /**
         * bind service for controller or other service inject. 绑定promotion数据库所对应的Service
         */
        bind(PingouService.class).to(PingouServiceImpl.class);

        //必须expose
        expose(PingouService.class);
    }

    @Singleton
    public static class PromotionDataSourceProvider implements Provider<DataSource>{

        final DBApi db;

        @Inject
        public PromotionDataSourceProvider(final DBApi db) {
            this.db = db;
        }

        @Override
        public DataSource get() {
            return db.getDatabase("promotion").getDataSource();
        }
    }
}

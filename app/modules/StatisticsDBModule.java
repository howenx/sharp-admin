package modules;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import mapper.SIDMapper;
import mapper.SOrderLineMapper;
import mapper.SOrderMapper;
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
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class StatisticsDBModule extends PrivateModule {


    @Override
    protected void configure() {
        install(new org.mybatis.guice.MyBatisModule(){
            @Override
            protected void initialize() {
                environmentId("statistics");
                //开启驼峰式自动映射
                mapUnderscoreToCamelCase(true);
                bindDataSourceProviderType(RecordDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);

                //只针对statistics数据库的Mapper,不可以将一个Mapper多Module进行Add
                addMapperClass(SIDMapper.class);
                addMapperClass(SOrderMapper.class);
                addMapperClass(SOrderLineMapper.class);
            }
        });

        /**
         * bind SQLsession to isolate the multiple datasources.无须更改
         * 如果需要取SqlSession:
         * @Named("statistics")
         * @Inject
         * private SqlSession sqlsession;
         */
        bind(SqlSession.class).annotatedWith(Names.named("statistics")).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
        expose(SqlSession.class).annotatedWith(Names.named("statistics"));

        /**
         * bind service for controller or other service inject. 绑定statistics数据库所对应的Service
         */
        bind(SIDService.class).to(SIDServiceImpl.class);
        bind(SOrderService.class).to(SOrderServiceImpl.class);
        bind(SOrderLineService.class).to(SOrderLineServiceImpl.class);

        //必须expose
        expose(SIDService.class);
        expose(SOrderService.class);
        expose(SOrderLineService.class);
    }

    @Singleton
    public static class RecordDataSourceProvider implements Provider<DataSource> {
        final DBApi db;

        @Inject
        public RecordDataSourceProvider(final DBApi db) {
            this.db = db;
        }

        @Override
        public DataSource get() {
            return db.getDatabase("statistics").getDataSource();
        }
    }
}

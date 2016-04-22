package modules;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import mapper.DataLogMapper;
import mapper.ItemStatisMapper;
import mapper.UserLogMapper;
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
 * 连接record数据库
 * Created by Sunny Wu.
 * 16/01/13
 */
public class RecordDBModule extends PrivateModule {

    @Override
    protected void configure() {
        install(new org.mybatis.guice.MyBatisModule(){
            @Override
            protected void initialize() {
                environmentId("record");
                //开启驼峰式自动映射
                mapUnderscoreToCamelCase(true);
                bindDataSourceProviderType(RecordDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);

                //只针对record数据库的Mapper,不可以将一个Mapper多Module进行Add
                addMapperClass(DataLogMapper.class);
                addMapperClass(ItemStatisMapper.class);
                addMapperClass(UserLogMapper.class);
            }
        });

        /**
         * bind SQLsession to isolate the multiple datasources.无须更改
         * 如果需要取SqlSession:
         * @Named("record")
         * @Inject
         * private SqlSession sqlsession;
         */
        bind(SqlSession.class).annotatedWith(Names.named("record")).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
        expose(SqlSession.class).annotatedWith(Names.named("record"));

        /**
         * bind service for controller or other service inject. 绑定record数据库所对应的Service
         */
        bind(DataLogService.class).to(DataLogServiceImpl.class);
        bind(ItemStatisService.class).to(ItemStatisServiceImpl.class);
        bind(UserLogService.class).to(UserLogServiceImpl.class);

        //必须expose
        expose(DataLogService.class);
        expose(ItemStatisService.class);
        expose(UserLogService.class);
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
            return db.getDatabase("record").getDataSource();
        }
    }
}

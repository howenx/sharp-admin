package modules;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import mapper.*;
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
 * Created by sibyl.sun on 16/3/3.
 */
public class ProductsDBModule extends PrivateModule {
    @Override
    protected void configure() {
        install(new org.mybatis.guice.MyBatisModule(){
            @Override
            protected void initialize() {
                environmentId("products");
                //开启驼峰式自动映射
                mapUnderscoreToCamelCase(true);
                bindDataSourceProviderType(ProductsDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);

                //只针对products数据库的Mapper,不可以将一个Mapper多Module进行Add
                addMapperClass(SaleMapper.class);

            }
        });

        /**
         * bind SQLsession to isolate the multiple datasources.无须更改
         * 如果需要取SqlSession:
         * @Named("products")
         * @Inject
         * private SqlSession sqlsession;
         */
        bind(SqlSession.class).annotatedWith(Names.named("products")).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
        expose(SqlSession.class).annotatedWith(Names.named("products"));

        /**
         * bind service for controller or other service inject. 绑定products数据库所对应的Service
         */
        bind(SaleService.class).to(SaleServiceImpl.class);

        //必须expose
        expose(SaleService.class);

    }

    @Singleton
    public static class ProductsDataSourceProvider implements Provider<DataSource> {
        final DBApi db;

        @Inject
        public ProductsDataSourceProvider(final DBApi db) {
            this.db = db;
        }

        @Override
        public DataSource get() {
            return db.getDatabase("products").getDataSource();
        }
    }
}

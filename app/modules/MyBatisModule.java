package modules;

import com.google.inject.name.Named;
import controllers.Theme;
import mapper.BrandsMapper;
import mapper.CatesMapper;
import mapper.ProductsMapper;
import mapper.ThemeMapper;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import play.Logger;
import play.db.DBApi;

import com.google.inject.name.Names;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

/**
 * Guice bind interface to its implements and deposit to guice.
 * <p>
 * <p>
 *
 * @author Howen Xiong
 */
public class MyBatisModule extends org.mybatis.guice.MyBatisModule {

    /**
     * guice initialize for load mybatis configuration file.
     */
    @Override
    protected void initialize() {
        Logger.debug("init mybatis database and config file...");
        environmentId("development");
        bindConstant().annotatedWith(Names.named("mybatis.configuration.failFast")).to(true);
        bindDataSourceProviderType(DefaultDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClass(BrandsMapper.class);
        addMapperClass(CatesMapper.class);
        addMapperClass(ProductsMapper.class);
        addMapperClass(ThemeMapper.class);

//			environmentId("development1");
//			bindConstant().annotatedWith(Names.named("mybatis.configuration.failFast")).to(true);
//			bindDataSourceProviderType(AccountDataSourceProvider.class);
//			bindTransactionFactoryType(JdbcTransactionFactory.class);
//			addMapperClass(UserMapper.class);


    }

    /**
     * 如果单数据源的话,可以直接注入Database
     *
     * @Singleton public static class DefaultDataSourceProvider implements Provider<DataSource> {
     * final Database db;
     * @Inject public DefaultDataSourceProvider(final Database db) { this.db = db;}
     * @Override public DataSource get() {return db.getDataSource();}
     * }
     */

    @Singleton
    public static class DefaultDataSourceProvider implements Provider<DataSource> {
        final DBApi db;

        @Inject
        public DefaultDataSourceProvider(final DBApi db) {
            this.db = db;
        }


        @Override
        public DataSource get() {
            return db.getDatabase("default").getDataSource();
        }
    }

    @Singleton
    public static class AccountDataSourceProvider implements Provider<DataSource> {
        final DBApi account_db;

        @Inject
        public AccountDataSourceProvider(final DBApi db) {

            this.account_db = db;
        }

        @Override
        public DataSource get() {
            return account_db.getDatabase("account").getDataSource();
        }
    }
}

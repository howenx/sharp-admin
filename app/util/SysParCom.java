package util;

import com.squareup.okhttp.OkHttpClient;
import play.Configuration;
import redis.clients.jedis.JedisPubSub;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询参数表中的参数项
 * Created by hao on 16/2/28.
 */
@Singleton
public class SysParCom {


    public static String REDIS_URL;
    public static String REDIS_PASSWORD;
    public static Integer REDIS_PORT;
    public static String REDIS_CHANNEL;

    public static List<String> REDIS_SUBSCRIBE;

    //图片服务器url
    public static String IMAGE_URL;
    //发布服务器url
    public static String DEPLOY_URL;
    //图片上传服务器url
    public static String IMG_UPLOAD_URL;
    //查询ER订单延迟时间
    public static Integer ORDER_QUERY_DELAY;
    //查询ER订单时间间隔
    public static Integer ORDER_QUERY_INTERVAL;
    //获取物流链接
    public static String EXPRESS_POST_URL;
    //获取物流 授权key
    public static String EXPRESS_KEY;

    public static String EXPRESS_CUSTOMER;

    //ERP账户信息配置
    public static String URL;
    public static String COMPANY ;
    public static String LOGIN_NAME;
    public static String PASSWORD;
    public static String SECRET;
    public static Map<String,JedisPubSub> JEDIS_SUB;

    public static final OkHttpClient client = new OkHttpClient();


    @Inject
    public SysParCom(Configuration configuration) {
        JEDIS_SUB = new HashMap<>();
        REDIS_URL = configuration.getString("redis.host");
        REDIS_PASSWORD = configuration.getString("redis.password");
        REDIS_PORT = configuration.getInt("redis.port");
        REDIS_CHANNEL = configuration.getString("redis.channel");
        REDIS_SUBSCRIBE = configuration.getStringList("redis.subscribe");
        IMAGE_URL = configuration.getString("image.server.url");
        DEPLOY_URL = configuration.getString("deploy.server.url");
        IMG_UPLOAD_URL = configuration.getString("image.upload.url");
        ORDER_QUERY_DELAY = configuration.getInt("shop.order.query.delay");
        ORDER_QUERY_INTERVAL = configuration.getInt("shop.order.query.interval");
        EXPRESS_POST_URL = configuration.getString("express.post.url");
        EXPRESS_KEY = configuration.getString("express.key");
        EXPRESS_CUSTOMER = configuration.getString("express.customer");

        URL = configuration.getString("erp.url");
        COMPANY = configuration.getString("erp.company");
        LOGIN_NAME = configuration.getString("erp.login.name");
        PASSWORD = configuration.getString("erp.login.pwd");
        SECRET = configuration.getString("erp.secret");
    }

}
package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import play.Configuration;
import play.mvc.WebSocket;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

/**
 * 查询参数表中的参数项
 * Created by hao on 16/2/28.
 */
@Singleton
@SuppressWarnings("unchecked")
public class SysParCom {


    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static Request.Builder builder = new Request.Builder();
    public static String REDIS_URL;
    public static String REDIS_PASSWORD;
    public static Integer REDIS_PORT;
    public static String REDIS_CHANNEL;

    public static List<String> REDIS_SUBSCRIBE;
    public static List<String> VERSION_PROJECT;

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
    public static String COMPANY;
    public static String LOGIN_NAME;
    public static String PASSWORD;
    public static String SECRET;
    //ERP推送订单
    public static String ERP_ORDER_PUSH;
    //订单申报
    public static String ORDER_DECLARA;

    public static ConcurrentMap<String, JedisPubSub> JEDIS_SUB;
    public static List<Jedis> JEDIS_COLLECT;
    public static List<JedisPool> JEDIS_POOLS;

    public final static ConcurrentLinkedDeque<WebSocket.Out<String>> WEBSOCKET_OUT_LIST = new ConcurrentLinkedDeque<>();


    public static List<ExecutorService> EXECUTOR_SERVICE;

    public static final OkHttpClient client = new OkHttpClient();

//    public static ExecutorService executor;


    //消息推送
    public static String MSG_PUSH;
    public static String MSG_SEND;
    //发送短息
    public static String SMS_SEND;


    public static String ALIPAY_PARTNER;
    public static String ALIPAY_SELLER_ID;
    public static String ALIPAY_KEY;
    public static String ALIPAY_RSA_PRIVATE_KEY;
    //shopping服务器url
    public static String SHOPPING_URL;
    public static Boolean ONE_CENT_PAY;

    //携程参数
    public static String AID;
    public static String SID;
    public static String KEY;
    public static String CTRIPURL;
    public static String REFRESHTOKEN;
    public static String ACCESSTOKEN;
    public static int REFRESHTIME;
    public static int ACCESSTIME;

    //分类入口数
    public static int CATEGORYCOUNT;
    //pay服务器url
    public static String PAY_URL;

    //拼购通知消息
    public static String PIN_SUCCESS_MSG;
    public static String PIN_ADD_MSG;
    //优惠券通知消息
    public static String COUPON_MSG;

    //Promotion服务器
    public static String PROMOTION_SERVER_URL;

    //WEB服务器
    public static String WEB_SERVER_URL;

    //境内发货可用快递公司
    public static Map<String,String> EXP_COMPANY_MAP = new HashMap<>();

    //所有快递公司代码
    public static Map<String,String> EXP_COMPANY_CODE = new HashMap<>();


    @Inject
    public SysParCom(Configuration configuration) {

        JEDIS_SUB = new ConcurrentHashMap<>();
        REDIS_URL = configuration.getString("redis.host");
        REDIS_PASSWORD = configuration.getString("redis.password");
        REDIS_PORT = configuration.getInt("redis.port");
        REDIS_CHANNEL = configuration.getString("redis.channel");
        REDIS_SUBSCRIBE = configuration.getStringList("redis.subscribe");
        VERSION_PROJECT = configuration.getStringList("version.project");
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

        ERP_ORDER_PUSH = configuration.getString("erp.order.push");

        ORDER_DECLARA = configuration.getString("order.declara");

        EXECUTOR_SERVICE = new ArrayList<>();

        JEDIS_COLLECT = new ArrayList<>();

        JEDIS_POOLS = new ArrayList<>();

//        executor = Executors.newFixedThreadPool(REDIS_SUBSCRIBE.size());

        //支付宝
        ALIPAY_PARTNER = configuration.getString("alipay.partner");
        ALIPAY_SELLER_ID = configuration.getString("alipay.seller.id");
        ALIPAY_KEY = configuration.getString("alipay.key");
        ALIPAY_RSA_PRIVATE_KEY=configuration.getString("alipay.rsa.private.key");

        SHOPPING_URL = configuration.getString("shopping.server.url");
        ONE_CENT_PAY = configuration.getBoolean("one.cent.pay");


        MSG_PUSH = configuration.getString("msg.push");
        MSG_SEND = configuration.getString("msg.send");
        SMS_SEND = configuration.getString("sms.send");

        //携程参数
        AID = configuration.getString("coupon.ctrip.AID");
        SID = configuration.getString("coupon.ctrip.SID");
        KEY = configuration.getString("coupon.ctrip.TokenKey");
        CTRIPURL = configuration.getString("coupon.ctrip.url");
        REFRESHTOKEN = configuration.getString("coupon.ctrip.getRefreshToken");
        ACCESSTOKEN = configuration.getString("coupon.ctrip.getAccessToken");
        REFRESHTIME = configuration.getInt("coupon.ctrip.refreshTokenTime");
        ACCESSTIME = configuration.getInt("coupon.ctrip.accessTokenTime");
        CATEGORYCOUNT = configuration.getInt("category.count");

        PAY_URL = configuration.getString("pay.server.url");

        //拼购通知消息
        PIN_SUCCESS_MSG = configuration.getString("pin.success.msg");
        PIN_ADD_MSG = configuration.getString("pin.add.msg");
        //优惠券通知消息
        COUPON_MSG = configuration.getString("coupon.msg");

        //Promotion服务器
        PROMOTION_SERVER_URL = configuration.getString("promotion.server.url");

        //WEB服务器
        WEB_SERVER_URL = configuration.getString("web.server.url");

        //境内发货可用快递公司
        EXP_COMPANY_MAP = new ObjectMapper().convertValue(configuration.getObject("nationExpress"),HashMap.class);

        //所有快递公司代码
        EXP_COMPANY_CODE = new ObjectMapper().convertValue(configuration.getObject("expressCode"),HashMap.class);

    }

}

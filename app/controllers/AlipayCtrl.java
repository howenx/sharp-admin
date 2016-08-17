package controllers;

import domain.order.Order;
import org.apache.commons.codec.digest.DigestUtils;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import service.OrderService;
import util.SysParCom;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

import static util.SysParCom.ALIPAY_KEY;
import static util.SysParCom.ALIPAY_RSA_PRIVATE_KEY;

/**
 * 支付宝退款
 * Created by sibyl.sun on 16/6/3.
 */
public class AlipayCtrl extends Controller {

    public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
    @Inject
    private OrderService orderService;
    /**
     * 获取退款参数
     * @param orderId
     * @return
     */
    public Map<String, String> getRefundParams(Long orderId) {
        Order order=orderService.getOrderById(orderId);

        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        if(null==order) {
            return sParaTemp;
        }

        sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("partner", SysParCom.ALIPAY_PARTNER);
        sParaTemp.put("_input_charset", "utf-8");
        sParaTemp.put("notify_url", SysParCom.PAY_URL + "/client/alipay/pay/refund");
        sParaTemp.put("seller_user_id", SysParCom.ALIPAY_PARTNER);
        sParaTemp.put("refund_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        sParaTemp.put("batch_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + orderId);
        sParaTemp.put("batch_num", "1");
        //付款金额，必填 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
        String total_fee ;
        if (SysParCom.ONE_CENT_PAY) {
            total_fee="0.01";
        } else {
            total_fee=order.getPayTotal().toPlainString();
        }

        String detail_data = order.getPgTradeNo() + "^" + total_fee + "^" + "HMM";
        sParaTemp.put("detail_data", detail_data);
        Map<String, String> map = buildRequestPara(sParaTemp, "RSA");
        Logger.info("支付宝退款参数" + Json.toJson(map));
        return map;
    }

    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,String signType) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        String key=encodeKey(signType);
        //生成签名结果
        String  mysign=buildRequestMysign(sPara,key,signType);



        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", signType);
        return sPara;
    }
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, String> sPara,String key,String signType) {
        String prestr =createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = sign(prestr, key, "utf-8",signType);
        return mysign;
    }
    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset,String signType) {
        if(signType.equals("MD5")){
            text = text + key;
            return DigestUtils.md5Hex(getContentBytes(text, input_charset));
        }else if(signType.equals("RSA")){
            try
            {
                PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.getDecoder().decode(key) );
                KeyFactory keyf 				= KeyFactory.getInstance("RSA");
                PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

                java.security.Signature signature = java.security.Signature
                        .getInstance(SIGN_ALGORITHMS);

                signature.initSign(priKey);
                signature.update( text.getBytes(input_charset) );

                byte[] signed = signature.sign();

                return Base64.getEncoder().encodeToString(signed);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 加密需要的key
     * @param signType
     * @return
     */
    private static String encodeKey(String signType){
        String key=ALIPAY_KEY;
        if(signType.equals("RSA")){
            key=ALIPAY_RSA_PRIVATE_KEY;
        }
        return key;
    }
}

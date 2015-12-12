package order;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by handy on 15/12/11.
 * kakao china
 */
public class RestClient {

    public static final int TIMEOUT = 5 * 1000;

    /**
     * post form data, 返回String 数据,Json 数据.
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static <T> T post(String url, Map<String, String> headers, Map<String, String> params, Class<T> clazz) {

        WSRequest request = WS.url(url).setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        //设置头部
        headers.forEach((k, v) -> request.setHeader(k, v));
        StringBuffer sb = new StringBuffer();

        params.forEach((k, v) -> {
            if (StringUtils.isNotEmpty(v)) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(String.format("%s=%s", k, v));
            }
        });

        return request.post(sb.toString()).map(wsResponse -> {
            switch (wsResponse.getStatus()) {
                case 200: {
                    if (clazz.equals(JsonNode.class)) {
                        return clazz.cast(wsResponse.asJson());
                    } else {
                        return clazz.cast(wsResponse.getBody());
                    }
                }
                default:
                    return clazz.cast(wsResponse.getBody());
            }
        }).get(TIMEOUT);
    }


    /**
     * 创建参数的签名
     *
     * @param params
     * @param secret
     * @return
     */
    public static String create_sign(Map<String, String> params, String secret) {
        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value) || key.equals("sign")) {
                continue;
            }
            sb.append(String.format("%s%s", key, value));
        }

        String pre_sign = sb.toString();
        try {
            byte[] data = secret.getBytes("UTF-8");
            SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            byte[] text = pre_sign.getBytes("UTF-8");
            byte[] hashedBytes = mac.doFinal(text);
            String encode_sign = encode(Base64.encodeBase64String(hashedBytes));
            Logger.debug(encode_sign);
            return encode_sign;
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String encode(String s) throws UnsupportedEncodingException {

        char[] temp = URLEncoder.encode(s, "utf-8").toCharArray();
        for (int i = 0; i < temp.length - 2; i++) {
            if (temp[i] == '%') {
                temp[i + 1] = Character.toLowerCase(temp[i + 1]);
                temp[i + 2] = Character.toLowerCase(temp[i + 2]);
            }
        }
        return new String(temp);
    }
}
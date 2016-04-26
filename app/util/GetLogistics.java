package util;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import java.security.MessageDigest;
import java.util.Map;
import java.util.HashMap;


/**
 * Created by tiffany on 15/12/17.
 */
public class GetLogistics {

    public static String sendGet(String expNum,String expCom){
        String url = play.Play.application().configuration().getString("exp.k100.logistics");
        String key = play.Play.application().configuration().getString("exp.k100.key");
        String customer = play.Play.application().configuration().getString("exp.k100.customer");
        //String comCode = getExpCmpCode(expNum,key);
        Map<String,String> param = new HashMap<>();
        param.put("com",expCom);
        param.put("num",expNum);
        param.put("from","北京");
        param.put("to","北京");
        String sign = md5Encrypt(key + customer + param);
        Logger.error("签名是:" + sign);


        Map<String,String> params = new HashMap<>();
        params.put("customer",customer);
        params.put("sign",sign);
        JsonNode jsonParams = Json.toJson(params);

        String result = "";
//        try{
//            WSResponse response = WS.url(url).post(jsonParams).get(1000L);
//            result = new String(response.getBody().getBytes(),"utf-8");
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        return result;
    }

    //归属公司智能判断接口    Added by Tiffany Zhu 2016.04.25
    private final static String getExpCmpCode(String expNum,String key){
        String compCode = "";

        String url = play.Play.application().configuration().getString("exp.k100.company") + "?num="+ expNum +"&key=" + key;
        try {
            WSResponse response = WS.url(url).get().get(1000L);
            JsonNode responseJson = Json.parse(new String(response.getBody().getBytes(),"utf-8"));
            JsonNode json = responseJson.get(0);
            compCode = json.get("comCode").toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return compCode;
    }

    //MD5加密 32位 小写      Added by Tiffany Zhu 2016.04.22
    public final static String md5Encrypt(String info){
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = info.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}

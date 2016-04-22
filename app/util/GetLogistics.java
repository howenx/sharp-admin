package util;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import javax.inject.Inject;
import play.Configuration;

import java.security.MessageDigest;


/**
 * Created by tiffany on 15/12/17.
 */
public class GetLogistics {
    @Inject
    private Configuration configuration;

    public static JsonNode sendGet(String expNum,String expCompany){
        /*企业版*/
        String id = "425796724eeca6b3";  //授权key
        String com = expCompany;                 //快递公司代码
        String nu = expNum;     //快递单号
        String show = "0";                 //返回类型  0：返回json字符串，1：返回xml对象，2：返回html对象，3：返回text文本。 默认返回json字符串。
        String muti = "1";                 //返回信息数量：1:返回多行完整的信息，0:只返回一行信息。默认返回多行。
        String order = "desc";             //排序：desc：按时间由新到旧排列，asc：按时间由旧到新排列。不填默认返回倒序（大小写不敏感）
        String url = "http://api.kuaidi100.com/api?id=" + id + "&com="+ com +"&nu="+ nu +"&show="+ show +"&muti="+ muti +"&order=" + order;   //快递100 API 地址
        //String url = "http://api.kuaidi100.com/api?id=425796724eeca6b3&com=jd&nu=12837698789&show=0&muti=1&order=desc";

//        String url = "http://api.kuaidi100.com/api";
//        Map<String,String> params = new HashMap<>();
//        params.put("id","425796724eeca6b3");                //授权Key
//        params.put("com",expCompany);                       //快递公司代码
//        params.put("nu",expNum);                            //快递单号
//        params.put("show","0");                             //返回类型  0：返回json字符串，1：返回xml对象，2：返回html对象，3：返回text文本。 默认返回json字符串。
//        params.put("muti","1");                             //返回信息数量：1:返回多行完整的信息，0:只返回一行信息。默认返回多行。
//        params.put("order","desc");                         //排序：desc：按时间由新到旧排列，asc：按时间由旧到新排列。不填默认返回倒序（大小写不敏感）
//        JsonNode jsonParams = Json.toJson(params);

        JsonNode returnJson = null;

        String testStr = "ABCDEFG";

        try{
            //WSResponse response = WS.url(url).post(jsonParams).get(1000L);
            WSResponse response = WS.url(url).get().get(1000L);
            returnJson = Json.parse(new String(response.getBody().getBytes(),"utf-8"));
            Logger.error(returnJson.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
        return returnJson;
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

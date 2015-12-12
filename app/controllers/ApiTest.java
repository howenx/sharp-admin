package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.binary.Hex;
import play.Logger;
import play.libs.Json;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Sunny Wu.
 * 15/12/08
 */
public class ApiTest {

    public static void main(String[] args) throws Exception {
        ApiTest http = new ApiTest();
        Logger.error("APiTest Send Http GET request");
        String url = "http://121.43.186.32/rest";
        JsonNode result = http.sendPost(url,"");
        Logger.error(result.toString());
    }

    public static String UpperCaseUrlEncode(String s) throws UnsupportedEncodingException {

        char[] temp =URLEncoder.encode(s, "utf-8").toCharArray();
        for (int i = 0; i < temp.length - 2; i++)
        {
            if (temp[i] == '%')
            {
                temp[i + 1] = Character.toLowerCase(temp[i + 1]);
                temp[i + 2] = Character.toLowerCase(temp[i + 2]);
            }
        }
        return new String(temp);
    }

    public  static JsonNode sendPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection con = realUrl.openConnection();
            // 设置通用的请求属性

            String macKey = "lRINGlxXEAiXC2A9umPSboxW8Y1UArdH";
            String macData = "";

            Mac mac = Mac.getInstance("HMACSHA1");
//        get the bytes of the hmac key and data string
            byte[] secretByte = macKey.getBytes("UTF-8");
            byte[] dataBytes = macData.getBytes("UTF-8");
            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA1");

            mac.init(secret);
            byte[] doFinal = mac.doFinal(dataBytes);
            byte[] hexB = new Hex().encode(doFinal);
            String checksum = new String(hexB);
            String sign1 =  Base64.encodeBase64String(checksum.getBytes());
            Logger.error(sign1);




            String sign = URLEncoder.encode(sign1, "utf-8");

            Logger.error(sign);
            String company = Base64.encodeBase64String("北京可靠".getBytes("utf-8"));
            String loginName = Base64.encodeBase64String("manager".getBytes("utf-8"));
            String password = Base64.encodeBase64String("iwilley".getBytes("utf-8"));
            con.setRequestProperty("Company", company);
            con.setRequestProperty("LoginName", loginName);
            con.setRequestProperty("Password", password);
            con.setRequestProperty("Method", "B1EC2.Shop.Query");
            con.setRequestProperty("Version", "1.0");
            con.setRequestProperty("Sign", sign);
            // 发送POST请求必须设置如下两行
            con.setDoOutput(true);
            con.setDoInput(true);
//            con. = "application/x-www-form-urlencoded;charset=utf-8";
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(con.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return Json.toJson(result);
    }
}




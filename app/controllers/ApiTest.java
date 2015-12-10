package controllers;

import org.apache.commons.codec.binary.Hex;
import play.Logger;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sunny Wu.
 * 15/12/08
 */
public class ApiTest {

    public static void main(String[] args) throws Exception {
        ApiTest http = new ApiTest();
        Logger.error("APiTest Send Http GET request");
        http.sendGet();
    }

    private void sendGet() throws Exception {
        String url = "http://121.43.186.32";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        String macKey = "lRINGlxXEAiXC2A9umPSboxW8Y1UArdH";
        String macData = "";
//        System.out.println("MACDATA:"+macData);

        Mac mac = Mac.getInstance("HmacSHA1");
//        get the bytes of the hmac key and data string
        byte[] secretByte = macKey.getBytes("UTF-8");
        byte[] dataBytes = macData.getBytes("UTF-8");
        SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA1");

        mac.init(secret);
        byte[] doFinal = mac.doFinal(dataBytes);
        byte[] hexB = new Hex().encode(doFinal);
        String checksum = new String(hexB);
        String sign = new BASE64Encoder().encode(checksum.getBytes("utf-8"));



        String company = new BASE64Encoder().encode("北京可靠".getBytes("utf-8"));
        String loginName = new BASE64Encoder().encode("manager".getBytes("utf-8"));
        String password = new BASE64Encoder().encode("iwilley".getBytes("utf-8"));
        con.setRequestProperty("Company", company);
        con.setRequestProperty("LoginName", loginName);
        con.setRequestProperty("Password", password);
        con.setRequestProperty("Method", "B1EC2.ShopItem.Query");
        con.setRequestProperty("Version", "1.0");

        con.setRequestProperty("Sign", sign);


        int responseCode = con.getResponseCode();

        Logger.error("Sending 'GET' request to URL:" + url);
        Logger.error("Response Code:" + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine())!=null) {
            response.append(inputLine);
        }
        in.close();

        Logger.error(response.toString());


    }



}

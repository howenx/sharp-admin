package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.*;
import net.spy.memcached.MemcachedClient;
import play.Logger;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import javax.inject.Inject;
import java.util.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static util.SysParCom.*;

/**
 * Created by tiffany on 16/6/6.
 */
public class Ctrip {

    @Inject
    private MemcachedClient cache;

    /**
     * 访问携程API  Added by Tiffany Zhu 2016.06.06
     * @param ICODE         //接口名称
     * @param postData      //post数据
     * @return
     */
    public String ctripAPI(String ICODE,JsonNode postData){
        String result = "";
        try {
            Optional<Object> accessToken = Optional.ofNullable(cache.get("AccessToken"));
            if(!accessToken.isPresent()){
                getAccessToken();
            }
            HttpUrl url = HttpUrl.parse(CTRIPURL)
                    .newBuilder()
                    .setQueryParameter("AID", AID)
                    .setQueryParameter("SID",SID)
                    .setQueryParameter("ICODE",ICODE)
                    .setQueryParameter("UUID", UUID.randomUUID().toString())
                    .setQueryParameter("Token",accessToken.toString())
                    .setQueryParameter("mode","1")
                    .setQueryParameter("format","json").build();
            Logger.error("访问携程URL~~~~:" + url);
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON,postData.toString());
            Request request = builder.url(url).post(body).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = new String(response.body().bytes(), UTF_8);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取refreshToken   Added by Tiffany Zhu 2016.06.06
     */
    private void getRefreshToken(){
        try {
            WSResponse response = WS.url(REFRESHTOKEN)
                    .setQueryParameter("AID", AID)
                    .setQueryParameter("SID",SID)
                    .setQueryParameter("KEY",KEY)
                    .post("").get(1000L);
            if("200".equals(String.valueOf(response.getStatus()))){
                String refreshToken = new String(response.getBody().getBytes("ISO-8859-1"),UTF_8);
                cache.add("refreshToken",REFRESHTIME,refreshToken);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取AccessToken    Added by Tiffany Zhu 2016.06.06
     */
    private void getAccessToken(){
        try {
            Optional<Object> refreshToken = Optional.ofNullable(cache.get("refreshToken"));
            if(!refreshToken.isPresent()){
                getRefreshToken();
            }
            WSResponse response = WS.url(ACCESSTOKEN)
                    .setQueryParameter("AID", AID)
                    .setQueryParameter("SID",SID)
                    .setQueryParameter("refresh_token",refreshToken.toString())
                    .post("").get(1000L);
            if("200".equals(String.valueOf(response.getStatus()))){
                String AccessToken = new String(response.getBody().getBytes("ISO-8859-1"),UTF_8);
                cache.add("AccessToken",ACCESSTIME,AccessToken);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
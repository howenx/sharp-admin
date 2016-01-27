package order;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by tiffany on 15/12/17.
 */
public class GetLogistics {

    public static List<Object[]> sendGet(String expNum){
        String url = "http://www.baidu.com/";    //请求地址

        Map<String,String> params = new HashMap<>();  //参数
        params.put("appname","");   //商户名称
        params.put("appid","");     //威盛快递分配给商户的ID
        params.put("TrackingID",expNum);//威盛快递单号
        JsonNode jsonParams = Json.toJson(params);
        WSResponse response = WS.url(url).post(jsonParams).get(1000L);
        Logger.error(response.toString());
        List<Object[]> logisticsList = new ArrayList<>();

        /*
        if(response.getStatus()==200)
        {
            System.out.print(response);
            JsonNode jsonResponse = response.asJson();
            Logger.error(jsonResponse.toString());
            //取得物流信息
            if(jsonResponse.has("rtnList")){
             JsonNode jsonRtn   =  jsonResponse.findValue("rtnList");
                if(jsonRtn != null){
                    for(JsonNode json: jsonRtn){
                        if(json.isArray()){
                            Object[] object = new Object[2];
                            object[0] = json.get("Createtime");
                            object[1] = json.get("Remark");
                            logisticsList.add(object);
                        }
                    }
                }
            }
        }
        */
        return logisticsList;
    }
}

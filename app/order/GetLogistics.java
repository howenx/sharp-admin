package order;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;


/**
 * Created by tiffany on 15/12/17.
 */
public class GetLogistics {

    public static JsonNode sendGet(String expCompany,String expNum){

        String linkUrl = "http://api.avatardata.cn/ExpressNumber/Lookup";   //访问地址
        String key = "2c96c711104e47b394881cd7564d856f";                    //授权key
        String url = linkUrl + "?key="+ key +"&company=" + expCompany + "&id=" + expNum;    //带参数访问路径
        JsonNode json = null;
        WSResponse response = WS.url(url).get().get(5000l);
        if(response.getStatus() == 200){
            Logger.error(response.getBody().toString());
            json = Json.parse(response.getBody().toString());
        }
        return json;
    }
}

package util;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
import domain.order.OrderSplit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import play.Logger;
import play.libs.ws.WSResponse;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WS;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import static java.nio.charset.StandardCharsets.UTF_8;
import static util.SysParCom.*;

/**
 * Created by tiffany on 15/12/17.
 */
public class GetExpress {
    @Inject
    private  WSClient ws;

    public static List<Object[]> express(OrderSplit orderSplit) {
        List<Object[]> resultList = new ArrayList<>();

        ObjectNode obj = Json.newObject();
        obj.put("com", orderSplit.getExpressCode());
        obj.put("num", orderSplit.getExpressNum());
        String sign = ExpressMD5.encode(obj.toString() + EXPRESS_KEY + EXPRESS_CUSTOMER);
        try {
            WSResponse response = WS.url(EXPRESS_POST_URL).setQueryParameter("param",obj.toString())
                                                            .setQueryParameter("sign",sign)
                                                            .setQueryParameter("customer",EXPRESS_CUSTOMER)
                                                            .post("").get(1000L);

            if("200".equals(String.valueOf(response.getStatus()))){
                String tempStr = new String(response.getBody().getBytes("ISO-8859-1"),UTF_8);
                JSONObject jsonObject = JSONObject.fromObject(tempStr);
                if (jsonObject.has("data")) {
                   JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(Object array : jsonArray){
                        JSONObject arrayObject = JSONObject.fromObject(array);
                        Object[] object = new Object[3];
                        object[0] = arrayObject.get("time");
                        object[1] = arrayObject.get("ftime");
                        object[2] = arrayObject.get("context");
                        resultList.add(object);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Logger.error(Throwables.getStackTraceAsString(e));
        }
        return resultList;
    }
}

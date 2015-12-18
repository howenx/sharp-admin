package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import order.B1EC2Client;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by handy on 15/11/3.
 * kakao china
 */
@Singleton
public class Test extends Controller{

    @Security.Authenticated(UserAuth.class)
    public  Result test() {

//        Map<String, String > params = new HashMap<>();
 //       params.put("StartTime","2015-01-01 01:00:00");
 //       params.put  ("EndTime","2015-11-01 01:00:00");
 //       params.put("ShopId","1");
//        Integer shopId = 1;
//        Integer orderStatus = 10;
        Map<String, Object > params = new HashMap<>();
//        params.put("StartTime","2015-01-01 01:00:00");
//        params.put  ("EndTime","2015-11-01 01:00:00");
//        params.put("ShopOrderNo", "1000007");
//        params.put("ShopId",1);
//        params.put("MemberNick", "1000012");
//        params.put("OrderStatus", 10);
//        params.put("ShopCreatedTime", new Date());

//        Logger.debug(B1EC2Client.post("http://121.43.186.32","B1EC2.ShopOrder.Push",params, JsonNode.class).toString());
       // Logger.debug(B1EC2Client.post("http://121.43.186.32","B1EC2.SalesOrder.Query",params, JsonNode.class).toString());
        String returnVal =B1EC2Client.post("http://121.43.186.32","B1EC2.Express.Query",params, JsonNode.class).toString();

        Logger.debug(returnVal);
//        Logger.debug(B1EC2Client.post("http://121.43.186.32","B1EC2.SalesOrder.Query",params, JsonNode.class).toString());
//        Logger.debug(ctx().args.get("user").toString());
//        Pattern p = Pattern.compile("\\b[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\b");
//        Matcher m = p.matcher("kkmc.skein+cn@gmail.com");
//        if(m.matches()) {
//            Logger.debug("match");
//        }else {
//            Logger.debug("not match");
//        }

        for(int i=0; i< 1000; i++) {
            Logger.debug("" + regMatch("A9"));
        }
        return ok(returnVal);
    }

    private boolean strMatch(String str){

        if(str == null || "".equals(str)){
            return false;
        }

        if(str.length() != 2){
            return false;
        }

        if(!"ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(str.substring(0, 1))){
            return false;
        }

        if(!"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".contains(str.substring(1, 2))){
            return false;
        }

        return true;
    }

    private boolean regMatch(String str){

        String regEx="^[A-Z][0-9A-Z]$";
        Pattern pat=Pattern.compile(regEx);

        Matcher mat=pat.matcher(str == null ? "" : str);

        return mat.find();
    }
}

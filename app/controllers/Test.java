package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import order.B1EC2Client;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

        Map<String, String > params = new HashMap<>();
        params.put("StartTime","2015-01-01 01:00:00");
        params.put("EndTime","2015-11-01 01:00:00");
        params.put("ShopId","1");

        Logger.debug(B1EC2Client.post("http://121.43.186.32","B1EC2.ShopItem.Query",params, JsonNode.class).toString());
//        Logger.debug(ctx().args.get("user").toString());
//        Pattern p = Pattern.compile("\\b[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\b");
//        Matcher m = p.matcher("kkmc.skein+cn@gmail.com");
//        if(m.matches()) {
//            Logger.debug("match");
//        }else {
//            Logger.debug("not match");
//        }
        return ok("ok");
    }
}

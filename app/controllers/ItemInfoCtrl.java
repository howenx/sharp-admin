package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import order.B1EC2Client;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sunny Wu 15/12/22.
 *
 * 查询ERP中所有的商品信息
 *
 */
public class ItemInfoCtrl extends Controller {

        @Security.Authenticated(UserAuth.class)
        public Result getItemInfo() {
            Map params = new HashMap<>();
            params.put("StartTime","2015-01-01 01:00:00");
            params.put("EndTime","2016-12-31 01:00:00");

            JsonNode jsonItem = B1EC2Client.post("http://121.43.186.32","B1EC2.ItemInfoCtrl.Query",params, JsonNode.class);


            return ok();
        }
}

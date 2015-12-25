package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.ItemInfoAll;
import order.B1EC2Client;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sunny Wu 15/12/22.
 * <p>
 * 查询ERP中所有的商品信息
 */
public class ItemInfoAllCtrl extends Controller {

    /**
     * ERP 商品资料列表
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result getItemInfo(String lang) {
        Map params = new HashMap<>();
        params.put("StartTime", "2015-01-01 01:00:00");
        params.put("EndTime", "2016-12-31 01:00:00");

        JsonNode json = B1EC2Client.post("http://121.43.186.32", "B1EC2.ItemInfo.Query", params, JsonNode.class);

        List<ItemInfoAll.ItemInfo> itemInfoList = new ArrayList<>();
//        for (final JsonNode jsonNode : json.findValue("ItemInfos")) {
//            ItemInfoAll.ItemInfo itemInfo = new ItemInfoAll.ItemInfo();
//            itemInfo = Json.fromJson(jsonNode, ItemInfoAll.ItemInfo.class);
//            itemInfoList.add(itemInfo);
//        }
//        return ok(views.html.item.erp_itemlist.render(lang,itemInfoList, (User)ctx().args.get("user")));
        return ok(json);
    }
}

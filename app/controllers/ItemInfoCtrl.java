package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.User;
import entity.erp.ItemInfo;
import order.B1EC2Client;
import play.Logger;
import play.libs.Json;
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
public class ItemInfoCtrl extends Controller {

    /**
     * ERP 商品资料查询
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemInfoList(String lang) {
        Map params = new HashMap<>();
        params.put("StartTime", "2015-12-19 01:00:00");
        params.put("EndTime", "2015-12-25 01:00:00");

        JsonNode json = B1EC2Client.post("http://121.43.186.32", "B1EC2.ItemInfo.Query", params, JsonNode.class);

        List<ItemInfo> itemInfoList = new ArrayList<>();
        for (final JsonNode jsonNode : json.findValue("ItemInfos")) {
            ItemInfo itemInfo = Json.fromJson(jsonNode, ItemInfo.class);
            itemInfoList.add(itemInfo);
        }

        //取总数
        int countNum = itemInfoList.size();
        //共分几页
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;
        if(countNum%ThemeCtrl.PAGE_SIZE!=0){
            pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
        }
        return ok(views.html.item.erp_itemlist.render(lang,itemInfoList,ThemeCtrl.PAGE_SIZE,countNum,pageCount, (User)ctx().args.get("user")));

//        return ok(json);
    }


    /**
     * ERP 商品资料分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemInfoSearchAjax(String lang, int pageNum) {

        JsonNode json = request().body().asJson();
        if (null==json.findValue("StartTime") || "".equals(json.findValue("StartTime"))) {
            ((ObjectNode) json).put("StartTime","2015-07-01 00:00:00");
        }
        if (null==json.findValue("EndTime") || "".equals(json.findValue("EndTime"))) {
            ((ObjectNode) json).put("EndTime","2020-07-01 00:00:00");
        }

        Logger.error(json.toString());

        ItemInfo itemInfoNode = Json.fromJson(json, ItemInfo.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            Map params = new HashMap<>();
            params.put("StartTime", itemInfoNode.getStartTime().toString());
            params.put("EndTime", itemInfoNode.getEndTime().toString());

            JsonNode json2 = B1EC2Client.post("http://121.43.186.32", "B1EC2.ItemInfo.Query", params, JsonNode.class);

            List<ItemInfo> itemInfoList = new ArrayList<>();
            for (final JsonNode jsonNode : json2.findValue("ItemInfos")) {
                ItemInfo itemInfo = Json.fromJson(jsonNode, ItemInfo.class);
                itemInfoList.add(itemInfo);
            }
            //取总数
            int countNum = itemInfoList.size();
            //共分几页
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE;
            }

            //当前页数据


            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("itemInfo",itemInfoList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",ThemeCtrl.PAGE_SIZE);

            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }
}

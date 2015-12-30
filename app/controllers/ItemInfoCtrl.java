package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.User;
import entity.erp.ItemInfo;
import order.B1EC2Client;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;

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
//            Logger.error("修改前:"+jsonNode.toString());
            JsonNode itemNode = null;
            Iterator<String> keys = jsonNode.fieldNames();
            while(keys.hasNext()){
                String fieldName = keys.next();
                char[] array = fieldName.toCharArray();
                array[0] += 32;
                String newFieldName = String.valueOf(array);
                String fieldValue = jsonNode.findValue(fieldName).toString();
//                ((ObjectNode)itemNode).put(newFieldName,fieldValue)
            }
            Logger.error("修改后:"+itemNode.toString());
            ItemInfo itemInfo = Json.fromJson(itemNode, ItemInfo.class);
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
//        Logger.error("json数据:"+json.toString());
        ItemInfo itemInfoNode = Json.fromJson(json, ItemInfo.class);

//        Logger.error("分页数据:"+itemInfoNode.toString());
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            Map params = new HashMap<>();
            String startTime = itemInfoNode.getStartTime().toString();
            String endTime = itemInfoNode.getEndTime().toString();
            params.put("StartTime", startTime.substring(0,startTime.length()-2));
            params.put("EndTime", endTime.substring(0,endTime.length()-2));

//            Logger.error("开始时间:"+startTime.substring(0,startTime.length()-2));
//            Logger.error("结束时间:"+endTime.substring(0,endTime.length()-2));

            JsonNode json2 = B1EC2Client.post("http://121.43.186.32", "B1EC2.ItemInfo.Query", params, JsonNode.class);

//            Logger.error("json2数据:"+json2.toString());
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
            returnMap.put("topic",itemInfoList);
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

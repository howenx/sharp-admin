package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Inventory;
import entity.Order;
import entity.User;
import entity.pingou.PinCoupon;
import entity.pingou.PinSku;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;

import play.mvc.Result;
import play.mvc.Security;
import service.InventoryService;
import service.PingouService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by tiffany on 16/1/19.
 */
public class PingouCtrl extends Controller {

    @Inject
    PingouService pingouService;
    @Inject
    InventoryService inventoryService;

    /**
     * 添加拼购     Added by Tiffany Zhu 2016.01.19
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result addPingou(String lang){

        return ok(views.html.pingou.pingouAdd.render(lang,(User) ctx().args.get("user")));

    }

    /**
     * 保存拼购Sku Added by Tiffany Zhu 2016.01.21
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pingouSave(String lang){
        JsonNode json = request().body().asJson();
        PinSku pinSku = new PinSku();
        PinCoupon pinCoupon = new PinCoupon();
        if(json.has("pinSku")){
            pinSku = play.libs.Json.fromJson(json.findValue("pinSku"), PinSku.class);
        }
        if(json.has("pinCoupon")){
            pinCoupon = play.libs.Json.fromJson(json.findValue("pinCoupon"), PinCoupon.class);
        }
        Inventory inventory = inventoryService.getMasterInventory(pinSku.getItemId());
        pinSku.setInvId(inventory.getId());
        pinSku.setPinDiscount(pinSku.getFloorPrice().divide(inventory.getItemSrcPrice(),2));

        pingouService.insertPinSku(pinSku,pinCoupon);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 拼购列表 Added by Tiffany Zhu 2016.01.20
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pingouSearch(String lang){
        PinSku pinSku_temp = new PinSku();
        pinSku_temp.setPageSize(-1);
        pinSku_temp.setOffset(-1);
        int countNum = pingouService.getPinSkuAll().size();
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;
        if(countNum%ThemeCtrl.PAGE_SIZE != 0){
            pageCount =  countNum/ThemeCtrl.PAGE_SIZE + 1;
        }
        pinSku_temp.setPageSize(ThemeCtrl.PAGE_SIZE);
        pinSku_temp.setOffset(0);

        //拼购列表
        List<Object[]> rtnPinSkuList = new ArrayList<>();
        List<PinSku> pinSkuList = pingouService.getPinSkuPage(pinSku_temp);
        for(PinSku pinSku : pinSkuList){
            Object[] object = new Object[6];
            object[0] = pinSku.getPinId();      //活动ID
            object[1] = pinSku.getPinTitle();   //商品标题
            object[2] = pinSku.getStartAt();    //开始时间
            object[3] = pinSku.getEndAt();      //结束时间
            if("Y".equals(pinSku.getStatus())){
                object[4] = "正常";              //状态
            }
            if("N".equals(pinSku.getStatus())){
                object[4] = "下架";              //状态
            }
            if("P".equals(pinSku.getStatus())){
                object[4] = "预售";              //状态
            }

            object[5] = "";                     //已开团数
            rtnPinSkuList.add(object);
        }
        return ok(views.html.pingou.pingouSearch.render(lang,rtnPinSkuList,(User) ctx().args.get("user")));

    }

    /**
     * 拼购ajax查询     Added by Tiffany Zhu 2016.01.21
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result getPinSkuPage(String lang,int pageNum){
        JsonNode json = request().body().asJson();
        PinSku pinSku = Json.fromJson(json,PinSku.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            pinSku.setPageSize(-1);
            pinSku.setOffset(-1);
            //取总数
            int countNum = pingouService.getPinSkuPage(pinSku).size();
            //共分几页
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
            pinSku.setPageSize(ThemeCtrl.PAGE_SIZE);
            pinSku.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",pingouService.getPinSkuPage(pinSku));
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

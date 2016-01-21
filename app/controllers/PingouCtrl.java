package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Inventory;
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

        return ok(views.html.pingou.pingouSearch.render(lang,(User) ctx().args.get("user")));

    }


}

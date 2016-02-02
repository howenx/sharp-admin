package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.User;
import entity.pingou.PinSku;
import entity.pingou.PinTieredPrice;
import filters.UserAuth;
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

        return ok(views.html.pingou.pingouAdd.render(lang,ThemeCtrl.IMAGE_URL,(User) ctx().args.get("user")));

    }

    /**
     * 保存拼购Sku Added by Tiffany Zhu 2016.01.21
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pingouSave(String lang){
        JsonNode json = request().body().asJson();
        pingouService.pinSkuSave(json);
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
            if("".equals(pinSku.getStatus())){
                object[4] = "下架";              //状态
            }
            if("N".equals(pinSku.getStatus())){
                object[4] = "删除";              //状态
            }
            if("K".equals(pinSku.getStatus())){
                object[4] = "售空";              //状态
            }
            if("P".equals(pinSku.getStatus())){
                object[4] = "预售";              //状态
            }

            object[5] = "";                     //已开团数
            rtnPinSkuList.add(object);
        }
        return ok(views.html.pingou.pingouSearch.render(lang,ThemeCtrl.PAGE_SIZE,countNum,pageCount,rtnPinSkuList,(User) ctx().args.get("user")));

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

    /**
     * 通过ID获取拼购    Added by Tiffany Zhu 2016.01.22
     * @param lang
     * @param pinId
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result getPinSkuById(String lang,Long pinId){

        PinSku pinSku = pingouService.getPinSkuById(pinId);
        JsonNode imgJson = Json.parse(pinSku.getPinImg());
        Object[] img = new Object[3];
        String imgUrl =  imgJson.get("url").toString();
        img[0] = imgUrl.substring(1,imgUrl.length()-1);
        img[1] = imgJson.get("width");
        img[2] = imgJson.get("height");
        List<PinTieredPrice> tieredPrices = pingouService.getTieredPriceByPinId(pinId);
        List<Object[]> tieredList = new ArrayList<>();
        int index = 0;
        for(PinTieredPrice price : tieredPrices){
            index = index + 1;
            Object[] object = new Object[18];
            object[0] = price.getId();                  //阶梯价格的Id
            object[1] = index;                          //编号
            object[2] = price.getPeopleNum();           //人数
            object[3] = price.getPrice();               //价格
            object[4] = price.getMasterMinPrice();      //团长减价
            object[5] = price.getMemberMinPrice();      //团员减价
            object[6] = price.getMasterCouponClass();   //团长优惠券类别
            object[7] = "";                             //团长优惠券类别
            object[8] = price.getMasterCouponQuota();   //团长优惠券限额
            object[9] = price.getMasterCoupon();        //团长优惠券面值
            object[10] = price.getMasterCouponStartAt().toString().substring(0,19);//团长优惠券开始日期
            object[11] = price.getMasterCouponEndAt().toString().substring(0,19);  //团长优惠券结束日期
            object[12] = price.getMemberCouponClass();  //团员优惠券类别
            object[13] = "";                            //团员优惠券类别
            object[14] = price.getMemberCouponQuota();  //团员优惠券限额
            object[15] = price.getMemberCoupon();       //团员优惠券面值
            object[16] = price.getMemberCouponStartAt().toString().substring(0,19);//团员优惠券开始日期
            object[17] = price.getMemberCouponEndAt().toString().substring(0,19);  //团员优惠券届时日期
            tieredList.add(object);
        }
        return ok(views.html.pingou.pingouUpdate.render(lang,pinSku,img,tieredList,ThemeCtrl.IMAGE_URL,(User) ctx().args.get("user")));

    }
}

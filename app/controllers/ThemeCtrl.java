package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import entity.*;
import entity.pingou.PinSku;
import play.Logger;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ThemeCtrl management.
 * Created by howen on 15/10/28.
 */
public class ThemeCtrl extends Controller {

    //每页固定的取数
    public static final int PAGE_SIZE = 10;

    //图片服务器url
    public static final String IMAGE_URL = play.Play.application().configuration().getString("image.server.url");

    //发布服务器url
    public static final String DEPLOY_URL = play.Play.application().configuration().getString("deploy.server.url");

    //图片上传服务器url
    public static final String IMG_UPLOAD_URL = play.Play.application().configuration().getString("image.upload.url");

    //截图服务器url
    //public static final String IMG_CUT_URL = play.Play.application().configuration().getString("image.cut.url");;

    @Inject
    private ThemeService service;

    @Inject
    private ItemService itemService;

    @Inject
    private InventoryService inventoryService;

    @Inject
    private PingouService pingouService;

    @Inject
    private VaryPriceService varyPriceService;

    /**
     * 滚动条管理
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result slider(String lang) {
//        flash("success", session("username"));
        return ok(views.html.theme.slider.render(lang,service.sliderAll(),IMAGE_URL,(User) ctx().args.get("user")));
    }

    /**
     * 主题录入
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result thadd(String lang) {
//        flash("success", session("username"));
        return ok(views.html.theme.thadd.render(lang,IMAGE_URL,IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }


    /**
     * 滚动条变更保存
     * @param lang  语言
     * @return  view
     */
    @Security.Authenticated(UserAuth.class)
    public Result sliderSave(String lang){
        JsonNode json = request().body().asJson();
        service.sliderSave(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }


    /**
     * 主题查询
     * @param lang 语言
     * @return  view
     */
    @Security.Authenticated(UserAuth.class)
    public Result thsearch(String lang){

        Theme theme =new Theme();

        theme.setPageSize(-1);
        theme.setOffset(-1);

        //取总数
        int countNum = service.themeSearch(theme).size();
        //共分几页
        int pageCount = countNum/PAGE_SIZE;

        if(countNum%PAGE_SIZE!=0){
            pageCount = countNum/PAGE_SIZE+1;
        }

        theme.setPageSize(PAGE_SIZE);
        theme.setOffset(0);

        List<Theme> themeList = service.themeSearch(theme);
        List<Theme> resultList = new ArrayList<>();
        for(Theme tempTheme: themeList){
            JsonNode themeImg = Json.parse(tempTheme.getThemeImg());
            //url
            String themeImgUrl = themeImg.get("url").toString();
            String resultUrl = themeImgUrl.substring(1,themeImgUrl.length()-1);
            tempTheme.setThemeImg(resultUrl);
            resultList.add(tempTheme);
        }

        return ok(views.html.theme.thsearch.render(lang,IMAGE_URL,PAGE_SIZE,countNum,pageCount,resultList,(User) ctx().args.get("user")));
    }

    /**
     * ajax分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result thsearchAjax(String lang,int pageNum) {

        JsonNode json = request().body().asJson();

        Theme theme = Json.fromJson(json,Theme.class);

        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*PAGE_SIZE;

            theme.setPageSize(-1);
            theme.setOffset(-1);

            //取总数
            int countNum = service.themeSearch(theme).size();
            //共分几页
            int pageCount = countNum/PAGE_SIZE;

            if(countNum%PAGE_SIZE!=0){
                pageCount = countNum/PAGE_SIZE+1;
            }

            theme.setPageSize(PAGE_SIZE);
            theme.setOffset(offset);

            List<Theme> themeList = service.themeSearch(theme);
            List<Theme> resultList = new ArrayList<>();
            for(Theme tempTheme:themeList){
                JsonNode themeImg = Json.parse(tempTheme.getThemeImg());
                //url
                String themeImgUrl = themeImg.get("url").toString();
                String resultUrl = themeImgUrl.substring(1,themeImgUrl.length()-1);
                tempTheme.setThemeImg(resultUrl);
                resultList.add(tempTheme);
            }
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",resultList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",PAGE_SIZE);

            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 滚动条弹窗  主题列表和商品列表
     * @return sliderPop.scala.html
     */
    @Security.Authenticated(UserAuth.class)
    public Result sliderPop(){
        //主题列表
        List<Theme> themeList = service.getThemesAll();
        //含有主商品的主sku图的主题列表
        List<Object[]> thList = new ArrayList<>();
        for(Theme theme : themeList) {
            Object[] object = new Object[6];
            Item item = itemService.getItem(theme.getMasterItemId());
            Inventory inventory = inventoryService.getInventoriesByItemId(item.getId()).get(0);
            object[0] = theme.getId();
            object[1] = theme.getThemeImg();
            object[2] = theme.getMasterItemId();
            object[3] = inventory.getInvImg();
            object[4] = theme.getStartAt();
            object[5] = theme.getEndAt();
            thList.add(object);
        }
        //商品列表
        List<Item> itemList = itemService.getItemsAll();
        //含有主sku价格的商品列表
        List<Object[]> itList = new ArrayList<>();
        for(Item item : itemList) {
            Object[] object = new Object[9];
            Logger.error(item.toString());
            List<Inventory> inventoryList = inventoryService.getInventoriesByItemId(item.getId());
            Inventory inventory = inventoryList.get(0);
            object[0] = item.getId();
            object[1] = item.getItemTitle();
            object[2] = item.getItemMasterImg();
            object[3] = item.getOnShelvesAt();
            object[4] = item.getOffShelvesAt();
            object[5] = item.getState();
            object[6] = inventory.getItemPrice();
            object[7] = inventory.getItemSrcPrice();
            object[8] = inventory.getItemDiscount();
            itList.add(object);
        }
        return ok(views.html.theme.sliderPop.render(thList,itList,IMAGE_URL));
    }

    /**
     * 主题录入弹窗
     * @return thaddPop.scala.html
     */
    @Security.Authenticated(UserAuth.class)
    public Result thaddPop(){
        //所有sku列表
        List<Inventory> inventoryList = inventoryService.getAllInventories();
        List<Object[]> inList = new ArrayList<>();
        for(Inventory inventory : inventoryList){
            Item item = itemService.getItem(inventory.getItemId());
            Object[] object = new Object[8];
            object[0] = inventory.getId();
            object[1] = item.getItemTitle();
            JsonNode json = Json.parse(inventory.getInvImg());
            String url = json.get("url").toString();
            object[2] = url.substring(1,url.length()-1);
            object[3] = inventory.getStartAt();
            if("Y".equals(item.getState())){
                object[4] = "正常";

            }
            if("D".equals(item.getState())){
                object[4] = "下架";

            }
            if("N".equals(item.getState())){
                object[4] = "删除";

            }
            if("K".equals(item.getState())){
                object[4] = "售空";

            }
            if("P".equals(item.getState())){
                object[4] = "预售";

            }
            object[5] = inventory.getItemCostPrice();
            object[6] = inventory.getItemSrcPrice();
            object[7] = inventory.getItemDiscount();
            inList.add(object);
        }

        //拼购列表
        List<PinSku> pinSkuList = pingouService.getPinSkuAll();
        List<Object[]> pinList = new ArrayList<>();
        for(PinSku pinSku : pinSkuList){
            Object[] object = new Object[8];
            object[0] = pinSku.getPinId();
            object[1] = pinSku.getPinTitle();
            JsonNode json = Json.parse(pinSku.getPinImg());
            String url = json.get("url").toString();
            object[2] = url.substring(1,url.length()-1);
            object[3] = pinSku.getStartAt();
            if("Y".equals(pinSku.getStatus())){
                object[4] = "正常";

            }
            if("D".equals(pinSku.getStatus())){
                object[4] = "下架";

            }
            if("N".equals(pinSku.getStatus())){
                object[4] = "删除";

            }
            if("K".equals(pinSku.getStatus())){
                object[4] = "售空";

            }
            if("P".equals(pinSku.getStatus())){
                object[4] = "预售";

            }
            object[5] = pinSku.getFloorPrice();
            object[6] = inventoryService.getInventory(pinSku.getInvId()).getItemSrcPrice();
            object[7] = pinSku.getPinDiscount();
            pinList.add(object);
        }

        //多样化商品列表
        List<VaryPrice> varyPriceList = varyPriceService.getAllVaryPrices();
        List<Object[]> varyList = new ArrayList<>();
        for(VaryPrice varyPrice : varyPriceList){
            Inventory inventory = inventoryService.getInventory(varyPrice.getInvId());
            Item item = itemService.getItem(inventory.getItemId());
            Object[] object = new Object[8];
            object[0] = varyPrice.getId();
            object[1] = item.getItemTitle();
            String url = Json.parse(inventory.getInvImg()).get("url").toString();
            url = url.substring(1,url.length()-1);
            object[2] = url;
            object[3] = inventory.getStartAt();
            if("Y".equals(inventory.getState())){
                object[4] = "正常";
            }
            if("N".equals(inventory.getState())){
                object[4] = "下架";
            }
            object[5] = varyPrice.getPrice();
            object[6] = inventory.getItemSrcPrice();
            object[7] = varyPrice.getPrice().divide(inventory.getItemSrcPrice(),2);
            varyList.add(object);
        }

        return ok(views.html.theme.thaddPop.render(inList,pinList,varyList,IMAGE_URL));
    }


    /**
     * 保存主题   Added by Tiffany Zhu
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result themeSave(String lang){
        JsonNode json = request().body().asJson();
        Logger.error(json.toString());
        service.themeSave(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 获取主题模板 Added by Tiffany Zhu 2016.01.09
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result themeTemplates(String lang){
        List<ThemeTemplate> templateList = service.getTemplatesAll();
        return ok(views.html.theme.templates.render(lang,templateList,IMAGE_URL,IMG_UPLOAD_URL,(User) ctx().args.get("user")));

    }

    /**
     * 保存主题模板 Added by Tiffany Zhu 2016.01.13
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result themeTemplateSave(String lang){
        JsonNode json = request().body().asJson();
        Logger.error(json.toString());
        ThemeTemplate themeTemplate = Json.fromJson(json,ThemeTemplate.class);
        service.themeTemplateSave(themeTemplate);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }




    /**
     * 通过Id更新主题  Added by Tiffany Zhu 2015.12.30
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result updateThemeById(String lang,Long id){
        Theme theme = service.getThemeById(id);
        Logger.error(theme.toString());
        //主题的商品
        List<Object[]> itemList = new ArrayList<>();
        JsonNode ids = Json.parse(theme.getThemeItem());
        int itemNum = 0;
        for(JsonNode tempId : ids){
            itemNum = itemNum + 1;
            //普通商品sku
            String type = tempId.get("type").toString();
            String resultType = type.substring(1,type.length()-1);
            if("item".equals(resultType)){
                Object[] object = new Object[10];
                Inventory inventory = inventoryService.getInventory(tempId.get("id").asLong());
                Logger.error(inventory.toString());
                Item item = itemService.getItem(inventory.getItemId());
                Logger.error(inventory.toString());
                object[0] = inventory.getId();
                object[1] = item.getItemTitle();
               String  url = Json.parse(inventory.getInvImg()).get("url").toString();
                url = url.substring(1,url.length()-1);
                object[2] = url;
                object[3] = item.getOnShelvesAt().toString().substring(0,19);
                if("Y".equals(item.getState())){
                    object[4] = "正常";

                }
                if("D".equals(item.getState())){
                    object[4] = "下架";

                }
                if("N".equals(item.getState())){
                    object[4] = "删除";

                }
                if("K".equals(item.getState())){
                    object[4] = "售空";

                }
                if("P".equals(item.getState())){
                    object[4] = "预售";

                }
                object[5] = inventory.getItemPrice();
                object[6] = inventory.getItemSrcPrice();
                object[7] = inventory.getItemDiscount();
                object[8] = itemNum;
                object[9] = "普通";
                itemList.add(object);
            }

            if("pin".equals(resultType)){
                Object[] object = new Object[10];
                PinSku pinSku = pingouService.getPinSkuById(tempId.get("id").asLong());
                Inventory inventory = inventoryService.getInventory(pinSku.getInvId());
                Logger.error(pinSku.toString());
                object[0] = pinSku.getPinId();
                object[1] = pinSku.getPinTitle();
                String url = Json.parse(pinSku.getPinImg()).get("url").toString();
                url = url.substring(1,url.length()-1);
                object[2] = url;
                object[3] = pinSku.getStartAt().toString().substring(0,19);
                if("Y".equals(pinSku.getStatus())){
                    object[4] = "正常";

                }
                if("D".equals(pinSku.getStatus())){
                    object[4] = "下架";

                }
                if("N".equals(pinSku.getStatus())){
                    object[4] = "删除";

                }
                if("K".equals(pinSku.getStatus())){
                    object[4] = "售空";

                }
                if("P".equals(pinSku.getStatus())){
                    object[4] = "预售";

                }
                object[5] = pinSku.getFloorPrice();
                object[6] = inventory.getItemSrcPrice();
                object[7] = pinSku.getPinDiscount();
                object[8] = itemNum;
                object[9] = "拼购";
                itemList.add(object);
            }

            if("vary".equals(resultType)){
                Object[] object = new Object[10];
                VaryPrice varyPrice = varyPriceService.getVaryPriceById(tempId.get("id").asLong());
                Inventory inventory = inventoryService.getInventory(varyPrice.getInvId());
                Item item = itemService.getItem(inventory.getItemId());
                object[0] = varyPrice.getId();
                object[1] = item.getItemTitle();
                String url = Json.parse(inventory.getInvImg()).get("url").toString();
                url = url.substring(1,url.length()-1);
                object[2] = url;
                object[3] = inventory.getStartAt().toString().substring(0,19);
                if("Y".equals(varyPrice.getStatus())){
                    object[4] = "正常";

                }
                if("N".equals(varyPrice.getStatus())){
                    object[4] = "下架";

                }
                object[5] = varyPrice.getPrice();
                object[6] = inventory.getItemSrcPrice();
                object[7] = varyPrice.getPrice().divide(inventory.getItemSrcPrice(),2);
                object[8] = itemNum;
                object[9] = "多样化";
                itemList.add(object);
            }
        }
        //主题的主宣传图
        JsonNode themeImg = Json.parse(theme.getThemeImg());
        Object[] themeImgObject = new Object[3];
        //url
        String themeImgUrl = themeImg.get("url").toString();
        themeImgObject[0] = themeImgUrl.substring(1,themeImgUrl.length()-1);
        //width
        String themeImgWidth = themeImg.get("width").toString();
        themeImgObject[1] = themeImgWidth.substring(1,themeImgWidth.length()-1);
        //height
        String themeImgHeight =  themeImg.get("height").toString();
        themeImgObject[2] = themeImgHeight.substring(1,themeImgHeight.length()-1);

        //主题的首页主图
        JsonNode themeMasterImg = Json.parse(theme.getThemeMasterImg());
        Object[] masterImgObject = new Object[3];
        //url
        String masterImgUrl = themeMasterImg.get("url").toString();
        masterImgObject[0] = masterImgUrl.substring(1,masterImgUrl.length()-1);
        //width
        String masterImgWidth = themeMasterImg.get("width").toString();
        masterImgObject[1] = masterImgWidth.substring(1,masterImgWidth.length()-1);
        //masterImgObject[1] = masterImgWidth;
        //height
        String masterImgHeight = themeMasterImg.get("height").toString();
        masterImgObject[2] = masterImgHeight.substring(1,masterImgHeight.length()-1);
        //masterImgObject[2] = masterImgHeight;

        //主题的首页主图的标签
        List<Object[]> tagList = new ArrayList<>();
        JsonNode itemMasterTag = Json.parse(theme.getMasterItemTag());
        for(JsonNode tag : itemMasterTag){
            Logger.error(tag.toString());
            Object[] tagObject = new Object[5];
            //top
            tagObject[0] = tag.get("top").floatValue()*100 + "%" ;
            //url
            String tag_url = tag.get("url").toString();
            tagObject[1] = (tag_url.substring(2,tag_url.length()-1)).substring(12);
            //left
            tagObject[2] = tag.get("left").floatValue() * 100 + "%";
            //name
            String tag_name = tag.get("name").toString();
            tagObject[3] = tag_name.substring(1,tag_name.length()-1);
            //angle
            tagObject[4] = tag.get("angle").toString();
            tagList.add(tagObject);
        }

        return ok(views.html.theme.themeUpdate.render(lang,theme,itemList,themeImgObject,masterImgObject,tagList,IMAGE_URL,IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }


}

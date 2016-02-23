package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.*;
import entity.pingou.PinSku;
import filters.UserAuth;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints;
import play.data.validation.Validation;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.*;

import javax.inject.Inject;
import java.math.BigDecimal;
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

    @Inject
    private SubjectPriceService subjectPriceService;

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
            if(tempTheme.getType().equals("ordinary")){
                tempTheme.setType("普通");
            }
            if(tempTheme.getType().equals("h5")){
                tempTheme.setType("HTML5");
            }
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
                if(tempTheme.getType().equals("ordinary")){
                    tempTheme.setType("普通");
                }
                if(tempTheme.getType().equals("h5")){
                    tempTheme.setType("HTML5");
                }
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
        //SKU列表
        List<Inventory> inventoryList = inventoryService.getAllInventories();
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
            object[4] = pinSku.getStatus();
            object[5] = pinSku.getFloorPrice();
            object[6] = inventoryService.getInventory(pinSku.getInvId()).getItemSrcPrice();
            object[7] = pinSku.getPinDiscount();
            pinList.add(object);
        }
        return ok(views.html.theme.sliderPop.render(themeList,inventoryList,pinList,IMAGE_URL));
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
            Object[] object = new Object[9];
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
            JsonNode floorPriceJson = Json.parse(pinSku.getFloorPrice());
            BigDecimal price = floorPriceJson.get("price").decimalValue();
            object[5] = price;
            object[6] = inventoryService.getInventory(pinSku.getInvId()).getItemSrcPrice();
            object[7] = pinSku.getPinDiscount();
            object[8] = pinSku.getInvId();
            pinList.add(object);
        }

        //多样化商品列表
        List<VaryPrice> varyPriceList = varyPriceService.getAllVaryPrices();
        List<Object[]> varyList = new ArrayList<>();
        for(VaryPrice varyPrice : varyPriceList){
            Inventory inventory = inventoryService.getInventory(varyPrice.getInvId());
            Item item = itemService.getItem(inventory.getItemId());
            Object[] object = new Object[9];
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
            object[8] = varyPrice.getInvId();
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
        JsonNode jsonRequest = request().body().asJson();
        JsonNode json = jsonRequest.findValue("theme");
        Logger.error(json.toString());
        JsonNode ids = null;
        if(json.has("themeDesc")){
            ((ObjectNode)json).put("themeDesc",json.findValue("themeDesc").toString());
        }
        if(json.has("themeItem")){
            ids = json.findValue("themeItem");
            ((ObjectNode)json).put("themeItem",json.findValue("themeItem").toString());
        }
        if(json.has("themeTags")) {
            ((ObjectNode) json).put("themeTags", json.findValue("themeTags").toString());
        }
        Logger.error(json.toString());
        Theme theme = play.libs.Json.fromJson(json,Theme.class);
        Form<Theme> themeForm = Form.form(Theme.class);
        if(themeForm.hasErrors()){
            return badRequest();
        }
        theme.setOrDestory(false);
        service.themeSave(theme);

        //添加主题Id到商品中
        for(JsonNode idBar : ids){
            String type = idBar.findValue("type").toString();
            type = type.substring(1,type.length()-1);
            Long  id = idBar.get("id").asLong();
            //普通商品
            if("item".equals(type)){
                Inventory inventory = inventoryService.getInventory(id);
                String themeIds = inventory.getThemeId();
                if (themeIds== null || "".equals(themeIds)){
                    inventory.setThemeId(theme.getId().toString());
                }else{
                    if(!themeIds.contains(theme.getId().toString())){
                        themeIds = themeIds + "," + theme.getId().toString();
                        inventory.setThemeId(themeIds);
                    }
                }
                inventoryService.updInventoryThemeId(inventory);
            }
            //拼购商品
            if("pin".equals(type)){
                PinSku pinSku = pingouService.getPinSkuById(id);
                String themeIds = pinSku.getThemeId();
                if (themeIds== null || "".equals(themeIds)){
                    pinSku.setThemeId(theme.getId().toString());
                }else{
                    if(!themeIds.contains(theme.getId().toString())){
                        themeIds = themeIds + "," + theme.getId().toString();
                        pinSku.setThemeId(themeIds);
                    }
                }
                pingouService.updPinThemeId(pinSku);
            }
            //多样化商品
            if("vary".equals(type)){
                Logger.error(id.toString());
                VaryPrice varyPrice = varyPriceService.getVaryPriceById(id);
                String themeIds = varyPrice.getThemeId();
                if ( themeIds== null || "".equals(themeIds)){
                    varyPrice.setThemeId(theme.getId().toString());
                }else{
                    if(!themeIds.contains(theme.getId().toString())){
                        themeIds = themeIds + "," + theme.getId().toString();
                        varyPrice.setThemeId(themeIds);
                    }
                }
                varyPriceService.updVaryThemeId(varyPrice);
            }
        }

        //删除商品中主题ID
        JsonNode beforeUpdJson = jsonRequest.findValue("beforeUpdItems");
        if(beforeUpdJson.size() > 0){
            for(JsonNode beforeItem : beforeUpdJson){
                String beforeId = beforeItem.get("id").toString();
                //最新的商品中不包含变更前的商品
                if(!theme.getThemeItem().contains(beforeId)){
                    String type = beforeItem.get("type").toString();
                    type = type.substring(1,type.length()-1);
                    Long id = beforeItem.get("id").asLong();
                    //普通商品
                    if("item".equals(type)){
                        Inventory inventory = inventoryService.getInventory(id);
                        String themeIds = inventory.getThemeId();
                        if (themeIds != null){
                            if(themeIds.indexOf(theme.getId().toString()) == 0) {
                                themeIds = themeIds.replace(theme.getId().toString(),"") ;
                            }else{
                                themeIds = themeIds.replace("," + theme.getId().toString(),"") ;
                            }
                            inventory.setThemeId(themeIds);
                            inventoryService.updInventoryThemeId(inventory);
                        }
                    }
                    //拼购商品
                    if("pin".equals(type)){
                        PinSku pinSku = pingouService.getPinSkuById(id);
                        String themeIds = pinSku.getThemeId();
                        if (themeIds != null){
                            if(themeIds.indexOf(theme.getId().toString()) == 0) {
                                themeIds = themeIds.replace(theme.getId().toString(),"") ;
                            }else{
                                themeIds = themeIds.replace("," + theme.getId().toString(),"") ;
                            }
                            pinSku.setThemeId(themeIds);
                            pingouService.updPinThemeId(pinSku);
                        }
                    }
                    //多样化商品
                    if("vary".equals(type)){
                        VaryPrice varyPrice = varyPriceService.getVaryPriceById(id);
                        String themeIds = varyPrice.getThemeId();
                        if (themeIds != null){
                            if(themeIds.indexOf(theme.getId().toString()) == 0) {
                                themeIds = themeIds.replace(theme.getId().toString(),"") ;
                            }else{
                                themeIds = themeIds.replace("," + theme.getId().toString(),"") ;
                            }
                            varyPrice.setThemeId(themeIds);
                            varyPriceService.updVaryThemeId(varyPrice);
                        }
                    }
                }
            }
        }

        //创建自定义商品
        JsonNode customizeItems = jsonRequest.findValue("customizeItems");
        if(customizeItems.size() > 0){
            //JsonNode themeItem = json.findValue("themeItem");
            for(JsonNode customizeItem : customizeItems){
                SubjectPrice subjectPrice = Json.fromJson(customizeItem,SubjectPrice.class);
                subjectPrice.setThemeId(theme.getId());
                subjectPriceService.sbjPriceSave(subjectPrice);
                for(int i=0;i<ids.size();i++){
                    String type = ids.get(i).get("type").toString();
                    type = type.substring(1,type.length()-1);
                    Long itemId = ids.get(i).get("id").asLong();
                    if("customize".equals(type) && itemId.equals(subjectPrice.getInvId())){
                        ((ObjectNode)ids.get(i)).put("id",subjectPrice.getId().toString());
                    }
                }
            }
            //更新主题商品
            Theme updTheme = new Theme();
            updTheme.setId(theme.getId());
            updTheme.setThemeItem(ids.toString());
            service.updThemeItems(updTheme);
        }

        //删除自定义商品
        List<SubjectPrice> subjectPriceList = subjectPriceService.getSbjPriceByThemeId(theme.getId());
        if(customizeItems.size() > 0){
            for(SubjectPrice sbjPrice : subjectPriceList){
                if(customizeItems.toString().indexOf(sbjPrice.getInvId().toString())<0){
                    subjectPriceService.sbjPriceDelById(sbjPrice.getId());
                }
            }
        }else{
            subjectPriceService.getSbjPriceByThemeId(theme.getId());
        }
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
        //H5主题
        if(theme.getType().equals("h5")){
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
            return ok(views.html.theme.H5ThemeUpd.render(lang,theme,themeImgObject,IMAGE_URL,IMG_UPLOAD_URL,(User) ctx().args.get("user")));

        }
        //主题的商品
        List<Object[]> itemList = new ArrayList<>();
        if(theme.getThemeItem() != null && !("".equals(theme.getThemeItem()))){
            JsonNode ids = Json.parse(theme.getThemeItem());
            int itemNum = 0;
            for(JsonNode tempId : ids){
                itemNum = itemNum + 1;
                //普通商品sku
                String type = tempId.get("type").toString();
                String resultType = type.substring(1,type.length()-1);
                if("item".equals(resultType)){
                    Object[] object = new Object[11];
                    Inventory inventory = inventoryService.getInventory(tempId.get("id").asLong());
                    if(inventory != null){
                        object[0] = inventory.getId();
                        object[1] = inventory.getInvTitle();
                        String  url = Json.parse(inventory.getInvImg()).get("url").toString();
                        url = url.substring(1,url.length()-1);
                        object[2] = url;
                        object[3] = inventory.getStartAt().toString().substring(0,19);
                        if("Y".equals(inventory.getState())){
                            object[4] = "正常";

                        }
                        if("D".equals(inventory.getState())){
                            object[4] = "下架";

                        }
                        if("N".equals(inventory.getState())){
                            object[4] = "删除";

                        }
                        if("K".equals(inventory.getState())){
                            object[4] = "售空";

                        }
                        if("P".equals(inventory.getState())){
                            object[4] = "预售";

                        }
                        object[5] = inventory.getItemPrice();
                        object[6] = inventory.getItemSrcPrice();
                        object[7] = inventory.getItemDiscount();
                        object[8] = itemNum;
                        object[9] = "普通";
                        object[10] = inventory.getId();
                        itemList.add(object);
                    }
                }

                if("pin".equals(resultType)){
                    Object[] object = new Object[11];
                    PinSku pinSku = pingouService.getPinSkuById(tempId.get("id").asLong());
                    if(pinSku != null){
                        Inventory inventory = inventoryService.getInventory(pinSku.getInvId());
                        object[0] = pinSku.getInvId();
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
                        JsonNode floorPrice = Json.parse(pinSku.getFloorPrice());
                        object[5] = floorPrice.get("price");
                        object[6] = inventory.getItemSrcPrice();
                        object[7] = pinSku.getPinDiscount();
                        object[8] = itemNum;
                        object[9] = "拼购";
                        object[10] = pinSku.getPinId();
                        itemList.add(object);
                    }
                }

                if("vary".equals(resultType)){
                    Object[] object = new Object[11];
                    VaryPrice varyPrice = varyPriceService.getVaryPriceById(tempId.get("id").asLong());
                    if(varyPrice != null){
                        Inventory inventory = inventoryService.getInventory(varyPrice.getInvId());
                        Item item = itemService.getItem(inventory.getItemId());
                        object[0] = varyPrice.getInvId();
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
                        object[10] = varyPrice.getId();
                        itemList.add(object);
                    }

                }
                if("customize".equals(resultType)){
                    Object[] object = new Object[11];
                    SubjectPrice subjectPrice = subjectPriceService.getSbjPriceById(tempId.get("id").asLong());
                    if(subjectPrice != null){
                        Inventory inventory = inventoryService.getInventory(subjectPrice.getInvId());
                        Item item = itemService.getItem(inventory.getItemId());
                        object[0] = subjectPrice.getInvId();
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
                        object[5] = subjectPrice.getPrice();
                        object[6] = inventory.getItemSrcPrice();
                        object[7] = subjectPrice.getDiscount();
                        object[8] = itemNum;
                        object[9] = "自定义";
                        object[10] = subjectPrice.getId();
                        itemList.add(object);
                    }
                }
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
        //height
        String masterImgHeight = themeMasterImg.get("height").toString();
        masterImgObject[2] = masterImgHeight.substring(1,masterImgHeight.length()-1);

        //主题的首页主图的标签
        List<Object[]> tagList = new ArrayList<>();
        if(theme.getMasterItemTag() != null) {
            JsonNode itemMasterTag = Json.parse(theme.getMasterItemTag());
            for (JsonNode tag : itemMasterTag) {
                Logger.error(tag.toString());
                Object[] tagObject = new Object[6];
                //top
                tagObject[0] = tag.get("top").floatValue() * 100 + "%";
                //url
            /*
            String tag_url = tag.get("url").toString();
            tagObject[1] = (tag_url.substring(2,tag_url.length()-1)).substring(12);
            */
                JsonNode URLJson = tag.get("url");
                String url_id = URLJson.get("id").toString();
                url_id = url_id.substring(1, url_id.length() - 1);
                tagObject[1] = url_id;
                String url_type = URLJson.get("type").toString();
                url_type = url_type.substring(1, url_type.length() - 1);
                tagObject[5] = url_type;
                //left
                tagObject[2] = tag.get("left").floatValue() * 100 + "%";
                //name
                String tag_name = tag.get("name").toString();
                tagObject[3] = tag_name.substring(1, tag_name.length() - 1);
                //angle
                tagObject[4] = tag.get("angle").toString();
                tagList.add(tagObject);
            }
        }
        return ok(views.html.theme.themeUpdate.render(lang,theme,itemList,themeImgObject,masterImgObject,tagList,IMAGE_URL,IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }

    /**
     * 添加H5主题   Added by Tiffany Zhu 2016.02.01
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result addH5Theme(String lang){
        return ok(views.html.theme.H5ThemeAdd.render(lang,IMAGE_URL,IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }

    /**
     * 保存H5主题   Added by Tiffany Zhu 2016.02.01
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result h5ThemeSave(String lang){
        JsonNode json = request().body().asJson();
        Theme theme = Json.fromJson(json,Theme.class);
        Logger.error(json.toString());
        service.h5ThemeSave(theme);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

}

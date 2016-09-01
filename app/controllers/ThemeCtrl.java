package controllers;

import actor.ThemeDestroyActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
import domain.*;
import domain.pingou.PinSku;
import filters.UserAuth;
import modules.NewScheduler;
import play.Logger;
import play.data.Form;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Akka;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import service.*;
import util.RedisPool;
import util.Regex;
import util.SysParCom;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * ThemeCtrl management.
 * Created by howen on 15/10/28.
 */
public class ThemeCtrl extends Controller {

    //每页固定的取数
    public static final int PAGE_SIZE = 10;


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

    @Inject
    private NewScheduler newScheduler;

    @Inject
    private IDService idService;



    /**
     * 滚动条管理
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result slider(String lang) {
        List<Slider> sliderList = service.sliderAll();
        for (Slider slider : sliderList) {
            JsonNode img = Json.parse(slider.getImg());
            String imgUrl = "0";
            String width = "0";
            String height = "0";
            if (null!=img.get("url") && null!=img.get("width") && null!=img.get("height")) {
                imgUrl = img.get("url").asText();
                width = img.get("width").asText();
                height = img.get("height").asText();
            }
            slider.setImg(imgUrl+","+width+","+height);
        }
        return ok(views.html.theme.slider.render(lang,sliderList, SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }


    /**
     * 分类入口         Added by Tiffany Zhu 2016.07.27
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result category(String lang) {
        List<Slider> sliderList = service.getCategoryAll();
        for (Slider slider : sliderList) {
            JsonNode img = Json.parse(slider.getImg());
            String imgUrl = "0";
            String width = "0";
            String height = "0";
            if (null!=img.get("url") && null!=img.get("width") && null!=img.get("height")) {
                imgUrl = img.get("url").asText();
                width = img.get("width").asText();
                height = img.get("height").asText();
            }
            slider.setImg(imgUrl+","+width+","+height);
        }
        return ok(views.html.theme.category.render(lang,sliderList, SysParCom.CATEGORYCOUNT,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }

    /**
     * 分类入口保存         Added by Tiffany Zhu 2016.08.02
     * @param lang  语言
     * @return  view
     */
    @Security.Authenticated(UserAuth.class)
    public Result categorySave(String lang){
        JsonNode json = request().body().asJson();
        if (json.findValue("update").isArray()) {
            for (final JsonNode jsonNode : json.findValue("update")) {
                Form<Slider> sliderForm = Form.form(Slider.class).bind(jsonNode);
                Slider slider = Json.fromJson(jsonNode, Slider.class);
                //数据验证
                if (sliderForm.hasErrors() || !Regex.isJason(slider.getImg())) {
                    Logger.error("slider 表单数据有误.....");
                    return badRequest();
                }
            }
        }
//        Logger.error("数据:" + json);
        service.categorySave(json);


        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 分类入口  主题列表和商品列表  Added by Tiffany Zhu 2016.07.27
     * @return categoryPop.scala.html
     */
    @Security.Authenticated(UserAuth.class)
    public Result categoryPop(){
        //主题列表
        List<Theme> themeList = service.getCategoryThemes();
        List<Theme> tList = new ArrayList<>();
        String strNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//现在时间
        for(Theme theme : themeList) {
            if (theme.getEndAt().compareTo(strNow)>0) {
                theme.setThemeImg(Json.parse(theme.getThemeImg()).get("url").asText());
                tList.add(theme);
            }
        }
        List<Skus> skusList = inventoryService.getAllSkus();
        List<Skus> slist = new ArrayList<>();
        for(Skus skus : skusList) {//商品列表为(除自定义价格和多样化价格的预售和正常商品)
            if (!skus.getSkuType().equals("customize")&&!skus.getSkuType().equals("vary")&&(skus.getSkuTypeStatus().equals("P")||skus.getSkuTypeStatus().equals("Y"))) {
                skus.setSkuTypeImg(Json.parse(skus.getSkuTypeImg()).get("url").asText());
                slist.add(skus);
            }
        }

        //List<Cates> catesList = itemService.getSecDirectCates();
        List<Cates> catesList = itemService.getCatesAll();
        Logger.error("商品分类数据:" + catesList);
        if (themeList.size()>0 && skusList.size()>0 && catesList.size() > 0 ) {
            return ok(views.html.theme.categoryPop.render(tList,slist,catesList,SysParCom.IMAGE_URL));
        }
        else
            return ok("没有数据");
    }

    /**
     * 主题录入
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result thadd(String lang) {
//        flash("success", session("username"));
        return ok(views.html.theme.thadd.render(lang,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }


    /**
     * 滚动条变更保存
     * @param lang  语言
     * @return  view
     */
    @Security.Authenticated(UserAuth.class)
    public Result sliderSave(String lang){
        JsonNode json = request().body().asJson();
        if (json.findValue("update").isArray()) {
            for (final JsonNode jsonNode : json.findValue("update")) {
                Form<Slider> sliderForm = Form.form(Slider.class).bind(jsonNode);
                Slider slider = Json.fromJson(jsonNode, Slider.class);
                //数据验证
                if (sliderForm.hasErrors() || !Regex.isJason(slider.getImg())) {
                    Logger.error("slider 表单数据有误.....");
                    return badRequest();
                }
            }
        }
//        Logger.error("数据:" + json);
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
        service.updDestroy();

        Theme theme =new Theme();

        theme.setPageSize(-1);
        theme.setOffset(-1);

        //主题状态
        theme.setOrDestroy(false);

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
            if(tempTheme.getType().equals("detail")){
                tempTheme.setType("普通商品");
            }
            if(tempTheme.getType().equals("pin")){
                tempTheme.setType("拼购商品");
            }
            resultList.add(tempTheme);
        }
        //Logger.error(resultList.toString());

        return ok(views.html.theme.thsearch.render(lang,SysParCom.IMAGE_URL,PAGE_SIZE,countNum,pageCount,resultList,(User) ctx().args.get("user")));
    }

    /**
     * ajax分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result thsearchAjax(String lang,int pageNum) {

        service.updDestroy();
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
                if(tempTheme.getType().equals("detail")){
                    tempTheme.setType("普通商品");
                }
                if(tempTheme.getType().equals("pin")){
                    tempTheme.setType("拼购商品");
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
        List<Theme> tList = new ArrayList<>();
        String strNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//现在时间
        for(Theme theme : themeList) {
            if (theme.getEndAt().compareTo(strNow)>0 && (theme.getThemeState()==1 || theme.getThemeState()==3)) {
                theme.setThemeImg(Json.parse(theme.getThemeImg()).get("url").asText());
                tList.add(theme);
            }
        }
        List<Skus> skusList = inventoryService.getAllSkus();
        List<Skus> slist = new ArrayList<>();
        for(Skus skus : skusList) {//商品列表为(除自定义价格和多样化价格的预售和正常商品)
            if (!skus.getSkuType().equals("customize")&&!skus.getSkuType().equals("vary")&&(skus.getSkuTypeStatus().equals("P")||skus.getSkuTypeStatus().equals("Y"))) {
                skus.setSkuTypeImg(Json.parse(skus.getSkuTypeImg()).get("url").asText());
                slist.add(skus);
            }
        }
        if (themeList.size()>0 && skusList.size()>0) {
            return ok(views.html.theme.sliderPop.render(tList,slist,SysParCom.IMAGE_URL));
        }
        else
            return ok("没有数据");
    }

    /**
     * 主题录入弹窗
     * @return thaddPop.scala.html
     */
    @Security.Authenticated(UserAuth.class)
    public Result thaddPop(){
        //所有sku列表
        List<Inventory> inventoryList = inventoryService.getAvailableInventory();
        List<Object[]> inList = new ArrayList<>();
        for(Inventory inventory : inventoryList){
            Item item = itemService.getItem(inventory.getItemId());
            Object[] object = new Object[9];
            object[0] = inventory.getId();
            object[1] = inventory.getInvTitle();
            if(Regex.isJason(inventory.getInvImg())){
                JsonNode json = Json.parse(inventory.getInvImg());
                String url = json.get("url").toString();
                object[2] = url.substring(1,url.length()-1);
            }
            object[3] = inventory.getEndAt();

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
            object[8] = item.getId();
            inList.add(object);
        }

        //拼购列表
        List<PinSku> pinSkuList = pingouService.getAvailablePingou();
        List<Object[]> pinList = new ArrayList<>();
        for(PinSku pinSku : pinSkuList){
            Object[] object = new Object[10];
            object[0] = pinSku.getPinId();
            object[1] = pinSku.getPinTitle();
            if(Regex.isJason(pinSku.getPinImg())){
                JsonNode json = Json.parse(pinSku.getPinImg());
                String url = json.get("url").toString();
                object[2] = url.substring(1,url.length()-1);
            }
            object[3] = pinSku.getEndAt();
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
            if(Regex.isJason(pinSku.getFloorPrice())){
                JsonNode floorPriceJson = Json.parse(pinSku.getFloorPrice());
                BigDecimal price = floorPriceJson.get("price").decimalValue();
                object[5] = price;
            }
            if(inventoryService.getInventory(pinSku.getInvId()) != null){
                object[6] = inventoryService.getInventory(pinSku.getInvId()).getItemSrcPrice();
            }

            object[7] = pinSku.getPinDiscount();
            object[8] = pinSku.getInvId();
            Inventory inventory = inventoryService.getInventory(pinSku.getInvId());
            object[9] = inventory.getItemId();
            pinList.add(object);
        }

        //多样化商品列表
        List<VaryPrice> varyPriceList = varyPriceService.getAvailableVaryPrice();
        List<Object[]> varyList = new ArrayList<>();
        for(VaryPrice varyPrice : varyPriceList){
            Inventory inventory = inventoryService.getInventory(varyPrice.getInvId());

            Item item = itemService.getItem(inventory.getItemId());

            Object[] object = new Object[10];
            object[0] = varyPrice.getId();
            object[1] = item.getItemTitle();
            if(Regex.isJason(inventory.getInvImg())){
                String url = Json.parse(inventory.getInvImg()).get("url").toString();
                url = url.substring(1,url.length()-1);
                object[2] = url;
            }
            object[3] = inventory.getEndAt();
            if("Y".equals(varyPrice.getStatus())){
                object[4] = "正常";
            }
            if("D".equals(varyPrice.getStatus())){
                object[4] = "下架";
            }
            if("N".equals(varyPrice.getStatus())){
                object[4] = "删除";
            }
            if("K".equals(varyPrice.getStatus())){
                object[4] = "售空";
            }
            if("P".equals(varyPrice.getStatus())){
                object[4] = "预售";
            }
            object[6] = inventory.getItemSrcPrice();
            object[7] = varyPrice.getPrice().divide(inventory.getItemSrcPrice(),2);
            object[5] = varyPrice.getPrice();
            object[8] = varyPrice.getInvId();
            object[9] = inventory.getItemId();
            varyList.add(object);
        }
        return ok(views.html.theme.thaddPop.render(inList,pinList,varyList,SysParCom.IMAGE_URL));
    }


    /**
     * 保存主题   Added by Tiffany Zhu
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result themeSave(String lang){
        JsonNode jsonRequest = request().body().asJson();
        JsonNode json = jsonRequest.findValue("theme");
        Logger.error("主题数据~~~~:" + json);
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
        Theme theme = play.libs.Json.fromJson(json,Theme.class);
        //数据验证      ----start
        Form<Theme> themeForm = Form.form(Theme.class).bind(json);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间
        Date now = new Date();
        String strNow = sdfDate.format(now);
        //当前时间 + 6个月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH,+6);
        String validDate = sdfDate.format(calendar.getTime());
        //基本样式不匹配;主图片,商品ID不是Json格式;首页主图不是Json格式;主图标签不是Json格式;开始日期大于结束日期;
//        if(themeForm.hasErrors() || !(Regex.isJason(theme.getThemeImg())) || !(Regex.isJason(theme.getThemeItem())) || !(Regex.isJason(theme.getThemeMasterImg()))
//                || (theme.getMasterItemTag() != null && !(Regex.isJason(theme.getMasterItemTag()))) || (theme.getStartAt().compareTo(theme.getEndAt())>= 0) ||
//                theme.getEndAt().compareTo(strNow) < 0 || theme.getStartAt().compareTo(validDate) > 0 || theme.getEndAt().compareTo(validDate) > 0 ){
//            return badRequest();
//        }

        //基本样式不匹配;主图片,日期验证;       Modified by Tiffany Zhu 2016.07.29
        if(themeForm.hasErrors() || !(Regex.isJason(theme.getThemeImg())) || theme.getEndAt().compareTo(strNow) < 0 || theme.getStartAt().compareTo(validDate) > 0 || theme.getEndAt().compareTo(validDate) > 0 ){
            return badRequest();
        }
        //数据验证      ----end
        Logger.error("通过后台验证~~~~~~~~");
        service.themeSave(theme);

        //创建Scheduled Actor         ---start
        ActorRef themeOffShelf = Akka.system().actorOf(Props.create(ThemeDestroyActor.class,service));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endAt = null;
        try{
            endAt = sdf.parse(theme.getEndAt());
        }catch(Exception e){
            e.printStackTrace();
            Logger.error(Throwables.getStackTraceAsString(e));
        }
        if(endAt != null){
            FiniteDuration duration = Duration.create(endAt.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
            newScheduler.scheduleOnce(duration,themeOffShelf,theme.getId());
        }
        //创建Scheduled Actor         ---end

        //添加主题Id到商品中
        if (ids != null && ids.size() > 0){
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
                    //Logger.error(id.toString());
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
        }


        //删除商品中主题ID
        JsonNode beforeUpdJson = jsonRequest.findValue("beforeUpdItems");
        if(beforeUpdJson.size() > 0){
            for(JsonNode beforeItem : beforeUpdJson){
                String beforeId = beforeItem.get("id").toString();
                //最新的商品中不包含变更前的商品
                if(theme.getThemeItem() != null && !theme.getThemeItem().contains(beforeId)){
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
                Logger.error("保存自定义价格:" + subjectPrice);
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
                Logger.error("删除自定义价格:" + sbjPrice);
                if(customizeItems.toString().indexOf(sbjPrice.getInvId().toString())<0){
                    subjectPriceService.updStateById(sbjPrice.getId());
                }
            }
        }else{
            subjectPriceService.updStateByThemeId(theme.getId());
        }

//        //主题从下架到上架  更新自定商品的状态
        Theme themeTemp = service.getThemeById(theme.getId());
        if(!themeTemp.getOrDestroy() && customizeItems.size() > 0){
            List<SubjectPrice> subjectPriceList1 = new ArrayList<>();
            for(JsonNode customizeItem : customizeItems) {
                SubjectPrice subjectPrice = Json.fromJson(customizeItem,SubjectPrice.class);
                Inventory inventory = inventoryService.getInventory(subjectPrice.getInvId());
                if("Y".equals(inventory.getState())){
                    subjectPriceList1.add(subjectPrice);
                }
            }
            subjectPriceService.updStateOnShelf(subjectPriceList1);
        }

        //专用用户处理    Added by Tiffany Zhu 2016.08.23
        JsonNode usersJson = jsonRequest.findValue("users");
        if (usersJson != null && usersJson.size() >0){
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                //存入redis
                jedisPool = RedisPool.createPool();
                //验证 JedisPool
                if (jedisPool != null){
                    jedis = jedisPool.getResource();
                    //验证Jedis
                    if (jedis != null){
                        //用户专用主题
                        if (theme.getThemeState() == 2 && usersJson.size() > 0){
                            if (jedis.exists(theme.getId().toString())){
                                jedis.del(theme.getId().toString());
                            }
                            for (JsonNode node :usersJson){
                                ID id = Json.fromJson(node,ID.class);
                                jedis.sadd(theme.getId().toString(),id.getUserId().toString());
                            }
                            jedis.persist(theme.getId().toString());
                            //其他主题
                        }else {
                            if (jedis.exists(theme.getId().toString())){
                                jedis.del(theme.getId().toString());
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                Logger.error(Throwables.getStackTraceAsString(e));
            }finally {
//                if (jedisPool != null && jedis != null){
//                    jedisPool.returnBrokenResource(jedis);
//                }
            }
        }


        //主题显示位置
        JsonNode themeCateJson = jsonRequest.get("themeCates");
        List<ThemeCate> themeCateList = new ArrayList<>();
        service.delThemeCateByThemeId(theme.getId());
        for (JsonNode node:themeCateJson){
            ThemeCate themeCate = new ThemeCate();
            themeCate.setThemeId(theme.getId());
            themeCate.setThemeCateCode(node.asInt());
            if(node.asInt() == 1){
                themeCate.setThemeCateNm("首页");
            }
            if(node.asInt() == 2){
                themeCate.setThemeCateNm("拼购");
            }
            if(node.asInt() == 3){
                themeCate.setThemeCateNm("礼物");
            }
            themeCateList.add(themeCate);
        }
        service.addThemeCate(themeCateList);

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
        return ok(views.html.theme.templates.render(lang,templateList,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));

    }

    /**
     * 保存主题模板 Added by Tiffany Zhu 2016.01.13
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result themeTemplateSave(String lang){
        JsonNode json = request().body().asJson();
        //Logger.error(json.toString());
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
        //H5主题
        if(theme != null){
            //专用主题      Added by Tiffany Zhu 2016.08.23
            List<Object[]> idList = new ArrayList<>();
            if (theme.getThemeState() == 2){
                JedisPool jedisPool = null;
                Jedis jedis = null;
                try {
                    //存入redis
                    jedisPool = RedisPool.createPool();
                    //验证 JedisPool
                    if (jedisPool != null){
                        jedis = jedisPool.getResource();
                        //验证Jedis
                        if (jedis != null){
                            Set<String> setValues = jedis.smembers(id.toString());
                            int index = 1;
                            for(String userId : setValues){
                                ID user = idService.getID(Integer.valueOf(userId));
                                Object[] userObject = new Object[10];
                                userObject[0] = index;              //序号
                                userObject[1] = user.getUserId();   //用户ID
                                userObject[2] = user.getNickname(); //用户名
                                userObject[3] = user.getPhotoUrl(); //头像
                                if(user.getGender().equals("F")){   //性别
                                    userObject[4] = "女";
                                }else {
                                    userObject[4] = "男";
                                }
                                userObject[5] = user.getPhoneNum(); //电话号码
                                userObject[6] = user.getBirthday(); //生日
                                userObject[7] = user.getRegDt().toString().substring(0,19);    //注册日期
                                if (user.getIdType().equals("N")){          //用户类型
                                    userObject[8] = "普通手机用户";
                                }
                                if (user.getIdType().equals("W")){
                                    userObject[8] = "微信注册用户";
                                }
                                if (user.getIdType().equals("Q")){
                                    userObject[8] = "腾讯QQ用户";
                                }
                                if (user.getOrReal().equals("N")){  //是否实名
                                    userObject[9] = "否";
                                }else {
                                    userObject[9] = "是";
                                }
                                index++;
                                idList.add(userObject);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Logger.error(Throwables.getStackTraceAsString(e));
                }finally {
                    if (jedisPool != null && jedis != null){
                        jedisPool.returnBrokenResource(jedis);
                    }
                }
            }

            //主题显示位置
            List<ThemeCate> themeCateList = service.getThemeCate(id);
            boolean indexPage = false;
            boolean pinPage = false;
            boolean giftPage = false;
            for (ThemeCate themeCate : themeCateList){
                if(themeCate.getThemeCateCode() == 1){
                    indexPage = true;
                }else if (themeCate.getThemeCateCode() == 2){
                    pinPage = true;
                }else {
                    giftPage = true;
                }

            }
            if(theme.getType().equals("h5")){
                //主题的主宣传图
                JsonNode themeImg = Json.parse(theme.getThemeImg());
                Object[] themeImgObject = new Object[3];
                //url
                String themeImgUrl = themeImg.get("url").toString();
                themeImgObject[0] = themeImgUrl.substring(1,themeImgUrl.length()-1);
                //width
                themeImgObject[1] = themeImg.get("width").asInt();
                //height
                themeImgObject[2] = themeImg.get("height").asInt();
                return ok(views.html.theme.H5ThemeUpd.render(lang,theme,themeImgObject,idList,indexPage,pinPage,giftPage,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));

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
                        Object[] object = new Object[12];
                        Inventory inventory = inventoryService.getInventory(tempId.get("id").asLong());
                        if(inventory != null){
                            object[0] = inventory.getId();
                            object[1] = inventory.getInvTitle();
                            String  url = Json.parse(inventory.getInvImg()).get("url").toString();
                            url = url.substring(1,url.length()-1);
                            object[2] = url;
                            object[3] = inventory.getEndAt().toString().substring(0,19);
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
                            object[11] = inventory.getItemId();
                            itemList.add(object);
                        }
                    }

                    if("pin".equals(resultType)){
                        Object[] object = new Object[12];
                        PinSku pinSku = pingouService.getPinSkuById(tempId.get("id").asLong());
                        if(pinSku != null){
                            Inventory inventory = inventoryService.getInventory(pinSku.getInvId());
                            object[0] = pinSku.getInvId();
                            object[1] = pinSku.getPinTitle();
                            String url = Json.parse(pinSku.getPinImg()).get("url").toString();
                            url = url.substring(1,url.length()-1);
                            object[2] = url;
                            object[3] = pinSku.getEndAt().toString().substring(0,19);
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
                            object[11] = inventory.getItemId();
                            itemList.add(object);
                        }
                    }

                    if("vary".equals(resultType)){
                        Object[] object = new Object[12];
                        VaryPrice varyPrice = varyPriceService.getVaryPriceById(tempId.get("id").asLong());
                        if(varyPrice != null){
                            Inventory inventory = inventoryService.getInventory(varyPrice.getInvId());
                            Item item = itemService.getItem(inventory.getItemId());
                            object[0] = varyPrice.getInvId();
                            object[1] = item.getItemTitle();
                            String url = Json.parse(inventory.getInvImg()).get("url").toString();
                            url = url.substring(1,url.length()-1);
                            object[2] = url;
                            object[3] = inventory.getEndAt().toString().substring(0,19);
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
                            object[11] = inventory.getItemId();
                            itemList.add(object);
                        }

                    }
                    if("customize".equals(resultType)){
                        Object[] object = new Object[12];
                        SubjectPrice subjectPrice = subjectPriceService.getSbjPriceById(tempId.get("id").asLong());
                        if(subjectPrice != null){
                            Inventory inventory = inventoryService.getInventory(subjectPrice.getInvId());
                            Item item = itemService.getItem(inventory.getItemId());
                            object[0] = subjectPrice.getInvId();
                            object[1] = item.getItemTitle();
                            String  url = Json.parse(inventory.getInvImg()).get("url").toString();
                            url = url.substring(1,url.length()-1);
                            object[2] = url;
                            object[3] = theme.getEndAt().toString().substring(0,19);
                            if("Y".equals(subjectPrice.getState())){
                                object[4] = "正常";

                            }
                            if("D".equals(subjectPrice.getState())){
                                object[4] = "下架";

                            }
                            if("N".equals(subjectPrice.getState())){
                                object[4] = "删除";

                            }
                            if("K".equals(subjectPrice.getState())){
                                object[4] = "售空";

                            }
                            if("P".equals(subjectPrice.getState())){
                                object[4] = "预售";

                            }
                            object[5] = subjectPrice.getPrice();
                            object[6] = inventory.getItemSrcPrice();
                            object[7] = subjectPrice.getDiscount();
                            object[8] = itemNum;
                            object[9] = "自定义";
                            object[10] = subjectPrice.getId();
                            object[11] = inventory.getItemId();
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
            themeImgObject[1] = themeImg.get("width").asInt();
            //height
            themeImgObject[2] = themeImg.get("height").asInt();

            //主题的首页主图
            Object[] masterImgObject = new Object[3];
            if(theme.getThemeMasterImg() != null){
                JsonNode themeMasterImg = Json.parse(theme.getThemeMasterImg());
                //url
                String masterImgUrl = themeMasterImg.get("url").toString();
                masterImgObject[0] = masterImgUrl.substring(1,masterImgUrl.length()-1);
                //width
                masterImgObject[1] = themeMasterImg.get("width").asInt();
                //height
                masterImgObject[2] = themeMasterImg.get("height").asInt();
            }
            //主题的首页主图的标签
            List<Object[]> tagList = new ArrayList<>();
            if(theme.getMasterItemTag() != null) {
                JsonNode itemMasterTag = Json.parse(theme.getMasterItemTag());
                for (JsonNode tag : itemMasterTag) {
                    //Logger.error(tag.toString());
                    Object[] tagObject = new Object[6];
                    //top
                    tagObject[0] = tag.get("top").floatValue() * 100 + "%";
                    //url
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

            return ok(views.html.theme.themeUpdate.render(lang,theme,itemList,themeImgObject,masterImgObject,tagList,idList,indexPage,pinPage,giftPage,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
        }
        return null;
    }

    /**
     * 添加H5主题   Added by Tiffany Zhu 2016.02.01
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result addH5Theme(String lang){
        return ok(views.html.theme.H5ThemeAdd.render(lang,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }

    /**
     * 保存H5主题   Added by Tiffany Zhu 2016.02.01
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result h5ThemeSave(String lang){
        JsonNode json = request().body().asJson();
        JsonNode themeJson = json.get("theme");
        Logger.error(themeJson.toString());
        Theme theme = Json.fromJson(themeJson,Theme.class);
        //数据验证      ----start
        Form<Theme> themeForm = Form.form(Theme.class).bind(themeJson);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间
        Date now = new Date();
        String strNow = sdfDate.format(now);
        //当前时间 + 6个月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH,+6);
        String validDate = sdfDate.format(calendar.getTime());
        //基本样式不匹配;主图片,商品ID,首页主图,主图标签     不是Json格式
        if(themeForm.hasErrors() || !(Regex.isJason(theme.getThemeImg())) || (theme.getStartAt().compareTo(theme.getEndAt())>=0) ||
                theme.getEndAt().compareTo(strNow) < 0 || theme.getStartAt().compareTo(validDate) > 0 || theme.getEndAt().compareTo(validDate) > 0  ){
            return badRequest();
        }
        //数据验证      ----end
        theme.setType("h5");
        service.h5ThemeSave(theme);
        Logger.error("已保存的h5主题:" + theme);
        //创建Scheduled Actor         ---start
        ActorRef themeOffShelf = Akka.system().actorOf(Props.create(ThemeDestroyActor.class,service));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endAt = null;
        try{
            endAt = sdf.parse(theme.getEndAt());
        }catch(Exception e){
            e.printStackTrace();
            Logger.error(Throwables.getStackTraceAsString(e));
        }
        if(endAt != null){
            FiniteDuration duration = Duration.create(endAt.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
            newScheduler.scheduleOnce(duration,themeOffShelf,theme.getId());
        }
        //创建Scheduled Actor         ---end

        //专用用户处理    Added by Tiffany Zhu 2016.08.23
        JsonNode usersJson = json.findValue("users");
        if (usersJson != null && usersJson.size() >0){
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                //存入redis
                jedisPool = RedisPool.createPool();
                //验证 JedisPool
                if (jedisPool != null){
                    jedis = jedisPool.getResource();
                    //验证Jedis
                    if (jedis != null){
                        //用户专用主题
                        if (theme.getThemeState() == 2 && usersJson.size() > 0){
                            if (jedis.exists(theme.getId().toString())){
                                jedis.del(theme.getId().toString());
                            }
                            for (JsonNode node :usersJson){
                                ID id = Json.fromJson(node,ID.class);
                                jedis.sadd(theme.getId().toString(),id.getUserId().toString());
                            }
                            jedis.persist(theme.getId().toString());
                            //其他主题
                        }else {
                            if (jedis.exists(theme.getId().toString())){
                                jedis.del(theme.getId().toString());
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                Logger.error(Throwables.getStackTraceAsString(e));
            }finally {
//                if (jedisPool != null && jedis != null){
//                    jedisPool.returnBrokenResource(jedis);
//                }
            }
        }


        //主题显示位置
        JsonNode themeCateJson = json.get("themeCates");
        List<ThemeCate> themeCateList = new ArrayList<>();
        service.delThemeCateByThemeId(theme.getId());
        for (JsonNode node:themeCateJson){
            ThemeCate themeCate = new ThemeCate();
            themeCate.setThemeId(theme.getId());
            themeCate.setThemeCateCode(node.asInt());
            if(node.asInt() == 1){
                themeCate.setThemeCateNm("首页");
            }
            if(node.asInt() == 2){
                themeCate.setThemeCateNm("拼购");
            }
            if(node.asInt() == 3){
                themeCate.setThemeCateNm("礼物");
            }
            themeCateList.add(themeCate);
        }
        service.addThemeCate(themeCateList);

        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 主题排序     Added by Tiffany Zhu 2016.03.07
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result themeSort(String lang){
        List<Theme> themeList = service.getOnShelfTheme();
        List<Object[]> resultList = new ArrayList<>();
        int i = 0;
        for(Theme theme : themeList){
            i = i + 1;
            Object[] object = new Object[5];
            object[0] = i;
            object[1] = theme.getSortNu();
            object[2] = theme.getId();
            if("ordinary".equals(theme.getType())){
                object[3] = "普通";
            }
            if("h5".equals(theme.getType())){
                object[3] = "HTML5";
            }
            if("detail".equals(theme.getType())){
                object[3] = "普通商品";
            }
            if("pin".equals(theme.getType())){
                object[3] = "拼购商品";
            }
            JsonNode imgJson = Json.parse(theme.getThemeImg());
            String imgUrl = imgJson.get("url").toString();
            imgUrl = imgUrl.substring(1,imgUrl.length()-1);
            object[4] = imgUrl;
            resultList.add(object);
        }

        return ok(views.html.theme.themeSort.render(lang,resultList,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }

    /**
     * 保存主题排序       Added by Tiffany Zhu 2016.03.09
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result sortNuSave(String lang){
        JsonNode json = request().body().asJson();
        List<Theme> list = new ArrayList<>();
        for(JsonNode object : json){
            Theme theme = new Theme();
            String id = object.get("newId").toString();
            id = id.substring(1,id.length()-1);
            String sortNu = object.get("sortNu").toString();
            sortNu = sortNu.substring(1,sortNu.length()-1);
            theme.setId(Long.parseLong(id));
            theme.setSortNu(Integer.parseInt(sortNu));
            list.add(theme);
        }
        service.updThemeSortNu(list);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 主题弹窗选择  主题列表     Added by Sunny.Wu 2016.08.25
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result themePop(){
        //主题列表
        List<Theme> themeList = service.getCategoryThemes();
        List<Theme> tList = new ArrayList<>();
        String strNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//现在时间
        for(Theme theme : themeList) {
            if (theme.getEndAt().compareTo(strNow)>0) {
                String url = Json.parse(theme.getThemeImg()).get("url")==null?"":Json.parse(theme.getThemeImg()).get("url").asText();
                theme.setThemeImg(url);
                tList.add(theme);
            }
        }
        if (themeList.size()>0) {
            return ok(views.html.theme.themePop.render(tList,SysParCom.IMAGE_URL));
        }
        else
            return ok("没有数据");
    }

    /**
     * h5主题生成器      Added by Sunny.Wu 2016.08.31
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result h5Builder(String lang) {
        return ok(views.html.theme.h5Builder.render(lang,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }

}
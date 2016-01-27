package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.*;
import middle.ItemMiddle;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
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
 * 商品管理
 * Created by howen on 15/11/11.
 */
public class ItemCtrl extends Controller {


    private ItemService itemService;

    private ThemeService themeService;

    private InventoryService inventoryService;

    private CarriageService carriageService;

    private VaryPriceService varyPriceService;

    private DataLogService dataLogService;

    private ItemStatisService itemStatisService;

    private ItemMiddle itemMiddle;

    @Inject
    public ItemCtrl(ItemService itemService, ThemeService themeService, InventoryService inventoryService, CarriageService carriageService, VaryPriceService varyPriceService, DataLogService dataLogService, ItemStatisService itemStatisService) {
        this.itemService = itemService;
        this.themeService = themeService;
        this.inventoryService = inventoryService;
        this.carriageService = carriageService;
        this.varyPriceService = varyPriceService;
        this.dataLogService = dataLogService;
        this.itemStatisService = itemStatisService;
        itemMiddle = new ItemMiddle(itemService,inventoryService,varyPriceService,dataLogService,itemStatisService);
    }

    /**
     * Ajax for get sub category.
     *
     * @return Result
     *
     */

    public Result getSubCategory() {
        DynamicForm form = Form.form().bindFromRequest();
        Long pcid = Long.parseLong(form.get("pcid"));
        HashMap<String, Long> hashMap = new HashMap<String, Long>();
        hashMap.put("parentCateId", pcid);
        return ok(Json.toJson(itemService.getSubCates(hashMap)));
    }

    /**
     * 商品列表
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemList(String lang){

        Item item =new Item();

        item.setPageSize(-1);
        item.setOffset(-1);

        //取总数
        int countNum = itemService.itemSearch(item).size();
        //共分几页
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

        if(countNum%ThemeCtrl.PAGE_SIZE!=0){
            pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
        }

        item.setPageSize(ThemeCtrl.PAGE_SIZE);
        item.setOffset(0);
//        Logger.error("所有商品:"+service.itemSearch(item).toString());

        return ok(views.html.item.itemsearch.render(lang,ThemeCtrl.IMAGE_URL,ThemeCtrl.PAGE_SIZE,countNum,pageCount,itemService.itemSearch(item),(User) ctx().args.get("user")));
    }

    /**
     * ajax分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemSearchAjax(String lang,int pageNum) {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json,Item.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            item.setPageSize(-1);
            item.setOffset(-1);
            //取总数
            int countNum = itemService.itemSearch(item).size();
            //共分几页
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
            item.setPageSize(ThemeCtrl.PAGE_SIZE);
            item.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",itemService.itemSearch(item));
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
     * 商品查询弹窗
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemSearchPopup(String lang){
        return ok(views.html.item.itemlistPop.render(lang));
    }

    /**
     * 商品录入
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemCreate(String lang) {
        return ok(views.html.item.itemadd.render(lang,itemService.getAllBrands(),itemService.getParentCates(),ThemeCtrl.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }

    /**
     *  由商品id获得单个商品及其库存信息展示在详情页面
     * @param lang 语言
     * @param id 商品id
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result findItemById(String lang,Long id) {
        Item item = itemService.getItem(id);
        Cates cates = itemService.getCate(item.getCateId());
        String pCateNm = "";
        if(null != cates.getPcateId()) {
            pCateNm = itemService.getCate(cates.getPcateId()).getCateNm();
        } else pCateNm = cates.getCateNm();
        Brands brands = itemService.getBrands(item.getBrandId());
        List<Inventory> inventories = inventoryService.getInventoriesByItemId(id);
        //包含modelName的库存列表
        List<Object[]> invList = new ArrayList<>();
        for(Inventory inventory : inventories) {
            Object[] object = new Object[21];
            object[0] = inventory.getOrMasterInv();
            object[1] = inventory.getItemColor();
            object[2] = inventory.getItemSize();
            object[3] = inventory.getInvWeight();
            object[4] = inventory.getAmount();
            object[5] = inventory.getItemPrice();
            object[6] = inventory.getItemSrcPrice();
            object[7] = inventory.getItemCostPrice();
            object[8] = inventory.getItemDiscount();
            object[9] = inventory.getRestrictAmount();
            object[10] = inventory.getCarriageModelCode();
            //由库存表的carriageModelCode 得到 modelName
            object[11] = carriageService.getModelName(inventory.getCarriageModelCode());
            object[12] = inventory.getPostalTaxRate();
            object[13] = inventory.getPostalTaxCode();
            object[14] = inventory.getInvArea();
            object[15] = inventory.getInvCustoms();
            object[16] = inventory.getInvImg();
            object[17] = inventory.getItemPreviewImgs();
            object[18] = inventory.getState();
            object[19] = inventory.getRecordCode();
            object[20] = inventory.getRestAmount();
            invList.add(object);
        }

        return ok(views.html.item.itemdetail.render(item,invList,cates,pCateNm,brands,ThemeCtrl.IMAGE_URL,lang,(User) ctx().args.get("user")));
    }

    /**
     * 由商品id获得单个商品及其库存信息展示在修改页面
     * @param lang 语言
     * @param id 商品id
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result updateItemById(String lang,Long id) {
        Item item = itemService.getItem(id);
        //由商品类别id获取类别
        Cates cates = itemService.getCate(item.getCateId());
        //父类别名称
        String pCateNm = "";
        if(null != cates.getPcateId()) {
            pCateNm = itemService.getCate(cates.getPcateId()).getCateNm();
        } else pCateNm = cates.getCateNm();
        //由商品品牌id获取品牌
        Brands brands = itemService.getBrands(item.getBrandId());
        //由商品id获取库存列表
        List<Inventory> inventories = inventoryService.getInventoriesByItemId(id);
        //包含modelName的库存列表
        List<Object[]> invList = new ArrayList<>();
        for(Inventory inventory : inventories) {
            Object[] object = new Object[25];
            object[0] = inventory.getId();
            object[1] = inventory.getItemId();
            object[2] = inventory.getItemColor();
            object[3] = inventory.getItemSize();
            object[4] = inventory.getAmount();
            object[5] = inventory.getItemSrcPrice();
            object[6] = inventory.getItemPrice();
            object[7] = inventory.getItemCostPrice();
            object[8] = inventory.getItemDiscount();
            object[9] = inventory.getSoldAmount();
            object[10] = inventory.getRestAmount();
            object[11] = inventory.getInvImg();
            object[12] = inventory.getItemPreviewImgs();
            object[13] = inventory.getOrDestroy();
            object[14] = inventory.getOrMasterInv();
            object[15] = inventory.getState();
            object[16] = inventory.getInvArea();
            object[17] = inventory.getRestrictAmount();
            object[18] = inventory.getInvCustoms();
            object[19] = inventory.getPostalTaxCode();
            object[20] = inventory.getPostalTaxRate();
            object[21] = inventory.getInvWeight();
            object[22] = inventory.getCarriageModelCode();
            //由库存表的carriageModelCode 得到 modelName
            object[23] = carriageService.getModelName(inventory.getCarriageModelCode());
            object[24] = inventory.getRecordCode();
            invList.add(object);
        }
        return ok(views.html.item.itemupdate.render(item,invList,cates,pCateNm,brands,ThemeCtrl.IMAGE_URL,lang,itemService.getAllBrands(),itemService.getParentCates(),carriageService.getModels(),(User) ctx().args.get("user")));
    }

    /**
     * 添加商品
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemSave() {
        //操作人员的ip
        String operateIp = request().remoteAddress();
        String nickName = ((User) ctx().args.get("user")).nickname();
        JsonNode json = request().body().asJson();
//        Logger.error(json.toString());
        List<Long> list = ItemMiddle.itemSave(json, nickName, operateIp);
        return ok(list.toString());
    }

    /**
     * 添加商品, 弹窗商品添加规格       Added by Sunny.Wu
     * @return view
     */

    public Result itemAddPop() {
        return ok(views.html.item.itemaddPop.render(carriageService.getModels(),ThemeCtrl.IMG_UPLOAD_URL));
    }

    /**
     * 新增运费模板
     * @param lang 语言
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result carrCreate(String lang) {
        return ok(views.html.carriage.carrmodelAdd.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 运费模板保存
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result carrSave() {
        JsonNode json = request().body().asJson();
        carriageService.carrModelSave(json);
        return ok();

    }

    /**
     * 由modeCode得到该模板的所有运费计算方式
     * @param lang 语言
     * @param modelCode 模板代码
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result findModel(String lang,String modelCode) {
        List<Carriage> carrList = carriageService.getCarrsByModel(modelCode);
        return ok(views.html.carriage.carrmodelUpdate.render(lang,carrList,(User) ctx().args.get("user")));
    }

    /**
     * 有modelCode删除运费模板中所有数据
     * @param modelCode
     * @return Result
     */
    public Result delModel(String modelCode) {
        boolean bool = carriageService.delModelByCode(modelCode);
        if(bool==true)  return ok("true");
        else return ok("false");
    }

    /**
     * 运费模板列表
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result carrModelSearch(String lang) {
        List<Carriage> modelList = carriageService.getModels();
        List<Carriage> carriageList = carriageService.getAllCarriage();
        return ok(views.html.carriage.carrmodelList.render(lang,modelList,carriageList,(User) ctx().args.get("user")));
    }


    public Result carrPop() {
        return ok(views.html.carriage.cityPop.render());
    }


    /**
     * 品牌列表     Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result brandList(String lang){
        return ok(views.html.item.brandsearch.render(lang,ThemeCtrl.IMAGE_URL,itemService.getAllBrands(),(User) ctx().args.get("user")));
    }

    /**
     * 新增品牌     Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result brandAdd(String lang){
        return ok(views.html.item.brandadd.render(lang,(User) ctx().args.get("user")));
    }

    /**
     * 保存品牌    Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result brandSave(String lang){
        JsonNode json = request().body().asJson();
        Logger.error(json.toString());
        itemService.insertBrands(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 商品分类列表       Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateList(String lang){

        List<Cates> catesList= itemService.getCatesAll();
        //含有父类名的分类列表
        List<Object[]> caList = new ArrayList<>();
        for(Cates cates : catesList){
            Object[] object = new Object[6];        //类别Id
            object[0] = cates.getCateId();          //类别名
            object[1] = cates.getCateNm();          //父类Id
            object[2] = cates.getPcateId();         //父类名
            if(itemService.getCate(cates.getPcateId()) != null) {
                object[3] = itemService.getCate(cates.getPcateId()).getCateNm();//类别描述
            }else{
                object[3] = "";//类别描述
            }
            object[4] = cates.getCateDesc();        //类别描述
            object[5] = cates.getCateCode();        //类别Code
            caList.add(object);
        }
        return ok(views.html.item.catesearch.render(lang,caList,(User) ctx().args.get("user")));
    }

    /**
     * 新增商品分类       Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateAdd(String lang){
        return ok(views.html.item.cateadd.render(lang,itemService.getParentCates(),(User) ctx().args.get("user")));
    }

    /**
     * 保存商品分类
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateSave(String lang){
        JsonNode json = request().body().asJson();
        Logger.error(json.toString());
        itemService.catesSave(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }



}



package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.*;
import play.Logger;
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

    @Inject
    private ItemService service;

    @Inject
    private ThemeService themeService;

    @Inject
    private InventoryService inventoryService;

    @Inject
    private CarriageService carriageService;

    @Inject
    private OrderService orderService;


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
        int countNum = service.itemSearch(item).size();
        //共分几页
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

        if(countNum%ThemeCtrl.PAGE_SIZE!=0){
            pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
        }

        item.setPageSize(ThemeCtrl.PAGE_SIZE);
        item.setOffset(0);
        Logger.error("所有商品:"+service.itemSearch(item).toString());

        return ok(views.html.item.itemsearch.render(lang,ThemeCtrl.IMAGE_URL,ThemeCtrl.PAGE_SIZE,countNum,pageCount,service.itemSearch(item),(User) ctx().args.get("user")));
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
            int countNum = service.itemSearch(item).size();
            //共分几页
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
            item.setPageSize(ThemeCtrl.PAGE_SIZE);
            item.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",service.itemSearch(item));
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
        return ok(views.html.item.itemadd.render(lang,service.getAllBrands(),service.getParentCates(),carriageService.getModel(),(User) ctx().args.get("user")));
    }

    /**
     *  由商品id获得单个商品及其库存信息展示在详情页面
     * @param lang 语言
     * @param id 商品id
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result findItemById(String lang,Long id) {
        Item item = service.getItem(id);
        Cates cates = service.getCate(item.getCateId());
        String pCateNm = "";
        if(null != cates.getPcateId()) {
            pCateNm = service.getCate(cates.getPcateId()).getCateNm();
        } else pCateNm = cates.getCateNm();
        Brands brands = service.getBrands(item.getBrandId());
        List<Inventory> inventories = inventoryService.getInventoriesByItemId(id);
        //包含modelName的库存列表
        List<Object[]> invList = new ArrayList<>();
        for(Inventory inventory : inventories) {
            Object[] object = new Object[24];
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
            invList.add(object);
        }

        return ok(views.html.item.itemdetail.render(item,invList,cates,pCateNm,brands,ThemeCtrl.IMAGE_URL,lang,(User) ctx().args.get("user")));
    }

    /**
     * 由商品id获得单个商品及其库存信息展示在修改页面
     * @param lang 语言
     * @param id 商品id
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result updateItemById(String lang,Long id) {
        Item item = service.getItem(id);
        //由商品类别id获取类别
        Cates cates = service.getCate(item.getCateId());
        //父类别名称
        String pCateNm = "";
        if(null != cates.getPcateId()) {
            pCateNm = service.getCate(cates.getPcateId()).getCateNm();
        } else pCateNm = cates.getCateNm();
        //由商品品牌id获取品牌
        Brands brands = service.getBrands(item.getBrandId());
        //由商品id获取库存列表
        List<Inventory> inventories = inventoryService.getInventoriesByItemId(id);
        //包含modelName的库存列表
        List<Object[]> invList = new ArrayList<>();
        for(Inventory inventory : inventories) {
            Object[] object = new Object[24];
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
            invList.add(object);
        }
        return ok(views.html.item.itemupdate.render(item,invList,cates,pCateNm,brands,ThemeCtrl.IMAGE_URL,lang,service.getAllBrands(),service.getParentCates(),carriageService.getModel(),(User) ctx().args.get("user")));
    }

    /**
     * 添加商品
     * @return Result
     */
    public Result itemSave() {
        JsonNode json = request().body().asJson();
        Logger.error(json.toString());
        List<Long> list = service.itemSave(json);
        return ok(list.toString());
    }

    /**
     * 新增运费模板
     * @param lang 语言
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result carrCreate(String lang) {
        return ok(views.html.item.carrmodelAdd.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 运费模板列表
     * @param lang 语言
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result carrModelSearch(String lang) {
        return ok(views.html.item.carrmodelList.render(lang,(User) ctx().args.get("user")));
    }


    public Result carrPop() {
            return ok(views.html.item.cityPop.render());
        }

    /**
     * 订单列表
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderList(String lang){
        //含有物流信息的订单列表
        List<Order> orderList = orderService.getOrdersAll();
        for(Order order : orderList){
            //Object[] object =
        }
        return ok(views.html.item.ordersearch.render(lang,(User) ctx().args.get("user")));
    }


    /**
     * 品牌列表
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result brandList(String lang){
        return ok(views.html.item.brandsearch.render(lang,(User) ctx().args.get("user")));
    }

    /**
     * 新增品牌
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result brandAdd(String lang){
        return ok(views.html.item.brandadd.render(lang,(User) ctx().args.get("user")));
    }

    /**
     * 商品分类列表
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateList(String lang){
        return ok(views.html.item.catesearch.render(lang,(User) ctx().args.get("user")));
    }

    /**
     * 新增商品分类
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateAdd(String lang){
        return ok(views.html.item.cateadd.render(lang,(User) ctx().args.get("user")));
    }


}



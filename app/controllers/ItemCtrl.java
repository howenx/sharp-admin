package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import entity.*;
import order.GetLogistics;
import play.Logger;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.*;

import javax.inject.Inject;
import java.util.*;

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

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    private OrderLineService orderLineService;

    @Inject
    private OrderShipService orderShipService;



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
        return ok(views.html.item.itemadd.render(lang,service.getAllBrands(),service.getParentCates(),carriageService.getModels(),(User) ctx().args.get("user")));
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
            Object[] object = new Object[20];
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
        return ok(views.html.item.itemupdate.render(item,invList,cates,pCateNm,brands,ThemeCtrl.IMAGE_URL,lang,service.getAllBrands(),service.getParentCates(),carriageService.getModels(),(User) ctx().args.get("user")));
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
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result findModel(String lang,String modelCode) {
        List carrList = carriageService.getCarrsByModel(modelCode);
        return ok(views.html.item.carrmodelUpdate.render(lang,carrList,(User) ctx().args.get("user")));
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
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result carrModelSearch(String lang) {
        List<Carriage> modelList = carriageService.getModels();
        List<Carriage> carriageList = carriageService.getAllCarriage();
        return ok(views.html.item.carrmodelList.render(lang,modelList,carriageList,(User) ctx().args.get("user")));
    }


    public Result carrPop() {
            return ok(views.html.item.cityPop.render());
        }

    /**
     * 订单列表     Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderList(String lang){
        Order order_temp = new Order();
        order_temp.setPageSize(-1);
        order_temp.setOffset(-1);

        int countNum = orderService.getOrdersAll().size();
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;
        if(countNum%ThemeCtrl.PAGE_SIZE != 0){
            pageCount =  countNum/ThemeCtrl.PAGE_SIZE + 1;
        }
        order_temp.setPageSize(ThemeCtrl.PAGE_SIZE);
        order_temp.setOffset(0);

        //含有物流信息的订单列表
        List<Object[]> orList = new ArrayList<>();
        List<Order> orderList = orderService.getOrderPage(order_temp);
        for(Order order : orderList){
            Object[] object = new Object[6];
            Logger.error(order.toString());
            Logger.error(order.getOrderId().toString());
            object[0] = order.getOrderId();
            object[1] = order.getUserId();
            object[2] = order.getOrderCreateAt();
            object[3] = order.getPayTotal();
            object[4] = order.getPayMethod();
            if("I".equals(order.getOrderStatus())){
                object[5] = "未支付";
            }
            if("S".equals(order.getOrderStatus())){
                object[5] = "支付成功";
            }
            if("C".equals(order.getOrderStatus())){
                object[5] = "订单取消";
            }
            if("F".equals(order.getOrderStatus())){
                object[5] = "支付失败";
            }
            if("R".equals(order.getOrderStatus())){
                object[5] = "已签收";
            }
            if("D".equals(order.getOrderStatus())){
                object[5] = "已发货";
            }
            if("J".equals(order.getOrderStatus())){
                object[5] = "拒收";
            }

            orList.add(object);

        }
        return ok(views.html.item.ordersearch.render(lang,ThemeCtrl.PAGE_SIZE,countNum,pageCount,orList,(User) ctx().args.get("user")));

    }

    /**
     * 订单Ajax查询     Added by Tiffany Zhu
     * @param lang
     * @param pageNum
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderSearchAjax(String lang,int pageNum){
        JsonNode json = request().body().asJson();
        Order order = Json.fromJson(json,Order.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            order.setPageSize(-1);
            order.setOffset(-1);
            //取总数
            int countNum = orderService.getOrderPage(order).size();
            //共分几页
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
            order.setPageSize(ThemeCtrl.PAGE_SIZE);
            order.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",orderService.getOrderPage(order));
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
     * 订单详情     Added by Tiffany Zhu
     * @param lang
     * @param id
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderDetail(String lang,Long id){
        //获取订单
        Order order = orderService.getOrderById(id);
        //获取订单收货信息
        OrderShip orderShip = orderShipService.getShipByOrderId(id);
        //获取子订单
        List<OrderSplit> orderSplitList = orderSplitService.getSplitByOrderId(id);
        //返回的结果集
        List<List<List<Object[]>>> subOrdersAll = new ArrayList<>();
        //子订单序号
        int subOrderNum = 0;
        for(OrderSplit orderSplit : orderSplitList){
            //含有报关和产品的子订单
            List<List<Object[]>>  subOrderList = new ArrayList<>();
            //子订单基本信息
            List<Object[]> subOrderPart1 = new ArrayList<>();
            Object[] object1 = new Object[10];
            object1[0] = orderSplit.getOrderId();   //子订单编号
            object1[1] = orderSplit.getState();     //子订单报关状态
            object1[2] = orderSplit.getCustomsReturnCode(); //子订单支付报关状态
            object1[3] = orderSplit.getExpressNm(); //快递名称
            object1[4] = orderSplit.getExpressNum();//快递编号
            object1[5] = orderSplit.getShipFee();   //邮费
            object1[6] = orderSplit.getPostalFee(); //行邮税
            object1[7] = orderSplit.getTotalFee();  //商品总价
            object1[8] = orderSplit.getTotalPayFee();//支付费用总计
            subOrderNum = subOrderNum + 1;
            object1[9] = subOrderNum;
            subOrderPart1.add(object1);
            subOrderList.add(subOrderPart1);

            //子订单的全部商品
            List<OrderLine> orderLineList = orderLineService.getLineByOrderId(orderSplit.getOrderId());
            //包含商品名的子订单商品
            List<Object[]> subOrderPart2 = new ArrayList<>();
            for(OrderLine orderLine : orderLineList){
                Object[] object2 = new Object[6];
                Item item = service.getItem(orderLine.getItemId());
                object2[0] = item.getItemTitle();    //名称
                object2[1] = orderLine.getSkuImg();  //图片url
                object2[2] = orderLine.getSkuSize(); //尺码
                object2[3] = orderLine.getSkuColor();//颜色
                object2[4] = orderLine.getPrice();   //价格
                object2[5] = orderLine.getAmount();  //数量
                subOrderPart2.add(object2);
            }
            subOrderList.add(subOrderPart2);

            subOrdersAll.add(subOrderList);
        }
        return ok(views.html.item.orderdetail.render(lang,order,orderShip,subOrdersAll,ThemeCtrl.IMAGE_URL,(User) ctx().args.get("user")));
    }


    /**
     * 品牌列表     Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result brandList(String lang){
        Brands brands = new Brands();
        brands.setPageSize(-1);
        brands.setOffset(-1);

        int countNum = service.getAllBrands().size();
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;
        if(countNum%ThemeCtrl.PAGE_SIZE != 0){
            pageCount =  countNum/ThemeCtrl.PAGE_SIZE + 1;
        }
        brands.setPageSize(ThemeCtrl.PAGE_SIZE);
        brands.setOffset(0);
        return ok(views.html.item.brandsearch.render(lang,ThemeCtrl.IMAGE_URL,ThemeCtrl.PAGE_SIZE,countNum,pageCount,service.getBrandsPage(brands),(User) ctx().args.get("user")));
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
        service.insertBrands(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 商品分类列表       Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateList(String lang){
        return ok(views.html.item.catesearch.render(lang,(User) ctx().args.get("user")));
    }

    /**
     * 新增商品分类       Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateAdd(String lang){
        return ok(views.html.item.cateadd.render(lang,service.getParentCates(),(User) ctx().args.get("user")));
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
        service.catesSave(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }


}



package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.B1EC2Client;
import com.iwilley.b1ec2.api.domain.ItemInfo;
import com.iwilley.b1ec2.api.domain.ShopSkuPushLine;
import com.iwilley.b1ec2.api.domain.SkuInfo;
import com.iwilley.b1ec2.api.request.ShopItemPushRequest;
import com.iwilley.b1ec2.api.request.ShopQueryRequest;
import com.iwilley.b1ec2.api.response.ShopQueryResponse;
import domain.Inventory;
import domain.Item;
import domain.User;
import domain.erp.ShopItemOperate;
import filters.UserAuth;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.InventoryService;
import service.ItemService;
import util.SysParCom;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Sunny Wu 16/2/23.
 * <p>
 * ERP商品的操作
 */
public class ShopItemCtrl extends Controller {

    @Inject
    private ItemService itemService;

    @Inject
    private InventoryService inventoryService;

    /**
     * ERP 商品资料查询
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemInfoList(String lang) throws ApiException, ParseException {
        ShopItemOperate shopItemOperate = new ShopItemOperate();
        String startTime = "2016-01-28 00:00:00";
        String endTime = "2016-01-28 10:22:45";
        List<ItemInfo> itemInfoList = shopItemOperate.ItemInfoQuery(startTime, endTime);
        List<SkuInfo> skuInfoList = new ArrayList<>();
//        for(ItemInfo itemInfo : itemInfoList) {
//            for(SkuInfo skuInfo : itemInfo.getLines()) {
//                skuInfoList.add(skuInfo);
//            }
//        }
        int pageSize = 10;
        //取总数
        int countNum = itemInfoList.size();
        int skuNum = skuInfoList.size();
        //共分几页
        int pageCount = (int) Math.ceil((double) countNum / pageSize);
        return ok(views.html.item.erp_itemlist.render(lang,itemInfoList,skuInfoList,ThemeCtrl.PAGE_SIZE,countNum,pageCount, (User)ctx().args.get("user")));
    }

    /**
     * 按照输入的时间查询条件查询商品资料
     * @param lang 语言
     * @return map
     * @throws ParseException
     * @throws ApiException
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemInfoTimeSearch(String lang) throws ParseException, ApiException {
        JsonNode json = request().body().asJson();
        String startTime = json.get(0).asText();
        String endTime = json.get(1).asText();
        ShopItemOperate shopItemOperate = new ShopItemOperate();
        List<ItemInfo> itemInfoList = shopItemOperate.ItemInfoQuery(startTime, endTime);
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("itemInfoList",itemInfoList);
        return ok(Json.toJson(returnMap));
    }

    /**
     * ERP 商品资料分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemInfoSearchAjax(String lang, int pageNum) {

        return ok();
    }

    /**
     * erp商品导入style-admin
     * @return
     * @throws ParseException
     * @throws ApiException
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemImport() throws ParseException, ApiException {
        ShopItemOperate shopItemOperate = new ShopItemOperate();
        JsonNode json = request().body().asJson();
        String startTime = "";
        String endTime = "";
        if (null==json||"".equals(json.toString())) {
            startTime = "2016-01-28 00:00:00";
            endTime = "2016-01-28 10:22:45";
        } else {
//            Logger.error("导入时间"+json.asText());
            startTime = json.get(0).asText();
            endTime = json.get(1).asText();
        }
        List<ItemInfo> itemInfoList = shopItemOperate.ItemInfoQuery(startTime, endTime);
        //查询所有的库存数据
        List<Inventory> invList = inventoryService.getAllInventories();
        for(ItemInfo itemInfo : itemInfoList) {
            Item item = new Item();
            item.setCateId(12030L);
            item.setBrandId(100429L);
            item.setItemTitle(itemInfo.getItemName());
            item.setSupplyMerch("施华洛世奇");
            item.setItemFeatures("{}");
            item.setOrDestroy(false);
            item.setPublicity("[]");
            item.setItemDetail(itemInfo.getPictureUrl());
            itemService.itemInsert(item);
            for(SkuInfo skuInfo : itemInfo.getLines()) {
                int count = 0;
                for(int i=0; i<invList.size(); i++) {
                    Inventory inv = invList.get(i);
                    String invCode = inv.getInvCode();
                    if (skuInfo.getSkuCode().equals(invCode)) {
                        break;
                    }
                    count++;
                }
                if (count>=invList.size()) {//此条规格不在inventory表中
                    Inventory inventory = new Inventory();
                    inventory.setRestAmount(999999);
                    inventory.setInvImg(itemInfo.getPictureUrl());
                    inventory.setItemPreviewImgs("[]");
                    inventory.setOrDestroy(false);
                    inventory.setItemId(item.getId());
                    inventory.setOrMasterInv(false);
                    inventory.setState("Y");
                    inventory.setInvArea("G");
                    inventory.setRestrictAmount(999999);
                    inventory.setInvTitle(itemInfo.getItemName());
                    inventory.setItemColor(skuInfo.getSkuName().split("　")[0]);
                    inventory.setItemSize(skuInfo.getSkuName().split("　")[1]);
                    inventory.setInvCustoms("shanghai");
                    inventory.setPostalTaxCode("08020000");
                    inventory.setInvWeight((int)skuInfo.getWeight());
                    inventory.setCarriageModelCode("900011");
                    inventory.setRecordCode("{\"hangzhou\": \"02631510200071\"}");
                    inventory.setStartAt("2016-01-01 00:00:00");
                    inventory.setEndAt("2018-01-01 00:00:00");
                    inventory.setOrVaryPrice(false);
                    inventory.setInvCode(skuInfo.getSkuCode());
                    inventory.setAmount(999999);
                    inventory.setItemSrcPrice(new BigDecimal(skuInfo.getMarketPrice()));
                    inventory.setItemPrice(new BigDecimal(skuInfo.getSalesPrice()));
                    inventory.setItemCostPrice(new BigDecimal(skuInfo.getPurchasePrice()));
                    inventory.setItemDiscount(new BigDecimal(0));
                    inventoryService.insertInventory(inventory);
                }
            }
        }
        return ok("导入成功");
    }

    /**
     * 商品推送到erp
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result shopItemPush() throws ApiException, ParseException {
        ShopItemOperate shopItemOperate = new ShopItemOperate();
        JsonNode json = request().body().asJson();
        Long skuIds[] = new Long[json.size()];
        List<String> shopItemCodeList = new ArrayList<>();
        Logger.error("推送的SKU:"+json.toString());
        for(int i=0;i<json.size();i++){
            skuIds[i] = (json.get(i)).asLong();
            Inventory inventory = inventoryService.getInventory(skuIds[i]);
            ShopItemPushRequest request = new ShopItemPushRequest();
            request.shopItemCode = inventory.getId().toString();//宝贝编码
            request.shopId = 5;                           //店铺Id
            String state = inventory.getState();
            if ("Y".equals(state)) {
                request.status = "在售中";
            }
            if (!"Y".equals(state)) {
                request.status = "在库中";
            }
            request.createdTime = inventory.getCreateAt();//创建时间
            request.updateTime = (null==inventory.getUpdateAt()?inventory.getCreateAt():inventory.getUpdateAt());//修改时间
            request.shopItemName = inventory.getInvTitle();   //宝贝名称
//            request.isVirtual = false;
            request.pictureUrl = SysParCom.IMAGE_URL+Json.parse(inventory.getInvImg()).get("url").asText();//主图url
            request.outerId = inventory.getInvCode();//商家编码
            request.quantity = inventory.getRestAmount();//数量
            request.price = inventory.getItemPrice().doubleValue();//价格
            request.weight = inventory.getInvWeight()/1000.0;//重量
            List<ShopSkuPushLine> lineList = new ArrayList<ShopSkuPushLine>();//sku信息
            ShopSkuPushLine shopSkuPushLine = new ShopSkuPushLine();
            shopSkuPushLine.shopSkuCode = inventory.getId().toString();//网店skuCode
            shopSkuPushLine.outerId = inventory.getInvCode();          //商家代码
            shopSkuPushLine.property1 = inventory.getItemColor();      //平台属性1(颜色)
            shopSkuPushLine.property2 = inventory.getItemSize();       //平台属性2(尺寸)
            shopSkuPushLine.price = inventory.getItemPrice().doubleValue();//价格
            shopSkuPushLine.weight = inventory.getInvWeight()/1000.0;      //重量(千克)
            shopSkuPushLine.quantity = inventory.getRestAmount();              //数量
            lineList.add(shopSkuPushLine);
            request.setSkusInfo(lineList);
            Logger.error("推送商品数据:"+Json.toJson(request));
            String shopItemCode = shopItemOperate.ShopItemPush(request);
            shopItemCodeList.add(shopItemCode);
        }
        return ok(shopItemCodeList.toString());
    }


    @Security.Authenticated(UserAuth.class)
    public Result shopQuery() throws ApiException {
        B1EC2Client client = new B1EC2Client(SysParCom.URL, SysParCom.COMPANY,SysParCom.LOGIN_NAME, SysParCom.PASSWORD, SysParCom.SECRET);
        ShopQueryRequest request = new ShopQueryRequest();
        ShopQueryResponse response = client.execute(request);
        List<String> shopList = new ArrayList<>();
        if (response.getErrorCode() == 0) {
            shopList.addAll(response.getShops().stream().map(shop -> "Shop:" + shop.getShopId() + "," + shop.getShopName()).collect(Collectors.toList()));
        }
        return ok(shopList.toString());
    }

}

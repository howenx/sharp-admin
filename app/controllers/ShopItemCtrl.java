package controllers;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.domain.ItemInfo;
import com.iwilley.b1ec2.api.domain.SkuInfo;
import entity.Inventory;
import entity.Item;
import entity.User;
import entity.erp.ShopItemOperate;
import filters.UserAuth;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.InventoryService;
import service.ItemService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Wu 16/2/23.
 * <p>
 * 查询ERP中所有的商品信息
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
        for(ItemInfo itemInfo : itemInfoList) {
            for(SkuInfo skuInfo : itemInfo.getLines()) {
                skuInfoList.add(skuInfo);
            }
        }
        int pageSize = 10;
        //取总数
        int countNum = itemInfoList.size();
        int skuNum = skuInfoList.size();
        Logger.error("商品"+countNum);
        Logger.error("sku"+skuNum);
        //共分几页
        int pageCount = (int) Math.ceil((double) countNum / pageSize);
        return ok(views.html.item.erp_itemlist.render(lang,itemInfoList,skuInfoList,ThemeCtrl.PAGE_SIZE,countNum,pageCount, (User)ctx().args.get("user")));
    }

    /**
     * erp商品导入style-admin
     * @return
     * @throws ParseException
     * @throws ApiException
     */
//    @Security.Authenticated(UserAuth.class)
    public Result itemImport() throws ParseException, ApiException {
        ShopItemOperate shopItemOperate = new ShopItemOperate();
        String startTime = "2016-01-28 00:00:00";
        String endTime = "2016-01-28 10:22:45";
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
                    inventory.setStartAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-01-01 00:00:00").getTime()));
                    inventory.setEndAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-01-01 00:00:00").getTime()));
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
    public Result shopItemPush(){
        
        return ok();
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
}

package middle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.*;
import play.Logger;
import play.libs.Json;
import service.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/14.
 * kakao china.
 *
 * controller 和 service 的中间层,为了解决一个modules只能注入一个数据库中的service,
 * 在同一个方法里注入不同库中的mapper来处理复杂的业务逻辑
 */
public class ItemMiddle {

    public static ItemService itemService;
    public static InventoryService inventoryService;
    public static VaryPriceService varyPriceService;
    public static DataLogService dataLogService;
    public static ItemStatisService itemStatisService;

    public ItemMiddle(ItemService itemService, InventoryService inventoryService, VaryPriceService varyPriceService, DataLogService dataLogService, ItemStatisService itemStatisService) {
        this.itemService = itemService;
        this.inventoryService = inventoryService;
        this.varyPriceService = varyPriceService;
        this.dataLogService = dataLogService;
        this.itemStatisService = itemStatisService;
    }

    /**
     * 录入或更新商品信息和库存信息, 同时录入日志信息
     * @param json 商品和库存信息json串
     * @param enNm 操作人员
     * @param operateIp 操作人员ip
     * @return
     */
    public static List<Long> itemSave(JsonNode json, String enNm, String operateIp) {
        List<Long> list = new ArrayList<>();
        Item item = new Item();
        //日志信息
        DataLog dataLog = new DataLog();
        dataLog.setOperateUser(enNm);
        dataLog.setOperateIp(operateIp);
        //统计信息
        ItemStatis itemStatis = new ItemStatis();
        //items表录入数据
        if (json.has("item")) {
            JsonNode jsonItem = json.findValue("item");
            if (jsonItem.has("publicity")) {
                ((ObjectNode) jsonItem).put("publicity",jsonItem.findValue("publicity").toString());
            }
            if (jsonItem.has("itemFeatures")) {
                ((ObjectNode) jsonItem).put("itemFeatures",jsonItem.findValue("itemFeatures").toString());
            }
            if (jsonItem.has("itemDetailImgs")) {
                ((ObjectNode) jsonItem).put("itemDetailImgs",jsonItem.findValue("itemDetailImgs").toString());
            }
            item = Json.fromJson(json.findValue("item"),Item.class);
//            Logger.error(item.toString());
            //更新商品信息
            if (jsonItem.has("id")) {
                itemService.itemUpdate(item);
                //数据录入data_log表
                Long itemId = item.getId();
                dataLog.setOperateType("修改商品");
                dataLog.setLogContent("修改商品"+itemId);
                Item originItem = itemService.getItem(itemId);
                List<Inventory> originInv = inventoryService.getInventoriesByItemId(itemId);
                List<VaryPrice> originVP = new ArrayList<>();
                for(Inventory inventory : originInv) {
                    VaryPrice varyPrice = new VaryPrice();
                    varyPrice.setInvId(inventory.getId());
                    List<VaryPrice> varyPriceList = varyPriceService.getVaryPriceBy(varyPrice);
                    for(VaryPrice vp : varyPriceList) {
                        originVP.add(vp);
                    }
                }
                dataLog.setOriginData("{\"item\":"+Json.toJson(originItem).toString() + ",\"inventories\":"+Json.toJson(originInv).toString() + ",\"varyPrices\":"+Json.toJson(originVP).toString() + "}");
                dataLog.setNewData(json.toString());
//                Logger.error("log数据:"+dataLog.toString());
                dataLogService.insertDataLog(dataLog);
            }
            //录入商品信息
            else {
                itemService.itemInsert(item);
                //数据录入data_log表
                dataLog.setOperateType("新增商品");
                dataLog.setLogContent("新增商品"+item.getId());
                dataLog.setOriginData("{}");
                dataLog.setNewData(json.toString());
//                Logger.error("log数据:"+dataLog.toString());
                dataLogService.insertDataLog(dataLog);
            }
        }
        //往inventories表插入数据
        if (json.has("inventories")) {
            for(final JsonNode jsonNode : json.findValue("inventories")) {
                Logger.error("库存数据:"+jsonNode);
                Inventory inventory = new Inventory();
                if (jsonNode.has("inventory")) {
                    JsonNode jsonInv = jsonNode.findValue("inventory");
//                    if (jsonInv.has("itemPreviewImgs")) {
//                        ((ObjectNode) jsonInv).put("itemPreviewImgs",jsonInv.findValue("itemPreviewImgs").toString());
//                    }
                    if (jsonInv.has("recordCode")) {
                        ((ObjectNode) jsonInv).put("recordCode",jsonInv.findValue("recordCode").toString());
                    }
                    inventory = Json.fromJson(jsonInv, Inventory.class);
                    inventory.setItemId(item.getId());
//                Logger.error(inventory.toString());
                    //更新库存信息
                    if (jsonInv.has("id")) {
                        inventoryService.updateInventory(inventory);
                    }
                    //录入库存信息
                    else {
                        inventory.setInvTitle(item.getItemTitle());
                        inventory.setState("Y");
//                    Logger.error("sku信息:"+inventory);
                        inventoryService.insertInventory(inventory);
                        Logger.error("录入一条库存:"+inventory.getId());
                        String createDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                        itemStatis.setCreateDate(createDate);
                        itemStatis.setSkuId(inventory.getId());
                        itemStatis.setColor(inventory.getItemColor());
                        itemStatis.setSize(inventory.getItemSize());
                        itemStatisService.insertItemStatis(itemStatis);
                    }
                    list.add(inventory.getId());
                }
                //往vary_price表插入数据
                if (jsonNode.has("varyPrices")) {
                    for(final JsonNode varyPriceNode : jsonNode.findValue("varyPrices")) {
                        VaryPrice varyPrice = Json.fromJson(varyPriceNode, VaryPrice.class);
                        varyPrice.setInvId(inventory.getId());
                        varyPrice.setStatus("Y");
                        //更新多样化价格信息
                        if (varyPriceNode.has("id")) {
                            varyPriceService.updateVaryPrice(varyPrice);
                        }
                        else {
                            varyPriceService.insertVaryPrice(varyPrice);
                        }
                    }
                }
            }
        }
        return list;
    }

}

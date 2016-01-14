package middle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.DataLog;
import entity.Inventory;
import entity.Item;
import play.Logger;
import play.libs.Json;
import service.DataLogService;
import service.InventoryService;
import service.ItemService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/14.
 * kakao china.
 *
 * controller 和 service 的中间层,为了解决一个modules只能注入一个数据库中的service,
 * 在同一个方法里注入不同库中的mapper来处理复杂的业务逻辑
 */
public class ItemMiddle {

    /**
     * 录入或更新商品信息和库存信息, 同时录入日志信息
     * @param json 商品和库存信息json串
     * @param nickName 操作人员
     * @param operateIp 操作人员ip
     * @return
     */
    public static List<Long> itemSave(ItemService itemService, InventoryService inventoryService, DataLogService dataLogService, JsonNode json, String nickName, String operateIp) {
        List<Long> list = new ArrayList<>();
        Item item = new Item();
        DataLog dataLog = new DataLog();
        dataLog.setOperateUser(nickName);
        dataLog.setOperateIp(operateIp);
        //记录商品的状态
        String state = "";
        //记录各个sku状态的个数
        int y_num = 0;
        int d_num = 0;
        int k_num = 0;
        int sku_num= 0;
        //items表录入数据
        if (json.has("item")) {
            JsonNode jsonItem = json.findValue("item");
            if (jsonItem.has("publicity")) {
                ((ObjectNode) jsonItem).put("publicity",jsonItem.findValue("publicity").toString());
            }
            if (jsonItem.has("itemDetailImgs")) {
                ((ObjectNode) jsonItem).put("itemDetailImgs",jsonItem.findValue("itemDetailImgs").toString());
            }
            if (jsonItem.has("itemFeatures")) {
                ((ObjectNode) jsonItem).put("itemFeatures",jsonItem.findValue("itemFeatures").toString());
            }
            item = Json.fromJson(json.findValue("item"),Item.class);
//            Logger.error(item.toString());
            //更新商品信息
            if (jsonItem.has("id")) {
                //数据录入data_log表
                Long itemId = item.getId();
                dataLog.setOperateType("修改商品");
                dataLog.setLogContent("修改商品"+itemId);
                Item originItem = itemService.getItem(itemId);
                List<Inventory> originInv = inventoryService.getInventoriesByItemId(itemId);
                dataLog.setOriginData("{\"item\":"+Json.toJson(originItem).toString() + ",\"inventories\":"+Json.toJson(originInv).toString()+"}");
                dataLog.setNewData(json.toString());
                Logger.error("log数据:"+dataLog.toString());
                dataLogService.insertDataLog(dataLog);
                itemService.itemUpdate(item);
                state = item.getState();
            }
            //录入商品信息
            else {
                item.setState("Y");
                item.setOrDestroy(false);
                Logger.error("item数据:"+item);
                itemService.itemInsert(item);
                //数据录入data_log表
                dataLog.setOperateType("新增商品");
                dataLog.setLogContent("新增商品"+item.getId());
                dataLog.setOriginData("{}");
                dataLog.setNewData(json.toString());
                Logger.error("log数据:"+dataLog.toString());
                dataLogService.insertDataLog(dataLog);
            }
        }
        //往inventories表插入数据
        if (json.has("inventories")) {
            for(final JsonNode jsonNode : json.findValue("inventories")) {
                sku_num = json.findValue("inventories").size();
                if (jsonNode.has("itemPreviewImgs")) {
                    ((ObjectNode) jsonNode).put("itemPreviewImgs",jsonNode.findValue("itemPreviewImgs").toString());
                    ((ObjectNode) jsonNode).put("recordCode",jsonNode.findValue("recordCode").toString());
                }
                Inventory inventory = Json.fromJson(jsonNode, Inventory.class);
                inventory.setItemId(item.getId());
                inventory.setInvTitle(item.getItemTitle());
//                Logger.error(inventory.toString());
                //更新库存信息
                if (jsonNode.has("id")) {
                    //SKU状态有一个为Y,item状态为Y
                    if (inventory.getState().equals("Y")) {y_num+=1; item.setState("Y");}
                    if (inventory.getState().equals("D"))  {d_num+=1;}
                    if (inventory.getState().equals("K"))  {k_num+=1;}
                    //item状态设置为下架的情况
                    if (d_num>0 && y_num==0 && "Y".equals(state)) {item.setState("D");}
                    inventoryService.updateInventory(inventory);
                    itemService.itemUpdate(item);
                }
                //录入库存信息
                else {
                    inventory.setState("Y");
                    item.setOrDestroy(false);
                    inventoryService.insertInventory(inventory);

                }
                //查找该商品的库存中主skuId,更新到items表中masterInvId
//                List<Inventory> inventories = inventoryMapper.getInventoriesByItemId(item.getId());
//                for(Inventory inv : inventories) {
//                    if (inv.getOrMasterInv()==true) {
//                        Item item1 = itemMapper.getItem(item.getId());
//                        item1.setMasterInvId(inv.getId());
//                        itemMapper.itemUpdate(item1);
//                    }
//                }
                list.add(inventory.getId());
            }
        }
        //item状态若修改为D下架或K售空,该item下的所有sku状态都改为D或K,item状态由D或K改为Y sku状态全改为Y
        Long id = item.getId();
//        Logger.error("商品状态:"+state);
        List<Inventory> invList = inventoryService.getInventoriesByItemId(id);
        if ("D".equals(state) || "K".equals(state) || ("Y".equals(state) && d_num==sku_num) || ("Y".equals(state) && k_num==sku_num)) {
            for(Inventory inv : invList) {
                inv.setState(state);
                inventoryService.updateInventory(inv);
            }
            item.setState(state);
            itemService.itemUpdate(item);
        }
        return list;
    }


}

package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Inventory;
import entity.Item;
import mapper.InventoryMapper;
import mapper.ItemMapper;
import play.Logger;
import play.libs.Json;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理接口
 * Created by howen on 15/11/11.
 */
public class ItemServiceImpl implements ItemService{

    @Inject
    private ItemMapper itemMapper;

    @Inject
    private InventoryMapper inventoryMapper;

    @Override
    public List<Item> itemSearch(Item item) {
        return itemMapper.getItemPage(item);
    }

    @Override
    public List<Long> insertItem(JsonNode jsonItem, JsonNode jsonInventories) {
        List<Long> list = new ArrayList<>();
        if (jsonItem.has("publicity")) {
            ((ObjectNode) jsonItem).put("publicity",jsonItem.findValue("publicity").toString());
        }
        if (jsonItem.has("itemDetailImgs")) {
            ((ObjectNode) jsonItem).put("itemDetailImgs",jsonItem.findValue("itemDetailImgs").toString());
        }
        if (jsonItem.has("itemFeatures")) {
            ((ObjectNode) jsonItem).put("itemFeatures",jsonItem.findValue("itemFeatures").toString());
        }
        Item item = Json.fromJson(jsonItem,Item.class);
        item.setState("Y");
        item.setOrDestroy(false);
        Logger.error(item.toString());
        this.itemMapper.insertItem(item);

        //往inventories表插入数据
        for(final JsonNode jsonNode : jsonInventories) {
            if (jsonNode.has("itemPreviewImgs")) {
                ((ObjectNode) jsonNode).put("itemPreviewImgs",jsonNode.findValue("itemPreviewImgs").toString());
            }
            Inventory inventory = Json.fromJson(jsonNode, Inventory.class);
            inventory.setItemId(item.getId());
            inventory.setSoldAmount(0);
            inventory.setRestAmount(inventory.getAmount());
            inventory.setOrDestroy(false);
            inventory.setState("Y");
            inventory.setShipFee(new BigDecimal(0.00));
            inventory.setInvTitle(item.getItemTitle());
            Logger.error(item.toString());
            this.inventoryMapper.insertInventory(inventory);
            list.add(item.getId());

        }
        return list;
    }
}

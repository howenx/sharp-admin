package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Brands;
import entity.Cates;
import entity.Inventory;
import entity.Item;
import mapper.BrandsMapper;
import mapper.CatesMapper;
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

    @Inject
    private BrandsMapper brandsMapper;

    @Inject
    private CatesMapper catesMapper;

    @Override
    public List<Item> itemSearch(Item item) {
        return itemMapper.getItemPage(item);
    }


    /**
     * 录入或更新商品信息和库存信息
     * @param json 商品和库存信息json串
     * @return
     */
    @Override
    public List<Long> itemSave(JsonNode json) {
        List<Long> list = new ArrayList<>();
        Item item = new Item();
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
            item.setState("Y");
            item.setOrDestroy(false);
            Logger.error(item.toString());
            if (jsonItem.has("id")) {
                this.itemMapper.itemUpdate(item);
            }
            else this.itemMapper.itemInsert(item);
        }

        if (json.has("inventories")) {
            //往inventories表插入数据
            for(final JsonNode jsonNode : json.findValue("inventories")) {
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
                Logger.error(inventory.toString());
                if (jsonNode.has("id")) {
                    this.inventoryMapper.updateInventory(inventory);
                }
                else this.inventoryMapper.insertInventory(inventory);
                list.add(inventory.getId());
            }
        }
        return list;
    }

    @Override
    public Brands getBrand(Long brandId) {
        return brandsMapper.getBrand(brandId);
    }

    @Override
    public Cates getCate(Long cateId) {
        return catesMapper.getCate(cateId);
    }

    @Override
    public Item getItem(Long id) {
        return itemMapper.getItem(id);
    }

    @Override
    public List<Inventory> getinventoriesByItemId(Long itemId) {
        return inventoryMapper.getInventoriesByItemId(itemId);
    }

    /**
     * added by Tiffany Zhu 15/11/27.
     * 商品查询
     * @return list
     */
    @Override
    public List<Item> getItemsAll() { return itemMapper.getItemsAll(); }
}

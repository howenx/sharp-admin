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

    /**
     * 分页查询商品列表
     * @param item
     * @return list of Item
     */
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
                inventory.setState("Y");
                inventory.setInvTitle(item.getItemTitle());
                Logger.error(inventory.toString());
                if (jsonNode.has("id")) {
                    this.inventoryMapper.updateInventory(inventory);
                }
                else this.inventoryMapper.insertInventory(inventory);
                //查找该商品的库存中主skuId,更新到items表中masterInvId
                List<Inventory> inventories = inventoryMapper.getInventoriesByItemId(item.getId());
                for(Inventory inv : inventories) {
                    if (inv.getOrMasterInv()==true) {
                        Item item1 = itemMapper.getItem(item.getId());
                        item1.setMasterInvId(inv.getId());
                        itemMapper.itemUpdate(item1);
                    }
                }
                list.add(inventory.getId());
            }
        }
        return list;
    }

    /**
     * 由品牌id得到品牌Brands
     * @param brandId 品牌id
     * @return Brands
     */
    @Override
    public Brands getBrand(Long brandId) {
        return brandsMapper.getBrand(brandId);
    }

    /**
     * 由类别id得到类别Cates
     * @param cateId 类别id
     * @return Cates
     */
    @Override
    public Cates getCate(Long cateId) {
        return catesMapper.getCate(cateId);
    }

    /**
     * 由商品id得到商品Item
     * @param id 商品id
     * @return Item
     */
    @Override
    public Item getItem(Long id) {
        return itemMapper.getItem(id);
    }

    /**
     * added by Tiffany Zhu 15/11/27.
     * 商品查询
     * @return list
     */
    @Override
    public List<Item> getItemsAll() { return itemMapper.getItemsAll(); }

}

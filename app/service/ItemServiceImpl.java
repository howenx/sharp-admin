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
import play.libs.Json;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
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
                if(item.getState()=="N") item.setOrDestroy(true);
                this.itemMapper.itemUpdate(item);
            }
            //录入商品信息
            else {
                item.setState("Y");
                item.setOrDestroy(false);
                this.itemMapper.itemInsert(item);
            }
        }
        //往inventories表插入数据
        if (json.has("inventories")) {
            //记录各个sku状态的个数
            int y_num = 0;
            int d_num = 0;
            int k_num = 0;
            int n_num = 0;
            for(final JsonNode jsonNode : json.findValue("inventories")) {
                int sku_num = json.findValue("inventories").size();
                if (jsonNode.has("itemPreviewImgs")) {
                    ((ObjectNode) jsonNode).put("itemPreviewImgs",jsonNode.findValue("itemPreviewImgs").toString());
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
                    if (inventory.getState().equals("N"))  {n_num+=1; inventory.setOrDestroy(true);}
                    //item状态设置为下架的情况
                    if (d_num > 0 && y_num==0 || (k_num+n_num==sku_num)) {item.setState("D");}
                    if (k_num == sku_num) {item.setState("K");}
                    if (n_num == sku_num) {item.setState("N");}
                    this.inventoryMapper.updateInventory(inventory);
                    this.itemMapper.itemUpdate(item);
                }
                //录入库存信息
                else {
                    inventory.setState("Y");
                    item.setOrDestroy(false);
                    this.inventoryMapper.insertInventory(inventory);

                }
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

    /**
     * get single brands by id.
     *
     * @param brandId Integer
     * @return entity.Brands
     */

    @Override
    public Brands getBrands(Long brandId) {

        return this.brandsMapper.getBrands(brandId);
    }

    /**
     * get all brands from table.
     *
     * @return List of entity.Brands
     */

    @Override
    public List<Brands> getAllBrands() {

        return brandsMapper.getAllBrands();
    }

    /**
     * get parent categories.
     *
     * @return List of Cates
     */

    @Override
    public List<Cates> getParentCates() {

        return catesMapper.getParentCates();
    }

    /**
     * get sub categories.
     *
     * @param hashMap HashMap
     * @return List of Cates
     */

    @Override
    public List<Cates> getSubCates(HashMap<String, Long> hashMap) {

        return catesMapper.getSubCates(hashMap);
    }

    /**
     * 新增商品类别
     * @param cates
     * @return
     */
    @Override
    public Integer catesSave(Cates cates) {return catesMapper.insertCates(cates);}

}

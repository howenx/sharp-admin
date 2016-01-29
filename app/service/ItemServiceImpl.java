package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.*;
import mapper.*;
import play.Logger;
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

    @Override
    public Long itemInsert(Item item) {
        return itemMapper.itemInsert(item);
    }

    @Override
    public void itemUpdate(Item item) {
        itemMapper.itemUpdate(item);
    }

    /**
     * 录入或更新商品信息和库存信息
     * @param json 商品和库存信息json串
     * @param user 操作人员
     * @param operateIp 操作人员ip
     * @return
     */
    @Override
    public List<Long> itemSave(JsonNode json,String user, String operateIp) {
        List<Long> list = new ArrayList<>();
        Item item = new Item();
        DataLog dataLog = new DataLog();
        dataLog.setOperateUser(user);
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
                Item originItem = itemMapper.getItem(itemId);
                List<Inventory> originInv = inventoryMapper.getInventoriesByItemId(itemId);
                dataLog.setOriginData(originItem.toString() + originInv.toString());
                dataLog.setNewData(json.toString());
                Logger.error("log数据:"+dataLog.toString());
//                dataLogMapper.insertDataLog(dataLog);
                this.itemMapper.itemUpdate(item);
                state = item.getState();
            }
            //录入商品信息
            else {
                item.setState("Y");
                item.setOrDestroy(false);
                this.itemMapper.itemInsert(item);
                //数据录入data_log表
                dataLog.setOperateType("新增商品");
                dataLog.setLogContent("新增商品"+item.getId());
                dataLog.setOriginData("无");
                dataLog.setNewData(json.toString());
                Logger.error("log数据:"+dataLog.toString());
//                dataLogMapper.insertDataLog(dataLog);
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
        List<Inventory> invList = inventoryMapper.getInventoriesByItemId(id);
        if ("D".equals(state) || "K".equals(state) || ("Y".equals(state) && d_num==sku_num) || ("Y".equals(state) && k_num==sku_num)) {
            for(Inventory inv : invList) {
                inv.setState(state);
                this.inventoryMapper.updateInventory(inv);
            }
            item.setState(state);
            this.itemMapper.itemUpdate(item);
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
     * get brands page
     * @param brands
     * @return
     */
    @Override
    public List<Brands> getBrandsPage(Brands brands) {
        return brandsMapper.getBrandsPage(brands);
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
     * 商品分类列表
     * @return
     */
    @Override
    public List<Cates> getCatesAll() {
        return catesMapper.getCatesAll();
    }

    /**
     * 新增商品类别
     * @param json
     * @return
     */
    @Override
    public void catesSave(JsonNode json) {
        Logger.error(json.toString());
        Cates cates = play.libs.Json.fromJson(json,Cates.class);
        catesMapper.insertCates(cates);
    }

    /**
     * 新增品牌
     * @param json
     */
    @Override
    public void insertBrands(JsonNode json) {
        Logger.error(json.toString());
        Brands brands = play.libs.Json.fromJson(json,Brands.class);
        brandsMapper.insertBrands(brands);
    }

}

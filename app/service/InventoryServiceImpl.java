package service;

import domain.Inventory;
import domain.Skus;
import mapper.InventoryMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu.
 */
public class InventoryServiceImpl implements InventoryService {

    @Inject
    private InventoryMapper inventoryMapper;

    /**
     * 录入一条库存记录
     * @param inventory 库存记录
     * @return id
     */
    @Override
    public Integer insertInventory(Inventory inventory){
        return inventoryMapper.insertInventory(inventory);
    }

    /**
     * 由库存id获得一条库存记录
     * @param id
     * @return Inventory
     */
    @Override
    public Inventory getInventory(Long id) {
        return inventoryMapper.getInventory(id);
    }

    /**
     * 由商品id获得该商品的所有库存信息
     * @param itemId 商品id
     * @return list of inventory
     */
    @Override
    public List<Inventory> getInventoriesByItemId(Long itemId) {
        return inventoryMapper.getInventoriesByItemId(itemId);
    }

    /**
     * 更新一条库存记录
     * @param inventory 库存记录
     */
    @Override
    public void updateInventory(Inventory inventory) {
         inventoryMapper.updateInventory(inventory);
    }

    /**
     * 查询所有的库存记录
     * @return list of inventory
     */
    @Override
    public List<Inventory> getAllInventories() {
        return  inventoryMapper.getAllInventories();
    }

    /**
     * 分页查询sku信息
     * @param inventory sku
     * @return list of inventory
     */
    @Override
    public List<Inventory> invSearch(Inventory inventory) {
        return inventoryMapper.getSkuPage(inventory);
    }

    /**
     * 获取商品主sku Added by Tiffany Zhu 2016.01.14
     * @param itemId
     * @return
     */
    @Override
    public Inventory getMasterInventory(Long itemId) {
        return inventoryMapper.getMasterInventory(itemId);
    }

    /**
     * 更新主题ID   Added by Tiffany Zhu 2016.01.29
     * @param inventory
     */
    @Override
    public void updInventoryThemeId(Inventory inventory) {
        inventoryMapper.updInventoryThemeId(inventory);
    }

    /**
     * 由库存id查询视图skus的信息     Added by Sunny Wu 2016.03.01
     * @param inventory
     * @return List of Skus
     */
    @Override
    public List<Skus> getSkus(Inventory inventory) {
        return inventoryMapper.getSkus(inventory);
    }

    /**
     * 获取状态为"正常"和"预售"的库存商品    Added by Tiffany Zhu 2016.03.01
     *
     * @return
     */
    @Override
    public List<Inventory> getAvailableInventory() {
        return inventoryMapper.getAvailableInventory();
    }

    /**
     * 查询视图skus的所有信息        Added by Sunny Wu 2016.03.03
     * @return List of Skus
     */
    @Override
    public List<Skus> getAllSkus() {
        return inventoryMapper.getAllSkus();
    }

    /**
     * 由skuTypeId获取一条skus信息      Added By Sunny Wu 2016.09.01
     * @param skuTypeId 类型Id
     * @return
     */
    @Override
    public Skus getByTypeId(Long skuTypeId) {
        return inventoryMapper.getByTypeId(skuTypeId);
    }

}

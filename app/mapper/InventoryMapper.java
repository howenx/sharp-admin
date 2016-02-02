package mapper;

import entity.Inventory;

import java.util.List;

/**
 * InventoryMapper.xml for InventoryMapper interface.
 *
 * Created by Sunny Wu.
 */
public interface InventoryMapper {

    Integer insertInventory(Inventory inventory);

    Inventory getInventory(Long id);

    /**
     * 由itemId 获得多条库存记录
     * @param itemId 商品id
     * @return List of Inventory
     */
    List<Inventory> getInventoriesByItemId(Long itemId);

    /**
     * 更新一条库存信息
     * @param inventory
     */
    void updateInventory(Inventory inventory);

    List<Inventory> getAllInventories();

    List<Inventory> getSkuPage(Inventory inventory);

    /**
     * 获取商品的主sku  Added by Tiffany ZHu 2016.01.14
     * @param itemId
     * @return
     */
    Inventory getMasterInventory(Long itemId);

    /**
     * 更新主题ID   Added by Tiffany Zhu 2016.01.29
     * @param inventory
     */
    void updInventoryThemeId(Inventory inventory);

}

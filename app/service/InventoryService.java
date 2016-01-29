package service;

import entity.Inventory;

import java.util.List;

/**
 * Created by Sunny Wu 15/12/9.
 */
public interface InventoryService {

    Integer insertInventory(Inventory inventory);

    Inventory getInventory(Long id);

    List<Inventory> getInventoriesByItemId(Long itemId);

    void updateInventory(Inventory inventory);

    List<Inventory> getAllInventories();

    /**
     * 获取商品的主sku Added by Tiffany Zhu 2016.01.14
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

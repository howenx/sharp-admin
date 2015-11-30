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

}

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

    List<Inventory> getInventoriesByItemId(Long itemId);

    List<Inventory> getAllInventories();

}

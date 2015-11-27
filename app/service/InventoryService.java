package service;

import entity.Inventory;

import java.util.List;

/**
 * Created by Sunny Wu.
 */
public interface InventoryService {

    Integer insertInventory(Inventory inventory);

    Inventory getInventory(Long id);

    List<Inventory> getAllInventories();
}

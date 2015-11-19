package service;

import com.google.inject.Inject;
import entity.Inventory;

import java.util.List;

/**
 * Created by Sunny Wu.
 */
public class InventoryServiceImpl implements InventoryService {

    @Inject InventoryService inventoryService;

    @Override
    public Integer insertInventory(Inventory inventory){
        return inventoryService.insertInventory(inventory);
    }

    @Override
    public Inventory getInventory(Long id){
        return inventoryService.getInventory(id);
    }

    @Override
    public List<Inventory> getInventoryByItemId(Long itemId){
        return inventoryService.getInventoryByItemId(itemId);
    }

    @Override
    public List<Inventory> getAllInventories(){
        return inventoryService.getAllInventories();
    }
}

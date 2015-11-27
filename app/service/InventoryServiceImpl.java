package service;

import com.google.inject.Inject;
import entity.Inventory;
import mapper.InventoryMapper;

import java.util.List;

/**
 * Created by Sunny Wu.
 */
public class InventoryServiceImpl implements InventoryService {

    @Inject
    private InventoryMapper inventoryMapper;

    @Override
    public Integer insertInventory(Inventory inventory){
        return inventoryMapper.insertInventory(inventory);
    }

    @Override
    public Inventory getInventory(Long id){
        return inventoryMapper.getInventory(id);
    }

    @Override
    public List<Inventory> getAllInventories(){
        return inventoryMapper.getAllInventories();
    }

}

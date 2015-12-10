package service;

import entity.Carriage;
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


    //运费设置
    Integer insertCarriage(Carriage carriage);

    void updateCarriage(Carriage carriage);

    Carriage getCarriage(Long id);

    List<Carriage> getAllCarriage();

    List<Carriage> getModel();
}

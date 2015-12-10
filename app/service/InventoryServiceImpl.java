package service;

import entity.Carriage;
import entity.Inventory;
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
     * 录入一条运费信息
     * @param carriage 运费
     * @return id
     */
    @Override
    public Integer insertCarriage(Carriage carriage) {
        return inventoryMapper.insertCarriage(carriage);
    }

    /**
     * 更新一条运费信息
     * @param carriage
     */
    @Override
    public void updateCarriage(Carriage carriage) {
        inventoryMapper.updateCarriage(carriage);
    }

    /**
     * 根据id获取一条运费信息
     * @param id
     * @return Carriage
     */
    @Override
    public Carriage getCarriage(Long id) {
        return inventoryMapper.getCarriage(id);
    }

    /**
     * 获取所有的运费信息
     * @return list of Carriage
     */
    @Override
    public List<Carriage> getAllCarriage() {
        return inventoryMapper.getAllCarriage();
    }

    /**
     * 获取运费模板
     * @return list of Carriage
     */
    @Override
    public List<Carriage> getModel(){
        return inventoryMapper.getModel();
    }

}

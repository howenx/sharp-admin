package service;

import domain.Inventory;
import domain.Skus;

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

    List<Inventory> invSearch(Inventory inventory);

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

    /**
     * 由库存id查询视图skus的信息     Added by Sunny Wu 2016.03.01
     * @param inventory
     * @return List of Skus
     */
    List<Skus> getSkus(Inventory inventory);

    /**
     * 获取状态为"正常"和"预售"的库存商品    Added by Tiffany Zhu 2016.03.01
     * @return
     */
    List<Inventory> getAvailableInventory();

    /**
     * 查询视图skus的所有信息        Added by Sunny Wu 2016.03.03
     * @return List of Skus
     */
    List<Skus> getAllSkus();

    /**
     * 由skuTypeId获取一条skus信息      Added By Sunny Wu 2016.09.01
     * @param skuTypeId 类型Id
     * @return
     */
    Skus getByTypeId(Long skuTypeId);

}

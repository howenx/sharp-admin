package mapper;

import domain.Inventory;
import domain.Skus;

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

    /**
     * 获取所有的除下架外的库存信息
     * @return list of Inventory
     */
    List<Inventory> getAllNorInventories();

    /**
     * 查询所有主SKU        Added By Sunny Wu 2016.09.05
     * @return list of Inventory
     */
    List<Inventory> getAllMasterSKU();

    /**
     * 根据库存地获取商品集合       Added by Tiffany Zhu 2016.09.08
     * @param inventory
     * @return
     */
    List<Inventory> getInventoryList(Inventory inventory);

    /**
     * 查询所有需要报关的商品信息       Added By Sunny Wu 2016.09.12
     * @return
     */
    List<Inventory> getAllCustomSku();
}

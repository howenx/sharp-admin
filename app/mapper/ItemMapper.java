package mapper;

import domain.Item;

import java.util.List;

/**
 * 商品管理mapper
 * Created by howen on 15/11/11.
 */
public interface ItemMapper {

    List<Item> getItemPage(Item item);

    Long itemInsert(Item item);

    Item getItem(Long id);

    void itemUpdate(Item item);

    List<Item> getItemsAll();

    /**
     * 更新商品的主题ID    Added by Tiffany Zhu 2016.01.29
     * @param item
     */
    void updItemThemeId(Item item);

    /**
     * 按照供应商查询item      Added by Tiffany Zhu 2016.04.20
     * @param supplyMerch
     * @return
     */
    List<Item> getItemBySupplier(String supplyMerch);
}

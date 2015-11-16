package mapper;

import entity.Item;

import java.util.List;

/**
 * 商品管理mapper
 * Created by howen on 15/11/11.
 */
public interface ItemMapper {

    List<Item> getItemPage(Item item);

    Integer insertItem(Item item);
}

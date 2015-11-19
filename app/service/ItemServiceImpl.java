package service;

import entity.Item;
import mapper.ItemMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * 商品管理接口
 * Created by howen on 15/11/11.
 */
public class ItemServiceImpl implements ItemService{

    @Inject
    private ItemMapper itemMapper;

    @Override
    public List<Item> itemSearch(Item item) {
        return itemMapper.getItemPage(item);
    }

    @Override
    public  Integer insertItem(Item item) {
        return itemMapper.insertItem(item);
    }
}

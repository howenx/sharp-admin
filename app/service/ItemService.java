package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Brands;
import entity.Cates;
import entity.Inventory;
import entity.Item;

import java.util.List;

/**
 * 商品管理
 * Created by howen on 15/11/11.
 */
public interface ItemService {

    List<Item> itemSearch(Item item);

    List<Long> itemInsert(JsonNode json);

    Item getItem(Long id);

    void itemUpdate(Item item);

    List<Inventory> getinventoriesByItemId(Long itemId);

    Brands getBrand(Long brandId);

    Cates getCate(Long cateId);

    public List<Item> itemSearch();
}

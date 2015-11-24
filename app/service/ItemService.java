package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Item;

import java.util.List;

/**
 * 商品管理
 * Created by howen on 15/11/11.
 */
public interface ItemService {

    List<Item> itemSearch(Item item);

    List<Long> insertItem(JsonNode jsonItem, JsonNode jsonInventories);
}

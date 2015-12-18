package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Brands;
import entity.Cates;
import entity.Item;

import java.util.HashMap;
import java.util.List;

/**
 * 商品管理
 * Created by howen on 15/11/11.
 */
public interface ItemService {

    /**
     * get single brands entity by id.
     *
     * @param brandId Long
     * @return entity.Brands
     */
    Brands getBrands(Long brandId);

    /**
     * get All brands from table
     *
     * @return List of Brands.
     */
    List<Brands> getAllBrands();

    /**
     * get parent categories.
     *
     * @return List Cates entites.
     */
    List<Cates> getParentCates();

    /**
     * get sub categories.
     *
     * @param hashMap HashMap
     * @return List Cates entites.
     */
    List<Cates> getSubCates(HashMap<String, Long> hashMap);

    List<Item> itemSearch(Item item);

    List<Long> itemSave(JsonNode json);

    Item getItem(Long id);

    Cates getCate(Long cateId);

    List<Item> getItemsAll();

    /**
     * 新增商品类别
     * @param cates
     * @return
     */
    Integer catesSave(Cates cates);

    List<Brands>  getBrandsPage(Brands brands);
}

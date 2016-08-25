package service;

import com.fasterxml.jackson.databind.JsonNode;
import domain.Brands;
import domain.Cates;
import domain.Item;
import domain.VersionVo;

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

    Long itemInsert(Item item);

    void itemUpdate(Item item);

    Item getItem(Long id);

    Cates getCate(Long cateId);

    List<Item> getItemsAll();

    /**
     * 商品分类列表
     * @return
     */
    List<Cates> getCatesAll();

    /**
     * 新增商品类别
     * @param json
     * @return
     */
    void catesSave(JsonNode json);

    List<Brands>  getBrandsPage(Brands brands);

    /**
     * 保存品牌
     * @param json
     */
    void insertBrands(JsonNode json);


    Boolean insertVersioning(VersionVo versionVo);

    List<VersionVo> getVersioning(VersionVo versionVo);

    Boolean updateVersioning(VersionVo versionVo);

    /**
     * 按照供应商查询item      Added by Tiffany Zhu 2016.04.20
     * @param supplyMerch
     * @return
     */
    List<Item> getItemBySupplier(String supplyMerch);

    /**
     * 由id修改类别名称        Added By Sunny Wu 2016.06.24
     * @param cates 类别
     * @return
     */
    boolean updateCateNm(Cates cates);

    /**
     * 获取全部的二级商品类别 Added by Tiffany Zhu 2016.08.25
     * @return
     */
    List<Cates> getSecDirectCates();

}

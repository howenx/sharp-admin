package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Brands;
import entity.Cates;
import entity.Item;
import entity.VersionVo;
import mapper.*;
import play.Logger;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

/**
 * 商品管理接口
 * Created by howen on 15/11/11.
 */
public class ItemServiceImpl implements ItemService{

    @Inject
    private ItemMapper itemMapper;

    @Inject
    private InventoryMapper inventoryMapper;

    @Inject
    private BrandsMapper brandsMapper;

    @Inject
    private CatesMapper catesMapper;


    @Inject
    private VersioningMapper versioningMapper;

    /**
     * 分页查询商品列表
     * @param item
     * @return list of Item
     */
    @Override
    public List<Item> itemSearch(Item item) {
        return itemMapper.getItemPage(item);
    }

    @Override
    public Long itemInsert(Item item) {
        return itemMapper.itemInsert(item);
    }

    @Override
    public void itemUpdate(Item item) {
        itemMapper.itemUpdate(item);
    }

    /**
     * 由类别id得到类别Cates
     * @param cateId 类别id
     * @return Cates
     */
    @Override
    public Cates getCate(Long cateId) {
        return catesMapper.getCate(cateId);
    }

    /**
     * 由商品id得到商品Item
     * @param id 商品id
     * @return Item
     */
    @Override
    public Item getItem(Long id) {
        return itemMapper.getItem(id);
    }

    /**
     * added by Tiffany Zhu 15/11/27.
     * 商品查询
     * @return list
     */
    @Override
    public List<Item> getItemsAll() { return itemMapper.getItemsAll(); }

    /**
     * get single brands by id.
     *
     * @param brandId Integer
     * @return entity.Brands
     */

    @Override
    public Brands getBrands(Long brandId) {

        return this.brandsMapper.getBrands(brandId);
    }

    /**
     * get all brands from table.
     *
     * @return List of entity.Brands
     */

    @Override
    public List<Brands> getAllBrands() {

        return brandsMapper.getAllBrands();
    }

    /**
     * get brands page
     * @param brands
     * @return
     */
    @Override
    public List<Brands> getBrandsPage(Brands brands) {
        return brandsMapper.getBrandsPage(brands);
    }
    /**
     * get parent categories.
     *
     * @return List of Cates
     */

    @Override
    public List<Cates> getParentCates() {

        return catesMapper.getParentCates();
    }

    /**
     * get sub categories.
     *
     * @param hashMap HashMap
     * @return List of Cates
     */

    @Override
    public List<Cates> getSubCates(HashMap<String, Long> hashMap) {

        return catesMapper.getSubCates(hashMap);
    }

    /**
     * 商品分类列表
     * @return
     */
    @Override
    public List<Cates> getCatesAll() {
        return catesMapper.getCatesAll();
    }

    /**
     * 新增商品类别
     * @param json
     * @return
     */
    @Override
    public void catesSave(JsonNode json) {
        Logger.error(json.toString());
        Cates cates = play.libs.Json.fromJson(json,Cates.class);
        catesMapper.insertCates(cates);
    }

    /**
     * 新增品牌
     * @param json
     */
    @Override
    public void insertBrands(JsonNode json) {
        Logger.error(json.toString());
        Brands brands = play.libs.Json.fromJson(json,Brands.class);
        brandsMapper.insertBrands(brands);
    }


    @Override
    public Boolean insertVersioning(VersionVo versionVo) {
        return versioningMapper.insertVersioning(versionVo)>=0;
    }

    @Override
    public List<VersionVo> getVersioning(VersionVo versionVo) {
        return versioningMapper.getVersioning(versionVo);
    }

    @Override
    public Boolean updateVersioning(VersionVo versionVo) {
        return versioningMapper.updateVersioning(versionVo)>=0;
    }

}

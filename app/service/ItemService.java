package service;

import entity.Brands;
import entity.Cates;
import entity.Products;
import play.libs.Json;

import java.util.HashMap;
import java.util.List;

public interface ItemService {

    /**
     * get single brands entity by id.
     *
     * @param brandId Integer
     * @return entity.Brands
     */
    Brands getBrands(Integer brandId);

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
    List<Cates> getSubCates(HashMap<String, Integer> hashMap);

    /**
     * get single products entity by id.
     *
     * @param productId
     * @return entity.Products
     */
    Products getProducts(Integer productId);

    /**
     * get All products from table
     *
     * @return List of Products
     */
    List<Products> getAllProducts();

    /**
     * insert products into prods table
     *
     * @param multiProducts
     * @return boolean
     */
    List<Integer> insertProducts(Json multiProducts);

}

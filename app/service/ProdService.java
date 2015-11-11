package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Brands;
import entity.Cates;
import entity.Products;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProdService {

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
    Products getProducts(Long productId);

    /**
     * get All products from table
     * @param map
     * @return List of Products
     */
    List<Products> getAllProducts(Map<String,Integer> map);

    /**
     * insert products into prods table
     *
     * @param jsonProds
     * @return List<Long>
     */
    List<Long> insertProducts(JsonNode jsonProds);

}
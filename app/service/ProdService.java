package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Brands;
import entity.Cates;
import entity.Products;
import entity.Stock;

import java.util.HashMap;
import java.util.List;

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
    List<Cates> getSubCates(HashMap<String, Long> hashMap);

    /**
     * get single products entity by id.
     *
     * @param productId
     * @return entity.Products
     */
    Products getProducts(Long productId);

    /**
     * get All products from table
     * @param products
     * @return List of Products
     */
    List<Products> getAllProducts(Products products);

    /**
     * insert products into prods table
     *
     * @param jsonProd
     * @param jsonStocks
     * @return List<Long>
     */
    List<Long> insertProducts(JsonNode jsonProd, JsonNode jsonStocks);

    /**
     * get stocks by product id
     * @param prodId
     * @return List of Stocks
     */
    List<Stock> getStocksByProdId(Long prodId);

}

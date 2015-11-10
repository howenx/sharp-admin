package mapper;

import entity.Products;

import java.util.List;
import java.util.Map;

/**
 * ProductsMapper.xml for ProductsMapper interface.
 *
 * Created by Sunny Wu.
 */
public interface  ProductsMapper {

    /**
     * get products by productId.
     * @param productId
     * @return Products
     */
    Products getProducts(Long productId);

    /**
     * get all products.
     * @param map
     * @return List of Products entity
     */
    List<Products> getAllProducts(Map<String,Integer> map);

    /**
     * insert products
     * @param products
     * @return Integer
     */
    Integer insertProducts(Products products);

}

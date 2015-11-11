package mapper;

import entity.Products;

import java.util.List;

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
     * @param products
     * @return List of Products entity
     */
    List<Products> getAllProducts(Products products);

    /**
     * insert products
     * @param products
     * @return Integer
     */
    Integer insertProducts(Products products);

}

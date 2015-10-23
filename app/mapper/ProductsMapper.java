package mapper;

import entity.Products;

import java.util.List;

/**
 * ProductsMapping.xml for ProductsMapper interface.
 *
 * Created by Sunny Wu.
 */
public interface  ProductsMapper {

    /**
     * get products by productId.
     * @param productId
     * @return Products
     */
    Products getProducts(Integer productId);

    /**
     * get all products.
     * @return List of Products entity
     */
    List<Products> getAllProducts();

    /**
     * insert products
     * @param products
     * @return boolean
     */
    Integer insertProducts(Products products);

}

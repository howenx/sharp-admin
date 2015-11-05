package service;

import entity.Brands;
import entity.Cates;
import entity.Products;
import mapper.BrandsMapper;
import mapper.CatesMapper;
import mapper.ProductsMapper;
import play.Logger;
import play.libs.Json;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    /**
     * inject by guice.
     */
    @Inject
    private BrandsMapper brandsMapper;

    @Inject
    private CatesMapper catesMapper;

    @Inject
    private ProductsMapper productsMapper;

    /**
     * get single brands by id.
     *
     * @param brandId Integer
     * @return entity.Brands
     */

    @Override
    public Brands getBrands(Integer brandId) {

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
    public List<Cates> getSubCates(HashMap<String, Integer> hashMap) {

        return catesMapper.getSubCates(hashMap);
    }

    /**
     * get single products by id.
     *
     * @param productId
     * @return entity.Products
     */

    @Override
    public Products getProducts(Integer productId) {

        return this.productsMapper.getProducts(productId);
    }

    /**
     * get all products from table.
     *
     * @return List of entity.Products
     */

    @Override
    public List<Products> getAllProducts() {

        return this.productsMapper.getAllProducts();
    }

    /**
     * insert products into prods table.
     *
     * @param multiProducts
     * @return boolean
     */

    @Override
    public List<Integer> insertProducts(Json multiProducts) {
        List<Integer> list = new ArrayList();
        Products products = new Products();
        Integer id = productsMapper.insertProducts(products);
        list.add(id);
        return list;
    }

}

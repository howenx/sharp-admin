package service;

import entity.Brands;
import entity.Cates;
import entity.Products;
import mapper.BrandsMapper;
import mapper.CatesMapper;
import mapper.ProductsMapper;
import org.mybatis.guice.transactional.Isolation;
import org.mybatis.guice.transactional.Transactional;

import javax.inject.Inject;
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Brands getBrands(Integer brandId) {

        return this.brandsMapper.getBrands(brandId);
    }

    /**
     * get all brands from table.
     *
     * @return List of entity.Brands
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Brands> getAllBrands() {

        return brandsMapper.getAllBrands();
    }

    /**
     * get parent categories.
     *
     * @return List of Cates
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Products getProducts(Integer productId) {

        return this.productsMapper.getProducts(productId);
    }

    /**
     * get all products from table.
     *
     * @return List of entity.Products
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<Products> getAllProducts() {

        return this.productsMapper.getAllProducts();
    }

    /**
     * insert products into prods table.
     *
     * @param products
     * @return boolean
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Integer insertProducts(Products products) {
        return this.productsMapper.insertProducts(products);
    }

}

package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Brands;
import entity.Cates;
import entity.Products;
import entity.Stock;
import mapper.BrandsMapper;
import mapper.CatesMapper;
import mapper.ProductsMapper;
import mapper.StockMapper;
import play.Logger;
import play.libs.Json;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProdServiceImpl implements ProdService {

    /**
     * inject by guice.
     */
    @Inject
    private BrandsMapper brandsMapper;

    @Inject
    private CatesMapper catesMapper;

    @Inject
    private ProductsMapper productsMapper;

    @Inject
    private StockMapper stockMapper;

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
    public List<Cates> getSubCates(HashMap<String, Long> hashMap) {

        return catesMapper.getSubCates(hashMap);
    }

    /**
     * get single products by id.
     *
     * @param productId
     * @return entity.Products
     */

    @Override
    public Products getProducts(Long productId) {

        return this.productsMapper.getProducts(productId);
    }

    /**
     * get all products from table.
     * @param products
     * @return List of entity.Products
     */

    @Override
    public List<Products> getAllProducts(Products products) {

        return this.productsMapper.getAllProducts(products);
    }

    /**
     * insert products into prods table.
     *
     * @param jsonProd
     * @param jsonStocks
     * @return boolean
     */
    @Override
    public List<Long> insertProducts(JsonNode jsonProd, JsonNode jsonStocks) {

        List<Long> list = new ArrayList<>();
        if (jsonProd.has("productColor")) {
            ((ObjectNode) jsonProd).put("productColor",jsonProd.findValue("productColor").toString());
        }
        if (jsonProd.has("productSize")) {
            ((ObjectNode) jsonProd).put("productSize",jsonProd.findValue("productSize").toString());
        }
        if (jsonProd.has("previewImgs")) {
            ((ObjectNode) jsonProd).put("previewImgs",jsonProd.findValue("previewImgs").toString());
        }
        if (jsonProd.has("detailImgs")) {
            ((ObjectNode) jsonProd).put("detailImgs",jsonProd.findValue("detailImgs").toString());
        }
        if (jsonProd.has("features")) {
            ((ObjectNode) jsonProd).put("features",jsonProd.findValue("features").toString());
        }
        Products products = Json.fromJson(jsonProd, Products.class);
        Logger.error(products.toString());
        products.setMerchId(1001L);
        products.setProductState("Y");  //商品状态 'Y' 正常
        this.productsMapper.insertProducts(products);

        //往库存表插入数据
        for (final JsonNode jsonNode : jsonStocks) {
            Long id = products.getId();
            if (jsonNode.has("previewImgs")) {
                ((ObjectNode) jsonNode).put("previewImgs",jsonNode.findValue("previewImgs").toString());
            }
            Stock stock = Json.fromJson(jsonNode, Stock.class);
            stock.setProductId(products.getId());
            Logger.error(stock.toString());
            this.stockMapper.insertStock(stock);
            Logger.error(id.toString());
            list.add(products.getId());
        }
        return list;
    }

    /**
     * get stocks by prod id
     * @param prodId
     * @return List of entity.Stock
     */
    @Override
    public List<Stock> getStocksByProdId(Long prodId) {
        return this.stockMapper.getStocksByProdId(prodId);
    }

}

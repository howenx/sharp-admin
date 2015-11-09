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
    public Products getProducts(Long productId) {

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
     * @param json
     * @return boolean
     */

    @Override
    public List<Long> insertProducts(JsonNode json) {

        List<Long> list = new ArrayList<>();
        for (final JsonNode jsonNode : json) {
//            String sellOnDate=jsonNode.findValue("sellOnDate").toString();
//            String sellOffDate=jsonNode.findValue("sellOffDate").toString();
//            if (jsonNode.has("sellOnDate")) {
//                ((ObjectNode) jsonNode).putNull("sellOnDate");
//            }
//            if (jsonNode.has("sellOffDate")) {
//                ((ObjectNode) jsonNode).putNull("sellOffDate");
//            }
            if (jsonNode.has("previewImgs")) {
                ((ObjectNode) jsonNode).put("previewImgs",jsonNode.findValue("previewImgs").toString());
            }
            if (jsonNode.has("detailImgs")) {
                ((ObjectNode) jsonNode).put("detailImgs",jsonNode.findValue("detailImgs").toString());
            }
            if (jsonNode.has("features")) {
                ((ObjectNode) jsonNode).put("features",jsonNode.findValue("features").toString());
            }
            Products products = Json.fromJson(jsonNode, Products.class);

            Logger.debug(products.toString());

            products.setMerchId(1001);
            products.setProductState("Y");  //商品状态 'Y' 正常
            this.productsMapper.insertProducts(products);
            Long id = products.getId();

            Stock stock = new Stock();
            stock.setProductId(products.getId());
            stock.setProductColor(products.getProductColor());
            stock.setProductSize(products.getProductSize());
            stock.setProductAmount(products.getProductAmount());
            stock.setProductPrice(products.getProductPrice());
            stock.setRecommendPrice(products.getRecommendPrice());
            stock.setPreviewImgs(products.getPreviewImgs());
            Logger.error(stock.toString());
            //往库存表插入数据
            this.stockMapper.insertStock(stock);

            Logger.error(id.toString());
            list.add(products.getId());
        }
        return list;
    }

}

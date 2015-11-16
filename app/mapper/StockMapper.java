package mapper;

import entity.Stock;

import java.util.List;

/**
 * StockMapper.xml for StockMapper interface.
 *
 * Created by Sunny Wu.
 */
public interface StockMapper {

    /**
     * get stock by id.
     * @param id
     * @return Stock
     */
    Stock getStock(Long id);

    /**
     * get all stock.
     * @return List of Stock entity
     */
    List<Stock> getAllStock();

    /**
     *
     * @param prodId 商品id
     * @return List of Stock entity
     */
    List<Stock> getStocksByProdId(Long prodId);

    /**
     * insert stock
     * @param stock
     * @return Integer
     */
    Integer insertStock(Stock stock);

}

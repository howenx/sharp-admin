package service;

import entity.sale.SaleOrder;
import entity.sale.SaleProduct;

import java.util.List;

/**
 * Created by sibyl.sun on 16/3/3.
 */
public interface SaleService {
    /**
     * 插入销售产品
     * @param saleProduct
     * @return
     */
    Boolean insertSaleProduct(SaleProduct saleProduct);

    /**
     * 更新销售产品
     * @param saleProduct
     * @return
     */
    Boolean updateSaleProduct(SaleProduct saleProduct);

    /***
     * 查询销售产品
     * @param saleProduct
     * @return
     */
    List<SaleProduct> getSalaProduct(SaleProduct saleProduct);

    /**
     * 插入销售订单
     * @param saleOrder
     * @return
     */
    Boolean insertSaleOrder(SaleOrder saleOrder);

    /**
     * 更新销售订单
     * @param saleOrder
     * @return
     */
    Boolean updateSaleOrder(SaleOrder saleOrder);

    /**
     * 查询销售订单
     * @param saleOrder
     * @return
     */
    List<SaleOrder> getSalaOrder(SaleOrder saleOrder);

}

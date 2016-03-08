package service;

import entity.sale.SaleInventory;
import entity.sale.SaleOrder;
import entity.sale.SaleProduct;
import entity.sale.SaleStatistics;

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

    /**
     * 根据id获取销售产品
     * @param id
     * @return
     */
    SaleProduct getSaleProductById(Long id);

    /***
     * 查询销售产品
     * @param saleProduct
     * @return
     */
    List<SaleProduct> getSalaProduct(SaleProduct saleProduct);
    /**
     * 分页查询销售产品
     * @param saleProduct
     * @return
     */
    List<SaleProduct> getSaleProductPage(SaleProduct saleProduct);

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

    /***
     * 获取销售汇总
     * @param saleOrder
     * @return
     */
    List<SaleStatistics> getSaleStatistics(SaleOrder saleOrder);

    /**
     * 月份日销量
     * @param saleOrder
     * @return
     */
    List<SaleInventory>  getSaleInventory(SaleOrder saleOrder);

}

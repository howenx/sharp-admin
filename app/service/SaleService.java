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
    List<SaleProduct> getSaleProduct(SaleProduct saleProduct);
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
    List<SaleOrder> getSaleOrder(SaleOrder saleOrder);

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

    /**
     * 获取商品销售的总数量
     * @param id 商品id
     * @return
     */
    Integer getProductSaleCountTotal(Long id);
    /**
     * 分页查询销售订单
     * @param saleOrder
     * @return
     */
    List<SaleOrder> getSaleOrderPage(SaleOrder saleOrder);

    /**
     * 根据id获取销售订单
     * @param id
     * @return
     */
    SaleOrder getSaleOrderById(Long id);

    /**
     * 删除销售订单
     * @param id
     * @return
     */
    Boolean delSaleOrderById(Long id);

}

package service;

import entity.sale.SaleInventory;
import entity.sale.SaleOrder;
import entity.sale.SaleProduct;
import entity.sale.SaleStatistics;
import mapper.SaleMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * 销售
 * Created by sibyl.sun on 16/3/3.
 */
public class SaleServiceImpl implements SaleService{
    @Inject
    SaleMapper saleMapper;
    @Override
    public Boolean insertSaleProduct(SaleProduct saleProduct) {
        return saleMapper.insertSaleProduct(saleProduct)>0;
    }

    @Override
    public Boolean updateSaleProduct(SaleProduct saleProduct) {
        return saleMapper.updateSaleProduct(saleProduct)>0;
    }

    @Override
    public SaleProduct getSaleProductById(Long id) {
        return saleMapper.getSaleProductById(id);
    }

    @Override
    public List<SaleProduct> getSaleProduct(SaleProduct saleProduct) {
        return saleMapper.getSaleProduct(saleProduct);
    }

    @Override
    public List<SaleProduct> getSaleProductPage(SaleProduct saleProduct) {
        return saleMapper.getSaleProductPage(saleProduct);
    }

    @Override
    public Boolean insertSaleOrder(SaleOrder saleOrder) {
        return saleMapper.insertSaleOrder(saleOrder)>0;
    }

    @Override
    public Boolean updateSaleOrder(SaleOrder saleOrder) {
        return saleMapper.updateSaleOrder(saleOrder)>0;
    }

    @Override
    public List<SaleOrder> getSaleOrder(SaleOrder saleOrder) {
        return saleMapper.getSaleOrder(saleOrder);
    }

    @Override
    public List<SaleStatistics> getSaleStatistics(SaleOrder saleOrder) {
        return saleMapper.getSaleStatistics(saleOrder);
    }

    @Override
    public List<SaleInventory> getSaleInventory(SaleOrder saleOrder) {
        return saleMapper.getSaleInventory(saleOrder);
    }

    @Override
    public Integer getProductSaleCountTotal(Long id) {
        return saleMapper.getProductSaleCountTotal(id);
    }

    @Override
    public List<SaleOrder> getSaleOrderPage(SaleOrder saleOrder) {
        return saleMapper.getSaleOrderPage(saleOrder);
    }

    @Override
    public SaleOrder getSaleOrderById(Long id) {
        return saleMapper.getSaleOrderById(id);
    }

    @Override
    public Boolean delSaleOrderById(Long id) {
        return saleMapper.delSaleOrderById(id)>0;
    }
}

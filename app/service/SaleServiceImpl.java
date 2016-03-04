package service;

import entity.sale.SaleOrder;
import entity.sale.SaleProduct;
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
    public List<SaleProduct> getSalaProduct(SaleProduct saleProduct) {
        return saleMapper.getSalaProduct(saleProduct);
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
    public List<SaleOrder> getSalaOrder(SaleOrder saleOrder) {
        return saleMapper.getSalaOrder(saleOrder);
    }
}

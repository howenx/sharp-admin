package service;

import domain.SupplyOrder;
import mapper.SupplyOrderMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 16/4/19.
 */
public class SupplyOrderServiceImpl implements SupplyOrderService {
    @Inject
    private SupplyOrderMapper supplyOrderMapper;

    /**
     * 获取全部的供应商订单       Added by Tiffany Zhu 2016.04.20
     * @return
     */
    @Override
    public List<SupplyOrder> getSupplyOrderAll() {
        return supplyOrderMapper.getSupplyOrderAll();
    }
}

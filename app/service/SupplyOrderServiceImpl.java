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

    /**
     * 通过orderId 获取SupplyOrder      Added by Tiffany Zhu 2016.04.20
     * @param id
     * @return
     */
    @Override
    public SupplyOrder getSupplyByOrderId(Long id) {
        return supplyOrderMapper.getSupplyByOrderId(id);
    }

    /**
     * 获取SupplyOrder        Added by Tiffany Zhu 2016.04.20
     * @param supplyOrder
     * @return
     */
    @Override
    public List<SupplyOrder> getSupplyOrder(SupplyOrder supplyOrder) {
        return supplyOrderMapper.getSupplyOrder(supplyOrder);
    }

    /**
     * 向supplyOrder表中添加数据       Added by Tiffany Zhu 2016.04.21
     * @param supplyOrderList
     * @return
     */
    @Override
    public boolean addSupplyOrder(List<SupplyOrder> supplyOrderList) {
        return (supplyOrderMapper.addSupplyOrder(supplyOrderList)>0);
    }

    /**
     * 更新supplyOrder状态      Added by Tiffany Zhu 2016.04.21
     * @param supplyOrderList
     * @return
     */
    @Override
    public boolean updSupplyOrderStatus(List<SupplyOrder> supplyOrderList) {
        return (supplyOrderMapper.updSupplyOrderStatus(supplyOrderList)>0);
    }
}

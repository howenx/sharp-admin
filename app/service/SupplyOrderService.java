package service;

import domain.SupplyOrder;

import java.util.List;

/**
 * Created by tiffany on 16/4/19.
 */
public interface SupplyOrderService {
    /**
     * 获取全部的供应商订单       Added by Tiffany Zhu 2016.04.20
     * @return
     */
    List<SupplyOrder> getSupplyOrderAll();

    /**
     * 通过orderId 获取SupplyOrder      Added by Tiffany Zhu 2016.04.20
     * @param id
     * @return
     */
    SupplyOrder getSupplyByOrderId(Long id);

    /**
     * 获取SupplyOrder        Added by Tiffany Zhu 2016.04.20
     * @param supplyOrder
     * @return
     */
    List<SupplyOrder> getSupplyOrder(SupplyOrder supplyOrder);

    /**
     * 向supplyOrder表中添加数据       Added by Tiffany Zhu 2016.04.21
     * @param supplyOrderList
     * @return
     */
    boolean addSupplyOrder(List<SupplyOrder> supplyOrderList);

    /**
     * 更新supplyOrder状态      Added by Tiffany Zhu 2016.04.21
     * @param supplyOrderList
     * @return
     */
    boolean updSupplyOrderStatus(List<SupplyOrder> supplyOrderList);

}

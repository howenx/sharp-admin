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
}

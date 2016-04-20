package mapper;

import domain.SupplyOrder;

import java.util.List;

/**
 * Created by tiffany on 16/4/19.
 */
public interface SupplyOrderMapper {
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


}

package service;

import domain.order.OrderSplit;

import java.util.List;


/**
 * Created by tiffany on 15/12/17.
 */
public interface OrderSplitService {
    /**
     * 由订单Id获取订单报关情况
     * @param orderId
     * @return
     */
    List<OrderSplit> getSplitByOrderId(Long orderId);

    /**
     * 由子订单id获取子订单信息        Added By Sunny.Wu   2016.03.09
     * @param splitId 子订单号
     * @return OrderSplit
     */
    OrderSplit getSplitById(Long splitId);

    /**
     * 修改子订单信息(状态,物流信息)  Add By Sunny.Wu 2016.04.14
     * @param orderSplit 子订单
     */
    void updateSplitOrder(OrderSplit orderSplit);

    /**
     * 通过子订单ID 更新子订单信息 Added by Tiffany Zhu 2016.09.09
     * @param orderSplit
     */
    void updateSplitById(OrderSplit orderSplit);

}

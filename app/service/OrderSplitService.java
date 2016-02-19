package service;

import entity.order.OrderSplit;

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
}

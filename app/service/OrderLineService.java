package service;

import entity.OrderLine;

import java.util.List;

/**
 * Created by tiffany on 15/12/17.
 */
public interface OrderLineService {
    /**
     * 由订单Id获取订单商品
     * @param orderId
     * @return
     */
    List<OrderLine> getLineByOrderId(Long orderId);
}

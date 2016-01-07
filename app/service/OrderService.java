package service;

import entity.Order;
import java.util.List;

/**
 * Created by tiffany on 15/12/10.
 */
public interface OrderService {
    /**
     * 订单列表     Added by Tiffany Zhu
     * @return
     */
    List<Order> getOrdersAll();
    /**
     * 订单ajax查询     Added by Tiffany Zhu
     * @param order
     * @return
     */
    List<Order> getOrderPage(Order order);
    /**
     * 由订单ID订单详情   Added by Tiffany Zhu
     * @param orderId
     * @return
     */
    Order getOrderById(Long orderId);
    /**
     * 取消支付超时的订单 Added  by Tiffany Zhu
     * @param orderId
     */
    void orderCancel(Long orderId);

    /**
     * 超过24小时未支付的订单     Added by Tiffany Zhu
     * @return
     */
    List<Order> getOutTimeOrders();
}

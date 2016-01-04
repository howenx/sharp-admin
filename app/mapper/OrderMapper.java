package mapper;

import entity.Order;
import java.util.List;

/**
 * 订单
 * Created by tiffany on 15/12/10.
 */
public interface OrderMapper {
    /**
     * 订单列表     Added by Tiffany Zhu
     * @return
     */
    List<Order> getOrdersAll();

    /**
     * ajax分页查询     Added by Tiffany Zhu
     * @param order
     * @return
     */
    List<Order> getOrderPage(Order order);

    /**
     * 由订单ID获取订单详情      Added by Tiffany Zhu
     * @param orderId
     * @return
     */
    Order getOrderById(Long orderId);

    /**
     * 取消支付超时的订单 Added  by Tiffany Zhu
     * @param orderId
     */
    void orderCancel(Long orderId);

}

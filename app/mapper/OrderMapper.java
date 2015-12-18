package mapper;

import entity.Order;
import java.util.List;

/**
 * 订单
 * Created by tiffany on 15/12/10.
 */
public interface OrderMapper {

    List<Order> getOrdersAll();
    List<Order> getOrderPage(Order order);
    Order getOrderById(Long orderId);

}

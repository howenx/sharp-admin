package mapper;

import entity.Order;

import java.util.List;

/**
 * Created by tiffany on 15/12/10.
 */
public interface OrderMapper {

    List<Order> getOrdersAll();
    List<Order> getOrderPage(Order order);

}

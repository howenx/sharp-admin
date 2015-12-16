package service;

import entity.Order;

import java.util.List;

/**
 * Created by tiffany on 15/12/10.
 */
public interface OrderService {
    List<Order> getOrdersAll();
    List<Order> getOrderPage(Order order);
}

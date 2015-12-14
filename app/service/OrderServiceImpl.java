package service;

import entity.Order;
import mapper.OrderMapper;

import java.util.List;

/**
 * Created by tiffany on 15/12/10.
 */
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;
    @Override
    public List<Order> getOrdersAll() {
        return orderMapper.getOrdersAll();
    }
}

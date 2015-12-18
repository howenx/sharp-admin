package service;

import com.google.inject.Inject;
import entity.Order;
import mapper.OrderMapper;
import java.util.List;

/**
 * Created by tiffany on 15/12/10.
 */
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderMapper orderMapper;
    @Override
    public List<Order> getOrdersAll(){return orderMapper.getOrdersAll();}

    @Override
    public List<Order> getOrderPage(Order order) {
        return orderMapper.getOrderPage(order);
    }

    @Override
    public Order getOrderById(Long orderId) {return orderMapper.getOrderById(orderId);}
}

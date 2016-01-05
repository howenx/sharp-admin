package service;

import entity.Order;
import mapper.OrderMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 15/12/10.
 */
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderMapper orderMapper;

    /**
     * 订单列表     Added by Tiffany Zhu
     * @return
     */
    @Override
    public List<Order> getOrdersAll(){return orderMapper.getOrdersAll();}

    /**
     * 订单ajax查询     Added by Tiffany Zhu
     * @param order
     * @return
     */
    @Override
    public List<Order> getOrderPage(Order order) {
        return orderMapper.getOrderPage(order);
    }

    /**
     * 由订单ID订单详情   Added by Tiffany Zhu
     * @param orderId
     * @return
     */
    @Override
    public Order getOrderById(Long orderId) {return orderMapper.getOrderById(orderId);}

    /**
     * 取消支付超时的订单 Added  by Tiffany Zhu
     * @param orderId
     */
    @Override
    public void orderCancel(Long orderId) {
        orderMapper.orderCancel(orderId);
    }
}

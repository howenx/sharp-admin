package service;

import entity.order.OrderSplit;
import mapper.OrderSplitMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 15/12/17.
 */
public class OrderSplitServiceImpl implements OrderSplitService {
    @Inject
    OrderSplitMapper orderSplitMapper;

    /**
     * 由订单Id获取订单报关情况
     * @param orderId
     * @return
     */
    @Override
    public List<OrderSplit> getSplitByOrderId(Long orderId) {
        return orderSplitMapper.getSplitByOrderId(orderId);
    }

    /**
     * 由子订单id获取子订单信息        Added By Sunny.Wu   2016.03.09
     * @param splitId 子订单号
     * @return OrderSplit
     */
    @Override
    public OrderSplit getSplitById(Long splitId) {
        return orderSplitMapper.getSplitById(splitId);
    }
}

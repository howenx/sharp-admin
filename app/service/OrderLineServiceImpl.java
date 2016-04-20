package service;

import domain.Item;
import domain.order.OrderLine;
import mapper.OrderLineMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 15/12/17.
 */
public class OrderLineServiceImpl implements OrderLineService {
    @Inject
    private OrderLineMapper orderLineMapper;

    /**
     * 由订单Id获取订单商品
     * @param splitId
     * @return
     */
    @Override
    public List<OrderLine> getLineBySplitId(Long splitId) {
        return orderLineMapper.getLineBySplitId(splitId);
    }

    /**
     * 由订单id获取订单商品          Added By Sunny.Wu  2016.03.07
     * @param orderId 订单id
     * @return List of OrderLine
     */
    @Override
    public List<OrderLine> getLineByOrderId(Long orderId) {
        return orderLineMapper.getLineByOrderId(orderId);
    }

    /**
     * 通过item Id获取订单商品      Added by Tiffany Zhu 2016.04.20
     * @param itemList
     * @return
     */
    @Override
    public List<OrderLine> getLineByItems(List<Item> itemList) {
        return orderLineMapper.getLineByItems(itemList);
    }
}

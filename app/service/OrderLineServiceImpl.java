package service;

import entity.OrderLine;
import mapper.OrderLineMapper;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by tiffany on 15/12/17.
 */
public class OrderLineServiceImpl implements OrderLineService {
    @Inject
    private OrderLineMapper orderLineMapper;

    /**
     * 由订单Id获取订单商品
     * @param orderId
     * @return
     */
    @Override
    public List<OrderLine> getLineByOrderId(Long orderId) {
        return orderLineMapper.getLineByOrderId(orderId);
    }
}

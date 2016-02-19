package service;

import entity.order.OrderLine;
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
     * @param splitId
     * @return
     */
    @Override
    public List<OrderLine> getLineBySplitId(Long splitId) {
        return orderLineMapper.getLineBySplitId(splitId);
    }
}

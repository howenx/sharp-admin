package service;

import entity.order.OrderLine;

import java.util.List;

/**
 * Created by tiffany on 15/12/17.
 */
public interface OrderLineService {
    /**
     * 由订单Id获取订单商品
     * @param splitId
     * @return
     */
    List<OrderLine> getLineBySplitId(Long splitId);
}

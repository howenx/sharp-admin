package mapper;

import entity.OrderSplit;

import java.util.List;

/**
 * Created by tiffany on 15/12/17.
 */
public interface OrderSplitMapper {
    /**
     * 由订单Id获取报关信息
     * @param orderId
     * @return
     */
    List<OrderSplit> getSplitByOrderId(Long orderId);
}

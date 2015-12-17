package mapper;

import entity.OrderLine;

import java.util.List;

/**
 * Created by tiffany on 15/12/17.
 */
public interface OrderLineMapper {
    /**
     * 由订单Id获取订单商品
     * @param splitId
     * @return
     */
    List<OrderLine> getLineBySplitId(Long splitId);
}

package mapper;

import entity.order.Refund;

import java.util.List;

/**
 * Created by tiffany on 16/4/13.
 */
public interface RefundMapper {

    /**
     * 全部退款申请   Added by Tiffany Zhu 2016.04.14
     * @return
     */
    List<Refund> getRefundOrders();

    /**
     * 分页查询     Added by Tiffany Zhu 2016.04.14
     * @param refund
     * @return
     */
    List<Refund> getRefundOrderPage(Refund refund);
}

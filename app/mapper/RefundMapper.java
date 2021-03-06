package mapper;

import domain.Refund;
import domain.RefundTemp;

import java.util.List;

/**
 * Created by tiffany on 16/4/13.
 */
public interface RefundMapper {

    /**
     * 全部退款申请   Added by Tiffany Zhu 2016.04.14
     * @return
     */
    List<RefundTemp> getRefundOrders();

    /**
     * 分页查询退款申请     Added by Tiffany Zhu 2016.04.14
     * @param refundTemp
     * @return
     */
    List<RefundTemp> getRefundOrderPage(RefundTemp refundTemp);

    /**
     * 通过Id获取退款申请详情     Added by Tiffany Zhu 2016.04.14
     * @param id
     * @return
     */
    RefundTemp getRefundById(Long id);


    /**
     *  通过Id获取退款申请详情  用Refund对象   Added by Tiffany Zhu 2016.04.16
     * @param id
     * @return
     */
    Refund getRefundServiceById(Long id);

    /**
     * 更新退款申请       Added by Tiffany Zhu 2016.04.16
     */
    void updRefund(RefundTemp refundTemp);

    /**
     * 由订单号查询退款单记录      Add By Sunny.Wu 2016.06.21
     * @param orderId 订单号
     * @return Refund
     */
    Refund getRefundByOrderId(Long orderId);
}

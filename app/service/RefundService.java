package service;

import entity.Refund;

import java.util.List;

/**
 * Created by tiffany on 16/4/13.
 */
public interface RefundService {
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

    /**
     * 通过Id获取退款申请详情     Added by Tiffany Zhu 2016.04.14
     * @param id
     * @return
     */
    Refund getRefundById(Long id);

    /**
     * 更新退款申请       Added by Tiffany Zhu 2016.04.16
     */
    void updRefund(Refund refund);



}

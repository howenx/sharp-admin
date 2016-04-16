package service;

import entity.RefundTemp;

import java.util.List;

/**
 * Created by tiffany on 16/4/13.
 */
public interface RefundService {
    /**
     * 全部退款申请   Added by Tiffany Zhu 2016.04.14
     * @return
     */
    List<RefundTemp> getRefundOrders();

    /**
     * 分页查询     Added by Tiffany Zhu 2016.04.14
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
     * 更新退款申请       Added by Tiffany Zhu 2016.04.16
     */
    void updRefund(RefundTemp refundTemp);



}

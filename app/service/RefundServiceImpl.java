package service;

import domain.Refund;
import domain.RefundTemp;
import mapper.RefundMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 16/4/13.
 */
public class RefundServiceImpl implements RefundService{

    @Inject
    RefundMapper refundMapper;


    /**
     * 全部退款申请   Added by Tiffany Zhu 2016.04.14
     * @return
     */
    @Override
    public List<RefundTemp> getRefundOrders() {
        return refundMapper.getRefundOrders();
    }

    /**
     * 分页查询     Added by Tiffany Zhu 2016.04.14
     * @param refundTemp
     * @return
     */
    @Override
    public List<RefundTemp> getRefundOrderPage(RefundTemp refundTemp) {
        return refundMapper.getRefundOrderPage(refundTemp);
    }

    /**
     * 通过Id获取退款申请详情     Added by Tiffany Zhu 2016.04.14
     * @param id
     * @return
     */
    @Override
    public RefundTemp getRefundById(Long id) {
        return refundMapper.getRefundById(id);
    }


    /**
     * 更新退款申请       Added by Tiffany Zhu 2016.04.16
     */
    @Override
    public void updRefund(RefundTemp refundTemp) {
        refundMapper.updRefund(refundTemp);
    }

    /**
     *  通过Id获取退款申请详情  用Refund对象   Added by Tiffany Zhu 2016.04.16
     * @param id
     * @return
     */
    @Override
    public Refund getRefundServiceById(Long id) {
        return refundMapper.getRefundServiceById(id);
    }

    /**
     * 由订单号查询退款单记录      Add By Sunny.Wu 2016.06.21
     * @param orderId 订单号
     * @return Refund
     */
    @Override
    public Refund getRefundByOrderId(Long orderId) {
        return refundMapper.getRefundByOrderId(orderId);
    }
}

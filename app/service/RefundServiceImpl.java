package service;

import entity.Refund;
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
    public List<Refund> getRefundOrders() {
        return refundMapper.getRefundOrders();
    }

    /**
     * 分页查询     Added by Tiffany Zhu 2016.04.14
     * @param refund
     * @return
     */
    @Override
    public List<Refund> getRefundOrderPage(Refund refund) {
        return refundMapper.getRefundOrderPage(refund);
    }

    /**
     * 通过Id获取退款申请详情     Added by Tiffany Zhu 2016.04.14
     * @param id
     * @return
     */
    @Override
    public Refund getRefundById(Long id) {
        return refundMapper.getRefundById(id);
    }


    /**
     * 更新退款申请       Added by Tiffany Zhu 2016.04.16
     */
    @Override
    public void updRefund(Refund refund) {
        refundMapper.updRefund(refund);
    }
}

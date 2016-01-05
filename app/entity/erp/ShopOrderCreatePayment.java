package entity.erp;

import java.io.Serializable;

/**
 * Created by Sunny Wu 15/12/26.
 *
 * 订单付款信息
 */
public class ShopOrderCreatePayment implements Serializable {

    private Integer paymentId;      //付款方式 (1.现金支付2.银行转账3.网上支付4.支付宝5.天猫积分6.货到付款7.账户支付)
    private Double paymentTotal;    //付款金额
    private String paymentNo;       //付款单号

    public ShopOrderCreatePayment() {
    }

    @Override
    public String toString() {
        return "ShopOrderCreatePayment{" +
                "paymentId=" + paymentId +
                ", paymentTotal=" + paymentTotal +
                ", paymentNo='" + paymentNo + '\'' +
                '}';
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Double getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(Double paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public ShopOrderCreatePayment(Integer paymentId, Double paymentTotal, String paymentNo) {

        this.paymentId = paymentId;
        this.paymentTotal = paymentTotal;
        this.paymentNo = paymentNo;
    }
}

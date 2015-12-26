package entity.erp;

/**
 * Created by Sunny Wu 15/12/26.
 *
 * 订单付款信息
 */
public class ShopOrderCreatePayment {

    private Integer PaymentId;      //付款方式 (1.现金支付2.银行转账3.网上支付4.支付宝5.天猫积分6.货到付款7.账户支付)
    private Double PaymentTotal;    //付款金额
    private String PaymentNo;       //付款单号

    public ShopOrderCreatePayment() {
    }

    public Integer getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(Integer paymentId) {
        PaymentId = paymentId;
    }

    public Double getPaymentTotal() {
        return PaymentTotal;
    }

    public void setPaymentTotal(Double paymentTotal) {
        PaymentTotal = paymentTotal;
    }

    public String getPaymentNo() {
        return PaymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        PaymentNo = paymentNo;
    }

    @Override
    public String toString() {
        return "ShopOrderCreatePayment{" +
                "PaymentId=" + PaymentId +
                ", PaymentTotal=" + PaymentTotal +
                ", PaymentNo='" + PaymentNo + '\'' +
                '}';
    }

    public ShopOrderCreatePayment(Integer paymentId, Double paymentTotal, String paymentNo) {
        PaymentId = paymentId;
        PaymentTotal = paymentTotal;
        PaymentNo = paymentNo;
    }
}

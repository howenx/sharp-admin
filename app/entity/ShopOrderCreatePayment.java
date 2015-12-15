package entity;

/**
 * Created by Sunny Wu.
 */
public class ShopOrderCreatePayment {

    private Integer paymentId;

    private Double paymentTotal;

    private String paymentNo;

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

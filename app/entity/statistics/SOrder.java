package entity.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单统计
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class SOrder implements Serializable {

    private String sDate;           //日期
    private String pgTradeNo;       //pg订单号
    private String orderStatus;     //订单状态(I:初始化即未支付状态，S:成功，C：取消， F:失败，R:已收货，D:已经发货，J:拒收，N已删除，T已退款，PI:拼购未支付状态，PS:拼购支付成功状态)
    private Long orderId;           //订单ID
    private Long userId;            //用户ID
    private BigDecimal payTotal;    //订单支付总费用
    private String payMethod;       //支付渠道
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp orderCreateAt;//订单创建时间
    private String orderIp;         //订单IP
    private BigDecimal totalFee;    //购买商品的费用
    private Integer clientType;     //支付订单客户端类型

    public SOrder() {
    }

    @Override
    public String toString() {
        return "SOrder{" +
                "sDate='" + sDate + '\'' +
                ", pgTradeNo='" + pgTradeNo + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", payTotal=" + payTotal +
                ", payMethod='" + payMethod + '\'' +
                ", orderCreateAt=" + orderCreateAt +
                ", orderIp='" + orderIp + '\'' +
                ", totalFee=" + totalFee +
                ", clientType=" + clientType +
                '}';
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getPgTradeNo() {
        return pgTradeNo;
    }

    public void setPgTradeNo(String pgTradeNo) {
        this.pgTradeNo = pgTradeNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(BigDecimal payTotal) {
        this.payTotal = payTotal;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Timestamp getOrderCreateAt() {
        return orderCreateAt;
    }

    public void setOrderCreateAt(Timestamp orderCreateAt) {
        this.orderCreateAt = orderCreateAt;
    }

    public String getOrderIp() {
        return orderIp;
    }

    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public SOrder(String sDate, String pgTradeNo, String orderStatus, Long orderId, Long userId, BigDecimal payTotal, String payMethod, Timestamp orderCreateAt, String orderIp, BigDecimal totalFee, Integer clientType) {

        this.sDate = sDate;
        this.pgTradeNo = pgTradeNo;
        this.orderStatus = orderStatus;
        this.orderId = orderId;
        this.userId = userId;
        this.payTotal = payTotal;
        this.payMethod = payMethod;
        this.orderCreateAt = orderCreateAt;
        this.orderIp = orderIp;
        this.totalFee = totalFee;
        this.clientType = clientType;
    }
}

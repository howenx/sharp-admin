package domain.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单
 * Created by tiffany on 15/12/10.
 */
public class Order implements Serializable {

    private Long orderId;               //订单ID
    private Long userId;                //用户ID
    private BigDecimal payTotal;        //总费用
    private String payMethod;           //支付渠道
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp orderCreateAt;    //创建时间
    private String orderIp;             //订单IP
    private String pgTradeNo;           //pg订单号
    private String orderStatus;         //订单状态
    private String errorStr;            //支付错误返回的错误消息
    private BigDecimal discount;        //优惠钱数
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updatedAt;        //最后修改时间
    private String orderDesc;           //订单备注
    private BigDecimal shipFee;         //邮费
    private BigDecimal postalFee;       //行邮税
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp confirmReveiveAt; //用户收货确认时间
    private BigDecimal totalFee;        //用户支付的总费用
    private Integer shipTime;           //配送时间
    private Integer clientType;         //支付订单客户端类型
    private Integer orderType;          //订单类型
    private Long pinActiveId;           //拼购活动ID

    //分页,每页多少条
    private Integer pageSize;
    //分页,从第几条开始
    private Integer offset;
    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;
    //查询开始时间
    @Ignore
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp startTime;
    //查询结束时间
    @Ignore
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp endTime;

    public Order() {
    }

    public Order(Long orderId, Long userId, BigDecimal payTotal, String payMethod, Timestamp orderCreateAt, String orderIp, String pgTradeNo, String orderStatus, String errorStr, BigDecimal discount, Timestamp updatedAt, String orderDesc, BigDecimal shipFee, BigDecimal postalFee, Timestamp confirmReveiveAt, BigDecimal totalFee, Integer shipTime, Integer clientType, Integer orderType, Long pinActiveId, Integer pageSize, Integer offset, String sort, String order, Timestamp startTime, Timestamp endTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.payTotal = payTotal;
        this.payMethod = payMethod;
        this.orderCreateAt = orderCreateAt;
        this.orderIp = orderIp;
        this.pgTradeNo = pgTradeNo;
        this.orderStatus = orderStatus;
        this.errorStr = errorStr;
        this.discount = discount;
        this.updatedAt = updatedAt;
        this.orderDesc = orderDesc;
        this.shipFee = shipFee;
        this.postalFee = postalFee;
        this.confirmReveiveAt = confirmReveiveAt;
        this.totalFee = totalFee;
        this.shipTime = shipTime;
        this.clientType = clientType;
        this.orderType = orderType;
        this.pinActiveId = pinActiveId;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", payTotal=" + payTotal +
                ", payMethod='" + payMethod + '\'' +
                ", orderCreateAt=" + orderCreateAt +
                ", orderIp='" + orderIp + '\'' +
                ", pgTradeNo='" + pgTradeNo + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", errorStr='" + errorStr + '\'' +
                ", discount=" + discount +
                ", updatedAt=" + updatedAt +
                ", orderDesc='" + orderDesc + '\'' +
                ", shipFee=" + shipFee +
                ", postalFee=" + postalFee +
                ", confirmReveiveAt=" + confirmReveiveAt +
                ", totalFee=" + totalFee +
                ", shipTime=" + shipTime +
                ", clientType=" + clientType +
                ", orderType=" + orderType +
                ", pinActiveId=" + pinActiveId +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
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

    public String getErrorStr() {
        return errorStr;
    }

    public void setErrorStr(String errorStr) {
        this.errorStr = errorStr;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }

    public BigDecimal getPostalFee() {
        return postalFee;
    }

    public void setPostalFee(BigDecimal postalFee) {
        this.postalFee = postalFee;
    }

    public Timestamp getConfirmReveiveAt() {
        return confirmReveiveAt;
    }

    public void setConfirmReveiveAt(Timestamp confirmReveiveAt) {
        this.confirmReveiveAt = confirmReveiveAt;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getShipTime() {
        return shipTime;
    }

    public void setShipTime(Integer shipTime) {
        this.shipTime = shipTime;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getPinActiveId() {
        return pinActiveId;
    }

    public void setPinActiveId(Long pinActiveId) {
        this.pinActiveId = pinActiveId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}

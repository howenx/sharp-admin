package domain.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 京东订单每行采集的数据
 * Created by sibyl.sun on 16/3/3.
 */
public class SaleJdOrderLineInfo implements Serializable {
    //订单号,商品ID,商品名称,订购数量,支付方式,下单时间,京东价,订单金额,结算金额,余额支付,应付金额,订单状态,订单类型,
    // 下单帐号,客户姓名,客户地址,联系电话,订单备注,发票类型,发票抬头,发票内容,商家备注,商家备注等级（等级1-5为由高到低）,运费金额,付款确认时间,增值税发票,货号,订单完成时间,订单来源,订单渠道,

    private String jdOrderId; //订单号
    private String jdSkuId;   //京东商品id
    private Integer saleCount;//订购数量
    private BigDecimal price;    //京东价
    private BigDecimal orderValue;//订单金额
    private BigDecimal settleValue;//结算金额
    private BigDecimal payValue;    //应付金额
    private String jdOrderStatus;//京东订单状态
    private String remark;//商家备注
    private Integer remarkStatus;//商家备注等级（等级1-5为由高到低
    private BigDecimal shipFee ;//运费金额
    private Date saleAt;    //付款确认时间



    private String invArea;//库存区域区分：'S' 上海保税仓备货，'G'广州保税仓备货，'H'杭州保税仓备货，'SZ'上海保税区直邮，'GZ'广州保税仓直邮，'HZ'杭州保税仓直邮，'K' 海外直邮，'SELF' 自营商品

    public String getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(String jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

    public String getJdSkuId() {
        return jdSkuId;
    }

    public void setJdSkuId(String jdSkuId) {
        this.jdSkuId = jdSkuId;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(BigDecimal orderValue) {
        this.orderValue = orderValue;
    }

    public BigDecimal getSettleValue() {
        return settleValue;
    }

    public void setSettleValue(BigDecimal settleValue) {
        this.settleValue = settleValue;
    }

    public BigDecimal getPayValue() {
        return payValue;
    }

    public void setPayValue(BigDecimal payValue) {
        this.payValue = payValue;
    }

    public String getJdOrderStatus() {
        return jdOrderStatus;
    }

    public void setJdOrderStatus(String jdOrderStatus) {
        this.jdOrderStatus = jdOrderStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRemarkStatus() {
        return remarkStatus;
    }

    public void setRemarkStatus(Integer remarkStatus) {
        this.remarkStatus = remarkStatus;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }

    public Date getSaleAt() {
        return saleAt;
    }

    public void setSaleAt(Date saleAt) {
        this.saleAt = saleAt;
    }

    public String getInvArea() {
        return invArea;
    }

    public void setInvArea(String invArea) {
        this.invArea = invArea;
    }
}

package entity.sale;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 销售明细
 * Created by sibyl.sun on 16/3/3.
 */
public class SaleOrder implements Serializable {
    private Long id;             //唯一id
    private Timestamp saleAt;    //日期
    private String orderId;      //订单号
    private Long saleProductId;  //销售商品id
    private String productName; //品名
    private Integer categoryId;    //商品分类
    private BigDecimal price;    //单价
    private Integer count;       //数量
    private BigDecimal discountAmount;    //优惠额
    private BigDecimal saleTotal;    //销售额
    private BigDecimal jdRate;    //京东费率
    private BigDecimal jdFee;    //京东费用
    private BigDecimal cost;    //成本
    private BigDecimal shipFee;    //国内快递费
    private BigDecimal inteLogistics;    //国际物流
    private BigDecimal packFee;    //包装
    private BigDecimal storageFee;//仓储服务费
    private BigDecimal postalFee;    //行邮税
    private String postalTaxRate;    //行邮税税率，单位百分比，例如填入3，表示3%
    private BigDecimal profit;    //净利
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;     //创建时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;     //更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getSaleAt() {
        return saleAt;
    }

    public void setSaleAt(Timestamp saleAt) {
        this.saleAt = saleAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getSaleProductId() {
        return saleProductId;
    }

    public void setSaleProductId(Long saleProductId) {
        this.saleProductId = saleProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(BigDecimal saleTotal) {
        this.saleTotal = saleTotal;
    }

    public BigDecimal getJdRate() {
        return jdRate;
    }

    public void setJdRate(BigDecimal jdRate) {
        this.jdRate = jdRate;
    }

    public BigDecimal getJdFee() {
        return jdFee;
    }

    public void setJdFee(BigDecimal jdFee) {
        this.jdFee = jdFee;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }

    public BigDecimal getInteLogistics() {
        return inteLogistics;
    }

    public void setInteLogistics(BigDecimal inteLogistics) {
        this.inteLogistics = inteLogistics;
    }

    public BigDecimal getPackFee() {
        return packFee;
    }

    public void setPackFee(BigDecimal packFee) {
        this.packFee = packFee;
    }

    public BigDecimal getPostalFee() {
        return postalFee;
    }

    public BigDecimal getStorageFee() {
        return storageFee;
    }

    public void setStorageFee(BigDecimal storageFee) {
        this.storageFee = storageFee;
    }

    public void setPostalFee(BigDecimal postalFee) {
        this.postalFee = postalFee;
    }

    public String getPostalTaxRate() {
        return postalTaxRate;
    }

    public void setPostalTaxRate(String postalTaxRate) {
        this.postalTaxRate = postalTaxRate;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }
}

package domain.sale;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by sibyl.sun on 16/6/2.
 */
public class SaleOrderLine implements Serializable {
    private Long id;             //唯一id
    private Long saleOrderId;      //订单唯一id
    private Long saleProductId;  //销售商品id
    private Integer saleCount;       //数量
    private String jdOrderId;   //京东订单id
    private String jdSkuId;     //京东商品Id
    private BigDecimal jdPrice;     //京东价

    private String saleProductName; //品名
    private BigDecimal saleProductCost;        //成本
    private Integer categoryId;    //商品分类
    private Date saleAt;    //日期
    private BigDecimal jdRate;    //京东费率
    private BigDecimal jdFee;    //京东费用
    private String orderStatus;   //S:成功 T:退货
    private BigDecimal discountAmount;    //优惠额
    private BigDecimal postalTaxRate;    //行邮税税率，单位百分比，例如填入3，表示3%
    private Integer seq; //导入订单时顺序，用于导入优惠时对应

    private BigDecimal lineSaleTotal; //子订单的销售额
    private BigDecimal linePostalFee; //子订单的行邮税


    @JsonIgnore
    private String starttime;
    @JsonIgnore
    private String endtime;
    //分页,从第几条开始
    @JsonIgnore
    private Integer offset;

    @JsonIgnore
    //分页,每页多少条
    private Integer pageSize;
    private Integer shop; //商店  1-韩密美专营店  2-韩密美化妆品店

    /**销售月份*/
    private String saleMonth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(Long saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public Long getSaleProductId() {
        return saleProductId;
    }

    public void setSaleProductId(Long saleProductId) {
        this.saleProductId = saleProductId;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

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

    public BigDecimal getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public String getSaleProductName() {
        return saleProductName;
    }

    public void setSaleProductName(String saleProductName) {
        this.saleProductName = saleProductName;
    }

    public BigDecimal getSaleProductCost() {
        return saleProductCost;
    }

    public void setSaleProductCost(BigDecimal saleProductCost) {
        this.saleProductCost = saleProductCost;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Date getSaleAt() {
        return saleAt;
    }

    public void setSaleAt(Date saleAt) {
        this.saleAt = saleAt;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }


    public BigDecimal getPostalTaxRate() {
        return postalTaxRate;
    }

    public void setPostalTaxRate(BigDecimal postalTaxRate) {
        this.postalTaxRate = postalTaxRate;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getSaleMonth() {
        return saleMonth;
    }

    public void setSaleMonth(String saleMonth) {
        this.saleMonth = saleMonth;
    }

    public Integer getShop() {
        return shop;
    }

    public void setShop(Integer shop) {
        this.shop = shop;
    }

    public BigDecimal getLineSaleTotal() {
        return lineSaleTotal;
    }

    public void setLineSaleTotal(BigDecimal lineSaleTotal) {
        this.lineSaleTotal = lineSaleTotal;
    }

    public BigDecimal getLinePostalFee() {
        return linePostalFee;
    }

    public void setLinePostalFee(BigDecimal linePostalFee) {
        this.linePostalFee = linePostalFee;
    }

    @Override
    public String toString() {
        return "SaleOrderLine{" +
                "id=" + id +
                ", saleOrderId=" + saleOrderId +
                ", saleProductId=" + saleProductId +
                ", saleCount=" + saleCount +
                ", jdOrderId='" + jdOrderId + '\'' +
                ", jdSkuId='" + jdSkuId + '\'' +
                ", jdPrice=" + jdPrice +
                ", saleProductName='" + saleProductName + '\'' +
                ", saleProductCost=" + saleProductCost +
                ", categoryId=" + categoryId +
                ", saleAt=" + saleAt +
                ", jdRate=" + jdRate +
                ", jdFee=" + jdFee +
                ", orderStatus='" + orderStatus + '\'' +
                ", discountAmount=" + discountAmount +
                ", postalTaxRate=" + postalTaxRate +
                ", seq=" + seq +
                ", lineSaleTotal=" + lineSaleTotal +
                ", linePostalFee=" + linePostalFee +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", offset=" + offset +
                ", pageSize=" + pageSize +
                ", shop=" + shop +
                ", saleMonth='" + saleMonth + '\'' +
                '}';
    }
}

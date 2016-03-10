package entity.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 销售明细
 * Created by sibyl.sun on 16/3/3.
 */
public class SaleOrder implements Serializable {
    private Long id;             //唯一id
    private Date saleAt;    //日期
    private String orderId;      //订单号
    private Long saleProductId;  //销售商品id
    private String productName; //品名
    private Integer categoryId;    //商品分类
    private BigDecimal price;    //单价
    private Integer saleCount;       //数量
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
    private BigDecimal postalTaxRate;    //行邮税税率，单位百分比，例如填入3，表示3%
    private BigDecimal profit;    //净利
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;     //创建时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;     //更新时间
    private String invArea;//库存区域区分：'S' 上海保税仓备货，'G'广州保税仓备货，'H'杭州保税仓备货，'SZ'上海保税区直邮，'GZ'广州保税仓直邮，'HZ'杭州保税仓直邮，'K' 海外直邮，'SELF' 自营商品
    private Integer remarkStatus;//标记
    private String remark;//备注
    @JsonIgnore
    private Long createUserId; //创建订单的人
    @JsonIgnore
    private Long updateUserId; //创建订单的人

    @JsonIgnore
    private String starttime;
    @JsonIgnore
    private String endtime;
    /**销售月份*/
    private String saleMonth;

    //分页,从第几条开始
    @JsonIgnore
    private Integer offset;

    @JsonIgnore
    //分页,每页多少条
    private Integer pageSize;

    //按照哪个字段排序
    @JsonIgnore
    private String sort;
    //排序方式,降序,升序
    @JsonIgnore
    private String order;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSaleAt() {
        return saleAt;
    }

    public void setSaleAt(Date saleAt) {
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

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
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

    public BigDecimal getPostalTaxRate() {
        return postalTaxRate;
    }

    public void setPostalTaxRate(BigDecimal postalTaxRate) {
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

    public String getSaleMonth() {
        return saleMonth;
    }

    public void setSaleMonth(String saleMonth) {
        this.saleMonth = saleMonth;
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

    public String getInvArea() {
        return invArea;
    }

    public void setInvArea(String invArea) {
        this.invArea = invArea;
    }

    public Integer getRemarkStatus() {
        return remarkStatus;
    }

    public void setRemarkStatus(Integer remarkStatus) {
        this.remarkStatus = remarkStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }
}

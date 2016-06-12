package domain.sale;

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
 //   private Long saleProductId;  //销售商品id (删除)
//    private String productName; //品名
//    private Integer categoryId;    //商品分类
//    private BigDecimal price;    //单价
//    private Integer saleCount;       //数量
    private BigDecimal discountAmount;    //优惠额
    private BigDecimal saleTotal;    //销售额
//    private BigDecimal jdRate;    //京东费率
    private BigDecimal jdFeeSum;    //京东费用
    private BigDecimal productCostSum;    //成本总和
    private BigDecimal shipFee;    //国内快递费
    private BigDecimal inteLogistics;    //国际物流
    private BigDecimal packFee;    //包装
    private BigDecimal storageFee;//仓储服务费
    private BigDecimal postalFee;    //行邮税
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
    private Integer shop; //商店  1-韩密美专营店  2-韩密美化妆品店
    private Integer inputType;//1-手动录入  2-excel导入
//    private String jdSkuId;   //京东商品id,用于导入表格时关联数据，如果有多个以|分割
    private String orderStatus;   //S:成功 T:全部退货

    private Integer feeCategoryId;  //费用归属那个品类

    private BigDecimal orderValue;  //订单金额

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp orderFinishAt;     //订单完成时间


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
    @JsonIgnore
    private String jdOrderStatus;//京东订单状态


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

//    public Long getSaleProductId() {
//        return saleProductId;
//    }
//
//    public void setSaleProductId(Long saleProductId) {
//        this.saleProductId = saleProductId;
//    }



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

    public BigDecimal getJdFeeSum() {
        return jdFeeSum;
    }

    public void setJdFeeSum(BigDecimal jdFeeSum) {
        this.jdFeeSum = jdFeeSum;
    }

    public BigDecimal getProductCostSum() {
        return productCostSum;
    }

    public void setProductCostSum(BigDecimal productCostSum) {
        this.productCostSum = productCostSum;
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

    public Integer getShop() {
        return shop;
    }

    public void setShop(Integer shop) {
        this.shop = shop;
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public String getJdOrderStatus() {
        return jdOrderStatus;
    }

    public void setJdOrderStatus(String jdOrderStatus) {
        this.jdOrderStatus = jdOrderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getFeeCategoryId() {
        return feeCategoryId;
    }

    public void setFeeCategoryId(Integer feeCategoryId) {
        this.feeCategoryId = feeCategoryId;
    }

    public BigDecimal getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(BigDecimal orderValue) {
        this.orderValue = orderValue;
    }

    public Timestamp getOrderFinishAt() {
        return orderFinishAt;
    }

    public void setOrderFinishAt(Timestamp orderFinishAt) {
        this.orderFinishAt = orderFinishAt;
    }

    @Override
    public String toString() {
        return "SaleOrder{" +
                "id=" + id +
                ", saleAt=" + saleAt +
                ", orderId='" + orderId + '\'' +
                ", discountAmount=" + discountAmount +
                ", saleTotal=" + saleTotal +
                ", jdFeeSum=" + jdFeeSum +
                ", productCostSum=" + productCostSum +
                ", shipFee=" + shipFee +
                ", inteLogistics=" + inteLogistics +
                ", packFee=" + packFee +
                ", storageFee=" + storageFee +
                ", postalFee=" + postalFee +
                ", profit=" + profit +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", invArea='" + invArea + '\'' +
                ", remarkStatus=" + remarkStatus +
                ", remark='" + remark + '\'' +
                ", createUserId=" + createUserId +
                ", updateUserId=" + updateUserId +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", saleMonth='" + saleMonth + '\'' +
                ", shop=" + shop +
                ", inputType=" + inputType +
                ", orderStatus='" + orderStatus + '\'' +
                ", feeCategoryId=" + feeCategoryId +
                ", orderValue=" + orderValue +
                ", orderFinishAt=" + orderFinishAt +
                ", offset=" + offset +
                ", pageSize=" + pageSize +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", jdOrderStatus='" + jdOrderStatus + '\'' +
                '}';
    }
}

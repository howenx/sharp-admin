package domain.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 销售商品
 * Created by sibyl.sun on 16/3/3.
 */
public class SaleProduct implements Serializable {
    private Long id;         // 销售商品id
    private String name;     //商品名称
    private Integer categoryId;//商品分类
    private String skuCode;   //SKU编码
    private String productCode; //货品编码
    private String spec; //规格
    private Integer saleCount; //总销量
    private Integer inventory; //库存量
    private BigDecimal productCost; //商品成本
    private BigDecimal stockValue;  //库存商品价值
    private Integer purchaseCount; //进货量
    private Integer noCard; //无卡
    private Integer damage; //破损
    private Integer lessDelivery; //少配
    private Integer lessProduct; //少货
    private Integer emptyBox; //空盒
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;     //创建时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;     //更新时间
    private String invArea;//库存区域区分：'S' 上海保税仓备货，'G'广州保税仓备货，'H'杭州保税仓备货，'SZ'上海保税区直邮，'GZ'广州保税仓直邮，'HZ'杭州保税仓直邮，'K' 海外直邮，'SELF' 自营商品
    private Timestamp storageAt;//入库日期
    private Long customSkuId; //自定义商品id
    private Integer damageOther;//其他破损
    private String remark;//备注
    @JsonIgnore
    private Long createUserId; //创建订单的人
    @JsonIgnore
    private Long updateUserId; //创建订单的人

    private Integer backCount;//退单数

    private String jdSkuId;   //京东商品id,用于导入表格时关联数据，如果有多个以|分割

    private Integer saleFinishStatus; //1-未卖完   2-卖完了,导入订单时选择未卖完的商品

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
    private String starttime;
    @JsonIgnore
    private String endtime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }

    public BigDecimal getStockValue() {
        return stockValue;
    }

    public void setStockValue(BigDecimal stockValue) {
        this.stockValue = stockValue;
    }

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public Integer getNoCard() {
        return noCard;
    }

    public void setNoCard(Integer noCard) {
        this.noCard = noCard;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getLessDelivery() {
        return lessDelivery;
    }

    public void setLessDelivery(Integer lessDelivery) {
        this.lessDelivery = lessDelivery;
    }

    public Integer getLessProduct() {
        return lessProduct;
    }

    public void setLessProduct(Integer lessProduct) {
        this.lessProduct = lessProduct;
    }

    public Integer getEmptyBox() {
        return emptyBox;
    }

    public void setEmptyBox(Integer emptyBox) {
        this.emptyBox = emptyBox;
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

    public String getInvArea() {
        return invArea;
    }

    public void setInvArea(String invArea) {
        this.invArea = invArea;
    }

    public Timestamp getStorageAt() {
        return storageAt;
    }

    public void setStorageAt(Timestamp storageAt) {
        this.storageAt = storageAt;
    }

    public Long getCustomSkuId() {
        return customSkuId;
    }

    public void setCustomSkuId(Long customSkuId) {
        this.customSkuId = customSkuId;
    }

    public Integer getDamageOther() {
        return damageOther;
    }

    public void setDamageOther(Integer damageOther) {
        this.damageOther = damageOther;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getBackCount() {
        return backCount;
    }

    public void setBackCount(Integer backCount) {
        this.backCount = backCount;
    }


    public String getJdSkuId() {
        return jdSkuId;
    }

    public void setJdSkuId(String jdSkuId) {
        this.jdSkuId = jdSkuId;
    }

    public Integer getSaleFinishStatus() {
        return saleFinishStatus;
    }

    public void setSaleFinishStatus(Integer saleFinishStatus) {
        this.saleFinishStatus = saleFinishStatus;
    }
}

package entity.sale;

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
    @JsonIgnore
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;     //更新时间
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
}

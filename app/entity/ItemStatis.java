package entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Sunny Wu on 16/1/18.
 * kakao china.
 *
 * 商品统计
 */
public class ItemStatis implements Serializable{

    private Long id;             //主键
    private String createDate;   //创建日期字符串
    private Long skuId;          //sku id
    private BigDecimal costPrice;//sku成本价
    private BigDecimal srcPrice; //sku原件
    private BigDecimal salePrice;//sku销售价
    private Integer amount;      //sku库存总量
    private Integer soldAmount;  //sku售出数量
    private Integer restAmount;  //sku当前库存剩余量
    private String color;       //sku颜色
    private String size;        //sku尺寸

    public ItemStatis() {
    }
    
    public ItemStatis(Long id, String createDate, Long skuId, BigDecimal costPrice, BigDecimal srcPrice, BigDecimal salePrice, Integer amount, Integer soldAmount, Integer restAmount, String color, String size) {
        this.id = id;
        this.createDate = createDate;
        this.skuId = skuId;
        this.costPrice = costPrice;
        this.srcPrice = srcPrice;
        this.salePrice = salePrice;
        this.amount = amount;
        this.soldAmount = soldAmount;
        this.restAmount = restAmount;
        this.color = color;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getSrcPrice() {
        return srcPrice;
    }

    public void setSrcPrice(BigDecimal srcPrice) {
        this.srcPrice = srcPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(Integer soldAmount) {
        this.soldAmount = soldAmount;
    }

    public Integer getRestAmount() {
        return restAmount;
    }

    public void setRestAmount(Integer restAmount) {
        this.restAmount = restAmount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ItemStatis{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", skuId=" + skuId +
                ", costPrice=" + costPrice +
                ", srcPrice=" + srcPrice +
                ", salePrice=" + salePrice +
                ", amount=" + amount +
                ", soldAmount=" + soldAmount +
                ", restAmount=" + restAmount +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

}

package entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品统计
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class SOrderLine implements Serializable {

    private Long orderId;   //订单ID
    private Long skuId;     //sku ID
    private Long itemId;    //商品ID
    private Integer amount; //购买数量
    private BigDecimal price;//购买价格
    private String skuTitle; //sku标题
    private String skuColor; //sku颜色
    private String skuSize;  //sku尺码

    public SOrderLine() {
    }

    public SOrderLine(Long orderId, Long skuId, Long itemId, Integer amount, BigDecimal price, String skuTitle, String skuColor, String skuSize) {
        this.orderId = orderId;
        this.skuId = skuId;
        this.itemId = itemId;
        this.amount = amount;
        this.price = price;
        this.skuTitle = skuTitle;
        this.skuColor = skuColor;
        this.skuSize = skuSize;
    }

    @Override
    public String toString() {
        return "SOrderLine{" +
                "orderId=" + orderId +
                ", skuId=" + skuId +
                ", itemId=" + itemId +
                ", amount=" + amount +
                ", price=" + price +
                ", skuTitle='" + skuTitle + '\'' +
                ", skuColor='" + skuColor + '\'' +
                ", skuSize='" + skuSize + '\'' +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public String getSkuColor() {
        return skuColor;
    }

    public void setSkuColor(String skuColor) {
        this.skuColor = skuColor;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }
}

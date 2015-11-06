package entity;

import java.math.BigDecimal;

/**
 * A entity for products.
 *
 * Created by Sunny Wu.
 */
public class Stock {

    /**
     * 主键 id
     */
    private Long id;

    /**
    * 商品 id
    */
    private Long productId;

    /**
     * 商品 颜色
     */
    private String productColor;

    /**
     * 商品 尺寸
     */
    private String productSize;

    /**
     * 商品 数量
     */
    private Integer productAmount;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品 建议销售价
     */
    private BigDecimal recommendPrice;

    /**
     * 商品 预览图片
     */
    private String previewImgs;

    public Stock(){}

    public Stock(Long id, Long productId, String productColor, String productSize, Integer productAmount, BigDecimal productPrice, BigDecimal recommendPrice, String previewImgs) {
        this.id = id;
        this.productId = productId;
        this.productColor = productColor;
        this.productSize = productSize;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
        this.recommendPrice = recommendPrice;
        this.previewImgs = previewImgs;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", productId=" + productId +
                ", productColor='" + productColor + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productAmount=" + productAmount +
                ", productPrice=" + productPrice +
                ", recommendPrice=" + recommendPrice +
                ", previewImgs='" + previewImgs + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getRecommendPrice() {
        return recommendPrice;
    }

    public void setRecommendPrice(BigDecimal recommendPrice) {
        this.recommendPrice = recommendPrice;
    }

    public String getPreviewImgs() {
        return previewImgs;
    }

    public void setPreviewImgs(String previewImgs) {
        this.previewImgs = previewImgs;
    }
}

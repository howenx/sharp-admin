package domain.sale;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by sibyl.sun on 16/6/7.
 */
public class SaleOrderLineInfo implements Serializable {

    private Long lineId;           //唯一id
    private Long saleProductId;  //销售商品id
    private Integer saleCount;       //数量
    private BigDecimal jdPrice;     //京东价
    private BigDecimal jdRate;    //京东费率
    private BigDecimal discountAmountLine; //子订单的优惠额

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
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

    public BigDecimal getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public BigDecimal getJdRate() {
        return jdRate;
    }

    public void setJdRate(BigDecimal jdRate) {
        this.jdRate = jdRate;
    }

    public BigDecimal getDiscountAmountLine() {
        return discountAmountLine;
    }

    public void setDiscountAmountLine(BigDecimal discountAmountLine) {
        this.discountAmountLine = discountAmountLine;
    }

    @Override
    public String toString() {
        return "SaleOrderLineInfo{" +
                "lineId=" + lineId +
                ", saleProductId=" + saleProductId +
                ", saleCount=" + saleCount +
                ", jdPrice=" + jdPrice +
                ", jdRate=" + jdRate +
                ", discountAmountLine=" + discountAmountLine +
                '}';
    }
}

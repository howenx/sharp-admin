package entity.erp;

import java.io.Serializable;

/**
 * Created by Sunny Wu 15/12/26.
 *
 * 订单商品折扣信息
 */
public class ShopOrderCreateDiscount implements Serializable {

    private String discountName;    //折扣名称
    private Double discountFee;     //折扣额

    public ShopOrderCreateDiscount() {
    }

    @Override
    public String toString() {
        return "ShopOrderCreateDiscount{" +
                "discountName='" + discountName + '\'' +
                ", discountFee=" + discountFee +
                '}';
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public Double getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Double discountFee) {
        this.discountFee = discountFee;
    }

    public ShopOrderCreateDiscount(String discountName, Double discountFee) {

        this.discountName = discountName;
        this.discountFee = discountFee;
    }
}

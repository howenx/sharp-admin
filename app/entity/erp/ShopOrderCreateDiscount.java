package entity.erp;

/**
 * Created by Sunny Wu 15/12/26.
 *
 * 订单商品折扣信息
 */
public class ShopOrderCreateDiscount {

    private String DiscountName;    //折扣名称
    private Double DiscountFee;     //折扣额

    public ShopOrderCreateDiscount() {
    }

    public String getDiscountName() {
        return DiscountName;
    }

    public void setDiscountName(String discountName) {
        DiscountName = discountName;
    }

    public Double getDiscountFee() {
        return DiscountFee;
    }

    public void setDiscountFee(Double discountFee) {
        DiscountFee = discountFee;
    }

    @Override
    public String toString() {
        return "ShopOrderCreateDiscount{" +
                "DiscountName='" + DiscountName + '\'' +
                ", DiscountFee=" + DiscountFee +
                '}';
    }

    public ShopOrderCreateDiscount(String discountName, Double discountFee) {
        DiscountName = discountName;
        DiscountFee = discountFee;
    }
}

package entity.erp;

import java.io.Serializable;

/**
 * Created by Sunny Wu 15/12/26.
 *
 * 订单商品信息
 */
public class ShopOrderCreateLine implements Serializable {

    private String ShopLineNo;  //平台订单行号
    private String OuterId;     //外部代码
    private Integer Quantity;    //数量
    private Double Price;       //价格
    private String LineUdf1;    //自定义字段1
    private String LineUdf2;    //自定义字段2
    private String ItemName;    //商品名称
    private String SkuName;     //规格名称

    public ShopOrderCreateLine() {
    }

    public String getShopLineNo() {
        return ShopLineNo;
    }

    public void setShopLineNo(String shopLineNo) {
        ShopLineNo = shopLineNo;
    }

    public String getOuterId() {
        return OuterId;
    }

    public void setOuterId(String outerId) {
        OuterId = outerId;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getLineUdf1() {
        return LineUdf1;
    }

    public void setLineUdf1(String lineUdf1) {
        LineUdf1 = lineUdf1;
    }

    public String getLineUdf2() {
        return LineUdf2;
    }

    public void setLineUdf2(String lineUdf2) {
        LineUdf2 = lineUdf2;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getSkuName() {
        return SkuName;
    }

    public void setSkuName(String skuName) {
        SkuName = skuName;
    }

    @Override
    public String toString() {
        return "ShopOrderCreateLine{" +
                "ShopLineNo='" + ShopLineNo + '\'' +
                ", OuterId='" + OuterId + '\'' +
                ", Quantity=" + Quantity +
                ", Price=" + Price +
                ", LineUdf1='" + LineUdf1 + '\'' +
                ", LineUdf2='" + LineUdf2 + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", SkuName='" + SkuName + '\'' +
                '}';
    }

    public ShopOrderCreateLine(String shopLineNo, String outerId, Integer quantity, Double price, String lineUdf1, String lineUdf2, String itemName, String skuName) {
        ShopLineNo = shopLineNo;
        OuterId = outerId;
        Quantity = quantity;
        Price = price;
        LineUdf1 = lineUdf1;
        LineUdf2 = lineUdf2;
        ItemName = itemName;
        SkuName = skuName;
    }
}

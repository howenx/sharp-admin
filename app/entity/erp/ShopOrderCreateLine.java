package entity.erp;

import java.io.Serializable;

/**
 * Created by Sunny Wu 15/12/26.
 *
 * 订单商品信息
 */
public class ShopOrderCreateLine implements Serializable {

    private String shopLineNo;  //平台订单行号
    private String outerId;     //外部代码
    private Integer quantity;    //数量
    private Double price;       //价格
    private String lineUdf1;    //自定义字段1
    private String lineUdf2;    //自定义字段2
    private String itemName;    //商品名称
    private String skuName;     //规格名称

    public ShopOrderCreateLine() {
    }

    @Override
    public String toString() {
        return "ShopOrderCreateLine{" +
                "shopLineNo='" + shopLineNo + '\'' +
                ", outerId='" + outerId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", lineUdf1='" + lineUdf1 + '\'' +
                ", lineUdf2='" + lineUdf2 + '\'' +
                ", itemName='" + itemName + '\'' +
                ", skuName='" + skuName + '\'' +
                '}';
    }

    public String getShopLineNo() {
        return shopLineNo;
    }

    public void setShopLineNo(String shopLineNo) {
        this.shopLineNo = shopLineNo;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLineUdf1() {
        return lineUdf1;
    }

    public void setLineUdf1(String lineUdf1) {
        this.lineUdf1 = lineUdf1;
    }

    public String getLineUdf2() {
        return lineUdf2;
    }

    public void setLineUdf2(String lineUdf2) {
        this.lineUdf2 = lineUdf2;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public ShopOrderCreateLine(String shopLineNo, String outerId, Integer quantity, Double price, String lineUdf1, String lineUdf2, String itemName, String skuName) {

        this.shopLineNo = shopLineNo;
        this.outerId = outerId;
        this.quantity = quantity;
        this.price = price;
        this.lineUdf1 = lineUdf1;
        this.lineUdf2 = lineUdf2;
        this.itemName = itemName;
        this.skuName = skuName;
    }
}

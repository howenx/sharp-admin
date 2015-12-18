package entity;

/**
 * Created by Sunny Wu.
 */
public class ShopOrderCreateLine {

    private String shopLineNo;

    private String outerId;

    private Double quantity;

    private Double price;

    private String lineUdf1;

    private String lineUdf2;

    private String itemName;

    private String skuName;

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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
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

    public ShopOrderCreateLine(String shopLineNo, String outerId, Double quantity, Double price, String lineUdf1, String lineUdf2, String itemName, String skuName) {

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

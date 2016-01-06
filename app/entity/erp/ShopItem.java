package entity.erp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/5.
 *
 * 商品推送到ERP
 */
public class ShopItem implements Serializable {

    //宝贝编码
    @ApiField("ShopItemCode")
    public String shopItemCode;

    //宝贝名称
    @ApiField("ShopItemName")
    public String shopItemName;

    @ApiField("Type")
    public String type;

    //店铺Id
    @ApiField("ShopId")
    public int shopId;

    //店铺
    @ApiField("Shop")
    public Shop shop;

    //是否虚拟商品
    @ApiField("IsVirtual")
    public Boolean virtual;

    @ApiField("IsAgent")
    public Boolean agent;

    @ApiField("IsViolative")
    public Boolean violative;

    //主图Url
    @ApiField("PictureUrl")
    public String pictureUrl;

    //商家编码
    @ApiField("OuterId")
    public String outerId;

    //数量
    @ApiField("Quantity")
    public int quantity;

    //价格
    @ApiField("Price")
    public double price;

    //体积(立方米)
    @ApiField("Size")
    public double size;

    //重量(千克)
    @ApiField("Weight")
    public double weight;

    //上架状态(在售中;在库中)
    @ApiField("Status")
    public String status;

    //备注
    @ApiField("Memo")
    public String memo;

    //创建时间
    @ApiField("CreatedTime")
    public Date createdTime;

    //修改时间
    @ApiField("UpdateTime")
    public Date updateTime;

    @ApiField("UserSign")
    public String userSign;

    //sku
    @ApiListField("Lines")
    @ApiField("ShopSku")
    public List<ShopSku> lines;

    public ShopItem() {
        lines = new ArrayList<ShopSku>();
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "shopItemCode='" + shopItemCode + '\'' +
                ", shopItemName='" + shopItemName + '\'' +
                ", type='" + type + '\'' +
                ", shopId=" + shopId +
                ", shop=" + shop +
                ", virtual=" + virtual +
                ", agent=" + agent +
                ", violative=" + violative +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", outerId='" + outerId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", size=" + size +
                ", weight=" + weight +
                ", status='" + status + '\'' +
                ", memo='" + memo + '\'' +
                ", createdTime=" + createdTime +
                ", updateTime=" + updateTime +
                ", userSign='" + userSign + '\'' +
                ", lines=" + lines +
                '}';
    }

    public String getShopItemCode() {
        return shopItemCode;
    }

    public void setShopItemCode(String shopItemCode) {
        this.shopItemCode = shopItemCode;
    }

    public String getShopItemName() {
        return shopItemName;
    }

    public void setShopItemName(String shopItemName) {
        this.shopItemName = shopItemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public Boolean getAgent() {
        return agent;
    }

    public void setAgent(Boolean agent) {
        this.agent = agent;
    }

    public Boolean getViolative() {
        return violative;
    }

    public void setViolative(Boolean violative) {
        this.violative = violative;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public List<ShopSku> getLines() {
        return lines;
    }

    public void setLines(List<ShopSku> lines) {
        this.lines = lines;
    }
}

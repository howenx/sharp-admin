package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * A entity for Inventory.
 *
 * Created by Sunny Wu.
 */
public class Inventory {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long itemId;

    /**
     * 商品颜色
     */
    private String itemColor;

    /**
     * 商品尺寸
     */
    private String itemSize;

    /**
     * 成本价
     */
    private BigDecimal itemPrice;

    /**
     * 原价
     */
    private BigDecimal itemSrcPrice;

    /**
     * 售价
     */
    private BigDecimal itemCostPrice;

    /**
     * 折扣价
     */
    private BigDecimal itemDiscount;

    /**
     * 库存总量
     */
    private Integer amount;

    /**
     * 售出数量
     */
    private Integer soldAmount;

    /**
     * 剩余库存量
     */
    private Integer restAmount;

    /**
     * 主图
     */
    private String invImg;

    /**
     * 预览图
     */
    private String itemPreviewImgs;

    /**
     * 创建时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;

    /**
     * 更新时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;

    /**
     * 是否删除
     */
    private Boolean orDestroy;

    /**
     * 删除时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp destroyAt;

    /**
     * 是否为主sku
     */
    private Boolean orMasterInv;

    /**
     * 单个sku状态
     */
    private String state;

    private BigDecimal shipFee;

    private String invArea;

    private Integer restrictAmount;

    private String invTitle;

    public Inventory() {}

    public Inventory(Long id, Long itemId, String itemColor, String itemSize, BigDecimal itemPrice, BigDecimal itemSrcPrice, BigDecimal itemCostPrice, BigDecimal itemDiscount, Integer amount, Integer soldAmount, Integer restAmount, String invImg, String itemPreviewImgs, Timestamp createAt, Timestamp updateAt, Boolean orDestroy, Timestamp destroyAt, Boolean orMasterInv, String state, BigDecimal shipFee, String invArea, Integer restrictAmount, String invTitle) {
        this.id = id;
        this.itemId = itemId;
        this.itemColor = itemColor;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
        this.itemSrcPrice = itemSrcPrice;
        this.itemCostPrice = itemCostPrice;
        this.itemDiscount = itemDiscount;
        this.amount = amount;
        this.soldAmount = soldAmount;
        this.restAmount = restAmount;
        this.invImg = invImg;
        this.itemPreviewImgs = itemPreviewImgs;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.orDestroy = orDestroy;
        this.destroyAt = destroyAt;
        this.orMasterInv = orMasterInv;
        this.state = state;
        this.shipFee = shipFee;
        this.invArea = invArea;
        this.restrictAmount = restrictAmount;
        this.invTitle = invTitle;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", itemColor='" + itemColor + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemSrcPrice=" + itemSrcPrice +
                ", itemCostPrice=" + itemCostPrice +
                ", itemDiscount=" + itemDiscount +
                ", amount=" + amount +
                ", soldAmount=" + soldAmount +
                ", restAmount=" + restAmount +
                ", invImg='" + invImg + '\'' +
                ", itemPreviewImgs='" + itemPreviewImgs + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", orDestroy=" + orDestroy +
                ", destroyAt=" + destroyAt +
                ", orMasterInv=" + orMasterInv +
                ", state='" + state + '\'' +
                ", shipFee=" + shipFee +
                ", invArea='" + invArea + '\'' +
                ", restrictAmount=" + restrictAmount +
                ", invTitle='" + invTitle + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getItemSrcPrice() {
        return itemSrcPrice;
    }

    public void setItemSrcPrice(BigDecimal itemSrcPrice) {
        this.itemSrcPrice = itemSrcPrice;
    }

    public BigDecimal getItemCostPrice() {
        return itemCostPrice;
    }

    public void setItemCostPrice(BigDecimal itemCostPrice) {
        this.itemCostPrice = itemCostPrice;
    }

    public BigDecimal getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(BigDecimal itemDiscount) {
        this.itemDiscount = itemDiscount;
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

    public String getInvImg() {
        return invImg;
    }

    public void setInvImg(String invImg) {
        this.invImg = invImg;
    }

    public String getItemPreviewImgs() {
        return itemPreviewImgs;
    }

    public void setItemPreviewImgs(String itemPreviewImgs) {
        this.itemPreviewImgs = itemPreviewImgs;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getOrDestroy() {
        return orDestroy;
    }

    public void setOrDestroy(Boolean orDestroy) {
        this.orDestroy = orDestroy;
    }

    public Timestamp getDestroyAt() {
        return destroyAt;
    }

    public void setDestroyAt(Timestamp destroyAt) {
        this.destroyAt = destroyAt;
    }

    public Boolean getOrMasterInv() {
        return orMasterInv;
    }

    public void setOrMasterInv(Boolean orMasterInv) {
        this.orMasterInv = orMasterInv;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }

    public String getInvArea() {
        return invArea;
    }

    public void setInvArea(String invArea) {
        this.invArea = invArea;
    }

    public Integer getRestrictAmount() {
        return restrictAmount;
    }

    public void setRestrictAmount(Integer restrictAmount) {
        this.restrictAmount = restrictAmount;
    }

    public String getInvTitle() {
        return invTitle;
    }

    public void setInvTitle(String invTitle) {
        this.invTitle = invTitle;
    }
}

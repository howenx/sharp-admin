package entity;

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
     * 是否售空
     */
    private Boolean orSoldOut;

    /**
     * 单个库存请求地址
     */
    private String invUrl;

    /**
     * 主图
     */
    private String invImg;

    /**
     * 预览图
     */
    private String itemPreviewImgs;

    /**
     * 创建操作用户id
     */
    private Long createUid;

    /**
     * 创建时间
     */
    private Timestamp createAt;

    /**
     * 更新操作用户id
     */
    private Long updateUid;

    /**
     * 更新时间
     */
    private Timestamp updateAt;

    /**
     * 是否删除
     */
    private Boolean orDestory;

    /**
     * 删除操作用户id
     */
    private Long destoryUid;

    /**
     * 删除时间
     */
    private Timestamp destoryAt;

    /**
     * 是否为主sku
     */
    private Boolean orMasterInv;

    /**
     * 单个sku状态
     */
    private String state;

    public Inventory() {}

    public Inventory(Long id, Long itemId, String itemColor, String itemSize, BigDecimal itemPrice, BigDecimal itemSrcPrice, BigDecimal itemCostPrice, BigDecimal itemDiscount, Integer amount, Integer soldAmount, Integer restAmount, Boolean orSoldOut, String invUrl, String invImg, String itemPreviewImgs, Long createUid, Timestamp createAt, Long updateUid, Timestamp updateAt, Boolean orDestory, Long destoryUid, Timestamp destoryAt, Boolean orMasterInv, String state) {
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
        this.orSoldOut = orSoldOut;
        this.invUrl = invUrl;
        this.invImg = invImg;
        this.itemPreviewImgs = itemPreviewImgs;
        this.createUid = createUid;
        this.createAt = createAt;
        this.updateUid = updateUid;
        this.updateAt = updateAt;
        this.orDestory = orDestory;
        this.destoryUid = destoryUid;
        this.destoryAt = destoryAt;
        this.orMasterInv = orMasterInv;
        this.state = state;
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
                ", orSoldOut=" + orSoldOut +
                ", invUrl='" + invUrl + '\'' +
                ", invImg='" + invImg + '\'' +
                ", itemPreviewImgs='" + itemPreviewImgs + '\'' +
                ", createUid=" + createUid +
                ", createAt=" + createAt +
                ", updateUid=" + updateUid +
                ", updateAt=" + updateAt +
                ", orDestory=" + orDestory +
                ", destoryUid=" + destoryUid +
                ", destoryAt=" + destoryAt +
                ", orMasterInv=" + orMasterInv +
                ", state='" + state + '\'' +
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

    public Boolean getOrSoldOut() {
        return orSoldOut;
    }

    public void setOrSoldOut(Boolean orSoldOut) {
        this.orSoldOut = orSoldOut;
    }

    public String getInvUrl() {
        return invUrl;
    }

    public void setInvUrl(String invUrl) {
        this.invUrl = invUrl;
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

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getOrDestory() {
        return orDestory;
    }

    public void setOrDestory(Boolean orDestory) {
        this.orDestory = orDestory;
    }

    public Long getDestoryUid() {
        return destoryUid;
    }

    public void setDestoryUid(Long destoryUid) {
        this.destoryUid = destoryUid;
    }

    public Timestamp getDestoryAt() {
        return destoryAt;
    }

    public void setDestoryAt(Timestamp destoryAt) {
        this.destoryAt = destoryAt;
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
}

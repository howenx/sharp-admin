package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Sunny Wu on 16/3/1.
 * kakao china.
 */
public class Skus implements Serializable {

    private Long id;
    private String skuType;
    private Long skuTypeId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp skuTypeStartAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp skuTypeEndAt;
    private Integer skuTypeRestrictAmount;
    private String skuTypeFloorPrice;
    private BigDecimal skuTypeDiscount;
    private String skuTypeImg;
    private String skuTypeStatus;
    private String skuTypeThemeId;
    private Integer skuTypeSoldAmount;
    private Integer skuTypeLimitAmount;
    private String skuTypeTitle;
    private BigDecimal skuTypePrice;
    private Long invId;            //商品id
    private Long itemId;            //商品id
    private String itemColor;       //商品颜色
    private String itemSize;        //商品尺寸
    private BigDecimal itemPrice;   //成本价
    private BigDecimal itemSrcPrice;//原价
    private BigDecimal itemCostPrice;//售价
    private BigDecimal itemDiscount;//折扣价
    private Integer amount;         //库存总量
    private Integer soldAmount;     //销售数量
    private Integer restAmount;     //剩余库存
    private String invImg;          //sku主图
    private String itemPreviewImgs;//sku预览图
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;     //创建时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;     //更新时间
    private Boolean orDestroy;      //是否销毁
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp destroyAt;    //删除时间
    private Boolean orMasterInv;    //是否为主sku
    private String state;           //单个sku状态
    private String themeId;         //主题ID(可多个)
    private String shareUrl;        //分享的链接
    private Integer shareCount;     //分享次数
    private Integer collectCount;   //收藏次数
    private Integer browseCount;    //浏览次数
    private String invArea;         //库存区域
    private Integer restrictAmount;//限购数量
    private String invTitle;        //商品标题
    private String invCustoms;      //报关单位
    private String postalTaxRate;   //行邮税率
    private String postalTaxCode;   //行邮税号
    private  Integer invWeight;     //单个sku重量 单位克
    private String carriageModelCode;//邮费模板code
    private String recordCode;       //备案号
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp startAt;       //上架时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp endAt;         //下架时间
    private Boolean orVaryPrice;     //是否存在多样化价格
    private String invCode;          //货号

    public Skus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public Long getSkuTypeId() {
        return skuTypeId;
    }

    public void setSkuTypeId(Long skuTypeId) {
        this.skuTypeId = skuTypeId;
    }

    public Timestamp getSkuTypeStartAt() {
        return skuTypeStartAt;
    }

    public void setSkuTypeStartAt(Timestamp skuTypeStartAt) {
        this.skuTypeStartAt = skuTypeStartAt;
    }

    public Timestamp getSkuTypeEndAt() {
        return skuTypeEndAt;
    }

    public void setSkuTypeEndAt(Timestamp skuTypeEndAt) {
        this.skuTypeEndAt = skuTypeEndAt;
    }

    public Integer getSkuTypeRestrictAmount() {
        return skuTypeRestrictAmount;
    }

    public void setSkuTypeRestrictAmount(Integer skuTypeRestrictAmount) {
        this.skuTypeRestrictAmount = skuTypeRestrictAmount;
    }

    public String getSkuTypeFloorPrice() {
        return skuTypeFloorPrice;
    }

    public void setSkuTypeFloorPrice(String skuTypeFloorPrice) {
        this.skuTypeFloorPrice = skuTypeFloorPrice;
    }

    public BigDecimal getSkuTypeDiscount() {
        return skuTypeDiscount;
    }

    public void setSkuTypeDiscount(BigDecimal skuTypeDiscount) {
        this.skuTypeDiscount = skuTypeDiscount;
    }

    public String getSkuTypeImg() {
        return skuTypeImg;
    }

    public void setSkuTypeImg(String skuTypeImg) {
        this.skuTypeImg = skuTypeImg;
    }

    public String getSkuTypeStatus() {
        return skuTypeStatus;
    }

    public void setSkuTypeStatus(String skuTypeStatus) {
        this.skuTypeStatus = skuTypeStatus;
    }

    public String getSkuTypeThemeId() {
        return skuTypeThemeId;
    }

    public void setSkuTypeThemeId(String skuTypeThemeId) {
        this.skuTypeThemeId = skuTypeThemeId;
    }

    public Integer getSkuTypeSoldAmount() {
        return skuTypeSoldAmount;
    }

    public void setSkuTypeSoldAmount(Integer skuTypeSoldAmount) {
        this.skuTypeSoldAmount = skuTypeSoldAmount;
    }

    public Integer getSkuTypeLimitAmount() {
        return skuTypeLimitAmount;
    }

    public void setSkuTypeLimitAmount(Integer skuTypeLimitAmount) {
        this.skuTypeLimitAmount = skuTypeLimitAmount;
    }

    public String getSkuTypeTitle() {
        return skuTypeTitle;
    }

    public void setSkuTypeTitle(String skuTypeTitle) {
        this.skuTypeTitle = skuTypeTitle;
    }

    public BigDecimal getSkuTypePrice() {
        return skuTypePrice;
    }

    public void setSkuTypePrice(BigDecimal skuTypePrice) {
        this.skuTypePrice = skuTypePrice;
    }

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
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

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
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

    public String getInvCustoms() {
        return invCustoms;
    }

    public void setInvCustoms(String invCustoms) {
        this.invCustoms = invCustoms;
    }

    public String getPostalTaxRate() {
        return postalTaxRate;
    }

    public void setPostalTaxRate(String postalTaxRate) {
        this.postalTaxRate = postalTaxRate;
    }

    public String getPostalTaxCode() {
        return postalTaxCode;
    }

    public void setPostalTaxCode(String postalTaxCode) {
        this.postalTaxCode = postalTaxCode;
    }

    public Integer getInvWeight() {
        return invWeight;
    }

    public void setInvWeight(Integer invWeight) {
        this.invWeight = invWeight;
    }

    public String getCarriageModelCode() {
        return carriageModelCode;
    }

    public void setCarriageModelCode(String carriageModelCode) {
        this.carriageModelCode = carriageModelCode;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public Boolean getOrVaryPrice() {
        return orVaryPrice;
    }

    public void setOrVaryPrice(Boolean orVaryPrice) {
        this.orVaryPrice = orVaryPrice;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    @Override
    public String toString() {
        return "Skus{" +
                "id=" + id +
                ", skuType='" + skuType + '\'' +
                ", skuTypeId=" + skuTypeId +
                ", skuTypeStartAt=" + skuTypeStartAt +
                ", skuTypeEndAt=" + skuTypeEndAt +
                ", skuTypeRestrictAmount=" + skuTypeRestrictAmount +
                ", skuTypeFloorPrice=" + skuTypeFloorPrice +
                ", skuTypeDiscount=" + skuTypeDiscount +
                ", skuTypeImg='" + skuTypeImg + '\'' +
                ", skuTypeStatus='" + skuTypeStatus + '\'' +
                ", skuTypeThemeId='" + skuTypeThemeId + '\'' +
                ", skuTypeSoldAmount=" + skuTypeSoldAmount +
                ", skuTypeLimitAmount=" + skuTypeLimitAmount +
                ", skuTypeTitle='" + skuTypeTitle + '\'' +
                ", skuTypePrice=" + skuTypePrice +
                ", invId=" + invId +
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
                ", themeId='" + themeId + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", shareCount=" + shareCount +
                ", collectCount=" + collectCount +
                ", browseCount=" + browseCount +
                ", invArea='" + invArea + '\'' +
                ", restrictAmount=" + restrictAmount +
                ", invTitle='" + invTitle + '\'' +
                ", invCustoms='" + invCustoms + '\'' +
                ", postalTaxRate='" + postalTaxRate + '\'' +
                ", postalTaxCode='" + postalTaxCode + '\'' +
                ", invWeight=" + invWeight +
                ", carriageModelCode='" + carriageModelCode + '\'' +
                ", recordCode='" + recordCode + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", orVaryPrice=" + orVaryPrice +
                ", invCode='" + invCode + '\'' +
                '}';
    }

    public Skus(Long id, String skuType, Long skuTypeId, Timestamp skuTypeStartAt, Timestamp skuTypeEndAt, Integer skuTypeRestrictAmount, String skuTypeFloorPrice, BigDecimal skuTypeDiscount, String skuTypeImg, String skuTypeStatus, String skuTypeThemeId, Integer skuTypeSoldAmount, Integer skuTypeLimitAmount, String skuTypeTitle, BigDecimal skuTypePrice, Long invId, Long itemId, String itemColor, String itemSize, BigDecimal itemPrice, BigDecimal itemSrcPrice, BigDecimal itemCostPrice, BigDecimal itemDiscount, Integer amount, Integer soldAmount, Integer restAmount, String invImg, String itemPreviewImgs, Timestamp createAt, Timestamp updateAt, Boolean orDestroy, Timestamp destroyAt, Boolean orMasterInv, String state, String themeId, String shareUrl, Integer shareCount, Integer collectCount, Integer browseCount, String invArea, Integer restrictAmount, String invTitle, String invCustoms, String postalTaxRate, String postalTaxCode, Integer invWeight, String carriageModelCode, String recordCode, Timestamp startAt, Timestamp endAt, Boolean orVaryPrice, String invCode) {
        this.id = id;
        this.skuType = skuType;
        this.skuTypeId = skuTypeId;
        this.skuTypeStartAt = skuTypeStartAt;
        this.skuTypeEndAt = skuTypeEndAt;
        this.skuTypeRestrictAmount = skuTypeRestrictAmount;
        this.skuTypeFloorPrice = skuTypeFloorPrice;
        this.skuTypeDiscount = skuTypeDiscount;
        this.skuTypeImg = skuTypeImg;
        this.skuTypeStatus = skuTypeStatus;
        this.skuTypeThemeId = skuTypeThemeId;
        this.skuTypeSoldAmount = skuTypeSoldAmount;
        this.skuTypeLimitAmount = skuTypeLimitAmount;
        this.skuTypeTitle = skuTypeTitle;
        this.skuTypePrice = skuTypePrice;
        this.invId = invId;
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
        this.themeId = themeId;
        this.shareUrl = shareUrl;
        this.shareCount = shareCount;
        this.collectCount = collectCount;
        this.browseCount = browseCount;
        this.invArea = invArea;
        this.restrictAmount = restrictAmount;
        this.invTitle = invTitle;
        this.invCustoms = invCustoms;
        this.postalTaxRate = postalTaxRate;
        this.postalTaxCode = postalTaxCode;
        this.invWeight = invWeight;
        this.carriageModelCode = carriageModelCode;
        this.recordCode = recordCode;
        this.startAt = startAt;
        this.endAt = endAt;
        this.orVaryPrice = orVaryPrice;
        this.invCode = invCode;
    }
}

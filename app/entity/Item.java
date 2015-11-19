package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品管理
 * Created by howen on 15/11/11.
 */
public class Item implements Serializable{

    private Long id                  ;
    private Long ciqCode            ;
    private String lang                ;
    private Long cateId             ;
    private Long brandId            ;
    private String cateNm             ;
    private String brandNm            ;
    private String itemNm             ;
    private String itemTitle          ;
    private String itemDesc           ;
    private String barCode            ;
    private String barDes             ;
    private Long merchUid           ;
    private String merchNm           ;
    private String srcArea            ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp onShelvesAt       ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp offShelvesAt      ;
    private String itemMasterImg     ;
    private String itemPreviewImgs   ;
    private String itemDetailImgs    ;
    private String itemFeatures       ;
    private Boolean orJoinTheme       ;
    private Long themeId            ;
    private Boolean orMajorItem       ;
    private String state               ;
    private Boolean orFreeShip        ;
    private Boolean hasInvoice         ;
    private Boolean hasVatInvoice     ;
    private String deliveryArea       ;
    private String deliveryTime       ;
    private Boolean orRestrictBuy     ;
    private Integer restrictAmount     ;
    private String itemUrl            ;
    private Boolean orShoppingPoll    ;
    private Boolean orShare            ;
    private String shareKey           ;
    private String shareImg           ;
    private String shareUrl           ;
    private Integer shareCount         ;
    private Integer likeCount          ;
    private Integer collectCount       ;
    private Integer browseCount        ;
    private String itemNotice         ;
    private Long transUid           ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp transAt            ;
    private String transLang          ;
    private Boolean orDestory          ;
    private Long destoryUid         ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp destoryAt          ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp updateAt           ;
    private Long updateUid          ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp createAt           ;
    private Long createUid          ;

    //分页,每页多少条
    private Integer pageSize;

    //分页,从第几条开始
    private Integer offset;

    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;


    public Item() {
    }

    public Item(Long id, Long ciqCode, String lang, Long cateId, Long brandId, String cateNm, String brandNm, String itemNm, String itemTitle, String itemDesc, String barCode, String barDes, Long merchUid, String srcArea, Timestamp onShelvesAt, Timestamp offShelvesAt, String itemMasterImg, String itemPreviewImgs, String itemDetailImgs, String itemFeatures, Boolean orJoinTheme, Long themeId, Boolean orMajorItem, String state, Boolean orFreeShip, Boolean hasInvoice, Boolean hasVatInvoice, String deliveryArea, String deliveryTime, Boolean orRestrictBuy, Integer restrictAmount, String itemUrl, Boolean orShoppingPoll, Boolean orShare, String shareKey, String shareImg, String shareUrl, Integer shareCount, Integer likeCount, Integer collectCount, Integer browseCount, String itemNotice, Long transUid, Timestamp transAt, String transLang, Boolean orDestory, Long destoryUid, Timestamp destoryAt, Timestamp updateAt, Long updateUid, Timestamp createAt, Long createUid, Integer pageSize, Integer offset, String sort, String order) {
        this.id = id;
        this.ciqCode = ciqCode;
        this.lang = lang;
        this.cateId = cateId;
        this.brandId = brandId;
        this.cateNm = cateNm;
        this.brandNm = brandNm;
        this.itemNm = itemNm;
        this.itemTitle = itemTitle;
        this.itemDesc = itemDesc;
        this.barCode = barCode;
        this.barDes = barDes;
        this.merchUid = merchUid;
        this.merchNm = merchNm;
        this.srcArea = srcArea;
        this.onShelvesAt = onShelvesAt;
        this.offShelvesAt = offShelvesAt;
        this.itemMasterImg = itemMasterImg;
        this.itemPreviewImgs = itemPreviewImgs;
        this.itemDetailImgs = itemDetailImgs;
        this.itemFeatures = itemFeatures;
        this.orJoinTheme = orJoinTheme;
        this.themeId = themeId;
        this.orMajorItem = orMajorItem;
        this.state = state;
        this.orFreeShip = orFreeShip;
        this.hasInvoice = hasInvoice;
        this.hasVatInvoice = hasVatInvoice;
        this.deliveryArea = deliveryArea;
        this.deliveryTime = deliveryTime;
        this.orRestrictBuy = orRestrictBuy;
        this.restrictAmount = restrictAmount;
        this.itemUrl = itemUrl;
        this.orShoppingPoll = orShoppingPoll;
        this.orShare = orShare;
        this.shareKey = shareKey;
        this.shareImg = shareImg;
        this.shareUrl = shareUrl;
        this.shareCount = shareCount;
        this.likeCount = likeCount;
        this.collectCount = collectCount;
        this.browseCount = browseCount;
        this.itemNotice = itemNotice;
        this.transUid = transUid;
        this.transAt = transAt;
        this.transLang = transLang;
        this.orDestory = orDestory;
        this.destoryUid = destoryUid;
        this.destoryAt = destoryAt;
        this.updateAt = updateAt;
        this.updateUid = updateUid;
        this.createAt = createAt;
        this.createUid = createUid;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCiqCode() {
        return ciqCode;
    }

    public void setCiqCode(Long ciqCode) {
        this.ciqCode = ciqCode;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getCateNm() {
        return cateNm;
    }

    public void setCateNm(String cateNm) {
        this.cateNm = cateNm;
    }

    public String getBrandNm() {
        return brandNm;
    }

    public void setBrandNm(String brandNm) {
        this.brandNm = brandNm;
    }

    public String getItemNm() {
        return itemNm;
    }

    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBarDes() {
        return barDes;
    }

    public void setBarDes(String barDes) {
        this.barDes = barDes;
    }

    public Long getMerchUid() {
        return merchUid;
    }

    public void setMerchUid(Long merchUid) {
        this.merchUid = merchUid;
    }

    public String getSrcArea() {
        return srcArea;
    }

    public void setSrcArea(String srcArea) {
        this.srcArea = srcArea;
    }

    public Timestamp getOnShelvesAt() {
        return onShelvesAt;
    }

    public void setOnShelvesAt(Timestamp onShelvesAt) {
        this.onShelvesAt = onShelvesAt;
    }

    public Timestamp getOffShelvesAt() {
        return offShelvesAt;
    }

    public void setOffShelvesAt(Timestamp offShelvesAt) {
        this.offShelvesAt = offShelvesAt;
    }

    public String getItemMasterImg() {
        return itemMasterImg;
    }

    public void setItemMasterImg(String itemMasterImg) {
        this.itemMasterImg = itemMasterImg;
    }

    public String getItemPreviewImgs() {
        return itemPreviewImgs;
    }

    public void setItemPreviewImgs(String itemPreviewImgs) {
        this.itemPreviewImgs = itemPreviewImgs;
    }

    public String getItemDetailImgs() {
        return itemDetailImgs;
    }

    public void setItemDetailImgs(String itemDetailImgs) {
        this.itemDetailImgs = itemDetailImgs;
    }

    public String getItemFeatures() {
        return itemFeatures;
    }

    public void setItemFeatures(String itemFeatures) {
        this.itemFeatures = itemFeatures;
    }

    public Boolean getOrJoinTheme() {
        return orJoinTheme;
    }

    public void setOrJoinTheme(Boolean orJoinTheme) {
        this.orJoinTheme = orJoinTheme;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Boolean getOrMajorItem() {
        return orMajorItem;
    }

    public void setOrMajorItem(Boolean orMajorItem) {
        this.orMajorItem = orMajorItem;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getOrFreeShip() {
        return orFreeShip;
    }

    public void setOrFreeShip(Boolean orFreeShip) {
        this.orFreeShip = orFreeShip;
    }

    public Boolean getHasInvoice() {
        return hasInvoice;
    }

    public void setHasInvoice(Boolean hasInvoice) {
        this.hasInvoice = hasInvoice;
    }

    public Boolean getHasVatInvoice() {
        return hasVatInvoice;
    }

    public void setHasVatInvoice(Boolean hasVatInvoice) {
        this.hasVatInvoice = hasVatInvoice;
    }

    public String getDeliveryArea() {
        return deliveryArea;
    }

    public void setDeliveryArea(String deliveryArea) {
        this.deliveryArea = deliveryArea;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Boolean getOrRestrictBuy() {
        return orRestrictBuy;
    }

    public void setOrRestrictBuy(Boolean orRestrictBuy) {
        this.orRestrictBuy = orRestrictBuy;
    }

    public Integer getRestrictAmount() {
        return restrictAmount;
    }

    public void setRestrictAmount(Integer restrictAmount) {
        this.restrictAmount = restrictAmount;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public Boolean getOrShoppingPoll() {
        return orShoppingPoll;
    }

    public void setOrShoppingPoll(Boolean orShoppingPoll) {
        this.orShoppingPoll = orShoppingPoll;
    }

    public Boolean getOrShare() {
        return orShare;
    }

    public void setOrShare(Boolean orShare) {
        this.orShare = orShare;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
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

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
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

    public String getItemNotice() {
        return itemNotice;
    }

    public void setItemNotice(String itemNotice) {
        this.itemNotice = itemNotice;
    }

    public Long getTransUid() {
        return transUid;
    }

    public void setTransUid(Long transUid) {
        this.transUid = transUid;
    }

    public Timestamp getTransAt() {
        return transAt;
    }

    public void setTransAt(Timestamp transAt) {
        this.transAt = transAt;
    }

    public String getTransLang() {
        return transLang;
    }

    public void setTransLang(String transLang) {
        this.transLang = transLang;
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

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMerchNm() {
        return merchNm;
    }

    public void setMerchNm(String merchNm) {
        this.merchNm = merchNm;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", ciqCode=" + ciqCode +
                ", lang='" + lang + '\'' +
                ", cateId=" + cateId +
                ", brandId=" + brandId +
                ", cateNm='" + cateNm + '\'' +
                ", brandNm='" + brandNm + '\'' +
                ", itemNm='" + itemNm + '\'' +
                ", itemTitle='" + itemTitle + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", barCode='" + barCode + '\'' +
                ", barDes='" + barDes + '\'' +
                ", merchUid=" + merchUid +
                ", merchNm=" + merchNm +
                ", srcArea='" + srcArea + '\'' +
                ", onShelvesAt=" + onShelvesAt +
                ", offShelvesAt=" + offShelvesAt +
                ", itemMasterImg='" + itemMasterImg + '\'' +
                ", itemPreviewImgs='" + itemPreviewImgs + '\'' +
                ", itemDetailImgs='" + itemDetailImgs + '\'' +
                ", itemFeatures='" + itemFeatures + '\'' +
                ", orJoinTheme=" + orJoinTheme +
                ", themeId=" + themeId +
                ", orMajorItem=" + orMajorItem +
                ", state='" + state + '\'' +
                ", orFreeShip=" + orFreeShip +
                ", hasInvoice=" + hasInvoice +
                ", hasVatInvoice=" + hasVatInvoice +
                ", deliveryArea='" + deliveryArea + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", orRestrictBuy=" + orRestrictBuy +
                ", restrictAmount=" + restrictAmount +
                ", itemUrl='" + itemUrl + '\'' +
                ", orShoppingPoll=" + orShoppingPoll +
                ", orShare=" + orShare +
                ", shareKey='" + shareKey + '\'' +
                ", shareImg='" + shareImg + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", shareCount=" + shareCount +
                ", likeCount=" + likeCount +
                ", collectCount=" + collectCount +
                ", browseCount=" + browseCount +
                ", itemNotice='" + itemNotice + '\'' +
                ", transUid=" + transUid +
                ", transAt=" + transAt +
                ", transLang='" + transLang + '\'' +
                ", orDestory=" + orDestory +
                ", destoryUid=" + destoryUid +
                ", destoryAt=" + destoryAt +
                ", updateAt=" + updateAt +
                ", updateUid=" + updateUid +
                ", createAt=" + createAt +
                ", createUid=" + createUid +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}

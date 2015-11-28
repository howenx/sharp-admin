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
    private Long cateId             ;
    private Long brandId            ;
    private String itemTitle         ;
    private String supplyMerch           ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp onShelvesAt       ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp offShelvesAt      ;
    private String itemMasterImg   ;
    private String itemDetailImgs    ;
    private String itemFeatures       ;
    private Long themeId            ;
    private String state               ;
    private String shareUrl           ;
    private Integer shareCount         ;
    private Integer collectCount       ;
    private Integer browseCount        ;
    private String itemNotice         ;
    private Boolean orDestroy          ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp destroyAt          ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt           ;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp createAt           ;
    private Long masterInvId             ;
    private String publicity             ;
    //分页,每页多少条
    private Integer pageSize;

    //分页,从第几条开始
    private Integer offset;

    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", cateId=" + cateId +
                ", brandId=" + brandId +
                ", itemTitle='" + itemTitle + '\'' +
                ", supplyMerch='" + supplyMerch + '\'' +
                ", onShelvesAt=" + onShelvesAt +
                ", offShelvesAt=" + offShelvesAt +
                ", itemMasterImg='" + itemMasterImg + '\'' +
                ", itemDetailImgs='" + itemDetailImgs + '\'' +
                ", itemFeatures='" + itemFeatures + '\'' +
                ", themeId=" + themeId +
                ", state='" + state + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", shareCount=" + shareCount +
                ", collectCount=" + collectCount +
                ", browseCount=" + browseCount +
                ", itemNotice='" + itemNotice + '\'' +
                ", orDestroy=" + orDestroy +
                ", destroyAt=" + destroyAt +
                ", updateAt=" + updateAt +
                ", createAt=" + createAt +
                ", masterInvId=" + masterInvId +
                ", publicity='" + publicity + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getSupplyMerch() {
        return supplyMerch;
    }

    public void setSupplyMerch(String supplyMerch) {
        this.supplyMerch = supplyMerch;
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

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getItemNotice() {
        return itemNotice;
    }

    public void setItemNotice(String itemNotice) {
        this.itemNotice = itemNotice;
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

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Long getMasterInvId() {
        return masterInvId;
    }

    public void setMasterInvId(Long masterInvId) {
        this.masterInvId = masterInvId;
    }

    public String getPublicity() {
        return publicity;
    }

    public void setPublicity(String publicity) {
        this.publicity = publicity;
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
}

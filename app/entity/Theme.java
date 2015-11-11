package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 主题vo
 * Created by howen on 15/11/9.
 */
public class Theme implements Serializable{

    private Long id;
    private Long masterItemId;
    private String title;
    private String themeDesc;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp startAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp endAt;
    private BigDecimal themeDiscountUp;
    private BigDecimal itemPriceTop;
    private BigDecimal itemPriceLow;
    private String themeImg;
    private String themeUrl;
    private String themeTags;
    private Integer itemCount;
    private Integer themeTagCount;
    private Integer sortNu;
    private Boolean isDestory;
    private Long destoryUid;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp   destoryAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp   updateAt;
    private Long updateUid;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Timestamp   createAt;
    private Long createUid;
    private String themeSrcImg;

    private Integer pageSize;

    private Integer offset;

    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    public Theme() {
    }

    public Theme(Long id, Long masterItemId, String title, String themeDesc, Timestamp startAt, Timestamp endAt, BigDecimal themeDiscountUp, BigDecimal itemPriceTop, BigDecimal itemPriceLow, String themeImg, String themeUrl, String themeTags, Integer itemCount, Integer themeTagCount, Integer sortNu, Boolean isDestory, Long destoryUid, Timestamp destoryAt, Timestamp updateAt, Long updateUid, Timestamp createAt, Long createUid, String themeSrcImg, Integer pageSize, Integer offset, String sort, String order) {
        this.id = id;
        this.masterItemId = masterItemId;
        this.title = title;
        this.themeDesc = themeDesc;
        this.startAt = startAt;
        this.endAt = endAt;
        this.themeDiscountUp = themeDiscountUp;
        this.itemPriceTop = itemPriceTop;
        this.itemPriceLow = itemPriceLow;
        this.themeImg = themeImg;
        this.themeUrl = themeUrl;
        this.themeTags = themeTags;
        this.itemCount = itemCount;
        this.themeTagCount = themeTagCount;
        this.sortNu = sortNu;
        this.isDestory = isDestory;
        this.destoryUid = destoryUid;
        this.destoryAt = destoryAt;
        this.updateAt = updateAt;
        this.updateUid = updateUid;
        this.createAt = createAt;
        this.createUid = createUid;
        this.themeSrcImg = themeSrcImg;
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

    public Long getMasterItemId() {
        return masterItemId;
    }

    public void setMasterItemId(Long masterItemId) {
        this.masterItemId = masterItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThemeDesc() {
        return themeDesc;
    }

    public void setThemeDesc(String themeDesc) {
        this.themeDesc = themeDesc;
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

    public BigDecimal getThemeDiscountUp() {
        return themeDiscountUp;
    }

    public void setThemeDiscountUp(BigDecimal themeDiscountUp) {
        this.themeDiscountUp = themeDiscountUp;
    }

    public BigDecimal getItemPriceTop() {
        return itemPriceTop;
    }

    public void setItemPriceTop(BigDecimal itemPriceTop) {
        this.itemPriceTop = itemPriceTop;
    }

    public BigDecimal getItemPriceLow() {
        return itemPriceLow;
    }

    public void setItemPriceLow(BigDecimal itemPriceLow) {
        this.itemPriceLow = itemPriceLow;
    }

    public String getThemeImg() {
        return themeImg;
    }

    public void setThemeImg(String themeImg) {
        this.themeImg = themeImg;
    }

    public String getThemeUrl() {
        return themeUrl;
    }

    public void setThemeUrl(String themeUrl) {
        this.themeUrl = themeUrl;
    }

    public String getThemeTags() {
        return themeTags;
    }

    public void setThemeTags(String themeTags) {
        this.themeTags = themeTags;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getThemeTagCount() {
        return themeTagCount;
    }

    public void setThemeTagCount(Integer themeTagCount) {
        this.themeTagCount = themeTagCount;
    }

    public Integer getSortNu() {
        return sortNu;
    }

    public void setSortNu(Integer sortNu) {
        this.sortNu = sortNu;
    }

    public Boolean getDestory() {
        return isDestory;
    }

    public void setDestory(Boolean destory) {
        isDestory = destory;
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

    public String getThemeSrcImg() {
        return themeSrcImg;
    }

    public void setThemeSrcImg(String themeSrcImg) {
        this.themeSrcImg = themeSrcImg;
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

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", masterItemId=" + masterItemId +
                ", title='" + title + '\'' +
                ", themeDesc='" + themeDesc + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", themeDiscountUp=" + themeDiscountUp +
                ", itemPriceTop=" + itemPriceTop +
                ", itemPriceLow=" + itemPriceLow +
                ", themeImg='" + themeImg + '\'' +
                ", themeUrl='" + themeUrl + '\'' +
                ", themeTags='" + themeTags + '\'' +
                ", itemCount=" + itemCount +
                ", themeTagCount=" + themeTagCount +
                ", sortNu=" + sortNu +
                ", isDestory=" + isDestory +
                ", destoryUid=" + destoryUid +
                ", destoryAt=" + destoryAt +
                ", updateAt=" + updateAt +
                ", updateUid=" + updateUid +
                ", createAt=" + createAt +
                ", createUid=" + createUid +
                ", themeSrcImg='" + themeSrcImg + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
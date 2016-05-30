package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.validation.Constraints;
import util.Regex;

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
    @Constraints.Required
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String startAt;
    @Constraints.Required
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String endAt;
    private BigDecimal themeDiscountUp;
    private BigDecimal itemPriceTop;
    private BigDecimal itemPriceLow;
    @Constraints.Required
    private String themeImg;
    private String themeUrl;
    private String themeTags;
    private Integer itemCount;
    private Integer themeTagCount;
    private Integer sortNu;
    private Boolean orDestroy;
    private Long destoryUid;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp   destoryAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp   updateAt;
    private Long updateUid;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp   createAt;
    private Long createUid;
    private String themeSrcImg;
    private String themeItem;
    private String themeMasterImg;
    private String masterItemTag;
    private String type;
    //@Constraints.Pattern(Regex.HTTP)
    private String h5Link;

    //分页,每页多少条
    private Integer pageSize;

    //分页,从第几条开始
    private Integer offset;

    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    public Theme() {
    }

    public Theme(Long id, Long masterItemId, String title, String themeDesc, String startAt, String endAt, BigDecimal themeDiscountUp, BigDecimal itemPriceTop, BigDecimal itemPriceLow, String themeImg, String themeUrl, String themeTags, Integer itemCount, Integer themeTagCount, Integer sortNu, Boolean orDestroy, Long destoryUid, Timestamp destoryAt, Timestamp updateAt, Long updateUid, Timestamp createAt, Long createUid, String themeSrcImg, String themeItem, String themeMasterImg, String masterItemTag, String type, String h5Link, Integer pageSize, Integer offset, String sort, String order) {
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
        this.orDestroy = orDestroy;
        this.destoryUid = destoryUid;
        this.destoryAt = destoryAt;
        this.updateAt = updateAt;
        this.updateUid = updateUid;
        this.createAt = createAt;
        this.createUid = createUid;
        this.themeSrcImg = themeSrcImg;
        this.themeItem = themeItem;
        this.themeMasterImg = themeMasterImg;
        this.masterItemTag = masterItemTag;
        this.type = type;
        this.h5Link = h5Link;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", masterItemId=" + masterItemId +
                ", title='" + title + '\'' +
                ", themeDesc='" + themeDesc + '\'' +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                ", themeDiscountUp=" + themeDiscountUp +
                ", itemPriceTop=" + itemPriceTop +
                ", itemPriceLow=" + itemPriceLow +
                ", themeImg='" + themeImg + '\'' +
                ", themeUrl='" + themeUrl + '\'' +
                ", themeTags='" + themeTags + '\'' +
                ", itemCount=" + itemCount +
                ", themeTagCount=" + themeTagCount +
                ", sortNu=" + sortNu +
                ", orDestroy=" + orDestroy +
                ", destoryUid=" + destoryUid +
                ", destoryAt=" + destoryAt +
                ", updateAt=" + updateAt +
                ", updateUid=" + updateUid +
                ", createAt=" + createAt +
                ", createUid=" + createUid +
                ", themeSrcImg='" + themeSrcImg + '\'' +
                ", themeItem='" + themeItem + '\'' +
                ", themeMasterImg='" + themeMasterImg + '\'' +
                ", masterItemTag='" + masterItemTag + '\'' +
                ", type='" + type + '\'' +
                ", h5Link='" + h5Link + '\'' +
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

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
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

    public Boolean getOrDestroy() {
        return orDestroy;
    }

    public void setOrDestroy(Boolean orDestroy) {
        this.orDestroy = orDestroy;
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

    public String getThemeItem() {
        return themeItem;
    }

    public void setThemeItem(String themeItem) {
        this.themeItem = themeItem;
    }

    public String getThemeMasterImg() {
        return themeMasterImg;
    }

    public void setThemeMasterImg(String themeMasterImg) {
        this.themeMasterImg = themeMasterImg;
    }

    public String getMasterItemTag() {
        return masterItemTag;
    }

    public void setMasterItemTag(String masterItemTag) {
        this.masterItemTag = masterItemTag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getH5Link() {
        return h5Link;
    }

    public void setH5Link(String h5Link) {
        this.h5Link = h5Link;
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

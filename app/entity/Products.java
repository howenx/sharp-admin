package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * A entity for products.
 *
 * Created by Sunny Wu.
 */
public class Products implements Serializable{

    /**
     * 主键 id
     */
    private Long id;

    /**
     * 供货商 id
     */
    private Integer merchId;

    /**
     * 供货商 名称
     */
    private String merchName;

    /**
     * 语言
     */
    private String language;

    /**
     * 类别 id
     */
    private Integer cateId;

    /**
     * 类别名称
     */
    private String cateName;

    /**
     * 品牌 id
     */
    private Integer brandId;

    /**
     * 品牌 名称
     */
    private String brandName;

    /**
     * 商品 名称
     */
    private String productName;

    /**
     * 商品 颜色
     */
    private String productColor;

    /**
     * 商品 尺寸
     */
    private String productSize;

    /**
     * 商品 描述信息
     */
    private String productDesc;

    /**
     * 库存地
     */
    private String storeArea;

    /**
     * 原产地
     */
    private String sourceArea;

    /**
     * 销售开始日期
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp sellOnDate;

    /**
     * 销售结束日期
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp sellOffDate;

    /**
     * 商品 数量
     */
    private Integer productAmount;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品 建议销售价
     */
    private BigDecimal recommendPrice;

    /**
     * 商品 主图片
     */
    private String masterImg;

    /**
     * 商品 预览图片
     */
//    @JsonFormat(shape=JsonFormat.Shape.ARRAY)
    private String previewImgs;

    /**
     * 商品 详细图片
     */
    private String detailImgs;

    /**
     * 商品 属性
     */
    private String features;

    /**
     * 商品 状态
     */
    private String productState;

    /**
     * 是否已删除
     */
    private boolean destory;

    /**
     * 删除 操作用户id
     */
    private Integer destoryUid;

    /**
     * 最后更新日期
     */
    @JsonFormat(shape=JsonFormat.Shape.ANY, pattern="s")
    private Timestamp updateDate;

    /**
     * 最后更新操作用户id
     */
    private Integer updateUid;

    /**
     * 创建 日期
     */
    private Timestamp createDate;

    /**
     * 创建 操作用户id
     */
    private Integer createUid;

    private Integer pageSize;
    private Integer offset;
    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    public Products(){}

    public Products(Long id, Integer merchId, String merchName, String language, Integer cateId, String cateName, Integer brandId, String brandName, String productName, String productColor, String productSize, String productDesc, String storeArea, String sourceArea, Timestamp sellOnDate, Timestamp sellOffDate, Integer productAmount, BigDecimal productPrice, BigDecimal recommendPrice, String masterImg, String previewImgs, String detailImgs, String features, String productState, boolean destory, Integer destoryUid, Timestamp updateDate, Integer updateUid, Timestamp createDate, Integer createUid, Integer pageSize, Integer offset, String sort, String order) {
        this.id = id;
        this.merchId = merchId;
        this.merchName = merchName;
        this.language = language;
        this.cateId = cateId;
        this.cateName = cateName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.productName = productName;
        this.productColor = productColor;
        this.productSize = productSize;
        this.productDesc = productDesc;
        this.storeArea = storeArea;
        this.sourceArea = sourceArea;
        this.sellOnDate = sellOnDate;
        this.sellOffDate = sellOffDate;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
        this.recommendPrice = recommendPrice;
        this.masterImg = masterImg;
        this.previewImgs = previewImgs;
        this.detailImgs = detailImgs;
        this.features = features;
        this.productState = productState;
        this.destory = destory;
        this.destoryUid = destoryUid;
        this.updateDate = updateDate;
        this.updateUid = updateUid;
        this.createDate = createDate;
        this.createUid = createUid;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", merchId=" + merchId +
                ", merchName='" + merchName + '\'' +
                ", language='" + language + '\'' +
                ", cateId=" + cateId +
                ", cateName='" + cateName + '\'' +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", productName='" + productName + '\'' +
                ", productColor='" + productColor + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", storeArea='" + storeArea + '\'' +
                ", sourceArea='" + sourceArea + '\'' +
                ", sellOnDate=" + sellOnDate +
                ", sellOffDate=" + sellOffDate +
                ", productAmount=" + productAmount +
                ", productPrice=" + productPrice +
                ", recommendPrice=" + recommendPrice +
                ", masterImg='" + masterImg + '\'' +
                ", previewImgs='" + previewImgs + '\'' +
                ", detailImgs='" + detailImgs + '\'' +
                ", features='" + features + '\'' +
                ", productState='" + productState + '\'' +
                ", destory=" + destory +
                ", destoryUid=" + destoryUid +
                ", updateDate=" + updateDate +
                ", updateUid=" + updateUid +
                ", createDate=" + createDate +
                ", createUid=" + createUid +
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

    public Integer getMerchId() {
        return merchId;
    }

    public void setMerchId(Integer merchId) {
        this.merchId = merchId;
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getStoreArea() {
        return storeArea;
    }

    public void setStoreArea(String storeArea) {
        this.storeArea = storeArea;
    }

    public String getSourceArea() {
        return sourceArea;
    }

    public void setSourceArea(String sourceArea) {
        this.sourceArea = sourceArea;
    }

    public Timestamp getSellOnDate() {
        return sellOnDate;
    }

    public void setSellOnDate(Timestamp sellOnDate) {
        this.sellOnDate = sellOnDate;
    }

    public Timestamp getSellOffDate() {
        return sellOffDate;
    }

    public void setSellOffDate(Timestamp sellOffDate) {
        this.sellOffDate = sellOffDate;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getRecommendPrice() {
        return recommendPrice;
    }

    public void setRecommendPrice(BigDecimal recommendPrice) {
        this.recommendPrice = recommendPrice;
    }

    public String getMasterImg() {
        return masterImg;
    }

    public void setMasterImg(String masterImg) {
        this.masterImg = masterImg;
    }

    public String getPreviewImgs() {
        return previewImgs;
    }

    public void setPreviewImgs(String previewImgs) {
        this.previewImgs = previewImgs;
    }

    public String getDetailImgs() {
        return detailImgs;
    }

    public void setDetailImgs(String detailImgs) {
        this.detailImgs = detailImgs;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public boolean getDestory() {
        return destory;
    }

    public void setDestory(boolean destory) {
        this.destory = destory;
    }

    public Integer getDestoryUid() {
        return destoryUid;
    }

    public void setDestoryUid(Integer destoryUid) {
        this.destoryUid = destoryUid;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Integer createUid) {
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
}

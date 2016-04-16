package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.validation.Constraints;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A entity for commodity brands.
 * <p>
 * <p>
 *
 * @author Sunny Wu
 */
public class Brands implements Serializable {

    /**
     * brand id.
     */
    private Long brandId;

    /**
     * brand  description.
     */
    private String brandDesc;

    /**
     * logo url.
     */
    private String logo;

    /**
     * brand create date.
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDate;

    /**
     * brand  name.
     */
    @Constraints.Required
    private String brandNm;

    //分页,每页多少条
    private Integer pageSize;

    //分页,从第几条开始
    private Integer offset;

    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    public Brands() {
    }

    public Brands(Long brandId, String brandDesc, String logo, Timestamp createDate, String brandNm, Integer pageSize, Integer offset, String sort, String order) {
        this.brandId = brandId;
        this.brandDesc = brandDesc;
        this.logo = logo;
        this.createDate = createDate;
        this.brandNm = brandNm;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }

    @Override
    public String toString() {
        return "Brands{" +
                "brandId=" + brandId +
                ", brandDesc='" + brandDesc + '\'' +
                ", logo='" + logo + '\'' +
                ", createDate=" + createDate +
                ", brandNm='" + brandNm + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getBrandNm() {
        return brandNm;
    }

    public void setBrandNm(String brandNm) {
        this.brandNm = brandNm;
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

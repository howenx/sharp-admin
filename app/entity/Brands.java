package entity;

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
    private Timestamp createDate;

    /**
     * brand  name.
     */
    private String brandNm;

    public Brands() {
    }

    public Brands(Long brandId, String brandDesc, String logo, Timestamp createDate, String brandNm) {
        this.brandId = brandId;
        this.brandDesc = brandDesc;
        this.logo = logo;
        this.createDate = createDate;
        this.brandNm = brandNm;
    }

    @Override
    public String toString() {
        return "Brands{" +
                "brandId=" + brandId +
                ", brandDesc='" + brandDesc + '\'' +
                ", logo='" + logo + '\'' +
                ", createDate=" + createDate +
                ", brandNm='" + brandNm + '\'' +
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
}

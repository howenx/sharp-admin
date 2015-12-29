package entity.erp;

import java.io.Serializable;

/**
 * Created by Sunny Wu 15/12/25.
 * ERP 品牌
 */
public class Brand implements Serializable {

    private Integer BrandId;    //品牌Id
    private String BrandName;   //品牌名称

    public Brand() {
    }

    public Integer getBrandId() {
        return BrandId;
    }

    public void setBrandId(Integer BrandId) {
        this.BrandId = BrandId;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "BrandId=" + BrandId +
                ", BrandName='" + BrandName + '\'' +
                '}';
    }

    public Brand(Integer BrandId, String BrandName) {
        this.BrandId = BrandId;
        this.BrandName = BrandName;
    }
}

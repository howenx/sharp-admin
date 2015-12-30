package entity.erp;

import java.io.Serializable;

/**
 * Created by Sunny Wu 15/12/25.
 * ERP 品牌
 */
public class Brand implements Serializable {

    private Integer brandId;    //品牌Id
    private String brandName;   //品牌名称

    public Brand() {
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                '}';
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

    public Brand(Integer brandId, String brandName) {

        this.brandId = brandId;
        this.brandName = brandName;
    }
}

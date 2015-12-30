package entity.erp;

import java.io.Serializable;

/**
 * Created by Sunny Wu 15/12/25.
 */
public class SKU implements Serializable {

    private Integer skuId;      //SKUId
    private Integer itemId;
    private String skuCode;     //规格编码
    private String skuName;     //规格名称
    private String barCode;     //条形码
    private String property1;   //销售属性1
    private String property2;   //销售属性2
    private Double purchasePrice;//采购价格
    private Double salesPrice;  //销售价格
    private Double lowestPrice;  //最低售价
    private Double marketPrice;  //吊牌价格
    private Double size;         //体积(立方米)
    private Double weight;       //重量(千克)
    private String unit;
    private String supplierCode;
    private Integer proportion;
    private String userDefinedField1;
    private String userDefinedField2;
    private String userDefinedField3;
    private String userDefinedField4;
    private String userDefinedField5;

    public SKU() {
    }

    @Override
    public String toString() {
        return "SKU{" +
                "skuId=" + skuId +
                ", itemId=" + itemId +
                ", skuCode='" + skuCode + '\'' +
                ", skuName='" + skuName + '\'' +
                ", barCode='" + barCode + '\'' +
                ", property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", salesPrice=" + salesPrice +
                ", lowestPrice=" + lowestPrice +
                ", marketPrice=" + marketPrice +
                ", size=" + size +
                ", weight=" + weight +
                ", unit='" + unit + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", proportion=" + proportion +
                ", userDefinedField1='" + userDefinedField1 + '\'' +
                ", userDefinedField2='" + userDefinedField2 + '\'' +
                ", userDefinedField3='" + userDefinedField3 + '\'' +
                ", userDefinedField4='" + userDefinedField4 + '\'' +
                ", userDefinedField5='" + userDefinedField5 + '\'' +
                '}';
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }

    public String getUserDefinedField1() {
        return userDefinedField1;
    }

    public void setUserDefinedField1(String userDefinedField1) {
        this.userDefinedField1 = userDefinedField1;
    }

    public String getUserDefinedField2() {
        return userDefinedField2;
    }

    public void setUserDefinedField2(String userDefinedField2) {
        this.userDefinedField2 = userDefinedField2;
    }

    public String getUserDefinedField3() {
        return userDefinedField3;
    }

    public void setUserDefinedField3(String userDefinedField3) {
        this.userDefinedField3 = userDefinedField3;
    }

    public String getUserDefinedField4() {
        return userDefinedField4;
    }

    public void setUserDefinedField4(String userDefinedField4) {
        this.userDefinedField4 = userDefinedField4;
    }

    public String getUserDefinedField5() {
        return userDefinedField5;
    }

    public void setUserDefinedField5(String userDefinedField5) {
        this.userDefinedField5 = userDefinedField5;
    }

    public SKU(Integer skuId, Integer itemId, String skuCode, String skuName, String barCode, String property1, String property2, Double purchasePrice, Double salesPrice, Double lowestPrice, Double marketPrice, Double size, Double weight, String unit, String supplierCode, Integer proportion, String userDefinedField1, String userDefinedField2, String userDefinedField3, String userDefinedField4, String userDefinedField5) {

        this.skuId = skuId;
        this.itemId = itemId;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.barCode = barCode;
        this.property1 = property1;
        this.property2 = property2;
        this.purchasePrice = purchasePrice;
        this.salesPrice = salesPrice;
        this.lowestPrice = lowestPrice;
        this.marketPrice = marketPrice;
        this.size = size;
        this.weight = weight;
        this.unit = unit;
        this.supplierCode = supplierCode;
        this.proportion = proportion;
        this.userDefinedField1 = userDefinedField1;
        this.userDefinedField2 = userDefinedField2;
        this.userDefinedField3 = userDefinedField3;
        this.userDefinedField4 = userDefinedField4;
        this.userDefinedField5 = userDefinedField5;
    }
}

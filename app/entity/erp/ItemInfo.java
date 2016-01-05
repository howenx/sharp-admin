package entity.erp;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Sunny Wu 15/12/25.
 * ERP 商品
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemInfo implements Serializable{

    private Integer itemId;     //商品Id
    private String itemCode;    //货号
    private String itemName;    //商品名称
    private String pictureUrl;  //主图url
    private String barCode;     //条形码
    private Integer catCode;     //商品类目
    private String catName;     //商品类目名称
    private String catName1;    //一级类目名称
    private String catName2;    //二级类目名称
    private String catPath;     //类目路径
    private Double purchasePrice;//采购价格
    private Double salesPrice;  //销售价格
    private Double lowestPrice; //最低售价
    private Double marketPrice; //吊牌价格
    private String unit;        //单位
    private String invoiceName; //开票名称
    private Double size;        //体积(立方米)
    private Double weight;      //重量(千克)
    private Boolean isAgent;     //是否代销
    private Boolean isVirtual;   //是否虚拟商品
    private Boolean isStopProducte;//是否停产
    private Boolean isSerialNumber; //是否序列号管理
    private String memo;           //备注
    private Timestamp createdTime;  //创建日期
    private Timestamp updateTime;   //最后修改日期
    private String userSign;        //修改人
    private Brand brand;            //品牌
    private Integer brandId;     //品牌
    private Integer proportion;
    private Integer status;         //状态
    private Integer itemStatusEnum;
    private Integer shelfLife;
    private String purchaseUnit;
    private Supplier supplier;      //供应商
    private Integer supplierId;     //供应商Id
    private Integer supplierCode;   //供应商代码
    private List<SKU> lines;        //商品库存
    private String property1;      //自定义属性1
    private String property2;      //自定义属性2
    private String property3;      //自定义属性3
    private String property4;      //自定义属性4
    private String property5;      //自定义属性5
    private String property6;      //自定义属性6
    private String property7;      //自定义属性7
    private String property8;      //自定义属性8
    private String property9;      //自定义属性9
    private String property10;      //自定义属性10
    private String property11;      //自定义属性11
    private String property12;      //自定义属性12

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp startTime;    //商品修改开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp endTime;      //商品修改结束时间


    public ItemInfo() {
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "itemId=" + itemId +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", barCode='" + barCode + '\'' +
                ", catCode=" + catCode +
                ", catName='" + catName + '\'' +
                ", catName1='" + catName1 + '\'' +
                ", catName2='" + catName2 + '\'' +
                ", catPath='" + catPath + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", salesPrice=" + salesPrice +
                ", lowestPrice=" + lowestPrice +
                ", marketPrice=" + marketPrice +
                ", unit='" + unit + '\'' +
                ", invoiceName='" + invoiceName + '\'' +
                ", size=" + size +
                ", weight=" + weight +
                ", isAgent=" + isAgent +
                ", isVirtual=" + isVirtual +
                ", isStopProducte=" + isStopProducte +
                ", isSerialNumber=" + isSerialNumber +
                ", memo='" + memo + '\'' +
                ", createdTime=" + createdTime +
                ", updateTime=" + updateTime +
                ", userSign='" + userSign + '\'' +
                ", brand=" + brand +
                ", brandId=" + brandId +
                ", proportion=" + proportion +
                ", status=" + status +
                ", itemStatusEnum=" + itemStatusEnum +
                ", shelfLife=" + shelfLife +
                ", purchaseUnit='" + purchaseUnit + '\'' +
                ", supplier=" + supplier +
                ", supplierId=" + supplierId +
                ", supplierCode=" + supplierCode +
                ", lines=" + lines +
                ", property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                ", property3='" + property3 + '\'' +
                ", property4='" + property4 + '\'' +
                ", property5='" + property5 + '\'' +
                ", property6='" + property6 + '\'' +
                ", property7='" + property7 + '\'' +
                ", property8='" + property8 + '\'' +
                ", property9='" + property9 + '\'' +
                ", property10='" + property10 + '\'' +
                ", property11='" + property11 + '\'' +
                ", property12='" + property12 + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getCatCode() {
        return catCode;
    }

    public void setCatCode(Integer catCode) {
        this.catCode = catCode;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatName1() {
        return catName1;
    }

    public void setCatName1(String catName1) {
        this.catName1 = catName1;
    }

    public String getCatName2() {
        return catName2;
    }

    public void setCatName2(String catName2) {
        this.catName2 = catName2;
    }

    public String getCatPath() {
        return catPath;
    }

    public void setCatPath(String catPath) {
        this.catPath = catPath;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
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

    public Boolean getAgent() {
        return isAgent;
    }

    public void setAgent(Boolean agent) {
        isAgent = agent;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public Boolean getStopProducte() {
        return isStopProducte;
    }

    public void setStopProducte(Boolean stopProducte) {
        isStopProducte = stopProducte;
    }

    public Boolean getSerialNumber() {
        return isSerialNumber;
    }

    public void setSerialNumber(Boolean serialNumber) {
        isSerialNumber = serialNumber;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getItemStatusEnum() {
        return itemStatusEnum;
    }

    public void setItemStatusEnum(Integer itemStatusEnum) {
        this.itemStatusEnum = itemStatusEnum;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(Integer supplierCode) {
        this.supplierCode = supplierCode;
    }

    public List<SKU> getLines() {
        return lines;
    }

    public void setLines(List<SKU> lines) {
        this.lines = lines;
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

    public String getProperty3() {
        return property3;
    }

    public void setProperty3(String property3) {
        this.property3 = property3;
    }

    public String getProperty4() {
        return property4;
    }

    public void setProperty4(String property4) {
        this.property4 = property4;
    }

    public String getProperty5() {
        return property5;
    }

    public void setProperty5(String property5) {
        this.property5 = property5;
    }

    public String getProperty6() {
        return property6;
    }

    public void setProperty6(String property6) {
        this.property6 = property6;
    }

    public String getProperty7() {
        return property7;
    }

    public void setProperty7(String property7) {
        this.property7 = property7;
    }

    public String getProperty8() {
        return property8;
    }

    public void setProperty8(String property8) {
        this.property8 = property8;
    }

    public String getProperty9() {
        return property9;
    }

    public void setProperty9(String property9) {
        this.property9 = property9;
    }

    public String getProperty10() {
        return property10;
    }

    public void setProperty10(String property10) {
        this.property10 = property10;
    }

    public String getProperty11() {
        return property11;
    }

    public void setProperty11(String property11) {
        this.property11 = property11;
    }

    public String getProperty12() {
        return property12;
    }

    public void setProperty12(String property12) {
        this.property12 = property12;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public ItemInfo(Integer itemId, String itemCode, String itemName, String pictureUrl, String barCode, Integer catCode, String catName, String catName1, String catName2, String catPath, Double purchasePrice, Double salesPrice, Double lowestPrice, Double marketPrice, String unit, String invoiceName, Double size, Double weight, Boolean isAgent, Boolean isVirtual, Boolean isStopProducte, Boolean isSerialNumber, String memo, Timestamp createdTime, Timestamp updateTime, String userSign, Brand brand, Integer brandId, Integer proportion, Integer status, Integer itemStatusEnum, Integer shelfLife, String purchaseUnit, Supplier supplier, Integer supplierId, Integer supplierCode, List<SKU> lines, String property1, String property2, String property3, String property4, String property5, String property6, String property7, String property8, String property9, String property10, String property11, String property12, Timestamp startTime, Timestamp endTime) {

        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.pictureUrl = pictureUrl;
        this.barCode = barCode;
        this.catCode = catCode;
        this.catName = catName;
        this.catName1 = catName1;
        this.catName2 = catName2;
        this.catPath = catPath;
        this.purchasePrice = purchasePrice;
        this.salesPrice = salesPrice;
        this.lowestPrice = lowestPrice;
        this.marketPrice = marketPrice;
        this.unit = unit;
        this.invoiceName = invoiceName;
        this.size = size;
        this.weight = weight;
        this.isAgent = isAgent;
        this.isVirtual = isVirtual;
        this.isStopProducte = isStopProducte;
        this.isSerialNumber = isSerialNumber;
        this.memo = memo;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.userSign = userSign;
        this.brand = brand;
        this.brandId = brandId;
        this.proportion = proportion;
        this.status = status;
        this.itemStatusEnum = itemStatusEnum;
        this.shelfLife = shelfLife;
        this.purchaseUnit = purchaseUnit;
        this.supplier = supplier;
        this.supplierId = supplierId;
        this.supplierCode = supplierCode;
        this.lines = lines;
        this.property1 = property1;
        this.property2 = property2;
        this.property3 = property3;
        this.property4 = property4;
        this.property5 = property5;
        this.property6 = property6;
        this.property7 = property7;
        this.property8 = property8;
        this.property9 = property9;
        this.property10 = property10;
        this.property11 = property11;
        this.property12 = property12;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}






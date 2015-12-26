package entity.erp;

/**
 * Created by Sunny Wu 15/12/25.
 */
public class SKU {

    private Integer SkuId;      //SKUId
    private Integer ItemId;
    private String SkuCode;     //规格编码
    private String SkuName;     //规格名称
    private String BarCode;     //条形码
    private String property1;   //销售属性1
    private String Property2;   //销售属性2
    private Double PurchasePrice;//采购价格
    private Double SalesPrice;  //销售价格
    private Double LowestPrice;  //最低售价
    private Double MarketPrice;  //吊牌价格
    private Double Size;         //体积(立方米)
    private Double Weight;       //重量(千克)
    private String Unit;
    private String SupplierCode;
    private Integer Proportion;
    private String UserDefinedField1;
    private String UserDefinedField2;
    private String UserDefinedField3;
    private String UserDefinedField4;
    private String UserDefinedField5;

    public SKU() {
    }

    public Integer getSkuId() {
        return SkuId;
    }

    public void setSkuId(Integer skuId) {
        SkuId = skuId;
    }

    public Integer getItemId() {
        return ItemId;
    }

    public void setItemId(Integer itemId) {
        ItemId = itemId;
    }

    public String getSkuCode() {
        return SkuCode;
    }

    public void setSkuCode(String skuCode) {
        SkuCode = skuCode;
    }

    public String getSkuName() {
        return SkuName;
    }

    public void setSkuName(String skuName) {
        SkuName = skuName;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return Property2;
    }

    public void setProperty2(String property2) {
        Property2 = property2;
    }

    public Double getPurchasePrice() {
        return PurchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        PurchasePrice = purchasePrice;
    }

    public Double getSalesPrice() {
        return SalesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        SalesPrice = salesPrice;
    }

    public Double getLowestPrice() {
        return LowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        LowestPrice = lowestPrice;
    }

    public Double getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        MarketPrice = marketPrice;
    }

    public Double getSize() {
        return Size;
    }

    public void setSize(Double size) {
        Size = size;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getSupplierCode() {
        return SupplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        SupplierCode = supplierCode;
    }

    public Integer getProportion() {
        return Proportion;
    }

    public void setProportion(Integer proportion) {
        Proportion = proportion;
    }

    public String getUserDefinedField1() {
        return UserDefinedField1;
    }

    public void setUserDefinedField1(String userDefinedField1) {
        UserDefinedField1 = userDefinedField1;
    }

    public String getUserDefinedField2() {
        return UserDefinedField2;
    }

    public void setUserDefinedField2(String userDefinedField2) {
        UserDefinedField2 = userDefinedField2;
    }

    public String getUserDefinedField3() {
        return UserDefinedField3;
    }

    public void setUserDefinedField3(String userDefinedField3) {
        UserDefinedField3 = userDefinedField3;
    }

    public String getUserDefinedField4() {
        return UserDefinedField4;
    }

    public void setUserDefinedField4(String userDefinedField4) {
        UserDefinedField4 = userDefinedField4;
    }

    public String getUserDefinedField5() {
        return UserDefinedField5;
    }

    public void setUserDefinedField5(String userDefinedField5) {
        UserDefinedField5 = userDefinedField5;
    }

    @Override
    public String toString() {
        return "SKU{" +
                "SkuId=" + SkuId +
                ", ItemId=" + ItemId +
                ", SkuCode='" + SkuCode + '\'' +
                ", SkuName='" + SkuName + '\'' +
                ", BarCode='" + BarCode + '\'' +
                ", property1='" + property1 + '\'' +
                ", Property2='" + Property2 + '\'' +
                ", PurchasePrice=" + PurchasePrice +
                ", SalesPrice=" + SalesPrice +
                ", LowestPrice=" + LowestPrice +
                ", MarketPrice=" + MarketPrice +
                ", Size=" + Size +

                ", Weight=" + Weight +
                ", Unit='" + Unit + '\'' +
                ", SupplierCode='" + SupplierCode + '\'' +
                ", Proportion=" + Proportion +
                ", UserDefinedField1='" + UserDefinedField1 + '\'' +
                ", UserDefinedField2='" + UserDefinedField2 + '\'' +
                ", UserDefinedField3='" + UserDefinedField3 + '\'' +
                ", UserDefinedField4='" + UserDefinedField4 + '\'' +
                ", UserDefinedField5='" + UserDefinedField5 + '\'' +
                '}';
    }

    public SKU(Integer skuId, Integer itemId, String skuCode, String skuName, String barCode, String property1, String property2, Double purchasePrice, Double salesPrice, Double lowestPrice, Double marketPrice, Double size, Double weight, String unit, String supplierCode, Integer proportion, String userDefinedField1, String userDefinedField2, String userDefinedField3, String userDefinedField4, String userDefinedField5) {
        SkuId = skuId;
        ItemId = itemId;
        SkuCode = skuCode;
        SkuName = skuName;
        BarCode = barCode;
        this.property1 = property1;
        Property2 = property2;
        PurchasePrice = purchasePrice;
        SalesPrice = salesPrice;
        LowestPrice = lowestPrice;
        MarketPrice = marketPrice;
        Size = size;
        Weight = weight;
        Unit = unit;
        SupplierCode = supplierCode;
        Proportion = proportion;
        UserDefinedField1 = userDefinedField1;
        UserDefinedField2 = userDefinedField2;
        UserDefinedField3 = userDefinedField3;
        UserDefinedField4 = userDefinedField4;
        UserDefinedField5 = userDefinedField5;
    }
}

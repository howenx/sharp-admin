package entity;

import java.sql.Timestamp;

/**
 * Created by Sunny Wu 15/12/22.
 * ERP 商品
 */
public interface ItemInfoAll {

      class ItemInfo {
        private Integer ItemId;     //商品Id
        private String ItemCode;    //货号
        private String ItemName;    //商品名称
        private String PictureUrl;  //主图url
        private String BarCode;     //条形码
        private Integer CatCode;     //商品类目
        private String CatName;     //商品类目名称
        private String CatName1;    //一级类目名称
        private String CatName2;    //二级类目名称
        private String CatPath;     //类目路径
        private Double PurchasePrice;//采购价格
        private Double SalesPrice;  //销售价格
        private Double LowestPrice; //最低售价
        private Double MarketPrice; //吊牌价格
        private String Unit;        //单位
        private String InvoiceName; //开票名称
        private Double Size;        //体积(立方米)
        private Double Weight;      //重量(千克)
        private Boolean IsAgent;     //是否代销
        private Boolean IsVirtual;   //是否虚拟商品
        private Boolean IsStopProducte;//是否停产
        private Boolean IsSerialNumber; //是否序列号管理
        private String Memo;           //备注
        private Timestamp CreatedTime;  //创建日期
        private Timestamp UpdateTime;   //最后修改日期
        private String UserSign;        //修改人
        private Brand Brand;            //品牌
        private Integer BrandId;     //品牌
        private Integer Proportion;
        private Integer Status;         //状态
        private Integer ItemStatusEnum;
        private Integer ShelfLife;
        private String PurchaseUnit;
        private Supplier Supplier;      //供应商
        private Integer SupplierId;     //供应商Id
        private Integer SupplierCode;   //供应商代码
        private SKU    Lines;           //商品库存
        private  String Property1;      //自定义属性1
        private  String Property2;      //自定义属性2
        private  String Property3;      //自定义属性3
        private  String Property4;      //自定义属性4
        private  String Property5;      //自定义属性5
        private  String Property6;      //自定义属性6
        private  String Property7;      //自定义属性7
        private  String Property8;      //自定义属性8
        private  String Property9;      //自定义属性9
        private  String Property10;      //自定义属性10
        private  String Property11;      //自定义属性11
        private  String Property12;      //自定义属性12

          public ItemInfo() {
          }

          public ItemInfo(Integer itemId, String itemCode, String itemName, String pictureUrl, String barCode, Integer catCode, String catName, String catName1, String catName2, String catPath, Double purchasePrice, Double salesPrice, Double lowestPrice, Double marketPrice, String unit, String invoiceName, Double size, Double weight, Boolean isAgent, Boolean isVirtual, Boolean isStopProducte, Boolean isSerialNumber, String memo, Timestamp createdTime, Timestamp updateTime, String userSign, ItemInfoAll.Brand brand, Integer brandId, Integer proportion, Integer status, Integer itemStatusEnum, Integer shelfLife, String purchaseUnit, ItemInfoAll.Supplier supplier, Integer supplierId, Integer supplierCode, SKU lines, String property1, String property2, String property3, String property4, String property5, String property6, String property7, String property8, String property9, String property10, String property11, String property12) {
              ItemId = itemId;
              ItemCode = itemCode;
              ItemName = itemName;
              PictureUrl = pictureUrl;
              BarCode = barCode;
              CatCode = catCode;
              CatName = catName;
              CatName1 = catName1;
              CatName2 = catName2;
              CatPath = catPath;
              PurchasePrice = purchasePrice;
              SalesPrice = salesPrice;
              LowestPrice = lowestPrice;
              MarketPrice = marketPrice;
              Unit = unit;
              InvoiceName = invoiceName;
              Size = size;
              Weight = weight;
              IsAgent = isAgent;
              IsVirtual = isVirtual;
              IsStopProducte = isStopProducte;
              IsSerialNumber = isSerialNumber;
              Memo = memo;
              CreatedTime = createdTime;
              UpdateTime = updateTime;
              UserSign = userSign;
              Brand = brand;
              BrandId = brandId;
              Proportion = proportion;
              Status = status;
              ItemStatusEnum = itemStatusEnum;
              ShelfLife = shelfLife;
              PurchaseUnit = purchaseUnit;
              Supplier = supplier;
              SupplierId = supplierId;
              SupplierCode = supplierCode;
              Lines = lines;
              Property1 = property1;
              Property2 = property2;
              Property3 = property3;
              Property4 = property4;
              Property5 = property5;
              Property6 = property6;
              Property7 = property7;
              Property8 = property8;
              Property9 = property9;
              Property10 = property10;
              Property11 = property11;
              Property12 = property12;
          }

          @Override
          public String toString() {
              return "ItemInfo{" +
                      "ItemId=" + ItemId +
                      ", ItemCode='" + ItemCode + '\'' +
                      ", ItemName='" + ItemName + '\'' +
                      ", PictureUrl='" + PictureUrl + '\'' +
                      ", BarCode='" + BarCode + '\'' +
                      ", CatCode=" + CatCode +
                      ", CatName='" + CatName + '\'' +
                      ", CatName1='" + CatName1 + '\'' +
                      ", CatName2='" + CatName2 + '\'' +
                      ", CatPath='" + CatPath + '\'' +
                      ", PurchasePrice=" + PurchasePrice +
                      ", SalesPrice=" + SalesPrice +
                      ", LowestPrice=" + LowestPrice +
                      ", MarketPrice=" + MarketPrice +
                      ", Unit='" + Unit + '\'' +
                      ", InvoiceName='" + InvoiceName + '\'' +
                      ", Size=" + Size +
                      ", Weight=" + Weight +
                      ", IsAgent=" + IsAgent +
                      ", IsVirtual=" + IsVirtual +
                      ", IsStopProducte=" + IsStopProducte +
                      ", IsSerialNumber=" + IsSerialNumber +
                      ", Memo='" + Memo + '\'' +
                      ", CreatedTime=" + CreatedTime +
                      ", UpdateTime=" + UpdateTime +
                      ", UserSign='" + UserSign + '\'' +
                      ", Brand=" + Brand +
                      ", BrandId=" + BrandId +
                      ", Proportion=" + Proportion +
                      ", Status=" + Status +
                      ", ItemStatusEnum=" + ItemStatusEnum +
                      ", ShelfLife=" + ShelfLife +
                      ", PurchaseUnit='" + PurchaseUnit + '\'' +
                      ", Supplier=" + Supplier +
                      ", SupplierId=" + SupplierId +
                      ", SupplierCode=" + SupplierCode +
                      ", Lines=" + Lines +
                      ", Property1='" + Property1 + '\'' +
                      ", Property2='" + Property2 + '\'' +
                      ", Property3='" + Property3 + '\'' +
                      ", Property4='" + Property4 + '\'' +
                      ", Property5='" + Property5 + '\'' +
                      ", Property6='" + Property6 + '\'' +
                      ", Property7='" + Property7 + '\'' +
                      ", Property8='" + Property8 + '\'' +
                      ", Property9='" + Property9 + '\'' +
                      ", Property10='" + Property10 + '\'' +
                      ", Property11='" + Property11 + '\'' +
                      ", Property12='" + Property12 + '\'' +
                      '}';
          }

          public Integer getItemId() {
              return ItemId;
          }

          public void setItemId(Integer itemId) {
              ItemId = itemId;
          }

          public String getItemCode() {
              return ItemCode;
          }

          public void setItemCode(String itemCode) {
              ItemCode = itemCode;
          }

          public String getItemName() {
              return ItemName;
          }

          public void setItemName(String itemName) {
              ItemName = itemName;
          }

          public String getPictureUrl() {
              return PictureUrl;
          }

          public void setPictureUrl(String pictureUrl) {
              PictureUrl = pictureUrl;
          }

          public String getBarCode() {
              return BarCode;
          }

          public void setBarCode(String barCode) {
              BarCode = barCode;
          }

          public Integer getCatCode() {
              return CatCode;
          }

          public void setCatCode(Integer catCode) {
              CatCode = catCode;
          }

          public String getCatName() {
              return CatName;
          }

          public void setCatName(String catName) {
              CatName = catName;
          }

          public String getCatName1() {
              return CatName1;
          }

          public void setCatName1(String catName1) {
              CatName1 = catName1;
          }

          public String getCatName2() {
              return CatName2;
          }

          public void setCatName2(String catName2) {
              CatName2 = catName2;
          }

          public String getCatPath() {
              return CatPath;
          }

          public void setCatPath(String catPath) {
              CatPath = catPath;
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

          public String getUnit() {
              return Unit;
          }

          public void setUnit(String unit) {
              Unit = unit;
          }

          public String getInvoiceName() {
              return InvoiceName;
          }

          public void setInvoiceName(String invoiceName) {
              InvoiceName = invoiceName;
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

          public Boolean getAgent() {
              return IsAgent;
          }

          public void setAgent(Boolean agent) {
              IsAgent = agent;
          }

          public Boolean getVirtual() {
              return IsVirtual;
          }

          public void setVirtual(Boolean virtual) {
              IsVirtual = virtual;
          }

          public Boolean getStopProducte() {
              return IsStopProducte;
          }

          public void setStopProducte(Boolean stopProducte) {
              IsStopProducte = stopProducte;
          }

          public Boolean getSerialNumber() {
              return IsSerialNumber;
          }

          public void setSerialNumber(Boolean serialNumber) {
              IsSerialNumber = serialNumber;
          }

          public String getMemo() {
              return Memo;
          }

          public void setMemo(String memo) {
              Memo = memo;
          }

          public Timestamp getCreatedTime() {
              return CreatedTime;
          }

          public void setCreatedTime(Timestamp createdTime) {
              CreatedTime = createdTime;
          }

          public Timestamp getUpdateTime() {
              return UpdateTime;
          }

          public void setUpdateTime(Timestamp updateTime) {
              UpdateTime = updateTime;
          }

          public String getUserSign() {
              return UserSign;
          }

          public void setUserSign(String userSign) {
              UserSign = userSign;
          }

          public ItemInfoAll.Brand getBrand() {
              return Brand;
          }

          public void setBrand(ItemInfoAll.Brand brand) {
              Brand = brand;
          }

          public Integer getBrandId() {
              return BrandId;
          }

          public void setBrandId(Integer brandId) {
              BrandId = brandId;
          }

          public Integer getProportion() {
              return Proportion;
          }

          public void setProportion(Integer proportion) {
              Proportion = proportion;
          }

          public Integer getStatus() {
              return Status;
          }

          public void setStatus(Integer status) {
              Status = status;
          }

          public Integer getItemStatusEnum() {
              return ItemStatusEnum;
          }

          public void setItemStatusEnum(Integer itemStatusEnum) {
              ItemStatusEnum = itemStatusEnum;
          }

          public Integer getShelfLife() {
              return ShelfLife;
          }

          public void setShelfLife(Integer shelfLife) {
              ShelfLife = shelfLife;
          }

          public String getPurchaseUnit() {
              return PurchaseUnit;
          }

          public void setPurchaseUnit(String purchaseUnit) {
              PurchaseUnit = purchaseUnit;
          }

          public ItemInfoAll.Supplier getSupplier() {
              return Supplier;
          }

          public void setSupplier(ItemInfoAll.Supplier supplier) {
              Supplier = supplier;
          }

          public Integer getSupplierId() {
              return SupplierId;
          }

          public void setSupplierId(Integer supplierId) {
              SupplierId = supplierId;
          }

          public Integer getSupplierCode() {
              return SupplierCode;
          }

          public void setSupplierCode(Integer supplierCode) {
              SupplierCode = supplierCode;
          }

          public SKU getLines() {
              return Lines;
          }

          public void setLines(SKU lines) {
              Lines = lines;
          }

          public String getProperty1() {
              return Property1;
          }

          public void setProperty1(String property1) {
              Property1 = property1;
          }

          public String getProperty2() {
              return Property2;
          }

          public void setProperty2(String property2) {
              Property2 = property2;
          }

          public String getProperty3() {
              return Property3;
          }

          public void setProperty3(String property3) {
              Property3 = property3;
          }

          public String getProperty4() {
              return Property4;
          }

          public void setProperty4(String property4) {
              Property4 = property4;
          }

          public String getProperty5() {
              return Property5;
          }

          public void setProperty5(String property5) {
              Property5 = property5;
          }

          public String getProperty6() {
              return Property6;
          }

          public void setProperty6(String property6) {
              Property6 = property6;
          }

          public String getProperty7() {
              return Property7;
          }

          public void setProperty7(String property7) {
              Property7 = property7;
          }

          public String getProperty8() {
              return Property8;
          }

          public void setProperty8(String property8) {
              Property8 = property8;
          }

          public String getProperty9() {
              return Property9;
          }

          public void setProperty9(String property9) {
              Property9 = property9;
          }

          public String getProperty10() {
              return Property10;
          }

          public void setProperty10(String property10) {
              Property10 = property10;
          }

          public String getProperty11() {
              return Property11;
          }

          public void setProperty11(String property11) {
              Property11 = property11;
          }

          public String getProperty12() {
              return Property12;
          }

          public void setProperty12(String property12) {
              Property12 = property12;
          }
      }

    class Brand {
        private Integer BrandId;    //品牌Id
        private String BrandName;   //品牌名称

        public Brand() {
        }

        public Brand(Integer brandId, String brandName) {
            BrandId = brandId;
            BrandName = brandName;
        }

        @Override
        public String toString() {
            return "Brand{" +
                    "BrandId=" + BrandId +
                    ", BrandName='" + BrandName + '\'' +
                    '}';
        }

        public Integer getBrandId() {
            return BrandId;
        }

        public void setBrandId(Integer brandId) {
            BrandId = brandId;
        }

        public String getBrandName() {
            return BrandName;
        }

        public void setBrandName(String brandName) {
            BrandName = brandName;
        }
    }

    class Supplier {
        private Integer SupplierId; //供应商Id
        private String SupplierCode;//供应商代码
        private String SupplierName;//供应商名称
        private String Province;    //省份
        private String City;        //城市
        private String District;    //区
        private String Address;     //地址

        public Supplier() {
        }

        public Supplier(Integer supplierId, String supplierCode, String supplierName, String province, String city, String district, String address) {
            SupplierId = supplierId;
            SupplierCode = supplierCode;
            SupplierName = supplierName;
            Province = province;
            City = city;
            District = district;
            Address = address;
        }

        @Override
        public String toString() {
            return "Supplier{" +
                    "SupplierId=" + SupplierId +
                    ", SupplierCode='" + SupplierCode + '\'' +
                    ", SupplierName='" + SupplierName + '\'' +
                    ", Province='" + Province + '\'' +
                    ", City='" + City + '\'' +
                    ", District='" + District + '\'' +
                    ", Address='" + Address + '\'' +
                    '}';
        }

        public Integer getSupplierId() {
            return SupplierId;
        }

        public void setSupplierId(Integer supplierId) {
            SupplierId = supplierId;
        }

        public String getSupplierCode() {
            return SupplierCode;
        }

        public void setSupplierCode(String supplierCode) {
            SupplierCode = supplierCode;
        }

        public String getSupplierName() {
            return SupplierName;
        }

        public void setSupplierName(String supplierName) {
            SupplierName = supplierName;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(String province) {
            Province = province;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getDistrict() {
            return District;
        }

        public void setDistrict(String district) {
            District = district;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }
    }

    class SKU {
        private Integer SkuId;      //SKUID
        private Integer ItemId;
        private String SkuCode;     //规格编码
        private String SkuName;     //规格名称
        private String BarCode;     //条形码
        private String property1;   //销售属性1
        private String Property2;   //销售属性2
        private Double PurchasePrice;//采购价格
        private Double SalsesPrice;  //销售价格
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

        public SKU(Integer skuId, Integer itemId, String skuCode, String skuName, String barCode, String property1, String property2, Double purchasePrice, Double salsesPrice, Double lowestPrice, Double marketPrice, Double size, Double weight, String unit, String supplierCode, Integer proportion, String userDefinedField1, String userDefinedField2, String userDefinedField3, String userDefinedField4, String userDefinedField5) {
            SkuId = skuId;
            ItemId = itemId;
            SkuCode = skuCode;
            SkuName = skuName;
            BarCode = barCode;
            this.property1 = property1;
            Property2 = property2;
            PurchasePrice = purchasePrice;
            SalsesPrice = salsesPrice;
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
                    ", SalsesPrice=" + SalsesPrice +
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

        public Double getSalsesPrice() {
            return SalsesPrice;
        }

        public void setSalsesPrice(Double salsesPrice) {
            SalsesPrice = salsesPrice;
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
    }


}

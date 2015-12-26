package entity.erp;

/**
 * Created by Sunny Wu 15/12/25.
 */
public class Supplier {

    private Integer SupplierId; //供应商Id
    private String SupplierCode;//供应商代码
    private String SupplierName;//供应商名称
    private String Province;    //省份
    private String City;        //城市
    private String District;    //区
    private String Address;     //地址

    public Supplier() {
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

    public Supplier(Integer supplierId, String supplierCode, String supplierName, String province, String city, String district, String address) {
        SupplierId = supplierId;
        SupplierCode = supplierCode;
        SupplierName = supplierName;
        Province = province;
        City = city;
        District = district;
        Address = address;
    }
}

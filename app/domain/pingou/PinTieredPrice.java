package domain.pingou;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.validation.Constraints;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by tiffany on 16/1/28.
 */
public class PinTieredPrice implements Serializable {

    private Long id;                            //阶梯价格ID
    private String masterCouponClass;           //团长返券类别
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String masterCouponStartAt;      //团长优惠券开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String masterCouponEndAt;        //团长优惠券结束时间
    private Integer masterCouponQuota;          //团长优惠券限额
    private Integer memberCoupon;               //团员优惠券额度
    private String memberCouponClass;           //团员返券类别
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String memberCouponStartAt;      //团员优惠券开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String memberCouponEndAt;        //团员优惠券结束时间
    private Integer memberCouponQuota;          //团员优惠券限额
    private Long pinId;                          //pin库存ID
    @Constraints.Required
    private int peopleNum;                       //人数
    @Constraints.Required
    private BigDecimal price;                    //价格
    private BigDecimal masterMinPrice;           //团长减价
    private BigDecimal memberMinPrice;           //团员减价
    private Integer masterCoupon;                //团长返券额度

    public PinTieredPrice() {
    }

    public PinTieredPrice(Long id, String masterCouponClass, String masterCouponStartAt, String masterCouponEndAt, Integer masterCouponQuota, Integer memberCoupon, String memberCouponClass, String memberCouponStartAt, String memberCouponEndAt, Integer memberCouponQuota, Long pinId, int peopleNum, BigDecimal price, BigDecimal masterMinPrice, BigDecimal memberMinPrice, Integer masterCoupon) {
        this.id = id;
        this.masterCouponClass = masterCouponClass;
        this.masterCouponStartAt = masterCouponStartAt;
        this.masterCouponEndAt = masterCouponEndAt;
        this.masterCouponQuota = masterCouponQuota;
        this.memberCoupon = memberCoupon;
        this.memberCouponClass = memberCouponClass;
        this.memberCouponStartAt = memberCouponStartAt;
        this.memberCouponEndAt = memberCouponEndAt;
        this.memberCouponQuota = memberCouponQuota;
        this.pinId = pinId;
        this.peopleNum = peopleNum;
        this.price = price;
        this.masterMinPrice = masterMinPrice;
        this.memberMinPrice = memberMinPrice;
        this.masterCoupon = masterCoupon;
    }

    @Override
    public String toString() {
        return "PinTieredPrice{" +
                "id=" + id +
                ", masterCouponClass='" + masterCouponClass + '\'' +
                ", masterCouponStartAt='" + masterCouponStartAt + '\'' +
                ", masterCouponEndAt='" + masterCouponEndAt + '\'' +
                ", masterCouponQuota=" + masterCouponQuota +
                ", memberCoupon=" + memberCoupon +
                ", memberCouponClass='" + memberCouponClass + '\'' +
                ", memberCouponStartAt='" + memberCouponStartAt + '\'' +
                ", memberCouponEndAt='" + memberCouponEndAt + '\'' +
                ", memberCouponQuota=" + memberCouponQuota +
                ", pinId=" + pinId +
                ", peopleNum=" + peopleNum +
                ", price=" + price +
                ", masterMinPrice=" + masterMinPrice +
                ", memberMinPrice=" + memberMinPrice +
                ", masterCoupon=" + masterCoupon +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMasterCouponClass() {
        return masterCouponClass;
    }

    public void setMasterCouponClass(String masterCouponClass) {
        this.masterCouponClass = masterCouponClass;
    }

    public String getMasterCouponStartAt() {
        return masterCouponStartAt;
    }

    public void setMasterCouponStartAt(String masterCouponStartAt) {
        this.masterCouponStartAt = masterCouponStartAt;
    }

    public String getMasterCouponEndAt() {
        return masterCouponEndAt;
    }

    public void setMasterCouponEndAt(String masterCouponEndAt) {
        this.masterCouponEndAt = masterCouponEndAt;
    }

    public Integer getMasterCouponQuota() {
        return masterCouponQuota;
    }

    public void setMasterCouponQuota(Integer masterCouponQuota) {
        this.masterCouponQuota = masterCouponQuota;
    }

    public Integer getMemberCoupon() {
        return memberCoupon;
    }

    public void setMemberCoupon(Integer memberCoupon) {
        this.memberCoupon = memberCoupon;
    }

    public String getMemberCouponClass() {
        return memberCouponClass;
    }

    public void setMemberCouponClass(String memberCouponClass) {
        this.memberCouponClass = memberCouponClass;
    }

    public String getMemberCouponStartAt() {
        return memberCouponStartAt;
    }

    public void setMemberCouponStartAt(String memberCouponStartAt) {
        this.memberCouponStartAt = memberCouponStartAt;
    }

    public String getMemberCouponEndAt() {
        return memberCouponEndAt;
    }

    public void setMemberCouponEndAt(String memberCouponEndAt) {
        this.memberCouponEndAt = memberCouponEndAt;
    }

    public Integer getMemberCouponQuota() {
        return memberCouponQuota;
    }

    public void setMemberCouponQuota(Integer memberCouponQuota) {
        this.memberCouponQuota = memberCouponQuota;
    }

    public Long getPinId() {
        return pinId;
    }

    public void setPinId(Long pinId) {
        this.pinId = pinId;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMasterMinPrice() {
        return masterMinPrice;
    }

    public void setMasterMinPrice(BigDecimal masterMinPrice) {
        this.masterMinPrice = masterMinPrice;
    }

    public BigDecimal getMemberMinPrice() {
        return memberMinPrice;
    }

    public void setMemberMinPrice(BigDecimal memberMinPrice) {
        this.memberMinPrice = memberMinPrice;
    }

    public Integer getMasterCoupon() {
        return masterCoupon;
    }

    public void setMasterCoupon(Integer masterCoupon) {
        this.masterCoupon = masterCoupon;
    }
}

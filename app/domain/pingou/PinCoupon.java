package domain.pingou;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * Created by tiffany on 16/1/20.
 */
public class PinCoupon implements Serializable {

    private Long id;                        // 主键ID
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String memberCouponEndAt;    //团员优惠券结束时间
    private Integer memberCouponQuota;          //团员优惠券限额
    private Integer masterCoupon;               //团长返券额度
    private String masterCouponClass;       //团长返券类别
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String masterCouponStartAt;  //团长优惠券开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String masterCouponEndAt;    //团长优惠券结束时间
    private Integer masterCouponQuota;          //团长优惠券限额
    private Integer memberCoupon;               //团员返券额度
    private String memberCouponClass;       //团员返券类别
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String memberCouponStartAt;  //团员优惠券开始时间
    private Long pinActiveId;                     //拼购活动ID

    public PinCoupon() {
    }

    public PinCoupon(Long id, String memberCouponEndAt, Integer memberCouponQuota, Integer masterCoupon, String masterCouponClass, String masterCouponStartAt, String masterCouponEndAt, Integer masterCouponQuota, Integer memberCoupon, String memberCouponClass, String memberCouponStartAt, Long pinActiveId) {
        this.id = id;
        this.memberCouponEndAt = memberCouponEndAt;
        this.memberCouponQuota = memberCouponQuota;
        this.masterCoupon = masterCoupon;
        this.masterCouponClass = masterCouponClass;
        this.masterCouponStartAt = masterCouponStartAt;
        this.masterCouponEndAt = masterCouponEndAt;
        this.masterCouponQuota = masterCouponQuota;
        this.memberCoupon = memberCoupon;
        this.memberCouponClass = memberCouponClass;
        this.memberCouponStartAt = memberCouponStartAt;
        this.pinActiveId = pinActiveId;
    }

    @Override
    public String toString() {
        return "PinCoupon{" +
                "id=" + id +
                ", memberCouponEndAt='" + memberCouponEndAt + '\'' +
                ", memberCouponQuota=" + memberCouponQuota +
                ", masterCoupon=" + masterCoupon +
                ", masterCouponClass='" + masterCouponClass + '\'' +
                ", masterCouponStartAt='" + masterCouponStartAt + '\'' +
                ", masterCouponEndAt='" + masterCouponEndAt + '\'' +
                ", masterCouponQuota=" + masterCouponQuota +
                ", memberCoupon=" + memberCoupon +
                ", memberCouponClass='" + memberCouponClass + '\'' +
                ", memberCouponStartAt='" + memberCouponStartAt + '\'' +
                ", pinActiveId=" + pinActiveId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getMasterCoupon() {
        return masterCoupon;
    }

    public void setMasterCoupon(Integer masterCoupon) {
        this.masterCoupon = masterCoupon;
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

    public Long getPinActiveId() {
        return pinActiveId;
    }

    public void setPinActiveId(Long pinActiveId) {
        this.pinActiveId = pinActiveId;
    }
}

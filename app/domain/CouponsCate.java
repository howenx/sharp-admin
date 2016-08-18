package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.validation.Constraints;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠券类别
 * Created by Sunny Wu on 16/6/27.
 * kakao china.
 */
public class CouponsCate implements Serializable {

    private Long coupCateId;        //主键id
    @Constraints.Required
    private String coupCateNm;  //优惠券类别名称
    @Constraints.Required
    private Integer couponType;   //优惠券类别
    @Constraints.Required
    private BigDecimal limitQuota;//限制满多少才能使用
    @Constraints.Required
    private BigDecimal denomination;//面值
    @Constraints.Required
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String startAt;      //可使用开始时间
    @Constraints.Required
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String endAt;        //可使用截止时间

    public CouponsCate() {
    }

    @Override
    public String toString() {
        return "CouponsCate{" +
                "coupCateId=" + coupCateId +
                ", coupCateNm='" + coupCateNm + '\'' +
                ", couponType=" + couponType +
                ", limitQuota=" + limitQuota +
                ", denomination=" + denomination +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                '}';
    }

    public Long getCoupCateId() {
        return coupCateId;
    }

    public void setCoupCateId(Long coupCateId) {
        this.coupCateId = coupCateId;
    }

    public String getCoupCateNm() {
        return coupCateNm;
    }

    public void setCoupCateNm(String coupCateNm) {
        this.coupCateNm = coupCateNm;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public BigDecimal getLimitQuota() {
        return limitQuota;
    }

    public void setLimitQuota(BigDecimal limitQuota) {
        this.limitQuota = limitQuota;
    }

    public BigDecimal getDenomination() {
        return denomination;
    }

    public void setDenomination(BigDecimal denomination) {
        this.denomination = denomination;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public CouponsCate(Long coupCateId, String coupCateNm, Integer couponType, BigDecimal limitQuota, BigDecimal denomination, String startAt, String endAt) {

        this.coupCateId = coupCateId;
        this.coupCateNm = coupCateNm;
        this.couponType = couponType;
        this.limitQuota = limitQuota;
        this.denomination = denomination;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}



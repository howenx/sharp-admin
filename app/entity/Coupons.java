package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class Coupons implements Serializable {

    private String coupId;      //主键优惠券id
    private BigDecimal limitQuota;//限制满多少才能使用
    private String CateNm;        //类别名称 (1全场通用券，2新人优惠券，3饰品类商品适用券，4化妆品类商品适用券，5指定商品适用券，6服饰类商品适用券，7免邮券)
    private Long userId;          //用户id
    private Long cateId;          //类别id (("化妆品", 153)，("配饰", 172)，("服饰", 165)，("全场通用券","555")，("邮费", 999))
    private BigDecimal denomination;//面值
    private Timestamp startAt;      //可使用开始时间
    private Timestamp endAt;        //可使用截止时间
    private String state;           //优惠券状态 ("N"未使用，"Y"已经使用过，“S"自动失效，“F”免邮专用)
    private Long orderId;           //订单id
    private Timestamp useAt;        //使用时间

    public Coupons() {
    }

    public String getCoupId() {
        return coupId;
    }

    public void setCoupId(String coupId) {
        this.coupId = coupId;
    }

    public BigDecimal getLimitQuota() {
        return limitQuota;
    }

    public void setLimitQuota(BigDecimal limitQuota) {
        this.limitQuota = limitQuota;
    }

    public String getCateNm() {
        return CateNm;
    }

    public void setCateNm(String cateNm) {
        CateNm = cateNm;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public BigDecimal getDenomination() {
        return denomination;
    }

    public void setDenomination(BigDecimal denomination) {
        this.denomination = denomination;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Timestamp getUseAt() {
        return useAt;
    }

    public void setUseAt(Timestamp useAt) {
        this.useAt = useAt;
    }

    @Override
    public String toString() {
        return "CouponsService{" +
                "coupId='" + coupId + '\'' +
                ", limitQuota=" + limitQuota +
                ", CateNm='" + CateNm + '\'' +
                ", userId=" + userId +
                ", cateId=" + cateId +
                ", denomination=" + denomination +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", state='" + state + '\'' +
                ", orderId=" + orderId +
                ", useAt=" + useAt +
                '}';
    }

    public Coupons(String coupId, BigDecimal limitQuota, String cateNm, Long userId, Long cateId, BigDecimal denomination, Timestamp startAt, Timestamp endAt, String state, Long orderId, Timestamp useAt) {
        this.coupId = coupId;
        this.limitQuota = limitQuota;
        CateNm = cateNm;
        this.userId = userId;
        this.cateId = cateId;
        this.denomination = denomination;
        this.startAt = startAt;
        this.endAt = endAt;
        this.state = state;
        this.orderId = orderId;
        this.useAt = useAt;
    }
}

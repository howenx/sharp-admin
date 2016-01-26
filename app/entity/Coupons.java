package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Random;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class Coupons implements Serializable {

    private String coupId;      //主键优惠券id
    private BigDecimal limitQuota;//限制满多少才能使用
    private String CateNm;        //类别名称
    private Long userId;          //用户id
    private Long cateId;          //类别id (153 化妆品类商品适用券，172 配饰类商品适用券, 165 服饰类商品适用券, 555 全场通用券, 777 新人优惠券, 211 指定商品适用券, 999 免邮券)
    private BigDecimal denomination;//面值
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp startAt;      //可使用开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp endAt;        //可使用截止时间
    private String state;           //优惠券状态 ("N"未使用，"Y"已经使用过，“S"自动失效，“F”免邮专用)
    private Long orderId;           //订单id
    private Timestamp useAt;        //使用时间

    public Coupons() {
    }

    //生成优惠券码
    private static final String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String intChar = "0123456789";

    public static String GetCode(Long code,int length){
        Random r = new Random();
        String pass = "";
        while (pass.length () != length){
            if(pass.length()==0){
                int spot = r.nextInt (25);
                pass += uCase.charAt(spot);
            }
            int rPick = r.nextInt(4);
            if (rPick == 1 || rPick == 0) {
                int spot = r.nextInt (25);
                pass += uCase.charAt(spot);
            } else if (rPick == 2 || rPick ==3) {
                int spot = r.nextInt (9);
                pass += intChar.charAt (spot);
            }
        }
        return code+pass;
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

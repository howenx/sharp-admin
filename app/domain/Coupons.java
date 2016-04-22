package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.validation.Constraints;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Random;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class Coupons implements Serializable {

    private String coupId;      //主键优惠券id
    @Constraints.Required
    private BigDecimal limitQuota;//限制满多少才能使用
    private String cateNm;        //类别名称
    private Long userId;          //用户id
    @Constraints.Required
    private Long cateId;          //类别id (153 化妆品类商品适用券，172 配饰类商品适用券, 165 服饰类商品适用券, 555 全场通用券, 777 新人优惠券, 211 指定商品适用券, 999 免邮券)
    @Constraints.Required
    private BigDecimal denomination;//面值
    @Constraints.Required
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String startAt;      //可使用开始时间
    @Constraints.Required
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String endAt;        //可使用截止时间
    private String state;           //优惠券状态 ("N"未使用，"Y"已经使用过，“S"自动失效，“F”免邮专用)
    private Long orderId;           //订单id
    private Timestamp useAt;        //使用时间

    //分页,每页多少条
    private Integer pageSize;
    //分页,从第几条开始
    private Integer offset;
    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;
    //查询开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp useStartAt;
    //查询结束时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp useEndAt;

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

    @Override
    public String toString() {
        return "Coupons{" +
                "coupId='" + coupId + '\'' +
                ", limitQuota=" + limitQuota +
                ", cateNm='" + cateNm + '\'' +
                ", userId=" + userId +
                ", cateId=" + cateId +
                ", denomination=" + denomination +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", state='" + state + '\'' +
                ", orderId=" + orderId +
                ", useAt=" + useAt +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", useStartAt=" + useStartAt +
                ", useEndAt=" + useEndAt +
                '}';
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
        return cateNm;
    }

    public void setCateNm(String cateNm) {
        this.cateNm = cateNm;
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Timestamp getUseStartAt() {
        return useStartAt;
    }

    public void setUseStartAt(Timestamp useStartAt) {
        this.useStartAt = useStartAt;
    }

    public Timestamp getUseEndAt() {
        return useEndAt;
    }

    public void setUseEndAt(Timestamp useEndAt) {
        this.useEndAt = useEndAt;
    }

    public static String getuCase() {
        return uCase;
    }

    public static String getIntChar() {
        return intChar;
    }

    public Coupons(String coupId, BigDecimal limitQuota, String cateNm, Long userId, Long cateId, BigDecimal denomination, String startAt, String endAt, String state, Long orderId, Timestamp useAt, Integer pageSize, Integer offset, String sort, String order, Timestamp useStartAt, Timestamp useEndAt) {

        this.coupId = coupId;
        this.limitQuota = limitQuota;
        cateNm = cateNm;
        this.userId = userId;
        this.cateId = cateId;
        this.denomination = denomination;
        this.startAt = startAt;
        this.endAt = endAt;
        this.state = state;
        this.orderId = orderId;
        this.useAt = useAt;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
        this.useStartAt = useStartAt;
        this.useEndAt = useEndAt;
    }
}

package entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by tiffany on 16/4/13.
 * 退货订单
 */
public class Refund {
    private Long id;                    //主键
    private String pgMessage;           //支付公司返回消息
    private int amount;                 //退货单品数量
    private String refundImg;           //退货时上传的照片
    private String contactName;         //联系人姓名
    private String contactTel;          //联系人电话
    private String expressCompany;      //快递公司名称
    private String expressCompCode;     //快递公司编码
    private String expressNum;          //快递编号
    private String rejectReason;        //客服拒绝退货申请原因
    private Long userId;                //用户ID
    private Long orderId;               //订单ID
    private Long splitOrderId;          //子订单ID
    private Long skuId;                 //库存ID
    private String refundType;          //退款类型
    private BigDecimal pay_back_fee;    //退款金额
    private String reason;              //退款原因
    private String state;               //状态
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;         //创建时间
    private String pgTradeNo;           //退款成功后支付公司返回的交易码
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;         //更新时间
    private String pgCode;              //支付公司退款返回信息码

    //分页,每页多少条
    private Integer pageSize;
    //分页,从第几条开始
    private Integer offset;
    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    public Refund() {
    }

    public Refund(Long id, String pgMessage, int amount, String refundImg, String contactName, String contactTel, String expressCompany, String expressCompCode, String expressNum, String rejectReason, Long userId, Long orderId, Long splitOrderId, Long skuId, String refundType, BigDecimal pay_back_fee, String reason, String state, Timestamp createAt, String pgTradeNo, Timestamp updateAt, String pgCode, Integer pageSize, Integer offset, String sort, String order) {
        this.id = id;
        this.pgMessage = pgMessage;
        this.amount = amount;
        this.refundImg = refundImg;
        this.contactName = contactName;
        this.contactTel = contactTel;
        this.expressCompany = expressCompany;
        this.expressCompCode = expressCompCode;
        this.expressNum = expressNum;
        this.rejectReason = rejectReason;
        this.userId = userId;
        this.orderId = orderId;
        this.splitOrderId = splitOrderId;
        this.skuId = skuId;
        this.refundType = refundType;
        this.pay_back_fee = pay_back_fee;
        this.reason = reason;
        this.state = state;
        this.createAt = createAt;
        this.pgTradeNo = pgTradeNo;
        this.updateAt = updateAt;
        this.pgCode = pgCode;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }

    @Override
    public String toString() {
        return "Refund{" +
                "id=" + id +
                ", pgMessage='" + pgMessage + '\'' +
                ", amount=" + amount +
                ", refundImg='" + refundImg + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", expressCompany='" + expressCompany + '\'' +
                ", expressCompCode='" + expressCompCode + '\'' +
                ", expressNum='" + expressNum + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", splitOrderId=" + splitOrderId +
                ", skuId=" + skuId +
                ", refundType='" + refundType + '\'' +
                ", pay_back_fee=" + pay_back_fee +
                ", reason='" + reason + '\'' +
                ", state='" + state + '\'' +
                ", createAt=" + createAt +
                ", pgTradeNo='" + pgTradeNo + '\'' +
                ", updateAt=" + updateAt +
                ", pgCode='" + pgCode + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPgMessage() {
        return pgMessage;
    }

    public void setPgMessage(String pgMessage) {
        this.pgMessage = pgMessage;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRefundImg() {
        return refundImg;
    }

    public void setRefundImg(String refundImg) {
        this.refundImg = refundImg;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressCompCode() {
        return expressCompCode;
    }

    public void setExpressCompCode(String expressCompCode) {
        this.expressCompCode = expressCompCode;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSplitOrderId() {
        return splitOrderId;
    }

    public void setSplitOrderId(Long splitOrderId) {
        this.splitOrderId = splitOrderId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public BigDecimal getPay_back_fee() {
        return pay_back_fee;
    }

    public void setPay_back_fee(BigDecimal pay_back_fee) {
        this.pay_back_fee = pay_back_fee;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getPgTradeNo() {
        return pgTradeNo;
    }

    public void setPgTradeNo(String pgTradeNo) {
        this.pgTradeNo = pgTradeNo;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public String getPgCode() {
        return pgCode;
    }

    public void setPgCode(String pgCode) {
        this.pgCode = pgCode;
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
}

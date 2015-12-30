package entity.erp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Sunny Wu on 15/12/26.
 * 商品订单
 */
public class ShopOrder implements Serializable{

    private String shopOrderNo;     //平台订单号
    private Integer shopId;         //店铺Id
    private String memberNick;      //客户名称
    private Integer orderStatus;    //订单状态 (0:草稿 10：未发货 20：已发货 30：已完结 40：已关闭 50：已取消)
    private Timestamp shopCreatedTime;   //下单时间
    private String orderStatusName; //平台订单状态 (平台订单状态描述，如已付款，等待发货等等。都为中文描述，仅备注作用)
    private Boolean isCod;          //是否货到付款 (默认false)
    private Boolean isDistribution; //是否分销    (默认false)
    private Boolean isJhs;          //是否聚划算   (默认false)
    private Boolean isPresale;      //是否预售     (默认false)
    private Boolean isMobile;       //是否手机订单  (默认false)
    private Double discountFee;     //折扣金额
    private Double postFee;         //邮费
    private Double adjustFee;       //调整金额
    private Double goodsTotal;      //商品总额
    private Double orderTotal;      //应付金额
    private Double receivedTotal;   //实际收款
    private Timestamp shopPayTime;  //平台付款时间
    private String buyerMemo;       //买家留言
    private String sellerMemo;      //卖家备注
    private String shopFlag;        //卖家旗帜 (blue,green,orange,pink,purple,red,yellow)
    private String receiverName;    //收货人姓名
    private String receiverState;   //收货人省份
    private String receiverCity;    //收货人城市
    private String receiverDistrict;//收货人地区
    private String receiverAddress; //收货人地址
    private String receiverZip;     //收货人邮编
    private String receiverMobile;  //收货人手机
    private String receiverPhone;   //收货人电话
    private Integer invoiceType;    //开票类型 (0:无需发票;10:普通发票;20:增值税普通发票;25:增值税专用发票;30:收据;默认0)
    private String invoiceMemo;     //开票备注
    private String invoiceName;     //发票抬头
    private String expressName;     //快递公司
    private String expressTrackNo;  //快递单号
    private String buyerAlipayNo;   //买家支付宝
    private String buyerEmail;      //买家邮箱
    private String alipayOrderNo;   //支付宝付款编号
    private Timestamp deliveryTime; //发货时间
    private Timestamp endTime;      //交易完成时间
    private String userDefinedField1;                   //自定义字段1
    private String userDefinedField2;                   //自定义字段2
    private List<ShopOrderCreateLine> itemLineInfo;           //商品信息
    private List<ShopOrderCreateDiscount> discountLineInfo;   //折扣信息
    private List<ShopOrderCreatePayment> paymentLineInfo;     //付款信息

    public ShopOrder() {
    }

    public String getShopOrderNo() {
        return shopOrderNo;
    }

    public void setShopOrderNo(String shopOrderNo) {
        this.shopOrderNo = shopOrderNo;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getMemberNick() {
        return memberNick;
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getShopCreatedTime() {
        return shopCreatedTime;
    }

    public void setShopCreatedTime(Timestamp shopCreatedTime) {
        this.shopCreatedTime = shopCreatedTime;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public Boolean getCod() {
        return isCod;
    }

    public void setCod(Boolean cod) {
        isCod = cod;
    }

    public Boolean getDistribution() {
        return isDistribution;
    }

    public void setDistribution(Boolean distribution) {
        isDistribution = distribution;
    }

    public Boolean getJhs() {
        return isJhs;
    }

    public void setJhs(Boolean jhs) {
        isJhs = jhs;
    }

    public Boolean getPresale() {
        return isPresale;
    }

    public void setPresale(Boolean presale) {
        isPresale = presale;
    }

    public Boolean getMobile() {
        return isMobile;
    }

    public void setMobile(Boolean mobile) {
        isMobile = mobile;
    }

    public Double getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Double discountFee) {
        this.discountFee = discountFee;
    }

    public Double getPostFee() {
        return postFee;
    }

    public void setPostFee(Double postFee) {
        this.postFee = postFee;
    }

    public Double getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(Double adjustFee) {
        this.adjustFee = adjustFee;
    }

    public Double getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(Double goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Double getReceivedTotal() {
        return receivedTotal;
    }

    public void setReceivedTotal(Double receivedTotal) {
        this.receivedTotal = receivedTotal;
    }

    public Timestamp getShopPayTime() {
        return shopPayTime;
    }

    public void setShopPayTime(Timestamp shopPayTime) {
        this.shopPayTime = shopPayTime;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public String getShopFlag() {
        return shopFlag;
    }

    public void setShopFlag(String shopFlag) {
        this.shopFlag = shopFlag;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceMemo() {
        return invoiceMemo;
    }

    public void setInvoiceMemo(String invoiceMemo) {
        this.invoiceMemo = invoiceMemo;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressTrackNo() {
        return expressTrackNo;
    }

    public void setExpressTrackNo(String expressTrackNo) {
        this.expressTrackNo = expressTrackNo;
    }

    public String getBuyerAlipayNo() {
        return buyerAlipayNo;
    }

    public void setBuyerAlipayNo(String buyerAlipayNo) {
        this.buyerAlipayNo = buyerAlipayNo;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getAlipayOrderNo() {
        return alipayOrderNo;
    }

    public void setAlipayOrderNo(String alipayOrderNo) {
        this.alipayOrderNo = alipayOrderNo;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getUserDefinedField1() {
        return userDefinedField1;
    }

    public void setUserDefinedField1(String userDefinedField1) {
        this.userDefinedField1 = userDefinedField1;
    }

    public String getUserDefinedField2() {
        return userDefinedField2;
    }

    public void setUserDefinedField2(String userDefinedField2) {
        this.userDefinedField2 = userDefinedField2;
    }

    public List<ShopOrderCreateLine> getItemLineInfo() {
        return itemLineInfo;
    }

    public void setItemLineInfo(List<ShopOrderCreateLine> itemLineInfo) {
        this.itemLineInfo = itemLineInfo;
    }

    public List<ShopOrderCreateDiscount> getDiscountLineInfo() {
        return discountLineInfo;
    }

    public void setDiscountLineInfo(List<ShopOrderCreateDiscount> discountLineInfo) {
        this.discountLineInfo = discountLineInfo;
    }

    public List<ShopOrderCreatePayment> getPaymentLineInfo() {
        return paymentLineInfo;
    }

    public void setPaymentLineInfo(List<ShopOrderCreatePayment> paymentLineInfo) {
        this.paymentLineInfo = paymentLineInfo;
    }

    @Override
    public String toString() {
        return "ShopOrder{" +
                "shopOrderNo='" + shopOrderNo + '\'' +
                ", shopId=" + shopId +
                ", memberNick='" + memberNick + '\'' +
                ", orderStatus=" + orderStatus +
                ", shopCreatedTime=" + shopCreatedTime +
                ", orderStatusName='" + orderStatusName + '\'' +
                ", isCod=" + isCod +
                ", isDistribution=" + isDistribution +
                ", isJhs=" + isJhs +
                ", isPresale=" + isPresale +
                ", isMobile=" + isMobile +
                ", discountFee=" + discountFee +
                ", postFee=" + postFee +
                ", adjustFee=" + adjustFee +
                ", goodsTotal=" + goodsTotal +
                ", orderTotal=" + orderTotal +
                ", receivedTotal=" + receivedTotal +
                ", shopPayTime=" + shopPayTime +
                ", buyerMemo='" + buyerMemo + '\'' +
                ", sellerMemo='" + sellerMemo + '\'' +
                ", shopFlag='" + shopFlag + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverState='" + receiverState + '\'' +
                ", receiverCity='" + receiverCity + '\'' +
                ", receiverDistrict='" + receiverDistrict + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverZip='" + receiverZip + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", invoiceType=" + invoiceType +
                ", invoiceMemo='" + invoiceMemo + '\'' +
                ", invoiceName='" + invoiceName + '\'' +
                ", expressName='" + expressName + '\'' +
                ", expressTrackNo='" + expressTrackNo + '\'' +
                ", buyerAlipayNo='" + buyerAlipayNo + '\'' +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", alipayOrderNo='" + alipayOrderNo + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", endTime=" + endTime +
                ", userDefinedField1='" + userDefinedField1 + '\'' +
                ", userDefinedField2='" + userDefinedField2 + '\'' +
                ", itemLineInfo=" + itemLineInfo +
                ", discountLineInfo=" + discountLineInfo +
                ", paymentLineInfo=" + paymentLineInfo +
                '}';
    }

    public ShopOrder(String shopOrderNo, Integer shopId, String memberNick, Integer orderStatus, Timestamp shopCreatedTime, String orderStatusName, Boolean isCod, Boolean isDistribution, Boolean isJhs, Boolean isPresale, Boolean isMobile, Double discountFee, Double postFee, Double adjustFee, Double goodsTotal, Double orderTotal, Double receivedTotal, Timestamp shopPayTime, String buyerMemo, String sellerMemo, String shopFlag, String receiverName, String receiverState, String receiverCity, String receiverDistrict, String receiverAddress, String receiverZip, String receiverMobile, String receiverPhone, Integer invoiceType, String invoiceMemo, String invoiceName, String expressName, String expressTrackNo, String buyerAlipayNo, String buyerEmail, String alipayOrderNo, Timestamp deliveryTime, Timestamp endTime, String userDefinedField1, String userDefinedField2, List<ShopOrderCreateLine> itemLineInfo, List<ShopOrderCreateDiscount> discountLineInfo, List<ShopOrderCreatePayment> paymentLineInfo) {
        this.shopOrderNo = shopOrderNo;
        this.shopId = shopId;
        this.memberNick = memberNick;
        this.orderStatus = orderStatus;
        this.shopCreatedTime = shopCreatedTime;
        this.orderStatusName = orderStatusName;
        this.isCod = isCod;
        this.isDistribution = isDistribution;
        this.isJhs = isJhs;
        this.isPresale = isPresale;
        this.isMobile = isMobile;
        this.discountFee = discountFee;
        this.postFee = postFee;
        this.adjustFee = adjustFee;
        this.goodsTotal = goodsTotal;
        this.orderTotal = orderTotal;
        this.receivedTotal = receivedTotal;
        this.shopPayTime = shopPayTime;
        this.buyerMemo = buyerMemo;
        this.sellerMemo = sellerMemo;
        this.shopFlag = shopFlag;
        this.receiverName = receiverName;
        this.receiverState = receiverState;
        this.receiverCity = receiverCity;
        this.receiverDistrict = receiverDistrict;
        this.receiverAddress = receiverAddress;
        this.receiverZip = receiverZip;
        this.receiverMobile = receiverMobile;
        this.receiverPhone = receiverPhone;
        this.invoiceType = invoiceType;
        this.invoiceMemo = invoiceMemo;
        this.invoiceName = invoiceName;
        this.expressName = expressName;
        this.expressTrackNo = expressTrackNo;
        this.buyerAlipayNo = buyerAlipayNo;
        this.buyerEmail = buyerEmail;
        this.alipayOrderNo = alipayOrderNo;
        this.deliveryTime = deliveryTime;
        this.endTime = endTime;
        this.userDefinedField1 = userDefinedField1;
        this.userDefinedField2 = userDefinedField2;
        this.itemLineInfo = itemLineInfo;
        this.discountLineInfo = discountLineInfo;
        this.paymentLineInfo = paymentLineInfo;
    }
}

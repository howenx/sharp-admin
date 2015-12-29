package entity.erp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Sunny Wu on 15/12/26.
 * 商品订单
 */
public class ShopOrder implements Serializable{

    private String ShopOrderNo;     //平台订单号
    private Integer ShopId;         //店铺Id
    private String MemberNick;      //客户名称
    private Integer OrderStatus;    //订单状态 (0:草稿 10：未发货 20：已发货 30：已完结 40：已关闭 50：已取消)
    private Timestamp ShopCreatedTime;   //下单时间
    private String OrderStatusName; //平台订单状态 (平台订单状态描述，如已付款，等待发货等等。都为中文描述，仅备注作用)
    private Boolean IsCod;          //是否货到付款 (默认false)
    private Boolean IsDistribution; //是否分销    (默认false)
    private Boolean IsJhs;          //是否聚划算   (默认false)
    private Boolean IsPresale;      //是否预售     (默认false)
    private Boolean IsMobile;       //是否手机订单  (默认false)
    private Double DiscountFee;     //折扣金额
    private Double PostFee;         //邮费
    private Double AdjustFee;       //调整金额
    private Double GoodsTotal;      //商品总额
    private Double OrderTotal;      //应付金额
    private Double ReceivedTotal;   //实际收款
    private Timestamp ShopPayTime;  //平台付款时间
    private String BuyerMemo;       //买家留言
    private String SellerMemo;      //卖家备注
    private String ShopFlag;        //卖家旗帜 (blue,green,orange,pink,purple,red,yellow)
    private String ReceiverName;    //收货人姓名
    private String ReceiverState;   //收货人省份
    private String ReceiverCity;    //收货人城市
    private String ReceiverDistrict;//收货人地区
    private String ReceiverAddress; //收货人地址
    private String ReceiverZip;     //收货人邮编
    private String ReceiverMobile;  //收货人手机
    private String ReceiverPhone;   //收货人电话
    private Integer InvoiceType;    //开票类型 (0:无需发票;10:普通发票;20:增值税普通发票;25:增值税专用发票;30:收据;默认0)
    private String InvoiceMemo;     //开票备注
    private String InvoiceName;     //发票抬头
    private String ExpressName;     //快递公司
    private String ExpressTrackNo;  //快递单号
    private String BuyerAlipayNo;   //买家支付宝
    private String BuyerEmail;      //买家邮箱
    private String AlipayOrderNo;   //支付宝付款编号
    private Timestamp DeliveryTime; //发货时间
    private Timestamp EndTime;      //交易完成时间
    private String UserDefinedField1;                   //自定义字段1
    private String UserDefinedField2;                   //自定义字段2
    private List<ShopOrderCreateLine> ItemLineInfo;           //商品信息
    private List<ShopOrderCreateDiscount> DiscountLineInfo;   //折扣信息
    private List<ShopOrderCreatePayment> PaymentLineInfo;     //付款信息

    public ShopOrder() {
    }

    public String getShopOrderNo() {
        return ShopOrderNo;
    }

    public void setShopOrderNo(String shopOrderNo) {
        ShopOrderNo = shopOrderNo;
    }

    public Integer getShopId() {
        return ShopId;
    }

    public void setShopId(Integer shopId) {
        ShopId = shopId;
    }

    public String getMemberNick() {
        return MemberNick;
    }

    public void setMemberNick(String memberNick) {
        MemberNick = memberNick;
    }

    public Integer getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        OrderStatus = orderStatus;
    }

    public Timestamp getShopCreatedTime() {
        return ShopCreatedTime;
    }

    public void setShopCreatedTime(Timestamp shopCreatedTime) {
        ShopCreatedTime = shopCreatedTime;
    }

    public String getOrderStatusName() {
        return OrderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        OrderStatusName = orderStatusName;
    }

    public Boolean getCod() {
        return IsCod;
    }

    public void setCod(Boolean cod) {
        IsCod = cod;
    }

    public Boolean getDistribution() {
        return IsDistribution;
    }

    public void setDistribution(Boolean distribution) {
        IsDistribution = distribution;
    }

    public Boolean getJhs() {
        return IsJhs;
    }

    public void setJhs(Boolean jhs) {
        IsJhs = jhs;
    }

    public Boolean getPresale() {
        return IsPresale;
    }

    public void setPresale(Boolean presale) {
        IsPresale = presale;
    }

    public Boolean getMobile() {
        return IsMobile;
    }

    public void setMobile(Boolean mobile) {
        IsMobile = mobile;
    }

    public Double getDiscountFee() {
        return DiscountFee;
    }

    public void setDiscountFee(Double discountFee) {
        DiscountFee = discountFee;
    }

    public Double getPostFee() {
        return PostFee;
    }

    public void setPostFee(Double postFee) {
        PostFee = postFee;
    }

    public Double getAdjustFee() {
        return AdjustFee;
    }

    public void setAdjustFee(Double adjustFee) {
        AdjustFee = adjustFee;
    }

    public Double getGoodsTotal() {
        return GoodsTotal;
    }

    public void setGoodsTotal(Double goodsTotal) {
        GoodsTotal = goodsTotal;
    }

    public Double getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        OrderTotal = orderTotal;
    }

    public Double getReceivedTotal() {
        return ReceivedTotal;
    }

    public void setReceivedTotal(Double receivedTotal) {
        ReceivedTotal = receivedTotal;
    }

    public Timestamp getShopPayTime() {
        return ShopPayTime;
    }

    public void setShopPayTime(Timestamp shopPayTime) {
        ShopPayTime = shopPayTime;
    }

    public String getBuyerMemo() {
        return BuyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        BuyerMemo = buyerMemo;
    }

    public String getSellerMemo() {
        return SellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        SellerMemo = sellerMemo;
    }

    public String getShopFlag() {
        return ShopFlag;
    }

    public void setShopFlag(String shopFlag) {
        ShopFlag = shopFlag;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getReceiverState() {
        return ReceiverState;
    }

    public void setReceiverState(String receiverState) {
        ReceiverState = receiverState;
    }

    public String getReceiverCity() {
        return ReceiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        ReceiverCity = receiverCity;
    }

    public String getReceiverDistrict() {
        return ReceiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        ReceiverDistrict = receiverDistrict;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        ReceiverAddress = receiverAddress;
    }

    public String getReceiverZip() {
        return ReceiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        ReceiverZip = receiverZip;
    }

    public String getReceiverMobile() {
        return ReceiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        ReceiverMobile = receiverMobile;
    }

    public String getReceiverPhone() {
        return ReceiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        ReceiverPhone = receiverPhone;
    }

    public Integer getInvoiceType() {
        return InvoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        InvoiceType = invoiceType;
    }

    public String getInvoiceMemo() {
        return InvoiceMemo;
    }

    public void setInvoiceMemo(String invoiceMemo) {
        InvoiceMemo = invoiceMemo;
    }

    public String getInvoiceName() {
        return InvoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        InvoiceName = invoiceName;
    }

    public String getExpressName() {
        return ExpressName;
    }

    public void setExpressName(String expressName) {
        ExpressName = expressName;
    }

    public String getExpressTrackNo() {
        return ExpressTrackNo;
    }

    public void setExpressTrackNo(String expressTrackNo) {
        ExpressTrackNo = expressTrackNo;
    }

    public String getBuyerAlipayNo() {
        return BuyerAlipayNo;
    }

    public void setBuyerAlipayNo(String buyerAlipayNo) {
        BuyerAlipayNo = buyerAlipayNo;
    }

    public String getBuyerEmail() {
        return BuyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        BuyerEmail = buyerEmail;
    }

    public String getAlipayOrderNo() {
        return AlipayOrderNo;
    }

    public void setAlipayOrderNo(String alipayOrderNo) {
        AlipayOrderNo = alipayOrderNo;
    }

    public Timestamp getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public Timestamp getEndTime() {
        return EndTime;
    }

    public void setEndTime(Timestamp endTime) {
        EndTime = endTime;
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

    public List<ShopOrderCreateLine> getItemLineInfo() {
        return ItemLineInfo;
    }

    public void setItemLineInfo(List<ShopOrderCreateLine> itemLineInfo) {
        ItemLineInfo = itemLineInfo;
    }

    public List<ShopOrderCreateDiscount> getDiscountLineInfo() {
        return DiscountLineInfo;
    }

    public void setDiscountLineInfo(List<ShopOrderCreateDiscount> discountLineInfo) {
        DiscountLineInfo = discountLineInfo;
    }

    public List<ShopOrderCreatePayment> getPaymentLineInfo() {
        return PaymentLineInfo;
    }

    public void setPaymentLineInfo(List<ShopOrderCreatePayment> paymentLineInfo) {
        PaymentLineInfo = paymentLineInfo;
    }

    @Override
    public String toString() {
        return "ShopOrder{" +
                "ShopOrderNo='" + ShopOrderNo + '\'' +
                ", ShopId=" + ShopId +
                ", MemberNick='" + MemberNick + '\'' +
                ", OrderStatus=" + OrderStatus +
                ", ShopCreatedTime=" + ShopCreatedTime +
                ", OrderStatusName='" + OrderStatusName + '\'' +
                ", IsCod=" + IsCod +
                ", IsDistribution=" + IsDistribution +
                ", IsJhs=" + IsJhs +
                ", IsPresale=" + IsPresale +
                ", IsMobile=" + IsMobile +
                ", DiscountFee=" + DiscountFee +
                ", PostFee=" + PostFee +
                ", AdjustFee=" + AdjustFee +
                ", GoodsTotal=" + GoodsTotal +
                ", OrderTotal=" + OrderTotal +
                ", ReceivedTotal=" + ReceivedTotal +
                ", ShopPayTime=" + ShopPayTime +
                ", BuyerMemo='" + BuyerMemo + '\'' +
                ", SellerMemo='" + SellerMemo + '\'' +
                ", ShopFlag='" + ShopFlag + '\'' +
                ", ReceiverName='" + ReceiverName + '\'' +
                ", ReceiverState='" + ReceiverState + '\'' +
                ", ReceiverCity='" + ReceiverCity + '\'' +
                ", ReceiverDistrict='" + ReceiverDistrict + '\'' +
                ", ReceiverAddress='" + ReceiverAddress + '\'' +
                ", ReceiverZip='" + ReceiverZip + '\'' +
                ", ReceiverMobile='" + ReceiverMobile + '\'' +
                ", ReceiverPhone='" + ReceiverPhone + '\'' +
                ", InvoiceType=" + InvoiceType +
                ", InvoiceMemo='" + InvoiceMemo + '\'' +
                ", InvoiceName='" + InvoiceName + '\'' +
                ", ExpressName='" + ExpressName + '\'' +
                ", ExpressTrackNo='" + ExpressTrackNo + '\'' +
                ", BuyerAlipayNo='" + BuyerAlipayNo + '\'' +
                ", BuyerEmail='" + BuyerEmail + '\'' +
                ", AlipayOrderNo='" + AlipayOrderNo + '\'' +
                ", DeliveryTime=" + DeliveryTime +
                ", EndTime=" + EndTime +
                ", UserDefinedField1='" + UserDefinedField1 + '\'' +
                ", UserDefinedField2='" + UserDefinedField2 + '\'' +
                ", ItemLineInfo=" + ItemLineInfo +
                ", DiscountLineInfo=" + DiscountLineInfo +
                ", PaymentLineInfo=" + PaymentLineInfo +
                '}';
    }

    public ShopOrder(String shopOrderNo, Integer shopId, String memberNick, Integer orderStatus, Timestamp shopCreatedTime, String orderStatusName, Boolean isCod, Boolean isDistribution, Boolean isJhs, Boolean isPresale, Boolean isMobile, Double discountFee, Double postFee, Double adjustFee, Double goodsTotal, Double orderTotal, Double receivedTotal, Timestamp shopPayTime, String buyerMemo, String sellerMemo, String shopFlag, String receiverName, String receiverState, String receiverCity, String receiverDistrict, String receiverAddress, String receiverZip, String receiverMobile, String receiverPhone, Integer invoiceType, String invoiceMemo, String invoiceName, String expressName, String expressTrackNo, String buyerAlipayNo, String buyerEmail, String alipayOrderNo, Timestamp deliveryTime, Timestamp endTime, String userDefinedField1, String userDefinedField2, List<ShopOrderCreateLine> itemLineInfo, List<ShopOrderCreateDiscount> discountLineInfo, List<ShopOrderCreatePayment> paymentLineInfo) {
        ShopOrderNo = shopOrderNo;
        ShopId = shopId;
        MemberNick = memberNick;
        OrderStatus = orderStatus;
        ShopCreatedTime = shopCreatedTime;
        OrderStatusName = orderStatusName;
        IsCod = isCod;
        IsDistribution = isDistribution;
        IsJhs = isJhs;
        IsPresale = isPresale;
        IsMobile = isMobile;
        DiscountFee = discountFee;
        PostFee = postFee;
        AdjustFee = adjustFee;
        GoodsTotal = goodsTotal;
        OrderTotal = orderTotal;
        ReceivedTotal = receivedTotal;
        ShopPayTime = shopPayTime;
        BuyerMemo = buyerMemo;
        SellerMemo = sellerMemo;
        ShopFlag = shopFlag;
        ReceiverName = receiverName;
        ReceiverState = receiverState;
        ReceiverCity = receiverCity;
        ReceiverDistrict = receiverDistrict;
        ReceiverAddress = receiverAddress;
        ReceiverZip = receiverZip;
        ReceiverMobile = receiverMobile;
        ReceiverPhone = receiverPhone;
        InvoiceType = invoiceType;
        InvoiceMemo = invoiceMemo;
        InvoiceName = invoiceName;
        ExpressName = expressName;
        ExpressTrackNo = expressTrackNo;
        BuyerAlipayNo = buyerAlipayNo;
        BuyerEmail = buyerEmail;
        AlipayOrderNo = alipayOrderNo;
        DeliveryTime = deliveryTime;
        EndTime = endTime;
        UserDefinedField1 = userDefinedField1;
        UserDefinedField2 = userDefinedField2;
        ItemLineInfo = itemLineInfo;
        DiscountLineInfo = discountLineInfo;
        PaymentLineInfo = paymentLineInfo;
    }
}

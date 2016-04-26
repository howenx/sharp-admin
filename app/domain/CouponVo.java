package domain;

import java.io.Serializable;

/**
 * Created by Sunny Wu on 16/4/25.
 * kakao china.
 * 优惠券系统Coupon
 */
public class CouponVo implements Serializable{

    private String couponNumber;    //coupon编码
    private String couponName;      //coupon名称
    private String couponType;      //coupon类型(兑换券 EXCHANGE,  礼品券 GIFTCARD,  代金券 MONEY)
    private String placeCode;       //使用店铺编码
    private String placeName;       //使用店铺名称
    private String brandCode;       //品牌编码
    private String brandName;       //品牌名称
    private String event;           //API类型(发行 CREATED,  作废 DROPPED,  延长有效 RENEWED)
    private String eventId;         //API类型ID
    private String code;            //商品编码
    private Integer standardPrice;   //原价
    private Integer price;           //折扣价
    private String issuedAt;        //发行日期(yyyyMMddHHmmss)
    private String expiredAt;       //有效期(yyyyMMddHHmmss)
    private String maxExpiredAt;    //最大有效期
    private String status;          //状态(可使用 NOT_USED, 已使用 USED,  作废 DROPPED)
    private String modifiedAt;      //状态更新时间

    //分页,每页多少条
    private Integer pageSize;
    //分页,从第几条开始
    private Integer offset;
    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    public CouponVo() {
    }

    @Override
    public String toString() {
        return "CouponVo{" +
                "couponNumber='" + couponNumber + '\'' +
                ", couponName='" + couponName + '\'' +
                ", couponType='" + couponType + '\'' +
                ", placeCode='" + placeCode + '\'' +
                ", placeName='" + placeName + '\'' +
                ", brandCode='" + brandCode + '\'' +
                ", brandName='" + brandName + '\'' +
                ", event='" + event + '\'' +
                ", eventId='" + eventId + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", standardPrice=" + standardPrice +
                ", issuedAt='" + issuedAt + '\'' +
                ", expiredAt='" + expiredAt + '\'' +
                ", maxExpiredAt='" + maxExpiredAt + '\'' +
                ", status='" + status + '\'' +
                ", modifiedAt='" + modifiedAt + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }

    public String getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(String couponNumber) {
        this.couponNumber = couponNumber;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Integer standardPrice) {
        this.standardPrice = standardPrice;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getMaxExpiredAt() {
        return maxExpiredAt;
    }

    public void setMaxExpiredAt(String maxExpiredAt) {
        this.maxExpiredAt = maxExpiredAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
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

    public CouponVo(String couponNumber, String couponName, String couponType, String placeCode, String placeName, String brandCode, String brandName, String event, String eventId, String code, Integer price, Integer standardPrice, String issuedAt, String expiredAt, String maxExpiredAt, String status, String modifiedAt, Integer pageSize, Integer offset, String sort, String order) {

        this.couponNumber = couponNumber;
        this.couponName = couponName;
        this.couponType = couponType;
        this.placeCode = placeCode;
        this.placeName = placeName;
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.event = event;
        this.eventId = eventId;
        this.code = code;
        this.price = price;
        this.standardPrice = standardPrice;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.maxExpiredAt = maxExpiredAt;
        this.status = status;
        this.modifiedAt = modifiedAt;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }
}

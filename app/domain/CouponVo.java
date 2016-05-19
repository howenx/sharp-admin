package domain;

import java.io.Serializable;

/**
 * Created by Sunny Wu on 16/4/25.
 * Modified by Tiffany Zhu 2016.05.18
 * kakao china.
 * 优惠券系统Coupon
 */
public class CouponVo implements Serializable{

    private Long couponNumber;              //coupon编码
    private String usedPlaceCode;           //使用店铺编码
    private String usedPlaceName;           //使用店铺名称
    private String brandCode;               //品牌编码
    private String brandName;               //品牌名称
    private String maximumExpiredAt;        //最大有效期
    private String event;                   //API类型    CREATED（发行）,   DROPPED（作废）,   RENEWED（延长有效）
    private String eventId;                 //API类型ID
    private String modifiedAt;              //更新时间  格式 yyyyMMddHHmmss
    private Long id;                        //主键
    private String couponName;              //coupon名称
    private String code;                    //coupon编码
    private String status;                  //NOT_USED（可使用 ）,  ISSUE（已发放）， USED（已使用）,DROPPED（  作废 ）
    private int standardPrice;              //原价
    private int price;                      //折扣价
    private String expiredAt;               //有效期，格式 yyyyMMddHHmmss
    private String issuedAt;                //发行日期，格式 yyyyMMddHHmmss
    private String couponType;              //兑换券 EXCHANGE,  礼品券 GIFTCARD,  代金券 MONEY

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

    public CouponVo(Long couponNumber, String usedPlaceCode, String usedPlaceName, String brandCode, String brandName, String maximumExpiredAt, String event, String eventId, String modifiedAt, Long id, String couponName, String code, String status, int standardPrice, int price, String expiredAt, String issuedAt, String couponType, Integer pageSize, Integer offset, String sort, String order) {
        this.couponNumber = couponNumber;
        this.usedPlaceCode = usedPlaceCode;
        this.usedPlaceName = usedPlaceName;
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.maximumExpiredAt = maximumExpiredAt;
        this.event = event;
        this.eventId = eventId;
        this.modifiedAt = modifiedAt;
        this.id = id;
        this.couponName = couponName;
        this.code = code;
        this.status = status;
        this.standardPrice = standardPrice;
        this.price = price;
        this.expiredAt = expiredAt;
        this.issuedAt = issuedAt;
        this.couponType = couponType;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }

    @Override
    public String toString() {
        return "CouponVo{" +
                "couponNumber=" + couponNumber +
                ", usedPlaceCode='" + usedPlaceCode + '\'' +
                ", usedPlaceName='" + usedPlaceName + '\'' +
                ", brandCode='" + brandCode + '\'' +
                ", brandName='" + brandName + '\'' +
                ", maximumExpiredAt='" + maximumExpiredAt + '\'' +
                ", event='" + event + '\'' +
                ", eventId='" + eventId + '\'' +
                ", modifiedAt='" + modifiedAt + '\'' +
                ", id=" + id +
                ", couponName='" + couponName + '\'' +
                ", code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", standardPrice=" + standardPrice +
                ", price=" + price +
                ", expiredAt='" + expiredAt + '\'' +
                ", issuedAt='" + issuedAt + '\'' +
                ", couponType='" + couponType + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }

    public Long getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(Long couponNumber) {
        this.couponNumber = couponNumber;
    }

    public String getUsedPlaceCode() {
        return usedPlaceCode;
    }

    public void setUsedPlaceCode(String usedPlaceCode) {
        this.usedPlaceCode = usedPlaceCode;
    }

    public String getUsedPlaceName() {
        return usedPlaceName;
    }

    public void setUsedPlaceName(String usedPlaceName) {
        this.usedPlaceName = usedPlaceName;
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

    public String getMaximumExpiredAt() {
        return maximumExpiredAt;
    }

    public void setMaximumExpiredAt(String maximumExpiredAt) {
        this.maximumExpiredAt = maximumExpiredAt;
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

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(int standardPrice) {
        this.standardPrice = standardPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
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

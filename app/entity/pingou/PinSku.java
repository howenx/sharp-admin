package entity.pingou;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.validation.Constraints;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by tiffany on 16/1/20.
 */
public class PinSku implements Serializable {

    private Long pinId;         //拼购ID
    private String pinImg;      //生成后列表图
    private String shareUrl;    //分享短连接
    private String status;      //状态
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp createAt; //创建时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt; //更新时间
    private String themeId;     //主题ID
    private String pinTitle;    //拼购商品标题
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp startAt;  //开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp endAt;        //结束时间
    private int restrictAmount;     //每个ID限购数量
    private String floorPrice;  //拼购最低价
    private Long  invId;            //库存ID
    private BigDecimal pinDiscount; //拼购最低折扣
    private int activityCount;      //已开团数

    //分页,每页多少条
    private Integer pageSize;
    //分页,从第几条开始
    private Integer offset;
    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    public PinSku() {
    }

    public PinSku(Long pinId, String pinImg, String shareUrl, String status, Timestamp createAt, Timestamp updateAt, String themeId, String pinTitle, Timestamp startAt, Timestamp endAt, int restrictAmount, String floorPrice, Long invId, BigDecimal pinDiscount, int activityCount, Integer pageSize, Integer offset, String sort, String order) {
        this.pinId = pinId;
        this.pinImg = pinImg;
        this.shareUrl = shareUrl;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.themeId = themeId;
        this.pinTitle = pinTitle;
        this.startAt = startAt;
        this.endAt = endAt;
        this.restrictAmount = restrictAmount;
        this.floorPrice = floorPrice;
        this.invId = invId;
        this.pinDiscount = pinDiscount;
        this.activityCount = activityCount;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }

    @Override
    public String toString() {
        return "PinSku{" +
                "pinId=" + pinId +
                ", pinImg='" + pinImg + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", status='" + status + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", themeId='" + themeId + '\'' +
                ", pinTitle='" + pinTitle + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", restrictAmount=" + restrictAmount +
                ", floorPrice='" + floorPrice + '\'' +
                ", invId=" + invId +
                ", pinDiscount=" + pinDiscount +
                ", activityCount=" + activityCount +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }

    public Long getPinId() {
        return pinId;
    }

    public void setPinId(Long pinId) {
        this.pinId = pinId;
    }

    public String getPinImg() {
        return pinImg;
    }

    public void setPinImg(String pinImg) {
        this.pinImg = pinImg;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getPinTitle() {
        return pinTitle;
    }

    public void setPinTitle(String pinTitle) {
        this.pinTitle = pinTitle;
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

    public int getRestrictAmount() {
        return restrictAmount;
    }

    public void setRestrictAmount(int restrictAmount) {
        this.restrictAmount = restrictAmount;
    }

    public String getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(String floorPrice) {
        this.floorPrice = floorPrice;
    }

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
    }

    public BigDecimal getPinDiscount() {
        return pinDiscount;
    }

    public void setPinDiscount(BigDecimal pinDiscount) {
        this.pinDiscount = pinDiscount;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
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

package entity.pingou;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    private String pinTitle;    //拼购商品标题
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp startAt;  //开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp endAt;        //结束时间
    private String pinPriceRule;    //价格阶梯
    private int restrictAmount;     //每个ID限购数量
    private BigDecimal floorPrice;  //拼购最低价
    private Long  invId;            //库存ID
    private BigDecimal pinDiscount; //拼购最低折扣
    private Long itemId;            //商品ID


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

    public PinSku(Long pinId, String pinImg, String shareUrl, String status, Timestamp createAt, Timestamp updateAt, String pinTitle, Timestamp startAt, Timestamp endAt, String pinPriceRule, int restrictAmount, BigDecimal floorPrice, Long invId, BigDecimal pinDiscount, Long itemId, Integer pageSize, Integer offset, String sort, String order) {
        this.pinId = pinId;
        this.pinImg = pinImg;
        this.shareUrl = shareUrl;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.pinTitle = pinTitle;
        this.startAt = startAt;
        this.endAt = endAt;
        this.pinPriceRule = pinPriceRule;
        this.restrictAmount = restrictAmount;
        this.floorPrice = floorPrice;
        this.invId = invId;
        this.pinDiscount = pinDiscount;
        this.itemId = itemId;
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
                ", pinTitle='" + pinTitle + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", pinPriceRule='" + pinPriceRule + '\'' +
                ", restrictAmount=" + restrictAmount +
                ", floorPrice=" + floorPrice +
                ", invId=" + invId +
                ", pinDiscount=" + pinDiscount +
                ", itemId=" + itemId +
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

    public String getPinPriceRule() {
        return pinPriceRule;
    }

    public void setPinPriceRule(String pinPriceRule) {
        this.pinPriceRule = pinPriceRule;
    }

    public int getRestrictAmount() {
        return restrictAmount;
    }

    public void setRestrictAmount(int restrictAmount) {
        this.restrictAmount = restrictAmount;
    }

    public BigDecimal getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(BigDecimal floorPrice) {
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
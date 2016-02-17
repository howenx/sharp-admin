package entity.pingou;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by tiffany on 16/1/20.
 */
public class PinActivity implements Serializable {
    private Long pinActiveId;   //拼购活动ID
    private String pinUrl;      //此团的分享短连接
    private Long pinId;         //拼购ID
    private Long masterUserId;  //团长用户ID
    private int personNum;      //拼购人数
    private BigDecimal pinPrice;//拼购价格
    private int joinPersons;    //已参加活动人数
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp createAt; //发起时间
    private String status;      //状态
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp endAt;    //截止时间
    private String pinTitle;    //商品标题

    //分页,每页多少条
    private Integer pageSize;
    //分页,从第几条开始
    private Integer offset;
    //按照哪个字段排序
    private String sort;
    //排序方式,降序,升序
    private String order;

    public PinActivity() {
    }

    public PinActivity(Long pinActiveId, String pinUrl, Long pinId, Long masterUserId, int personNum, BigDecimal pinPrice, int joinPersons, Timestamp createAt, String status, Timestamp endAt, String pinTitle, Integer pageSize, Integer offset, String sort, String order) {
        this.pinActiveId = pinActiveId;
        this.pinUrl = pinUrl;
        this.pinId = pinId;
        this.masterUserId = masterUserId;
        this.personNum = personNum;
        this.pinPrice = pinPrice;
        this.joinPersons = joinPersons;
        this.createAt = createAt;
        this.status = status;
        this.endAt = endAt;
        this.pinTitle = pinTitle;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
    }

    @Override
    public String toString() {
        return "PinActivity{" +
                "pinActiveId=" + pinActiveId +
                ", pinUrl='" + pinUrl + '\'' +
                ", pinId=" + pinId +
                ", masterUserId=" + masterUserId +
                ", personNum=" + personNum +
                ", pinPrice=" + pinPrice +
                ", joinPersons=" + joinPersons +
                ", createAt=" + createAt +
                ", status='" + status + '\'' +
                ", endAt=" + endAt +
                ", pinTitle='" + pinTitle + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                '}';
    }

    public Long getPinActiveId() {
        return pinActiveId;
    }

    public void setPinActiveId(Long pinActiveId) {
        this.pinActiveId = pinActiveId;
    }

    public String getPinUrl() {
        return pinUrl;
    }

    public void setPinUrl(String pinUrl) {
        this.pinUrl = pinUrl;
    }

    public Long getPinId() {
        return pinId;
    }

    public void setPinId(Long pinId) {
        this.pinId = pinId;
    }

    public Long getMasterUserId() {
        return masterUserId;
    }

    public void setMasterUserId(Long masterUserId) {
        this.masterUserId = masterUserId;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public BigDecimal getPinPrice() {
        return pinPrice;
    }

    public void setPinPrice(BigDecimal pinPrice) {
        this.pinPrice = pinPrice;
    }

    public int getJoinPersons() {
        return joinPersons;
    }

    public void setJoinPersons(int joinPersons) {
        this.joinPersons = joinPersons;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public String getPinTitle() {
        return pinTitle;
    }

    public void setPinTitle(String pinTitle) {
        this.pinTitle = pinTitle;
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

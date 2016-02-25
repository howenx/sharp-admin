package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Sunny Wu on 16/1/13.
 * kakao china.
 */
public class DataLog implements Serializable {

    private Long id;            //主键id
    private String operateUser; //操作人员
    private String operateIp;   //操作ip
    private String operateType; //操作类型
    private String logContent;  //日志内容
    private String originData;//原数据
    private String newData;    //新数据
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String operateTime; //操作时间


    private Integer pageSize;   //分页,每页多少条
    private Integer offset;     //分页,从第几条开始
    private String sort;        //按照哪个字段排序
    private String order;       //排序方式,降序,升序
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp startAt;  //查询开始时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp endAt;    //查询结束时间

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public DataLog(Long id, String operateUser, String operateIp, String operateType, String logContent, String originData, String newData, String operateTime, Integer pageSize, Integer offset, String sort, String order, Timestamp startAt, Timestamp endAt) {
        this.id = id;
        this.operateUser = operateUser;
        this.operateIp = operateIp;
        this.operateType = operateType;
        this.logContent = logContent;
        this.originData = originData;
        this.newData = newData;
        this.operateTime = operateTime;
        this.pageSize = pageSize;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public DataLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getOriginData() {
        return originData;
    }

    public void setOriginData(String originData) {
        this.originData = originData;
    }

    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
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

    @Override
    public String toString() {
        return "DataLog{" +
                "id=" + id +
                ", operateUser='" + operateUser + '\'' +
                ", operateIp='" + operateIp + '\'' +
                ", operateType='" + operateType + '\'' +
                ", logContent='" + logContent + '\'' +
                ", originData='" + originData + '\'' +
                ", newData='" + newData + '\'' +
                ", operateTime='" + operateTime + '\'' +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                '}';
    }
}

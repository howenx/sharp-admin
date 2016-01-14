package entity;

import java.io.Serializable;

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
    private String operateTime; //操作时间

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
                '}';
    }

    public DataLog(Long id, String operateUser, String operateIp, String operateType, String logContent, String originData, String newData, String operateTime) {
        this.id = id;
        this.operateUser = operateUser;
        this.operateIp = operateIp;
        this.operateType = operateType;
        this.logContent = logContent;
        this.originData = originData;
        this.newData = newData;
        this.operateTime = operateTime;
    }
}

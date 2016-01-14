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
    private String originalData;//原数据
    private String newlData;    //新数据
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

    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    public String getNewlData() {
        return newlData;
    }

    public void setNewlData(String newlData) {
        this.newlData = newlData;
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
                ", originalData='" + originalData + '\'' +
                ", newlData='" + newlData + '\'' +
                ", operateTime='" + operateTime + '\'' +
                '}';
    }

    public DataLog(Long id, String operateUser, String operateIp, String operateType, String logContent, String originalData, String newlData, String operateTime) {
        this.id = id;
        this.operateUser = operateUser;
        this.operateIp = operateIp;
        this.operateType = operateType;
        this.logContent = logContent;
        this.originalData = originalData;
        this.newlData = newlData;
        this.operateTime = operateTime;
    }
}

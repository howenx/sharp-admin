package entity.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户统计
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class SID implements Serializable {

    private String sDate;   //日期
    private Long userId;    //用户id
    private String regIp;   //注册ip
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp regDt;//注册时间

    public SID() {
    }

    @Override
    public String toString() {
        return "SID{" +
                "sDate='" + sDate + '\'' +
                ", userId=" + userId +
                ", regIp='" + regIp + '\'' +
                ", regDt=" + regDt +
                '}';
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Timestamp getRegDt() {
        return regDt;
    }

    public void setRegDt(Timestamp regDt) {
        this.regDt = regDt;
    }

    public SID(String sDate, Long userId, String regIp, Timestamp regDt) {

        this.sDate = sDate;
        this.userId = userId;
        this.regIp = regIp;
        this.regDt = regDt;
    }
}

package domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by tiffany on 16/5/20.
 */
public class CouponVoDropLog implements Serializable{
    private int id;             //主键
    private String noncestr;    //调用随机字符串
    private Long timestamp;     //调用时间戳
    private Timestamp createDt; //创建时间
    private String params;      //调用参数
    private String status;      //两个数值 'Y','N'

    public CouponVoDropLog() {
    }

    public CouponVoDropLog(int id, String noncestr, Long timestamp, Timestamp createDt, String params, String status) {
        this.id = id;
        this.noncestr = noncestr;
        this.timestamp = timestamp;
        this.createDt = createDt;
        this.params = params;
        this.status = status;
    }

    @Override
    public String toString() {
        return "CouponVoDropLog{" +
                "id=" + id +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp=" + timestamp +
                ", createDt=" + createDt +
                ", params='" + params + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

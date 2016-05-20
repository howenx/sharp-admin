package domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by Sunny Wu on 16/5/20.
 * kakao china.
 */
public class IDAdmin {

    private Long userId;    //用户id
    private String role;    //用户类型
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createdDt;//创建时间
    private String status;      //状态

    public IDAdmin() {
    }

    @Override
    public String toString() {
        return "IDAdmin{" +
                "userId=" + userId +
                ", role='" + role + '\'' +
                ", createdDt=" + createdDt +
                ", status='" + status + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Timestamp createdDt) {
        this.createdDt = createdDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public IDAdmin(Long userId, String role, Timestamp createdDt, String status) {

        this.userId = userId;
        this.role = role;
        this.createdDt = createdDt;
        this.status = status;
    }
}

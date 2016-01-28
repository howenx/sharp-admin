package entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Sunny Wu on 16/1/27.
 * kakao china.
 */
public class IDAdmin implements Serializable {

    private Long userId;        //用户id
    private String role;        //管理员角色
    private Timestamp createDt; //创建时间
    private String status;      //状态

    public IDAdmin() {
    }

    @Override
    public String toString() {
        return "IDAdmin{" +
                "userId=" + userId +
                ", role='" + role + '\'' +
                ", createDt=" + createDt +
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

    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public IDAdmin(Long userId, String role, Timestamp createDt, String status) {

        this.userId = userId;
        this.role = role;
        this.createDt = createDt;
        this.status = status;
    }
}

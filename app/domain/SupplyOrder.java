package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by tiffany on 16/4/19.
 */
public class SupplyOrder implements Serializable {
    private Long id;
    private Long orderId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateTime;
    private Long updateUser;
    private String state;

    public SupplyOrder() {
    }

    public SupplyOrder(Long id, Long orderId, Timestamp updateTime, Long updateUser, String state) {
        this.id = id;
        this.orderId = orderId;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.state = state;
    }

    @Override
    public String toString() {
        return "SupplyOrder{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                ", state='" + state + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

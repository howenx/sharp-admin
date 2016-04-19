package domain;

import java.io.Serializable;

/**
 * Created by tiffany on 16/4/19.
 */
public class AdminSupplier implements Serializable {
    private Long id;
    private Long userId;
    private String supplyMerch;
    private String supplyName;

    public AdminSupplier() {
    }

    public AdminSupplier(Long id, Long userId, String supplyMerch, String supplyName) {
        this.id = id;
        this.userId = userId;
        this.supplyMerch = supplyMerch;
        this.supplyName = supplyName;
    }

    @Override
    public String toString() {
        return "AdminSupplier{" +
                "id=" + id +
                ", userId=" + userId +
                ", supplyMerch='" + supplyMerch + '\'' +
                ", supplyName='" + supplyName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSupplyMerch() {
        return supplyMerch;
    }

    public void setSupplyMerch(String supplyMerch) {
        this.supplyMerch = supplyMerch;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }
}

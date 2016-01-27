package entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Sunny Wu on 16/1/19.
 * kakao china.
 * 多样化价格
 */
public class VaryPrice implements Serializable {

    private Long id;                //主键id
    private Long invId;             //库存Id
    private Long themeId;           //主题Id(可多个)
    private BigDecimal price;       //销售价
    private Integer soldAmount;     //售出数量
    private Integer limitAmount;    //限制销售数量
    private String status;          //状态('Y'--正常,'D'--下架,'N'--删除,'K'--售空，'P'--预售)

    public VaryPrice() {
    }

    @Override
    public String toString() {
        return "VaryPrice{" +
                "id=" + id +
                ", invId=" + invId +
                ", themeId=" + themeId +
                ", price=" + price +
                ", soldAmount=" + soldAmount +
                ", limitAmount=" + limitAmount +
                ", status='" + status + '\'' +
                '}';
    }

    public VaryPrice(Long id, Long invId, Long themeId, BigDecimal price, Integer soldAmount, Integer limitAmount, String status) {
        this.id = id;
        this.invId = invId;
        this.themeId = themeId;
        this.price = price;
        this.soldAmount = soldAmount;
        this.limitAmount = limitAmount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
    }

    public Integer getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(Integer soldAmount) {
        this.soldAmount = soldAmount;
    }

    public Integer getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(Integer limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
}
package entity;

import java.io.Serializable;

/**
 * Created by Sunny Wu on 16/1/18.
 * kakao china.
 *
 * 商品统计
 */
public class ItemStatis implements Serializable{

    private Long id;             //主键
    private String createDate;   //创建日期字符串
    private Long skuId;          //sku id
    private String color;       //sku颜色
    private String size;        //sku尺寸

    public ItemStatis() {
    }

    @Override
    public String toString() {
        return "ItemStatis{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", skuId=" + skuId +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ItemStatis(Long id, String createDate, Long skuId, String color, String size) {

        this.id = id;
        this.createDate = createDate;
        this.skuId = skuId;
        this.color = color;
        this.size = size;
    }
}

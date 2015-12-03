package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * slider entity
 * Created by howen on 15/11/2.
 */
public class Slider implements Serializable{

    private Long id;
    private String img;
    private Integer sortNu;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;
    private String itemTarget;
    private String targetType;

    public Slider(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getSortNu() {
        return sortNu;
    }

    public void setSortNu(Integer sortNu) {
        this.sortNu = sortNu;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public String getItemTarget() {
        return itemTarget;
    }

    public void setItemTarget(String itemTarget) {
        this.itemTarget = itemTarget;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return "Slider{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", sortNu=" + sortNu +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", itemTarget='" + itemTarget + '\'' +
                ", targetType='" + targetType + '\'' +
                '}';
    }

    public Slider(Long id, String img, Integer sortNu, Timestamp createAt, Timestamp updateAt, String itemTarget, String targetType) {
        this.id = id;
        this.img = img;
        this.sortNu = sortNu;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.itemTarget = itemTarget;
        this.targetType = targetType;
    }
}

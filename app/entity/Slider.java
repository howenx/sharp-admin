package entity;

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
    private Timestamp createAt;

    public Slider(Long id, String img, Integer sortNu, Timestamp createAt) {
        this.id = id;
        this.img = img;
        this.sortNu = sortNu;
        this.createAt = createAt;
    }

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

    @Override
    public String toString() {
        return "Slider{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", sortNu=" + sortNu +
                ", createAt=" + createAt +
                '}';
    }
}

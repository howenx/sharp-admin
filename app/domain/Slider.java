package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.validation.Constraints;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * slider entity
 * Created by howen on 15/11/2.
 */
public class Slider implements Serializable{

    private Long id;    //主键ID
    @Constraints.Required
    private String img; //滚动图URL
    private Integer sortNu;//排序号
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;//创建时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;//更新时间
//    @Constraints.Required
    private String itemTarget;  //商品目标地址
//    @Constraints.Required
    private String targetType;  //目标类型(T主题, D详细页面)
    private boolean orNav;      //是否导航图     Added by Tiffany Zhu 2016.07.27
    private String navText;     //导航文字显示    Added by Tiffany Zhu 2016.07.27


    public Slider(){}

    public Slider(Long id, String img, Integer sortNu, Timestamp createAt, Timestamp updateAt, String itemTarget, String targetType, boolean orNav, String navText) {
        this.id = id;
        this.img = img;
        this.sortNu = sortNu;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.itemTarget = itemTarget;
        this.targetType = targetType;
        this.orNav = orNav;
        this.navText = navText;
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
                ", orNav=" + orNav +
                ", navText='" + navText + '\'' +
                '}';
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

    public boolean isOrNav() {
        return orNav;
    }

    public void setOrNav(boolean orNav) {
        this.orNav = orNav;
    }

    public String getNavText() {
        return navText;
    }

    public void setNavText(String navText) {
        this.navText = navText;
    }
}

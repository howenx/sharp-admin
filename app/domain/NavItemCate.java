package domain;

import java.io.Serializable;

/**
 * Created by tiffany on 16/8/25.
 */
public class NavItemCate implements Serializable {
    private Long id;            //主键
    private Long navId;         //导航ID
    private Integer cateType;   //类型
    private Long cateTypeId;    //类型ID
    private boolean orDestroy;  //是否删除

    public NavItemCate() {
    }

    public NavItemCate(Long id, Long navId, Integer cateType, Long cateTypeId, boolean orDestroy) {
        this.id = id;
        this.navId = navId;
        this.cateType = cateType;
        this.cateTypeId = cateTypeId;
        this.orDestroy = orDestroy;
    }

    @Override
    public String toString() {
        return "NavItemCate{" +
                "id=" + id +
                ", navId=" + navId +
                ", cateType=" + cateType +
                ", cateTypeId=" + cateTypeId +
                ", orDestroy=" + orDestroy +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNavId() {
        return navId;
    }

    public void setNavId(Long navId) {
        this.navId = navId;
    }

    public Integer getCateType() {
        return cateType;
    }

    public void setCateType(Integer cateType) {
        this.cateType = cateType;
    }

    public Long getCateTypeId() {
        return cateTypeId;
    }

    public void setCateTypeId(Long cateTypeId) {
        this.cateTypeId = cateTypeId;
    }

    public boolean isOrDestroy() {
        return orDestroy;
    }

    public void setOrDestroy(boolean orDestroy) {
        this.orDestroy = orDestroy;
    }
}

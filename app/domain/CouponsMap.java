package domain;

import play.data.validation.Constraints;

import java.io.Serializable;

/**
 * 优惠券类别和所选择的商品,商品类目,主题的映射
 * Created by Sunny Wu on 16/8/31.
 * kakao china.
 */
public class CouponsMap implements Serializable {

    private Long id;                //主键
    private Long couponCateId;      //优惠券类别ID
    @Constraints.Required
    private Integer cateType;       //类型(1.全场，2.商品，3.sku，4.拼购商品，5.商品二级类目，6.主题，7.商品一级类目)
    @Constraints.Required
    private Long cateTypeId;        //类型ID(0表示任意商品，item_id，inv_id，pin_id，cate_id，theme_id，pcate_id)
    private Boolean orDestroy;      //是否删除(true：已经删除)

    public CouponsMap() {
    }

    public CouponsMap(Long id, Long couponCateId, Integer cateType, Long cateTypeId, Boolean orDestroy) {
        this.id = id;
        this.couponCateId = couponCateId;
        this.cateType = cateType;
        this.cateTypeId = cateTypeId;
        this.orDestroy = orDestroy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponCateId() {
        return couponCateId;
    }

    public void setCouponCateId(Long couponCateId) {
        this.couponCateId = couponCateId;
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

    public Boolean getOrDestroy() {
        return orDestroy;
    }

    public void setOrDestroy(Boolean orDestroy) {
        this.orDestroy = orDestroy;
    }

    @Override
    public String toString() {
        return "CouponsMap{" +
                "id=" + id +
                ", couponCateId=" + couponCateId +
                ", cateType=" + cateType +
                ", cateTypeId=" + cateTypeId +
                ", orDestroy=" + orDestroy +
                '}';
    }
}

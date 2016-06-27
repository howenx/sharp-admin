package domain;

import java.io.Serializable;

/**
 * 优惠券类别
 * Created by Sunny Wu on 16/6/27.
 * kakao china.
 */
public class CouponsCate implements Serializable {

    private Long cateId;    //优惠券类别id
    private String cateNm;  //优惠券类别名称

    public CouponsCate() {
    }

    @Override
    public String toString() {
        return "CouponsCate{" +
                "cateId=" + cateId +
                ", cateNm='" + cateNm + '\'' +
                '}';
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getCateNm() {
        return cateNm;
    }

    public void setCateNm(String cateNm) {
        this.cateNm = cateNm;
    }

    public CouponsCate(Long cateId, String cateNm) {

        this.cateId = cateId;
        this.cateNm = cateNm;
    }
}



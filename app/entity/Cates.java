package entity;

import java.io.Serializable;

/**
 * A entity for commodity categories.
 * <p>
 * <p>
 *
 * @author Sunny Wu
 */
public class Cates implements Serializable {

    /**
     * * cateId primary key.
     */
    private Long cateId;

    /**
     * parent cateId.
     */
    private Long pcateId;

    /**
     * cate  name.
     */
    private String cateNm;

    /**
     * cate description.
     */
    private String cateDesc;

    public Cates() {
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Long getPcateId() {
        return pcateId;
    }

    public void setPcateId(Long pcateId) {
        this.pcateId = pcateId;
    }

    public String getCateNm() {
        return cateNm;
    }

    public void setCateNm(String cateNm) {
        this.cateNm = cateNm;
    }

    public String getCateDesc() {
        return cateDesc;
    }

    public void setCateDesc(String cateDesc) {
        this.cateDesc = cateDesc;
    }

    public Cates(Long cateId, Long pcateId, String cateNm, String cateDesc) {

        this.cateId = cateId;
        this.pcateId = pcateId;
        this.cateNm = cateNm;
        this.cateDesc = cateDesc;
    }

    @Override
    public String toString() {
        return "Cates{" +
                "cateId=" + cateId +
                ", pcateId=" + pcateId +
                ", cateNm='" + cateNm + '\'' +
                ", cateDesc='" + cateDesc + '\'' +
                '}';
    }
}

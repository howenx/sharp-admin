package domain;

import java.io.Serializable;

/**
 * Created by tiffany on 16/8/24.
 */

public class ThemeCate implements Serializable {

    private Long themeCateId;       //主键
    private Integer themeCateCode;  //显示位置编码
    private String themeCateNm;     //显示位置名
    private Long themeId;           //主题Id

    public ThemeCate() {
    }

    public ThemeCate(Long themeCateId, Integer themeCateCode, String themeCateNm, Long themeId) {
        this.themeCateId = themeCateId;
        this.themeCateCode = themeCateCode;
        this.themeCateNm = themeCateNm;
        this.themeId = themeId;
    }

    @Override
    public String toString() {
        return "ThemeCate{" +
                "themeCateId=" + themeCateId +
                ", themeCateCode=" + themeCateCode +
                ", themeCateNm='" + themeCateNm + '\'' +
                ", themeId=" + themeId +
                '}';
    }

    public Long getThemeCateId() {
        return themeCateId;
    }

    public void setThemeCateId(Long themeCateId) {
        this.themeCateId = themeCateId;
    }

    public Integer getThemeCateCode() {
        return themeCateCode;
    }

    public void setThemeCateCode(Integer themeCateCode) {
        this.themeCateCode = themeCateCode;
    }

    public String getThemeCateNm() {
        return themeCateNm;
    }

    public void setThemeCateNm(String themeCateNm) {
        this.themeCateNm = themeCateNm;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
}

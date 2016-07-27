package domain;

import java.io.Serializable;

/**
 * Created by tiffany on 16/7/27.
 */
public class Category implements Serializable {

    private Long categoryId;        //主键
    private int categoryNum;        //位置序号
    private String categoryImg;     //图片
    private String categoryText;    //显示文字
    private Long themeId;           //主题ID

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryNum=" + categoryNum +
                ", categoryImg='" + categoryImg + '\'' +
                ", categoryText='" + categoryText + '\'' +
                ", themeId=" + themeId +
                '}';
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(int categoryNum) {
        this.categoryNum = categoryNum;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
}

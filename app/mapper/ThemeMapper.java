package mapper;

import domain.*;

import java.util.List;

/**
 * ThemeCtrl mapper
 * Created by howen on 15/11/2.
 */
public interface ThemeMapper {

     List<Slider> getSlidersAll();

     void updateSlider(Slider slider);

     Long insertSlider(Slider slider);

     void deleteSlider(Long id);

     List<Theme> getThemePage(Theme theme);

     void insertTheme(Theme theme);

     List<Theme> getThemesAll();

     void updateTheme(Theme theme);

     Theme getThemeById(Long id);

    /**
     * 更新主题商品       Added by Tiffany Zhu 2016.02.05
     * @param theme
     */
     void updThemeItems(Theme theme);

    /**
     * 获取全部主题模板 Added by Tiffany Zhu 2016.01.08
     * @return
     */
     List<ThemeTemplate> getTemplatesAll();

    /**
     * 添加主题模板  Added by Tiffany Zhu 2016.01.13
     * @param themeTemplate
     */
    void insertTemplate(ThemeTemplate themeTemplate);

    /**
     * 更新主题模板 Added by Tiffany Zhu 2016.01.14
     * @param themeTemplate
     */
    void updateTemplate(ThemeTemplate themeTemplate);

    /**
     * 添加H5主题   Added by Tiffany Zhu 2016.02.01
     * @param theme
     */
    void addH5Theme(Theme theme);

    /**
     * 更新H5主题   Added by Tiffany Zhu 2016.02.01
     * @param theme
     */
    void updH5Theme(Theme theme);

    /**
     * 更新过期主题下架 Added by Tiffany Zhu 2016.02.29
     */
    void updDestroy();

    /**
     * 更新主题下架   Added by Tiffany Zhu 2016.02.29
     * @param id
     */
    void updThemeDestroy(Long id);

    /**
     * 获取全部已上架的商品   Added by Tiffany Zhu 2016.03.08
     * @return
     */
    List<Theme> getOnShelfTheme();

    /**
     * 更新主题排序       Added by Tiffany Zhu 2016.03.09
     * @param list
     */
    void updThemeSortNu(List<Theme> list);

    /**
     * 获取全部的分类入口数据      Added by Tiffany Zhu 2016.07.28
     * @return
     */
    List<Slider> getCategoryAll();

    /**
     * 添加分类入口数据      Added by Tiffany Zhu 2016.08.02
     * @param slider
     */
    void insertCategory(Slider slider);

    /**
     * 修改分类入口数据      Added by Tiffany Zhu 2016.08.02
     * @param slider
     */
    Long updateCategory(Slider slider);

    /**
     * 获取用于分类入口的主题      Added by Tiffany Zhu 2016.08.24
     * @return
     */
    List<Theme> getCategoryThemes();

    /**
     * 获取主题显示的位置        Added by Tiffany Zhu 2016.08.24
     * @return
     */
    List<ThemeCate> getThemeCate(Long themeId);

    /**
     * 添加主题存放位置         Added by Tiffany Zhu 2016.08.24
     * @param themeCateList
     * @return
     */
    int addThemeCate(List<ThemeCate> themeCateList);

    /**
     * 删除主题位置       Added by Tiffany Zhu 2016.08.24
     * @param themeId
     * @return
     */
    void delThemeCateByThemeId(Long themeId);

    /**
     * 添加分类入口关联商品二级分类       Added by Tiffany Zhu 2016.08.25
     * @param navItemCateList
     */
    void addNavItemCate(List<NavItemCate> navItemCateList);

    /**
     * 更新入口关联数据        Added by Tiffany Zhu 2016.08.25
     * @param navItemCateList
     */
    void updNavItemCate(List<NavItemCate> navItemCateList);

    /**
     *  入口关联数据设置删除     Added by Tiffany Zhu 2016.08.25
     * @param navItemCateList
     */
    void updNavItemCateToDestroy(List<NavItemCate> navItemCateList);

    /**
     * 查询关联数据     Added by Tiffany Zhu 2016.08.29
     * @param navItemCate
     * @return
     */
    NavItemCate getNavItemCate(NavItemCate navItemCate);

    /**
     * 添加主题生成器      Added by Tiffany Zhu 2016.09.02
     * @param theme
     */
    void addThemeGenerator(Theme theme);

    /**
     * 更新主题生成器链接      Added by Tiffany Zhu 2016.09.05
     * @param theme
     */
    void updThemeGeneratorLink(Theme theme);

    /**
     * 更新主题生成器      Added by Tiffany Zhu 2016.09.05
     * @param theme
     */
    void updThemeGenerator(Theme theme);
}

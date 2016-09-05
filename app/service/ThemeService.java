package service;

import com.fasterxml.jackson.databind.JsonNode;
import domain.*;

import java.util.List;

/**
 * ThemeCtrl service
 * Created by howen on 15/11/2.
 */
public interface ThemeService {

     List<Slider> sliderAll();

     void sliderSave(JsonNode json);

     List<Theme> themeSearch(Theme theme);

     void themeSave(Theme theme);

     List<Theme> getThemesAll();

     Theme getThemeById(Long id);

    /**
     * 获取全部的主题模板 Added by Tiffany Zhu 2016.01.09
     * @return
     */
    List<ThemeTemplate> getTemplatesAll();

    /**
     * 保存主题模板 Added by Tiffany Zhu 2016.01.13
     * @param themeTemplate
     */
    void themeTemplateSave(ThemeTemplate themeTemplate);

    /**
     * 保存H5主题   Added by Tiffany Zhu 2016.02.01
     * @param theme
     */
    void h5ThemeSave(Theme theme);

    /**
     * 更新主题商品       Added by Tiffany Zhu 2016.02.05
     * @param theme
     */
    void updThemeItems(Theme theme);

    /**
     * 更新主题下架   Added by Tiffany Zhu 2016.02.29
     * @param id
     */
    void updThemeDestroy(Long id);

    /**
     * 更新过期主题下架 Added by Tiffany Zhu 2016.02.29
     */
    void updDestroy();

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
     * 保存分类入口数据         Added by Tiffany Zhu 2016.08.02
     * @param json
     */
    void categorySave(JsonNode json);

    /**
     * 获取用于分类入口的主题 Added by Tiffany Zhu 2016.08.24
     * @return
     */
    List<Theme> getCategoryThemes();

    /**
     * 获取主题显示的位置        Added by Tiffany Zhu 2016.08.24
     * @return
     */
    List<ThemeCate> getThemeCate(Long  themeId);

    /**
     * 添加主题存放位置         Added by Tiffany Zhu 2016.08.24
     * @param themeCateList
     * @return
     */
    int addThemeCate(List<ThemeCate> themeCateList);

    /**
     * 根据主题ID删除主题存在位置       Added by Tiffany Zhu 2016.08.24
     * @param themeId
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
     * 更新主题生成器      Added by Tiffany Zhu 2016.09.05
     * @param theme
     */
    void updThemeGeneratorLink(Theme theme);

    /**
     * 更新主题生成器      Added by Tiffany Zhu 2016.09.05
     * @param theme
     */
    void updThemeGenerator(Theme theme);


}

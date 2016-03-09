package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Slider;
import entity.Theme;
import entity.ThemeTemplate;

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


}

package mapper;

import entity.Slider;
import entity.Theme;
import entity.ThemeTemplate;

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
     * 更新主题下架       Added by Tiffany Zhu
     */
    void updDestroy();

}

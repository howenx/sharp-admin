package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Item;
import entity.Slider;
import entity.Theme;
import entity.ThemeTemplate;
import mapper.InventoryMapper;
import mapper.ThemeMapper;
import play.Logger;
import play.api.libs.json.Json;

import javax.inject.Inject;
import java.util.List;

/**
 * impl
 * Created by howen on 15/11/2.
 */
public class ThemeServiceImpl implements ThemeService {

    private static Integer sortNum = 0;

    @Inject
    private ThemeMapper themeMapper;
    @Inject
    private InventoryMapper inventoryMapper;



    /**
     * 滚动条查询
     * @return list
     */
    @Override
    public List<Slider> sliderAll() {
        return themeMapper.getSlidersAll();
    }

    /**
     * 主题查询
     * @param theme theme
     * @return list
     */
    @Override
    public List<Theme> themeSearch(Theme theme) {
        return themeMapper.getThemePage(theme);
    }

    /**
     * 滚动条保存
     * @param json JsonNode
     */
    @Override
    public void sliderSave(JsonNode json) {

        //取删除ID
        if (json.findValue("del").isArray()) {
            for (final JsonNode objNode : json.findValue("del")) {
                themeMapper.deleteSlider(objNode.asLong());
            }
        }

        //取变更的Slider
        if (json.findValue("update").isArray()) {
            for (final JsonNode objNode : json.findValue("update")) {

                Slider slider  = play.libs.Json.fromJson(objNode,Slider.class);

                if (slider.getId()==-1){
                    themeMapper.insertSlider(slider);
                }
                else{
                    themeMapper.updateSlider(slider);
                }
            }
        }
    }

    /**
     * Added by Tiffany Zhu 15/11/30.
     * 保存主题
     * @param theme
     */
    @Override
    public void themeSave(Theme theme)
    {
        if(theme.getId() == null){
            sortNum = sortNum + 1;
            theme.setSortNu(sortNum);
            themeMapper.insertTheme(theme);
        }else{
            themeMapper.updateTheme(theme);
        }
    }

    /**
     * Added By Tiffany Zhu 2015.12.30
     * 通过主题ID获取主题内容
     * @param id
     * @return
     */
    @Override
    public Theme getThemeById(Long id) {
        return themeMapper.getThemeById(id);
    }

    /**
     * Added by Sunny Wu 15/12/1
     * 查询所有的主题
     * @return list of Theme
     */
    @Override
    public List<Theme> getThemesAll() {
        return themeMapper.getThemesAll();
    }

    /**
     * 获取全部的主题模板 Added by Tiffany Zhu 2016.01.09
     * @return
     */
    @Override
    public List<ThemeTemplate> getTemplatesAll() {
        return themeMapper.getTemplatesAll();
    }

    /**
     * 保存主题模板 Added by Tiffany Zhu 2016.01.13
     * @param themeTemplate
     */
    @Override
    public void themeTemplateSave(ThemeTemplate themeTemplate) {
        Logger.error(themeTemplate.getId().toString());
        if (themeTemplate.getId() == 0){
            themeMapper.insertTemplate(themeTemplate);
        }else{
            themeMapper.updateTemplate(themeTemplate);
        }
    }

    /**
     * 保存H5主题   Added by Tiffany Zhu 2016.02.01
     * @param theme
     */
    @Override
    public void h5ThemeSave(Theme theme) {
        if(theme.getId() != null){
            sortNum = sortNum + 1;
            theme.setSortNu(sortNum);
            themeMapper.updH5Theme(theme);
        }
        else{
            themeMapper.addH5Theme(theme);
        }
    }

    /**
     * 更新主题商品       Added by Tiffany Zhu 2016.02.05
     * @param theme
     */
    @Override
    public void updThemeItems(Theme theme) {
        themeMapper.updThemeItems(theme);
    }

    /**
     * 更新过期主题下架 Added by Tiffany Zhu 2016.02.29
     */
    @Override
    public void updDestroy() {
        themeMapper.updDestroy();
    }

    /**
     * 更新主题下架   Added by Tiffany Zhu 2016.02.29
     * @param id
     */
    @Override
    public void updThemeDestroy(Long id) {
        themeMapper.updThemeDestroy(id);
    }

    /**
     * 获取全部已上架的商品   Added by Tiffany Zhu 2016.03.08
     * @return
     */
    @Override
    public List<Theme> getOnShelfTheme() {
        return themeMapper.getOnShelfTheme();
    }

    /**
     * 更新主题排序       Added by Tiffany Zhu 2016.03.09
     * @param list
     */
    @Override
    public void updThemeSortNu(List<Theme> list) {
        themeMapper.updThemeSortNu(list);
    }
}

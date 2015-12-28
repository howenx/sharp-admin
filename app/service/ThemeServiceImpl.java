package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Slider;
import entity.Theme;
import mapper.InventoryMapper;
import mapper.ThemeMapper;
import play.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * impl
 * Created by howen on 15/11/2.
 */
public class ThemeServiceImpl implements ThemeService {

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
     * 录入主题信息
     * @param json JsonNode
     */
    @Override
    public void themeSave(JsonNode json)
    {

        if(json.has("themeDesc")){
            ((ObjectNode)json).put("themeDesc",json.findValue("themeDesc").toString());
        }
        if(json.has("themeItem")){
            ((ObjectNode)json).put("themeItem",json.findValue("themeItem").toString());
        }
        if(json.has("themeTags")) {
            ((ObjectNode) json).put("themeTags", json.findValue("themeTags").toString());
        }

        Logger.error(json.toString());
        Theme theme = play.libs.Json.fromJson(json,Theme.class);
        theme.setOrDestory(false);
        themeMapper.insertTheme(theme);
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
     * 设置超出日期的主题
     * Added by Tiffany Zhu 15/12/25
     */
    @Override
    public void themesDestroy() {
        themeMapper.themesDestroy();
    }
}

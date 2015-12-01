package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Inventory;
import entity.Slider;
import entity.Theme;
import mapper.InventoryMapper;
import mapper.ThemeMapper;
import play.libs.Json;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

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
     * 商品库存
     * Added by Tiffany Zhu 15/11/30.
     * @return list
     */
    @Override
    public List<Inventory> getAllInventories(){return inventoryMapper.getAllInventories();}
    /**
     * Added by Tiffany Zhu 15/11/30.
     * 录入主题信息
     * @param json JsonNode
     */
    @Override
    public void themeSave(JsonNode json){
        Theme theme = new Theme();
        if(json.findValue("theme").isArray()){
            JsonNode jsonTheme = json.findValue("theme");
            if(jsonTheme.has("themeDesc")){
                ((ObjectNode)jsonTheme).put("themeDesc",jsonTheme.findValue("themeDesc").toString());
            }
            if(jsonTheme.has("itemCount")){
                ((ObjectNode)jsonTheme).put("itemCount",jsonTheme.findValue("itemCount").toString());
            }
            if(jsonTheme.has("themeTags")) {
                ((ObjectNode) jsonTheme).put("themeTags", jsonTheme.findValue("themeTags").toString());
            }
            theme = Json.fromJson(json.findValue("theme"),Theme.class);
            theme.setOrDestory(false);
            themeMapper.insertTheme(theme);
        }
    }

}

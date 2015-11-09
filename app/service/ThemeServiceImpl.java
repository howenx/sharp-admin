package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Slider;
import entity.Theme;
import mapper.ThemeMapper;
import play.Logger;
import play.api.libs.iteratee.Enumeratee;
import play.api.libs.json.Json;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * impl
 * Created by howen on 15/11/2.
 */
public class ThemeServiceImpl implements ThemeService {

    @Inject
    private ThemeMapper themeMapper;

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
     * @param pageSize 每页多少条
     * @param offset   从第几条开始取数据
     * @return list
     */
    @Override
    public List<Theme> themeSearch(int pageSize, int offset) {
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("pageSize", pageSize);
        map.put("offset", offset);
        return themeMapper.getThemePage(map);
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

        ObjectMapper mapper = new ObjectMapper();

        //取变更的Slider
        if (json.findValue("update").isArray()) {
            for (final JsonNode objNode : json.findValue("update")) {
                Slider slider = new Slider();
                try {
                    slider = mapper.readValue(objNode.toString(), Slider.class);
                } catch (IOException e) {
                    Logger.error(e.getMessage());
                }
                if (slider.getId()==-1){
                    themeMapper.insertSlider(slider);
                }
                else{
                    themeMapper.updateSlider(slider);
                }
            }
        }
    }
}

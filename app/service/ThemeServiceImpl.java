package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Slider;
import mapper.ThemeMapper;
import play.Logger;
import play.api.libs.json.Json;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * impl
 * Created by howen on 15/11/2.
 */
public class ThemeServiceImpl implements ThemeService {

    @Inject
    private ThemeMapper themeMapper;

    @Override
    public List<Slider> sliderAll() {
        return themeMapper.getSlidersAll();
    }

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

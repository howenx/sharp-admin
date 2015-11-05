package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Slider;

import java.util.List;

/**
 * Theme service
 * Created by howen on 15/11/2.
 */
public interface ThemeService {

    public List<Slider> sliderAll();

    public void sliderSave(JsonNode json);
}

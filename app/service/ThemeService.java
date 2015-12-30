package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Slider;
import entity.Theme;

import java.util.List;

/**
 * ThemeCtrl service
 * Created by howen on 15/11/2.
 */
public interface ThemeService {

     List<Slider> sliderAll();

     void sliderSave(JsonNode json);

     List<Theme> themeSearch(Theme theme);

     void themeSave(JsonNode json);

     List<Theme> getThemesAll();

}

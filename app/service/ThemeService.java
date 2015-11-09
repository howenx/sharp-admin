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

    public List<Slider> sliderAll();

    public void sliderSave(JsonNode json);

    public List<Theme> themeSearch(int pageSize,int offset);
}

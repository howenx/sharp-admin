package mapper;

import entity.Slider;
import entity.Theme;

import java.util.List;
import java.util.Map;

/**
 * ThemeCtrl mapper
 * Created by howen on 15/11/2.
 */
public interface ThemeMapper {

    public List<Slider> getSlidersAll();

    public void updateSlider(Slider slider);

    public Long insertSlider(Slider slider);

    public void deleteSlider(Long id);

    public List<Theme> getThemePage(Map<String,Integer> map);
}

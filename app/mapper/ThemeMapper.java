package mapper;

import entity.Slider;

import java.util.List;

/**
 * Theme mapper
 * Created by howen on 15/11/2.
 */
public interface ThemeMapper {

    public List<Slider> getSlidersAll();

    public void updateSlider(Slider slider);

    public Long insertSlider(Slider slider);

    public void deleteSlider(Long id);
}

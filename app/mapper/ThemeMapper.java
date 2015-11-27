package mapper;

import entity.Item;
import entity.Slider;
import entity.Theme;

import java.util.List;

/**
 * ThemeCtrl mapper
 * Created by howen on 15/11/2.
 */
public interface ThemeMapper {

     List<Slider> getSlidersAll();

     void updateSlider(Slider slider);

     Long insertSlider(Slider slider);

     void deleteSlider(Long id);

     List<Theme> getThemePage(Theme theme);

     List<Item> getItemsAll();
}

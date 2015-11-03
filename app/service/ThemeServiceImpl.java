package service;

import entity.Slider;
import mapper.ThemeMapper;

import javax.inject.Inject;
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
}

package service;

import entity.SubjectPrice;

import java.util.List;

/**
 * Created by tiffany on 16/2/4.
 */
public interface SubjectPriceService {
    /**
     * 在主题中添加多元化价格   Added by Tiffany Zhu 2016.02.04
     * @param subjectPrice
     */
    void sbjPriceSave(SubjectPrice subjectPrice);

    /**
     * 通过主题ID获取多元化价格    Added by Tiffany Zhu 2016.02.05
     * @param themeId
     * @return
     */
    List<SubjectPrice> getSbjPriceByThemeId(Long themeId);

    /**
     * 删除多元化价格      Added by Tiffany Zhu 2016.02.05
     * @param id
     */
    void sbjPriceDelById(Long  id);

    /**
     *通过多元化价格ID获取多元化价格信息    Added by Tiffany Zhu 2016.02.06
     * @param id
     * @return
     */
    SubjectPrice getSbjPriceById(Long id);

    /**
     * 通过主题ID删除多元化价格      Added by Tiffany Zhu 2016.02.05
     * @param id
     */
    void sbjPriceDelByThemeId(Long id);
}

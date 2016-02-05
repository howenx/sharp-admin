package service;

import entity.SubjectPrice;
import mapper.SubjectPriceMapper;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 16/2/4.
 */
public class SubjectPriceServiceImpl  implements SubjectPriceService{
    @Inject
    SubjectPriceMapper subjectPriceMapper;

    /**
     * 从主题添加多元化价格   Added by Tiffany Zhu 2016.02.04
     * @param subjectPrice
     */
    @Override
    public void sbjPriceSave(SubjectPrice subjectPrice) {
        if(subjectPriceMapper.getSbjPrice(subjectPrice) == null){
            subjectPriceMapper.sbjPriceAdd(subjectPrice);
        }else{
            subjectPriceMapper.sbjPriceUpd(subjectPrice);
        }
    }

    /**
     * 通过主题ID获取多元化价格    Added by Tiffany Zhu 2016.02.05
     * @param themeId
     * @return
     */
    @Override
    public List<SubjectPrice> getSbjPriceByThemeId(Long themeId) {
        return subjectPriceMapper.getSbjPriceByThemeId(themeId);
    }

    /**
     * 删除多元化价格      Added by Tiffany Zhu 2016.02.05
     * @param subjectPrice
     */
    @Override
    public void sbjPriceDel(SubjectPrice subjectPrice) {
        subjectPriceMapper.sbjPriceDel(subjectPrice);
    }
}

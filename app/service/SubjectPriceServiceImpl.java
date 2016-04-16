package service;

import domain.SubjectPrice;
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
        if(subjectPrice.getId() == null){
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
     *通过多元化价格ID获取多元化价格信息    Added by Tiffany Zhu 2016.02.06
     * @param id
     * @return
     */
    @Override
    public SubjectPrice getSbjPriceById(Long id) {
        return subjectPriceMapper.getSbjPriceById(id);
    }

    /**
     * 通过ID令多元化价格商品下架       Added by Tiffany Zhu 2016.02.26
     * @param id
     */
    @Override
    public void updStateById(Long id) {
        subjectPriceMapper.updStateById(id);
    }

    /**
     * 通过主题ID令多元化价格商品下架       Added by Tiffany Zhu 2016.02.26
     * @param themeId
     */
    @Override
    public void updStateByThemeId(Long themeId) {
        subjectPriceMapper.updStateByThemeId(themeId);
    }


    /**
     * 由invId获取多元化价格     Added By Sunny.Wu 2016.02.26
     * @param invId 库存Id
     * @return List of SubjectPrice
     */
    @Override
    public List<SubjectPrice> getSbjPriceByInvId(Long invId) {
        return subjectPriceMapper.getSbjPriceByInvId(invId);
    }

    /**
     * 更新多元化价格          Added By Sunny.Wu 2016.02.26
     * @param subjectPrice
     */
    @Override
    public void sbjPriceUpd(SubjectPrice subjectPrice) {
        subjectPriceMapper.sbjPriceUpd(subjectPrice);
    }
}

package service;

import entity.SubjectPrice;
import mapper.SubjectPriceMapper;
import javax.inject.Inject;

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
    public void sbjPriceAdd(SubjectPrice subjectPrice) {
        subjectPriceMapper.sbjPriceAdd(subjectPrice);
    }
}

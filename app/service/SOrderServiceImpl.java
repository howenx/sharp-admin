package service;

import entity.statistics.SOrder;
import mapper.SOrderMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class SOrderServiceImpl implements SOrderService {

    @Inject
    private SOrderMapper sOrderMapper;

    @Override
    public List<SOrder> getSOrderBy(SOrder sOrder) {
        return sOrderMapper.getSOrderBy(sOrder);
    }

    @Override
    public List<SOrder> getAllSOrder() {
        return sOrderMapper.getAllSOrder();
    }

}

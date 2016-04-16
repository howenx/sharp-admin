package service;

import domain.statistics.SOrderLine;
import mapper.SOrderLineMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class SOrderLineServiceImpl implements SOrderLineService {

    @Inject
    private SOrderLineMapper sOrderLineMapper;

    @Override
    public List<SOrderLine> getSOrderLineBy(SOrderLine sOrderLine) {
        return sOrderLineMapper.getSOrderLineBy(sOrderLine);
    }

    @Override
    public List<SOrderLine> getAllSOrderLine() {
        return sOrderLineMapper.getAllSOrderLine();
    }

}

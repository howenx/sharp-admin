package service;

import entity.VaryPrice;
import mapper.VaryPriceMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/19.
 * kakao china.
 */
public class VaryPriceServiceImpl implements VaryPriceService {

    @Inject
    private VaryPriceMapper varyPriceMapper;

    @Override
    public Long insertVaryPrice(VaryPrice varyPrice) {
        return varyPriceMapper.insertVaryPrice(varyPrice);
    }

    @Override
    public void updateVaryPrice(VaryPrice varyPrice) {
        varyPriceMapper.updateVaryPrice(varyPrice);
    }

    @Override
    public List<VaryPrice> getVaryPriceBy(VaryPrice varyPrice) {
        return varyPriceMapper.getVaryPriceBy(varyPrice);
    }

    @Override
    public List<VaryPrice> getAllVaryPrices() {
        return varyPriceMapper.getAllVaryPrices();
    }
}
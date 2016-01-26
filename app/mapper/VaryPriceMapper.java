package mapper;

import entity.VaryPrice;

import java.util.List;

/**
 * Created by Sunny Wu on 16/1/19.
 * kakao china.
 */
public interface VaryPriceMapper {

    Long insertVaryPrice(VaryPrice varyPrice);

    void updateVaryPrice(VaryPrice varyPrice);

    List<VaryPrice> getVaryPriceBy(VaryPrice varyPrice);

    List<VaryPrice> getAllVaryPrices();

    /**
     * 通过id获取多样化价格      Added by Tiffany Zhu 2016.01.26
     * @param id
     * @return
     */
    VaryPrice getVaryPriceById(Long id);

}

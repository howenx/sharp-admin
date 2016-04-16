package mapper;

import domain.VaryPrice;

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

    /**
     * 更新主题ID   Added by Tiffany Zhu 2016.01.29
     * @param varyPrice
     */
    void updVaryThemeId(VaryPrice varyPrice);

    /**
     * 获取状态为"正常"和"预售"的多样化价格商品    Added by Tiffany Zhu 2016.03.01
     * @return
     */
    List<VaryPrice> getAvailableVaryPrice();

}

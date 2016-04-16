package service;

import domain.ItemStatis;

import java.util.List;

/**
 * Created by Sunny Wu on 16/1/18.
 * kakao china.
 */
public interface ItemStatisService {

    /**
     * 录入一条商品统计数据
     * @param itemStatis
     * @return Long
     */
    Long insertItemStatis(ItemStatis itemStatis);

    /**
     * 查询一条统计数据
     * @param id
     * @return itemStatis
     */
    ItemStatis getItemStatis(Long id);

    /**
     * 查询所有的统计数据
     * @return list of itemStatis
     */
    List<ItemStatis> getAllItemStatis();

}

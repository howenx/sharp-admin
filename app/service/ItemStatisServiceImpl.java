package service;

import domain.ItemStatis;
import mapper.ItemStatisMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/18.
 * kakao china.
 */
public class ItemStatisServiceImpl implements ItemStatisService {

    @Inject
    private ItemStatisMapper itemStatisMapper;

    /**
     * 录入一条商品统计数据
     * @param itemStatis
     * @return Long
     */
    @Override
    public Long insertItemStatis(ItemStatis itemStatis) {
        return itemStatisMapper.insertItemStatis(itemStatis);
    }

    /**
     * 查询一条统计数据
     * @param id
     * @return itemStatis
     */
    @Override
    public ItemStatis getItemStatis(Long id) {
        return itemStatisMapper.getItemStatis(id);
    }

    /**
     * 查询所有的统计数据
     * @return list of itemStatis
     */
    @Override
    public List<ItemStatis> getAllItemStatis() {
        return itemStatisMapper.getAllItemStatis();
    }

}

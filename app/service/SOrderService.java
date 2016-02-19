package service;

import entity.SOrder;

import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public interface SOrderService {

    List<SOrder> getSOrderBy(SOrder sOrder);

    List<SOrder> getAllSOrder();

}

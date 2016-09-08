package service;

import domain.Inventory;
import domain.Item;
import domain.order.OrderLine;

import java.util.List;

/**
 * Created by tiffany on 15/12/17.
 */
public interface OrderLineService {
    /**
     * 由订单Id获取订单商品
     * @param splitId
     * @return
     */
    List<OrderLine> getLineBySplitId(Long splitId);

    /**
     * 由订单id获取订单商品          Added By Sunny.Wu  2016.03.07
     * @param orderId 订单id
     * @return List of OrderLine
     */
    List<OrderLine> getLineByOrderId(Long orderId);

    /**
     * 通过item Id获取订单商品      Added by Tiffany Zhu 2016.04.20
     * @param itemList
     * @return
     */
    List<OrderLine> getLineByItems(List<Item> itemList);

    /**
     * 通过SkuId 获取子订单信息 Added by Tiffany Zhu 2016.09.08
     * @param inventoryList
     * @return
     */
    List<OrderLine> getOrderLineBySku(List<Inventory> inventoryList);
}

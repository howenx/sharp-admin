package mapper;

import domain.order.Order;
import domain.order.OrderLine;

import java.util.List;

/**
 * 订单
 * Created by tiffany on 15/12/10.
 */
public interface OrderMapper {
    /**
     * 订单列表     Added by Tiffany Zhu
     * @return
     */
    List<Order> getOrdersAll();

    /**
     * ajax分页查询     Added by Tiffany Zhu
     * @param order
     * @return
     */
    List<Order> getOrderPage(Order order);

    /**
     * 由订单ID获取订单详情      Added by Tiffany Zhu
     * @param orderId
     * @return
     */
    Order getOrderById(Long orderId);

    /**
     * 取消支付超时的订单 Added  by Tiffany Zhu
     * @param orderIds
     */
    void orderCancel(Long[] orderIds);

    /**
     * 超过24小时未支付的订单     Added by Tiffany Zhu 2016.01.05
     * @return
     */
    List<Order> getOutTimeOrders();

    /**
     * 通过拼购活动ID获取订单     Added by Tiffany Zhu 2016.04.09
     * @param pinActivityId
     * @return
     */
    List<Order> getOrderByPinAtvId(Long pinActivityId);

    /**
     * 已支付成功的订单 更新至 成功状态    Added by Tiffany Zhu 2016.04.09
     * @param orderList
     */
    void updPinOrderToSuccess(List<Order> orderList);

    /**
     * 更新订单信息(状态)              Add By Sunny.Wu 2016.04.14
     * @param order 订单
     */
    void updateOrder(Order order);

    /**
     * 获取已签收订单      Added by Tiffany Zhu 2016.04.15
     * @return
     */
    List<Order> getSignedOrders();

    /**
     * 确认收货     Added by Tiffany Zhu 2016.04.15
     * @param orderIds
     */
    void orderConfirmReceive(Long[] orderIds);

    /**
     * 获取Supplier 的订单       Added by Tiffany Zhu 2016.04.20
     * @param orderLineList
     * @return
     */
    List<Order> getOrderByIds(List<OrderLine> orderLineList);

    /**
     * 查询成功付款和成功退款的订单       Add By Sunny Wu 2016.06.16
     * @return list of order and refund order
     */
    List<Order> getTradeOrder(Order order);

    /**
     * 每日订单交易额,交易量统计        Add By Sunny Wu 2016.06.17
     * @return list of order
     */
    List<Order> countTradeOrder(Order order);

    /**
     * 商品的销量排行              Add By Sunny Wu 2016.06.17
     * @param order
     * @return list of orderLine
     */
    List<OrderLine> countTradeGoods(Order order);

}

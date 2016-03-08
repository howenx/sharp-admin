package controllers;

import com.google.inject.Inject;
import com.iwilley.b1ec2.api.ApiException;
import middle.ShopOrderPushMiddle;
import play.mvc.Controller;
import play.mvc.Result;
import service.IDService;
import service.OrderLineService;
import service.OrderService;
import service.OrderShipService;

/**
 * Created by Sunny Wu 15/12/26.
 * ERP订单的操作
 */
public class ShopOrderCtrl extends Controller {

    private OrderService orderService;

    private OrderShipService orderShipService;

    private OrderLineService orderLineService;

    private IDService idService;

    @Inject
    private ShopOrderPushMiddle shopOrderPushMiddle;

    @Inject
    public ShopOrderCtrl(OrderService orderService, IDService idService, OrderShipService orderShipService, OrderLineService orderLineService) {
        this.orderService = orderService;
        this.idService = idService;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
    }

    /**
     * 订单推送
     * @return
     */
    public Result shopOrderPush(Long orderId) throws ApiException {
        String shopOrderNo = shopOrderPushMiddle.shopOrderPush(orderId);
        return ok(shopOrderNo);
    }
}

package controllers;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.iwilley.b1ec2.api.ApiException;
import middle.ShopOrderMiddle;
import modules.NewScheduler;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import service.IDService;
import service.OrderLineService;
import service.OrderService;
import service.OrderShipService;
import util.SysParCom;

import javax.inject.Named;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sunny Wu 16/3/9.
 * ERP订单的操作
 */
public class ShopOrderCtrl extends Controller {

    private OrderService orderService;

    private OrderShipService orderShipService;

    private OrderLineService orderLineService;

    private IDService idService;

    @javax.inject.Inject
    private NewScheduler scheduler;

    @javax.inject.Inject
    @Named("salesOrderQueryActor")
    private ActorRef salesOrderQueryActor;

    @Inject
    private ShopOrderMiddle shopOrderMiddle;

    @Inject
    public ShopOrderCtrl(OrderService orderService, IDService idService, OrderShipService orderShipService, OrderLineService orderLineService) {
        this.orderService = orderService;
        this.idService = idService;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
    }

    /**
     * ERP推送订单
     * @return shopOrderNo 订单编号
     * @throws ApiException
     */
    public Result shopOrderPush() throws ApiException, ParseException {
        JsonNode json = request().body().asJson();
        List<String> shopOrderCodeList = new ArrayList<>();
        for(int i=0;i<json.size();i++) {
            Long orderId = (json.get(i)).asLong();
            //推送之前先查询,先从ERP查询该订单若已存在则不推送
            String shopOrderNo = "";
            List<Object> salesOrderList = shopOrderMiddle.salesOrderQuery(orderId.toString());
            if ((null == salesOrderList) ||  salesOrderList.size()==0) {
                shopOrderNo = shopOrderMiddle.shopOrderPush(orderId);
                Logger.error("推送结果:"+shopOrderNo);
                shopOrderCodeList.add(shopOrderNo);
                //推送成功的订单再创建schedule
                if (Json.parse(shopOrderNo).has("ShopOrderNo")) {
                    shopOrderNo = Json.parse(shopOrderNo).findValue("ShopOrderNo").asText();
                    Logger.error("订单"+shopOrderNo+":push to ERP");
                    //启动scheduler从erp查询订单,海关审核通过,更新物流信息
                    scheduler.schedule(Duration.create(SysParCom.ORDER_QUERY_DELAY, TimeUnit.MILLISECONDS),Duration.create(SysParCom.ORDER_QUERY_INTERVAL, TimeUnit.MILLISECONDS),salesOrderQueryActor,orderId);
                }
            }
        }
        return ok(shopOrderCodeList.toString());
    }

    /**
     * 根据订单编号查询ERP订单信息
     * @param shopOrderNo 平台订单编号
     * @return salesOrder
     * @throws ParseException
     * @throws ApiException
     */
    public Result salesOrderQuery(Long shopOrderNo) throws ParseException, ApiException {

        return ok(Json.toJson(shopOrderMiddle.salesOrderQuery(shopOrderNo.toString())));
    }
}

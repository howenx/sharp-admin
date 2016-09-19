package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.iwilley.b1ec2.api.ApiException;
import middle.ShopOrderMiddle;
import modules.NewScheduler;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.IDService;
import service.OrderLineService;
import service.OrderService;
import service.OrderShipService;
import util.SysParCom;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Sunny Wu 16/3/9.
 * ERP订单的操作
 */
public class ShopOrderCtrl extends Controller {

    private OrderService orderService;

    private OrderShipService orderShipService;

    private OrderLineService orderLineService;

    private IDService idService;

    @Inject
    private NewScheduler scheduler;

//    @javax.inject.Inject
//    @Named("salesOrderQueryActor")
//    private ActorRef salesOrderQueryActor;

    @Inject
    private ShopOrderMiddle shopOrderMiddle;

    @Inject
    private ActorSystem system;

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
//        List<String> shopOrderCodeList = new ArrayList<>();
        for(int i=0;i<json.size();i++) {
            Long orderId = (json.get(i)).asLong();
            //推送之前先查询,先从ERP查询该订单若已存在则不推送
//            String shopOrderNo = "";
            List<Object> salesOrderList = shopOrderMiddle.salesOrderQuery(orderId.toString());
            if ((null == salesOrderList) ||  salesOrderList.size()==0) {
                //调用推送订单Actor
                system.actorSelection(SysParCom.ERP_ORDER_PUSH).tell(orderId, ActorRef.noSender());

//                shopOrderNo = shopOrderMiddle.shopOrderPush(orderId);
//                Logger.error("推送结果:"+shopOrderNo);
//                shopOrderCodeList.add(shopOrderNo);
//                //推送成功的订单再创建schedule
//                if (Json.parse(shopOrderNo).has("ShopOrderNo")) {
//                    shopOrderNo = Json.parse(shopOrderNo).findValue("ShopOrderNo").asText();
//                    Logger.error("订单"+shopOrderNo+":push to ERP");
//                    //启动scheduler从erp查询订单,海关审核通过,更新物流信息
//                    scheduler.schedule(Duration.create(SysParCom.ORDER_QUERY_DELAY, TimeUnit.MILLISECONDS),Duration.create(SysParCom.ORDER_QUERY_INTERVAL, TimeUnit.MILLISECONDS),salesOrderQueryActor,orderId);
//                }
            }
        }
//        return ok(shopOrderCodeList.toString());
        return ok();
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

    /**
     *  订单申报    Added By Sunny Wu 2016.09.18
     * @return
     */
    public Result shopOrderDeclara() {
        JsonNode json = request().body().asJson();
        for(int i=0;i<json.size();i++) {
            Long orderId = (json.get(i)).asLong();
            //调用订单申报Actor
            Logger.error(orderId+ "订单申报。。。。");
            system.actorSelection(SysParCom.ORDER_DECLARA).tell(orderId, ActorRef.noSender());
        }
        return ok();
    }
}

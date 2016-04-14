package actor;

import akka.actor.AbstractActor;
import akka.actor.Cancellable;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import entity.Persist;
import entity.order.Order;
import entity.order.OrderSplit;
import middle.ShopOrderMiddle;
import modules.LevelFactory;
import play.Logger;
import play.libs.Json;
import service.OrderService;
import service.OrderSplitService;

import javax.inject.Inject;
import java.util.List;

/**
 * 由子订单号查询ERP订单信息的Actor
 * Created by Sunny Wu on 16/3/9.
 * kakao china.
 */
public class SalesOrderQueryActor extends AbstractActor {

    @Inject
    private LevelFactory levelFactory;

    @Inject
    private OrderService orderService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    public SalesOrderQueryActor(ShopOrderMiddle shopOrderMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            JsonNode jsonNode = null;
            OrderSplit orderSplit = orderSplitService.getSplitByOrderId(orderId).get(0);
            List<Object> salesOrderList = shopOrderMiddle.salesOrderQuery(orderId.toString());
            if ((null != salesOrderList) &&  salesOrderList.size()>0) {
                jsonNode = Json.toJson(salesOrderList.get(0));
                int orderStatus = jsonNode.get("orderStatus").intValue();  //系统订单状态
                String expressCode = jsonNode.get("express").get("expressCode").asText();//快递编码
                String expressName = jsonNode.get("express").get("expressName").asText();//快递名称
                String expressTrackNo = jsonNode.get("expressTrackNo").asText();         //快递单号
                String userDefinedField1 = jsonNode.get("userDefinedField1").asText();  //自定义字段1(东方支付推送状态)
                String userDefinedField4 = jsonNode.get("userDefinedField4").asText();  //自定义字段4(威盛推送状态)
                String userDefinedField2 = jsonNode.get("userDefinedField2").asText();  //自定义字段2(海关状态)
                //根据子订单的报关单位判断哪个自定义字段的推送状态
                //报关成功且海关返回物流单号,更新快递单号到
                if (("已推送".equals(userDefinedField1) || "已推送".equals(userDefinedField2)) && "审核通过".equals(userDefinedField4) && !"".equals(expressTrackNo)) {
                    //更新子订单状态,物流信息
                    orderSplit.setState("S");//报关成功
                    orderSplit.setExpressCode(expressCode);//快递公司代码
                    orderSplit.setExpressNm(expressName);//快递公司名称
                    orderSplit.setExpressNum(expressTrackNo);//快递单号
                    orderSplitService.updateSplitOrder(orderSplit);
                    //更新订单状态已发货
                    Order order = orderService.getOrderById(orderId);
                    order.setOrderStatus("D");
                    orderService.updateOrder(order);
                    //取消schedule
                    if (levelFactory.map.containsKey(orderId)) {
                        Persist p = levelFactory.map.get(orderId);
                        p.getCancellable().cancel();
                        levelFactory.map.remove(orderId);
                    }
                    if (levelFactory.get(orderId) != null) {
                        levelFactory.delete(orderId);
                    }
                    if (levelFactory.delMap.containsKey(orderId)) {
                        Cancellable delCancellable = levelFactory.delMap.get(orderId);
                        delCancellable.cancel();
                        levelFactory.delMap.remove(orderId);
                    }
                }
            }
            Logger.debug(orderId+":sales order query....");
        }).matchAny(s-> {
            Logger.error("ERP order query error!", s.toString());
            unhandled(s);
        }).build());
    }
}

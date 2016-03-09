package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import middle.ShopOrderMiddle;
import play.Logger;

import javax.inject.Inject;

/**
 * 由子订单号查询ERP订单信息的Actor
 * Created by Sunny Wu on 16/3/9.
 * kakao china.
 */
public class SalesOrderQueryActor extends AbstractActor {

    @Inject
    public SalesOrderQueryActor(ShopOrderMiddle shopOrderMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
//            List<Object> salesOrderList = shopOrderMiddle.salesOrderQuery(orderId.toString());
            Logger.debug(orderId+":sales order query....");
            //查询到订单状态为已交货
//            String orderStatus  = Json.parse(Json.toJson(salesOrderList.get(0)).asText()).get("orderStatus").asText();

        }).matchAny(s-> {
            Logger.error("ERP order query error!", s.toString());
            unhandled(s);
        }).build());
    }
}

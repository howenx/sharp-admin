package middle;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.domain.ShopOrderCreateLine;
import com.iwilley.b1ec2.api.request.ShopOrderCreateRequest;
import entity.ID;
import entity.Inventory;
import entity.erp.ShopOrderOperate;
import entity.order.Order;
import entity.order.OrderLine;
import entity.order.OrderShip;
import entity.order.OrderSplit;
import service.*;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sunny Wu on 16/3/7.
 * kakao china.
 */
public class ShopOrderMiddle {

    @Inject
    private OrderService orderService;

    @Inject
    private OrderShipService orderShipService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    private OrderLineService orderLineService;

    @Inject
    private IDService idService;

    @Inject
    private InventoryService inventoryService;

    /**
     * ERP 推送订单(子订单)
     * @param splitId 子订单id
     * @return 平台订单编号
     * @throws ApiException
     */
    public String shopOrderPush(Long splitId) throws ApiException {
        ShopOrderOperate shopOrderOperate = new ShopOrderOperate();
        ShopOrderCreateRequest request = new ShopOrderCreateRequest();
        OrderSplit orderSplit = orderSplitService.getSplitById(splitId);
        Long orderId = orderSplit.getOrderId();
        Order order = orderService.getOrderById(orderId);
        OrderShip orderShip = orderShipService.getShipByOrderId(orderId);
        List<OrderLine> orderLineList = orderLineService.getLineByOrderId(orderId);
        ID id = idService.getID(order.getUserId().intValue());
        String nickname = id.getNickname();     //用户名称
        //订单信息
        request.shopOrderNo = splitId.toString();//平台订单号
        request.shopId = 1;                      //店铺id
        request.memberNick = nickname;         //客户名称
        request.orderStatus = 10;              //订单状态(0:草稿 10：未发货 20：已发货 30：已完结 40：已关闭 50：已取消)
        request.shopCreatedTime = order.getOrderCreateAt();//下单时间
        request.orderStatusName = "未发货";//平台订单状态
//        request.isDistribution = true;  //是否分销
        request.isMobile = true;                           //是否手机订单
//        request.discountFee = order.getDiscount().doubleValue();//折扣金额
        request.postFee = orderSplit.getShipFee().doubleValue();//邮费
        request.goodsTotal = orderSplit.getTotalFee().doubleValue();//商品总额
        request.orderTotal = orderSplit.getTotalPayFee().doubleValue();//应付金额
//        request.receivedTotal();                                 //实际收款
        request.setShopPayTime(new Timestamp(new Date().getTime()));//平台付款时间
        //订单明细
        request.setReceiverName(orderShip.getDeliveryName());    //收货人姓名
        request.setReceiverState(orderShip.getDeliveryCity().split(" ")[0]);//收货人省份
        request.setReceiverCity(orderShip.getDeliveryCity().split(" ")[1]);//收货人城市
        request.setReceiverDistrict(orderShip.getDeliveryCity().split(" ")[2]);//收货人地区
        request.setReceiverAddress(orderShip.getDeliveryAddress());             //收货人地址
        request.setReceiverMobile(orderShip.getDeliveryTel());                  //收货人手机
        //订单商品信息
        List<ShopOrderCreateLine> lines = new ArrayList<>();
//        Logger.error("orderLineList"+orderLineList);
        for(OrderLine orderLine : orderLineList) {
            ShopOrderCreateLine shopOrderCreateLine = new ShopOrderCreateLine();
            shopOrderCreateLine.shopLineNo = orderLine.getLineId().toString();    //平台订单行号
            Long skuId = orderLine.getSkuId();
            Inventory inventory = inventoryService.getInventory(skuId);
            shopOrderCreateLine.outerId = inventory.getInvCode();                //外部编码
            shopOrderCreateLine.quantity = orderLine.getAmount();   //数量
            shopOrderCreateLine.price = orderLine.getPrice().doubleValue();//价格
            shopOrderCreateLine.itemName = orderLine.getSkuTitle();        //商品名称
            shopOrderCreateLine.skuName = orderLine.getSkuColor()+orderLine.getSkuSize();//规格名称
            lines.add(shopOrderCreateLine);
        }
//        Logger.error("lines"+lines.get(0).getPrice()+lines.get(0).getSkuName()+lines.get(0).getItemName());
//        Logger.error("lines"+lines.get(1).getPrice()+lines.get(1).getSkuName()+lines.get(1).getItemName());
        //付款方式
//        List<ShopOrderCreatePayment> paymentList = new ArrayList<>();
//        request.setPaymentLines(paymentList);
        request.setItemLines(lines);
//        ShopOrderCreateResponse response = client.execute(request);
        return shopOrderOperate.ShopOrderPush(request);     //返回平台订单编号
    }


    /**
     * 由平台订单编号查询ERP订单信息
     * @param shopOrderNo 平台订单编号
     * @return salesOrder 订单信息
     * @throws ParseException
     * @throws ApiException
     */
    public List<Object> salesOrderQuery(String shopOrderNo) throws ParseException, ApiException {
        ShopOrderOperate shopOrderOperate = new ShopOrderOperate();
        return shopOrderOperate.SalesOrderQuery(shopOrderNo);
    }


}

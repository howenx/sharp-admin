package middle;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.B1EC2Client;
import com.iwilley.b1ec2.api.domain.ShopOrderCreateLine;
import com.iwilley.b1ec2.api.request.ShopOrderCreateRequest;
import com.iwilley.b1ec2.api.response.ShopOrderCreateResponse;
import entity.ID;
import entity.erp.Constants;
import entity.order.Order;
import entity.order.OrderLine;
import entity.order.OrderShip;
import entity.order.OrderSplit;
import service.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Wu on 16/3/7.
 * kakao china.
 */
public class ShopOrderPushMiddle {

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

    public String shopOrderPush(Long splitId) throws ApiException {

        B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY, Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);
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
        request.isMobile = true;                           //是否手机订单
//        request.discountFee = order.getDiscount().doubleValue();//折扣金额
        request.postFee = orderSplit.getShipFee().doubleValue();//邮费
        request.goodsTotal = orderSplit.getTotalFee().doubleValue();//商品总额
        request.orderTotal = orderSplit.getTotalPayFee().doubleValue();//应付金额
//        request.receivedTotal();                                 //实际收款
//        request.setShopPayTime();                                  //平台付款时间
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
//            shopOrderCreateLine.outerId();                          //外部编码
            shopOrderCreateLine.quantity = orderLine.getAmount();   //数量
            shopOrderCreateLine.price = orderLine.getPrice().doubleValue();//价格
            shopOrderCreateLine.itemName = orderLine.getSkuTitle();
            shopOrderCreateLine.skuName = orderLine.getSkuColor()+orderLine.getSkuSize();
            lines.add(shopOrderCreateLine);
        }
//        Logger.error("lines"+lines.get(0).getPrice());
//        Logger.error("lines"+lines.get(1).getPrice());
        //付款方式
//        List<ShopOrderCreatePayment> paymentList = new ArrayList<>();
//        request.setPaymentLines(paymentList);
        request.setItemLines(lines);
        ShopOrderCreateResponse response = client.execute(request);
        return response.getBody();     //返回平台订单编号
    }


}

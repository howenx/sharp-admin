package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.erp.ShopOrderCreateDiscount;
import entity.erp.ShopOrderCreateLine;
import entity.erp.ShopOrderCreatePayment;
import order.B1EC2Client;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sunny Wu 15/12/26.
 * 订单推送ERP
 */
public class ShopOrderCtrl extends Controller {

    public Result ShopOrderPush() {

        List<ShopOrderCreateLine> ItemLineInfo = new ArrayList<>();
        ShopOrderCreateLine shopOrderCreateLine = new ShopOrderCreateLine();
        shopOrderCreateLine.setShopLineNo("25689491");
        shopOrderCreateLine.setOuterId("77700232");
        shopOrderCreateLine.setQuantity(1);
        shopOrderCreateLine.setPrice(146.00);
        shopOrderCreateLine.setLineUdf1("");
        shopOrderCreateLine.setLineUdf2("");
        shopOrderCreateLine.setItemName("德国原产 SENSITIVE温和防敏无香型滋润护手霜100ml");
        shopOrderCreateLine.setSkuName("100ml 抗敏感");

        ItemLineInfo.add(shopOrderCreateLine);

        Map params = new HashMap<>();
        params.put("ShopOrderNo", "77700232");
        params.put("ShopId", 1);
        params.put("MemberNick", "xiaomaolv");
        params.put("OrderStatus", 10);
        params.put("ShopCreatedTime", "2015-12-25 16:01:28");
        params.put("ItemLineInfo", ItemLineInfo);


        params.put("OrderStatusName", "already");
        params.put("IsCod", false);
        params.put("IsDistribution", false);
        params.put("IsJhs", false);
        params.put("IsPresale", false);
        params.put("IsMobile", false);
        params.put("DiscountFee", 0);
        params.put("PostFee", 20.00);
        params.put("AdjustFee", 0);
        params.put("GoodsTotal", 146.00);
        params.put("OrderTotal", 166.00);
        params.put("ReceivedTotal", 166.00);
        params.put("ShopPayTime", "2015-12-25 16:06:28");
        params.put("BuyerMemo", "quickly");
        params.put("SellerMemo", "please wait");
        params.put("ShopFlag", "red");
        params.put("ReceiverName", "zhangsan");
        params.put("ReceiverState", "Peking");
        params.put("ReceiverCity", "Peking");
        params.put("ReceiverDistrict", "chaoyang");
        params.put("ReceiverAddress", "luowabuilding");
        params.put("ReceiverZip", "100000");
        params.put("ReceiverMobile", "12345678900");
        params.put("ReceiverPhone", "12345678");
        params.put("InvoiceType", 10);
        params.put("InvoiceMemo", "ordinary");
        params.put("InvoiceName", "Kakao");
        params.put("ExpressName", "JD");
        params.put("ExpressTrackNo", "123");
        params.put("BuyerAlipayNo", "qwe");
        params.put("BuyerEmail", "1323@qq..com");
        params.put("AlipayOrderNo", "");
        params.put("DeliveryTime", "2015-12-27 12:12:12");
        params.put("EndTime", "2015-12-29 13:24:45");
        params.put("UserDefinedField1", "");
        params.put("UserDefinedField2", "");


        List<ShopOrderCreateDiscount> DiscountLineInfo = new ArrayList<>();
        ShopOrderCreateDiscount shopOrderCreateDiscount = new ShopOrderCreateDiscount();
        shopOrderCreateDiscount.setDiscountName("first use");
        shopOrderCreateDiscount.setDiscountFee(20.00);
        DiscountLineInfo.add(shopOrderCreateDiscount);

        List<ShopOrderCreatePayment> PaymentLineInfo = new ArrayList<>();
        ShopOrderCreatePayment shopOrderCreatePayment = new ShopOrderCreatePayment();
        shopOrderCreatePayment.setPaymentId(1);
        shopOrderCreatePayment.setPaymentTotal(166.00);
        shopOrderCreatePayment.setPaymentNo("23456");
        PaymentLineInfo.add(shopOrderCreatePayment);

        params.put("DiscountLineInfo", DiscountLineInfo);
        params.put("PaymentLineInfo", PaymentLineInfo);

        String ShopOrderNo = B1EC2Client.post("http://121.43.186.32", "B1EC2.ShopOrder.Push", params, JsonNode.class).toString();

        return ok(ShopOrderNo);
    }
}

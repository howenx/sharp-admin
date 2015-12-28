package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.erp.ShopOrderCreateLine;
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




        String ShopOrderNo = B1EC2Client.post("http://121.43.186.32", "B1EC2.ShopOrder.Push", params, JsonNode.class).toString();

        return ok(ShopOrderNo);
    }
}

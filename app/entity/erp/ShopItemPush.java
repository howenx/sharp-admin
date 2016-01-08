package entity.erp;


import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.B1EC2Client;
import com.iwilley.b1ec2.api.domain.ShopSkuPushLine;
import com.iwilley.b1ec2.api.request.ShopItemPushRequest;
import com.iwilley.b1ec2.api.response.ShopItemPushResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/6.
 * 商品推送到ERP
 */
public class ShopItemPush {

    public static void main(String[] args) throws ParseException, ApiException {
        B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY,
                Constants.LOGIN_NAME, Constants.PASSWORD,Constants.SECRET);
        ShopItemPushRequest request = new ShopItemPushRequest();
        DateFormat format = new SimpleDateFormat(
                com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);
        request.shopItemName = "Milupa";
        request.shopItemCode = "17001";
        request.shopId = 1;
        request.status = "在售中";
        request.createdTime = format.parse("1970-01-01 16:00:00");
        request.updateTime = format.parse("2015-07-07 11:12:07");
        request.pictureUrl = "images/201412/thumb_img/1410_thumb_G_1418407930975.jpg";
        request.price = 22.00;
        request.quantity = 1000;
        request.weight = 0.320;

        List<ShopSkuPushLine> lineList = new ArrayList<ShopSkuPushLine>();
        ShopSkuPushLine ShopSkuPushLine1 = new ShopSkuPushLine();
        ShopSkuPushLine1.shopSkuCode = "17001";
        ShopSkuPushLine1.outerId = "ECS0001411";
        ShopSkuPushLine1.price = 22.00;
        ShopSkuPushLine1.quantity = 1000;
        ShopSkuPushLine1.weight = 0.320;
        request.setSkusInfo(lineList);
        ShopItemPushResponse response = client.execute(request);
        System.out.println(response.getBody());
    }

}

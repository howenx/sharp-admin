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
 * Created by Sunny Wu on 16/1/13.
 */
public class ShopItemPush {

    public static void main(String[] args) throws ParseException, ApiException {

        B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY, Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);
        ShopItemPushRequest request = new ShopItemPushRequest();
        DateFormat format = new SimpleDateFormat(com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);
        request.shopItemName = "SWAROVSKI CRYSTAL";
        request.shopItemCode = "20160113";
        request.shopId = 1;
        request.status = "在售中";
        request.createdTime = format.parse("2016-01-3 12:00:00");
        request.updateTime = format.parse("2016-01-3 12:00:01");
        request.pictureUrl = "http://hmm-images.oss-cn-beijing.aliyuncs.com/1b6dade7e7144786aa12f2ec4649d5551450835380847.jpg";
        request.price = 424.00;
        request.quantity = 999;
        request.weight = 0.71;

        List<ShopSkuPushLine> lineList = new ArrayList<>();
        ShopSkuPushLine shopSkuPushLine1 = new ShopSkuPushLine();
        shopSkuPushLine1.shopSkuCode = "1911";
        shopSkuPushLine1.outerId = "HMM0113001";
        shopSkuPushLine1.price = 424.00;
        shopSkuPushLine1.quantity = 999;
        shopSkuPushLine1.weight = 0.71;
        lineList.add(shopSkuPushLine1);

        request.setSkusInfo(lineList);
        ShopItemPushResponse response = client.execute(request);
        System.out.print(response.getBody());

    }
}

package entity.erp;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.B1EC2Client;
import com.iwilley.b1ec2.api.domain.ShopOrderCreateLine;
import com.iwilley.b1ec2.api.request.ShopOrderCreateRequest;
import com.iwilley.b1ec2.api.response.ShopOrderCreateResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/13.
 */
public class ShopOrderPush {

    public static void main(String[] args) throws ParseException, ApiException {

        B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY, Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);
        ShopOrderCreateRequest request = new ShopOrderCreateRequest();
        DateFormat format = new SimpleDateFormat(com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);
        request.memberNick = "terwq";
        request.shopOrderNo = "20160122008";
        request.shopId = 1;
        request.orderStatus = 10;
        request.shopCreatedTime = format.parse("2016-01-13 12:11:11");
        request.userDefinedField1 = "userDefinedField1";
        request.userDefinedField2 = "userDefinedField2";

        List<ShopOrderCreateLine> line1 = new ArrayList<>();
        ShopOrderCreateLine shopOrderCreateLine1 = new ShopOrderCreateLine();
        shopOrderCreateLine1.shopLineNo = "20160113121011";
        shopOrderCreateLine1.outerId = "HMM0113001";
        shopOrderCreateLine1.price = 123;
        shopOrderCreateLine1.quantity = 23;
        shopOrderCreateLine1.lineUdf1 = "lineUdf1";
        shopOrderCreateLine1.lineUdf2 = "lineUdf2";
        shopOrderCreateLine1.itemName  = "hmm";
        shopOrderCreateLine1.skuName  = "baise";
        line1.add(shopOrderCreateLine1);

        request.setItemLines(line1);
        ShopOrderCreateResponse response = client.execute(request);
        System.out.print(response.getBody());

    }
}

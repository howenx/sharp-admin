package entity.erp;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.B1EC2Client;
import com.iwilley.b1ec2.api.domain.ShopItem;
import com.iwilley.b1ec2.api.request.ShopItemQueryRequest;
import com.iwilley.b1ec2.api.response.ShopItemQueryResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Wu on 16/2/23.
 * kakao china.
 */
public class ShopItemOperate {

    private B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY, Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);

    public List<ShopItem> ShopItemQuery(String startTime, String endTime) throws ApiException, ParseException {
        List<ShopItem> shopItemList = new ArrayList<>();
        DateFormat format = new SimpleDateFormat(com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);
//        int pageSize = 8;
        ShopItemQueryRequest request = new ShopItemQueryRequest();
        request.setStartTime(format.parse(startTime));
        request.setEndTime(format.parse(endTime));
//        request.setPageSize(pageSize);
        ShopItemQueryResponse response = client.execute(request);
        shopItemList = response.getShopItems();
//        ShopItem
        return shopItemList;
    }

    public void ShopItemPush() {

    }


}

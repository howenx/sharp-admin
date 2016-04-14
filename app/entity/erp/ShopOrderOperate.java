package entity.erp;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.B1EC2Client;
import com.iwilley.b1ec2.api.request.ShopOrderCreateRequest;
import com.iwilley.b1ec2.api.response.SalesOrderQueryResponse;
import com.iwilley.b1ec2.api.response.ShopOrderCreateResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sunny Wu on 16/3/9.
 * kakao china.
 */
public class ShopOrderOperate {

    /**
     * ERP推送订单
     * @param request
     * @return 平台订单编号
     * @throws ApiException
     */
    public String ShopOrderPush(ShopOrderCreateRequest request) throws ApiException {
        B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY, Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);
        ShopOrderCreateResponse response = client.execute(request);
//        return Json.parse(response.getBody()).get("ShopOrderNo").asText();
        return response.getBody();
    }
    /**
     * ERP 订单查询
     * @param shopOrderNo 平台订单编号
     * @return
     * @throws ApiException
     * @throws ParseException
     */
    public List<Object> SalesOrderQuery(String shopOrderNo) throws ApiException, ParseException {
        B1EC2Client client = new B1EC2Client(Constants.URL, Constants.COMPANY, Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);
        List<Object> salesOrderList = new ArrayList<>();
        DateFormat format = new SimpleDateFormat(com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);
        int pageSize = 30;
        SalesOrderQueryRequestVo request = new SalesOrderQueryRequestVo();
        request.setShopOrderNo(shopOrderNo);
        request.setStartTime(format.parse("2000-01-01 00:00:00"));
        request.setEndTime(format.parse("2100-01-01 00:00:00"));
        request.setPageSize(pageSize);
        SalesOrderQueryResponse response = client.execute(request);
        if (response.getErrorCode()==0 && response.getTotalResults()>0) {
            //由总条数和每页条数得出总页数
            int totalPages = (int) Math.ceil((double) response.getTotalResults()/pageSize);
            for(int i=1;i<=totalPages;i++) {
                request.setPageNum(i);
                //每页的数据
                response = client.execute(request);
                //所有数据压入一个集合中
                salesOrderList.addAll(response.getSalesOrders().stream().collect(Collectors.toList()));
            }
        }
        return salesOrderList;
    }
}

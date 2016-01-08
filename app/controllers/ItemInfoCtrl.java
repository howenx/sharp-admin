package controllers;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.request.ShopItemQueryRequest;
import com.iwilley.b1ec2.api.response.ShopItemQueryResponse;
import entity.User;
import entity.erp.Constants;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Sunny Wu 15/12/22.
 * <p>
 * 查询ERP中所有的商品信息
 */
public class ItemInfoCtrl extends Controller {

    /**
     * ERP 商品资料查询
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemInfoList(String lang) throws ApiException, ParseException {

       com.iwilley.b1ec2.api.B1EC2Client client = new com.iwilley.b1ec2.api.B1EC2Client(Constants.URL, Constants.COMPANY,Constants.LOGIN_NAME, Constants.PASSWORD, Constants.SECRET);

        int pageSize = 10;
        DateFormat format = new SimpleDateFormat(com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);

        ShopItemQueryRequest request = new ShopItemQueryRequest();
        request.setStartTime(format.parse("2015-07-20 00:00:00"));
        request.setEndTime(format.parse("2016-01-07 00:00:00"));

        request.setPageSize(pageSize);

        ShopItemQueryResponse response = client.execute(request);

        int totalPages = 0;
        if (response.getErrorCode() == 0 && response.getTotalResults() > 0) {
            totalPages = (int) Math.ceil((double) response
                    .getTotalResults() / pageSize);

            //最多取5页数据
//            for (int i = 1; i <= totalPages && i <= 5; i++) {
//                request.setPageNum(i);
//                response = client.execute(request);
//                System.out.println("page:" + i + "/" + totalPages);
//
//                for (ShopItem shopItem : response.getShopItems()) {
//                    System.out.println("Item Info:" + shopItem.getShopItemCode() + ","
//                            + shopItem.getShopItemName());
//
//                    for (ShopSku shopSku : shopItem.getLines()) {
//                        System.out.println("  SKU Info:" + shopSku.getShopSkuCode()
//                                + "," + shopSku.getProperties());
//                    }
//                }
//                System.out.println();
//            }
        }

        //取总数
        int countNum = response.getTotalResults();
        //共分几页
        int pageCount = totalPages;
//        if(countNum%ThemeCtrl.PAGE_SIZE!=0){
//            pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
//        }
        return ok(views.html.item.erp_itemlist.render(lang,response.getShopItems(),ThemeCtrl.PAGE_SIZE,countNum,pageCount, (User)ctx().args.get("user")));

    }


    /**
     * ERP 商品资料分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemInfoSearchAjax(String lang, int pageNum) {

        return ok();
    }
}

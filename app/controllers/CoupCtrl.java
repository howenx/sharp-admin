package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Coupons;
import entity.User;
import filters.UserAuth;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.CouponsService;
import service.IDService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class CoupCtrl extends Controller {

    @Inject
    private CouponsService couponsService;

    @Inject
    private IDService idService;

    private int pageSize = 5;

    /**
     * 发放优惠券
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupCreate(String lang) {
        return ok(views.html.coupon.coupadd.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 保存优惠券
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupSave() {
        JsonNode json = request().body().asJson();
        couponsService.couponsSave(json);
        return ok("保存成功");
    }

    /**
     * 查询已使用过的优惠券       Added By Sunny.Wu 2016.03.08
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupSearch(String lang) {
        Coupons coupons = new Coupons();
        coupons.setPageSize(-1);
        coupons.setOffset(-1);
        int countNum = couponsService.getAllUsedCoupons().size();//取总数
        Logger.error(couponsService.getAllUsedCoupons().toString());
        int pageCount = countNum/pageSize;//共分几页
        if (countNum%pageSize!=0) {
            pageCount = countNum/pageSize+1;
        }
        coupons.setPageSize(pageSize);
        coupons.setOffset(0);
        List<Coupons> couponsList = couponsService.getUsedCouponsPage(coupons);
        for(Coupons coup : couponsList) {
            String phoneNum = idService.getID(coup.getUserId().intValue()).getPhoneNum();
            coup.setUserId(Long.parseLong(phoneNum));//用户id字段保存用户的手机号
        }
        Logger.error(countNum+","+pageCount+","+(User) ctx().args.get("user")+couponsList.toString());
//        Lo
        return ok(views.html.coupon.coupsearch.render(lang, pageSize, countNum, pageCount, couponsList, (User) ctx().args.get("user")));
    }

    /**
     * 已使用优惠券分页查询列表         Added By Sunny.Wu 2016.03.08
     * @param lang 语言
     * @param pageNum 请求页数
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupSearchAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        Coupons coupons = Json.fromJson(json,Coupons.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*pageSize;
            coupons.setPageSize(-1);
            coupons.setOffset(-1);
            int countNum = couponsService.getUsedCouponsPage(coupons).size();//取总数
            int pageCount = countNum/pageSize;//共分几页
            if (countNum%pageSize!=0) {
                pageCount = countNum/pageSize+1;
            }
            coupons.setPageSize(pageSize);
            coupons.setOffset(offset);
            Logger.error(coupons+"");
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            List<Coupons> couponsList = couponsService.getUsedCouponsPage(coupons);
            Logger.error(couponsList+"");
            for(Coupons coup : couponsList) {
                String phoneNum = idService.getID(coup.getUserId().intValue()).getPhoneNum();
                coup.setUserId(Long.parseLong(phoneNum));//用户id字段保存用户的手机号
            }
            returnMap.put("topic",couponsList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",pageSize);
            Logger.error(returnMap.toString());
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

}

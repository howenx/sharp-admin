package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import domain.Coupons;
import domain.User;
import domain.order.Order;
import filters.UserAuth;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.CouponsService;
import service.IDService;
import service.OrderService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class CoupCtrl extends Controller {

    @Inject
    private CouponsService couponsService;

    @Inject
    private IDService idService;

    @Inject
    private OrderService orderService;

    public static final int pageSize = 10;

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
        //--------------------数据验证------------------start
        Date now = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNow = sdfDate.format(now);//现在时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH,+6);
        String maxDate = sdfDate.format(calendar.getTime());
        for(final JsonNode jsonNode : json) {
            Form<Coupons> couponsForm = Form.form(Coupons.class).bind(jsonNode);
            Coupons coupons  = Json.fromJson(jsonNode, Coupons.class);
            String startAt = coupons.getStartAt();
            String endAt = coupons.getEndAt();
            //数据验证(限额不能小于0,面值不能小于0,开始时间不能大于结束时间,结束时间不能小于现在时间,开始时间和结束时间不能超过当前时间6个月)
            if (couponsForm.hasErrors() || coupons.getLimitQuota().compareTo(new BigDecimal(0.00))<0  || coupons.getDenomination().compareTo(new BigDecimal(0.00))<0
                   || startAt.compareTo(endAt)>0 || endAt.compareTo(strNow)<0 || startAt.compareTo(maxDate)>0 || endAt.compareTo(maxDate)>0) {
                Logger.error("coupon 表单数据有误.....");
                return badRequest();
            }
        }
        //--------------------数据验证------------------start
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
        int countNum = couponsService.getUsedCouponsPage(coupons).size();//取总数
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
            List<Coupons> couponsList = couponsService.getUsedCouponsPage(coupons);
            for(Coupons coup : couponsList) {
                String phoneNum = idService.getID(coup.getUserId().intValue()).getPhoneNum();
                coup.setUserId(Long.parseLong(phoneNum));//用户id字段保存用户的手机号
                Order order = orderService.getOrderById(coup.getOrderId());
                if (null != order)
                    coup.setLimitQuota(order.getPayTotal());//优惠券限额字段保存订单的金额
                else coup.setLimitQuota(BigDecimal.valueOf(0));
//                Logger.error(coup.toString());
            }
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",couponsList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",pageSize);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

}

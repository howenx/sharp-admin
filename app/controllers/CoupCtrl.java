package controllers;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import domain.*;
import domain.order.Order;
import filters.UserAuth;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.*;
import util.SysParCom;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

    @Inject
    private InventoryService inventoryService;

    @Inject
    private ItemService itemService;

    @Inject
    private ThemeService themeService;

    @Inject
    @Named("couponSendActor")
    private ActorRef couponSendActor;

    private static final int PAGE_SIZE = 30;

    /**
     * 发放优惠券
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupCreate(String lang) {
        List<CouponsCate> couponsCateList = couponsService.getSendCouponsCate();
        return ok(views.html.coupon.coupadd.render(lang, couponsCateList, (User) ctx().args.get("user")));
    }

    /**
     * 保存优惠券
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupSave() {
        JsonNode json = request().body().asJson();
        //--------------------数据验证------------------start
//        Date now = new Date();
//        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String strNow = sdfDate.format(now);//现在时间
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(now);
//        calendar.add(Calendar.MONTH,+6);
//        String maxDate = sdfDate.format(calendar.getTime());
        for(final JsonNode jsonNode : json) {
            CouponRec couponRec  = Json.fromJson(jsonNode, CouponRec.class);
//            Form<Coupons> couponsForm = Form.form(Coupons.class).bind(jsonNode);
//            String startAt = coupons.getStartAt();
//            String endAt = coupons.getEndAt();
//            //数据验证(限额不能小于0,面值不能小于0,开始时间不能大于结束时间,结束时间不能小于现在时间,开始时间和结束时间不能超过当前时间6个月)
//            if (couponsForm.hasErrors() || coupons.getLimitQuota().compareTo(new BigDecimal(0.00))<0  || coupons.getDenomination().compareTo(new BigDecimal(0.00))<0
//                   || startAt.compareTo(endAt)>0 || endAt.compareTo(strNow)<0 || startAt.compareTo(maxDate)>0 || endAt.compareTo(maxDate)>0) {
//                Logger.error("coupon 表单数据有误.....");
//                return badRequest();
//            }
            couponSendActor.tell(couponRec, null);
        }
        //--------------------数据验证------------------start
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
        int pageCount = countNum/PAGE_SIZE;//共分几页
        if (countNum%PAGE_SIZE!=0) {
            pageCount = countNum/PAGE_SIZE+1;
        }
        return ok(views.html.coupon.coupsearch.render(lang, PAGE_SIZE, countNum, pageCount, (User) ctx().args.get("user")));
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
            int offset = (pageNum-1)*PAGE_SIZE;
            coupons.setPageSize(-1);
            coupons.setOffset(-1);
            int countNum = couponsService.getUsedCouponsPage(coupons).size();//取总数
            int pageCount = countNum/PAGE_SIZE;//共分几页
            if (countNum%PAGE_SIZE!=0) {
                pageCount = countNum/PAGE_SIZE+1;
            }
            coupons.setPageSize(PAGE_SIZE);
            coupons.setOffset(offset);
            List<Coupons> couponsList = couponsService.getUsedCouponsPage(coupons);
            for(Coupons coup : couponsList) {
                coup.setCoupCateNm(couponsService.getCouponsCate(coup.getCoupCateId()).getCoupCateNm());
                String phoneNum = idService.getID(coup.getUserId().intValue()).getPhoneNum();
                coup.setUserId(Long.parseLong(phoneNum));//用户id字段保存用户的手机号
                Order order = orderService.getOrderById(coup.getOrderId());
                if (null != order)
                    coup.setLimitQuota(order.getPayTotal());//优惠券限额字段保存订单的金额
                else coup.setLimitQuota(BigDecimal.valueOf(0));
            }
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",couponsList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",PAGE_SIZE);
//            Logger.error(Json.toJson(returnMap).toString());
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 新增优惠券类别
     * @param lang 语言       Added By Sunny Wu 2016.08.18
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupCateCreate(String lang) {
        return ok(views.html.coupon.coupcateadd.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 保存优惠券类别          Added By Sunny Wu 2016.08.18
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupCateSave() {
        JsonNode json = request().body().asJson();
        Logger.error("json数据:"+json);
        //--------------------数据验证------------------start
        Date now = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNow = sdfDate.format(now);//现在时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH,+6);
        String maxDate = sdfDate.format(calendar.getTime());
        CouponsCate couponsCate = new CouponsCate();
        if (json.has("couponsCate")) {
            JsonNode jsonCouponsCate = json.findValue("couponsCate");
            couponsCate = Json.fromJson(jsonCouponsCate,CouponsCate.class);
//            couponsCate.setCouponType(2);
            Form<CouponsCate> couponsCateForm = Form.form(CouponsCate.class).bind(jsonCouponsCate);
            String startAt = couponsCate.getStartAt();
            String endAt = couponsCate.getEndAt();
//            Logger.error("优惠券类别信息:"+couponsCate.toString());
            //数据验证(限额不能小于0,面值不能小于0,开始时间不能大于结束时间,结束时间不能小于现在时间,开始时间和结束时间不能超过当前时间6个月)
            if (couponsCateForm.hasErrors() || couponsCate.getLimitQuota().compareTo(new BigDecimal(0.00))<0  || couponsCate.getDenomination().compareTo(new BigDecimal(0.00))<0
                    || startAt.compareTo(endAt)>0 || endAt.compareTo(strNow)<0 || startAt.compareTo(maxDate)>0 || endAt.compareTo(maxDate)>0) {
                Logger.error("CouponsCate 表单数据有误.....");
                return badRequest();
            }
        }
        if (json.has("couponsMapList")) {
            for (final JsonNode jsonNode : json.findValue("couponsMapList")) {
                CouponsMap couponsMap = Json.fromJson(jsonNode,CouponsMap.class);
                Form<CouponsMap> couponsMapForm = Form.form(CouponsMap.class).bind(jsonNode);
//                Logger.error("优惠券类别映射信息:"+couponsMap.toString());
                //数据验证
                if (couponsMapForm.hasErrors()) {
                    Logger.error("cateType: " + couponsMap.getCateType() + ", cateTypeId: " + couponsMap.getCateTypeId() + ", CouponsMap 表单数据有误.....");
                    return badRequest();
                }
            }
        }
        couponsService.couponsCateSave(json);
        //--------------------数据验证------------------start
        return ok("保存成功");
    }

    /**
     * 查询优惠券类别       Added By Sunny.Wu 2016.08.19
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupCateSearch(String lang) {
        return ok(views.html.coupon.coupcatesearch.render(lang, couponsService.getAllCouponsCate(), (User) ctx().args.get("user")));
    }

    /**
     * 修改优惠券指定的商品   Added By Sunny.Wu 2016.08.31
     * @param lang 语言
     * @param coupCateId 优惠券类别ID
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupCateUpdate(String lang, Long coupCateId) {
        CouponsCate couponsCate = couponsService.getCouponsCate(coupCateId);
        List<CouponsMap> couponsMapList = couponsService.getCouponsMapByCateId(coupCateId);
        Logger.error(couponsCate.toString());
//        Logger.error(couponsMapList.toString());
        String assignType = "";
        if ((couponsMapList.size()==1 && couponsMapList.get(0).getCateType()==1) || couponsMapList.size()==0)
            assignType = "none";
        else
            assignType = "assign";
        List<Inventory> itemList = new ArrayList<>();
        List<Skus> skusList = new ArrayList<>();
        List<Cates> catesList = new ArrayList<>();
        List<Theme> themeList = new ArrayList<>();
        for(CouponsMap couponsMap : couponsMapList) {
            Integer cateType = couponsMap.getCateType();
            Long cateTypeId = couponsMap.getCateTypeId();
            if (cateType == 2) {
                Inventory inventory = inventoryService.getMasterInventory(cateTypeId);
                String url = Json.parse(inventory.getInvImg()).get("url")==null?"":Json.parse(inventory.getInvImg()).get("url").asText();
                inventory.setInvImg(url);
                itemList.add(inventory);
            } else if (cateType==3 || cateType==4) {
                Skus skus = inventoryService.getByTypeId(cateTypeId);
                String url = Json.parse(skus.getSkuTypeImg()).get("url")==null?"":Json.parse(skus.getSkuTypeImg()).get("url").asText();
                skus.setSkuTypeImg(url);
                skusList.add(skus);
            } else if (cateType==5 || cateType==7) {
                Cates cates = itemService.getCate(cateTypeId);
                catesList.add(cates);
            } else if (cateType == 6) {
                Theme theme = themeService.getThemeById(cateTypeId);
                String url = Json.parse(theme.getThemeImg()).get("url")==null?"":Json.parse(theme.getThemeImg()).get("url").asText();
                theme.setThemeImg(url);
                themeList.add(theme);
            }
        }
        Logger.error("itemList:"+itemList.toString());
        Logger.error("skusList:"+skusList.toString());
        Logger.error("catesList:"+catesList.toString());
        Logger.error("themeList:"+themeList.toString());
        return ok(views.html.coupon.coupcateupdate.render(lang, couponsCate, itemList, skusList, catesList, themeList, assignType, SysParCom.IMAGE_URL, (User) ctx().args.get("user")));
    }


}

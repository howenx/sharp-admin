package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.User;
import entity.pingou.*;
import filters.UserAuth;
import play.Logger;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.PingouService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by tiffany on 16/1/19.
 */
public class PingouCtrl extends Controller {

    @Inject
    PingouService pingouService;
    /**
     * 添加拼购     Added by Tiffany Zhu 2016.01.19
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result addPingou(String lang){

        return ok(views.html.pingou.pingouAdd.render(lang,ThemeCtrl.IMAGE_URL,(User) ctx().args.get("user")));

    }

    /**
     * 保存拼购Sku Added by Tiffany Zhu 2016.01.21
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pingouSave(String lang){
        JsonNode json = request().body().asJson();
        pingouService.pinSkuSave(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 拼购列表 Added by Tiffany Zhu 2016.01.20
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pingouSearch(String lang){
        PinSku pinSku_temp = new PinSku();
        pinSku_temp.setPageSize(-1);
        pinSku_temp.setOffset(-1);
        int countNum = pingouService.getPinSkuAll().size();
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;
        if(countNum%ThemeCtrl.PAGE_SIZE != 0){
            pageCount =  countNum/ThemeCtrl.PAGE_SIZE + 1;
        }
        pinSku_temp.setPageSize(ThemeCtrl.PAGE_SIZE);
        pinSku_temp.setOffset(0);

        //拼购列表
        List<Object[]> rtnPinSkuList = new ArrayList<>();
        List<PinSku> pinSkuList = pingouService.getPinSkuPage(pinSku_temp);
        for(PinSku pinSku : pinSkuList){
            Object[] object = new Object[6];
            object[0] = pinSku.getPinId();      //活动ID
            object[1] = pinSku.getPinTitle();   //商品标题
            object[2] = pinSku.getStartAt();    //开始时间
            object[3] = pinSku.getEndAt();      //结束时间
            if("Y".equals(pinSku.getStatus())){
                object[4] = "正常";              //状态
            }
            if("".equals(pinSku.getStatus())){
                object[4] = "下架";              //状态
            }
            if("N".equals(pinSku.getStatus())){
                object[4] = "删除";              //状态
            }
            if("K".equals(pinSku.getStatus())){
                object[4] = "售空";              //状态
            }
            if("P".equals(pinSku.getStatus())){
                object[4] = "预售";              //状态
            }

            object[5] = pinSku.getActivityCount(); //已开团数
            rtnPinSkuList.add(object);
        }
        return ok(views.html.pingou.pingouSearch.render(lang,ThemeCtrl.PAGE_SIZE,countNum,pageCount,rtnPinSkuList,(User) ctx().args.get("user")));

    }

    /**
     * 拼购ajax查询     Added by Tiffany Zhu 2016.01.21
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result getPinSkuPage(String lang,int pageNum){
        JsonNode json = request().body().asJson();
        PinSku pinSku = Json.fromJson(json,PinSku.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            pinSku.setPageSize(-1);
            pinSku.setOffset(-1);
            //取总数
            int countNum = pingouService.getPinSkuPage(pinSku).size();
            //共分几页
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
            pinSku.setPageSize(ThemeCtrl.PAGE_SIZE);
            pinSku.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",pingouService.getPinSkuPage(pinSku));
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",ThemeCtrl.PAGE_SIZE);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 通过ID获取拼购    Added by Tiffany Zhu 2016.01.22
     * @param lang
     * @param pinId
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result getPinSkuById(String lang,Long pinId){

        PinSku pinSku = pingouService.getPinSkuById(pinId);
        JsonNode imgJson = Json.parse(pinSku.getPinImg());
        Object[] img = new Object[3];
        String imgUrl =  imgJson.get("url").toString();
        img[0] = imgUrl.substring(1,imgUrl.length()-1);
        img[1] = imgJson.get("width");
        img[2] = imgJson.get("height");
        List<PinTieredPrice> tieredPrices = pingouService.getTieredPriceByPinId(pinId);
        List<Object[]> tieredList = new ArrayList<>();
        int index = 0;
        for(PinTieredPrice price : tieredPrices){
            index = index + 1;
            Object[] object = new Object[18];
            object[0] = price.getId();                  //阶梯价格的Id
            object[1] = index;                          //编号
            object[2] = price.getPeopleNum();           //人数
            object[3] = price.getPrice();               //价格
            object[4] = price.getMasterMinPrice();      //团长减价
            object[5] = price.getMemberMinPrice();      //团员减价
            object[6] = price.getMasterCouponClass();   //团长优惠券类别
            if("153".equals(price.getMasterCouponClass())){
                object[7] = "化妆品类商品适用券";          //团长优惠券类别
            }
            if("172".equals(price.getMasterCouponClass())){
                object[7] = "配饰类商品适用券";             //团长优惠券类别
            }
            if("165".equals(price.getMasterCouponClass())){
                object[7] = "服饰类商品适用券";              //团长优惠券类别
            }
            if("555".equals(price.getMasterCouponClass())){
                object[7] = "全场通用券";                     //团长优惠券类别
            }
            if("777".equals(price.getMasterCouponClass())){
                object[7] = "新人优惠券";                     //团长优惠券类别
            }
            if("211".equals(price.getMasterCouponClass())){
                object[7] = "指定商品适用券";                 //团长优惠券类别
            }
            if("999".equals(price.getMasterCouponClass())){
                object[7] = "免邮券";                       //团长优惠券类别
            }
            object[8] = price.getMasterCouponQuota();   //团长优惠券限额
            object[9] = price.getMasterCoupon();        //团长优惠券面值
            if(price.getMasterCouponStartAt() != null){
                object[10] = price.getMasterCouponStartAt().toString().substring(0,19);//团长优惠券开始日期
            }
            if(price.getMasterCouponEndAt() != null){
                object[11] = price.getMasterCouponEndAt().toString().substring(0,19);  //团长优惠券结束日期
            }
            object[12] = price.getMemberCouponClass();  //团员优惠券类别
            if("153".equals(price.getMemberCouponClass())){
                object[13] = "化妆品类商品适用券";        //团员优惠券类别
            }
            if("172".equals(price.getMemberCouponClass())){
                object[13] = "配饰类商品适用券";         //团员优惠券类别
            }
            if("165".equals(price.getMemberCouponClass())){
                object[13] = "服饰类商品适用券";            //团员优惠券类别
            }
            if("555".equals(price.getMemberCouponClass())){
                object[13] = "全场通用券";                //团员优惠券类别
            }
            if("777".equals(price.getMemberCouponClass())){
                object[13] = "新人优惠券";               //团员优惠券类别
            }
            if("211".equals(price.getMemberCouponClass())){
                object[13] = "指定商品适用券";           //团员优惠券类别
            }
            if("999".equals(price.getMemberCouponClass())){
                object[13] = "免邮券";                  //团员优惠券类别
            }
            object[14] = price.getMemberCouponQuota();  //团员优惠券限额
            object[15] = price.getMemberCoupon();       //团员优惠券面值
            if(price.getMemberCouponStartAt() != null){
                object[16] = price.getMemberCouponStartAt().toString().substring(0,19);//团员优惠券开始日期
            }
            if(price.getMemberCouponEndAt() != null){
                object[17] = price.getMemberCouponEndAt().toString().substring(0,19);  //团员优惠券届时日期
            }
            tieredList.add(object);
        }
        return ok(views.html.pingou.pingouUpdate.render(lang,pinSku,img,tieredList,ThemeCtrl.IMAGE_URL,(User) ctx().args.get("user")));
    }

    /**
     * 手动添加拼购团      Added by Tiffany Zhu 2016.02.14
     * @param lang
     * @param pinId
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result activityManualAdd(String lang, Long pinId){
        PinSku pinSku = pingouService.getPinSkuById(pinId);
        JsonNode imgJson = Json.parse(pinSku.getPinImg());
        List<PinTieredPrice> tieredPrices = pingouService.getTieredPriceByPinId(pinId);
        List<Object[]> tieredList = new ArrayList<>();
        int index = 0;
        for(PinTieredPrice price : tieredPrices){
            index = index + 1;
            Object[] object = new Object[18];
            object[0] = price.getId();                  //阶梯价格的Id
            object[1] = index;                          //编号
            object[2] = price.getPeopleNum();           //人数
            object[3] = price.getPrice();               //价格
            object[4] = price.getMasterMinPrice();      //团长减价
            object[5] = price.getMemberMinPrice();      //团员减价
            object[6] = price.getMasterCouponClass();   //团长优惠券类别
            if("153".equals(price.getMasterCouponClass())){
                object[7] = "化妆品类商品适用券";          //团长优惠券类别
            }
            if("172".equals(price.getMasterCouponClass())){
                object[7] = "配饰类商品适用券";             //团长优惠券类别
            }
            if("165".equals(price.getMasterCouponClass())){
                object[7] = "服饰类商品适用券";              //团长优惠券类别
            }
            if("555".equals(price.getMasterCouponClass())){
                object[7] = "全场通用券";                     //团长优惠券类别
            }
            if("777".equals(price.getMasterCouponClass())){
                object[7] = "新人优惠券";                     //团长优惠券类别
            }
            if("211".equals(price.getMasterCouponClass())){
                object[7] = "指定商品适用券";                 //团长优惠券类别
            }
            if("999".equals(price.getMasterCouponClass())){
                object[7] = "免邮券";                       //团长优惠券类别
            }
            object[8] = price.getMasterCouponQuota();   //团长优惠券限额
            object[9] = price.getMasterCoupon();        //团长优惠券面值
            if(price.getMasterCouponStartAt() != null){
                object[10] = price.getMasterCouponStartAt().toString().substring(0,19);//团长优惠券开始日期
            }
            if(price.getMasterCouponEndAt() != null){
                object[11] = price.getMasterCouponEndAt().toString().substring(0,19);  //团长优惠券结束日期
            }
            object[12] = price.getMemberCouponClass();  //团员优惠券类别
            if("153".equals(price.getMemberCouponClass())){
                object[13] = "化妆品类商品适用券";        //团员优惠券类别
            }
            if("172".equals(price.getMemberCouponClass())){
                object[13] = "配饰类商品适用券";         //团员优惠券类别
            }
            if("165".equals(price.getMemberCouponClass())){
                object[13] = "服饰类商品适用券";            //团员优惠券类别
            }
            if("555".equals(price.getMemberCouponClass())){
                object[13] = "全场通用券";                //团员优惠券类别
            }
            if("777".equals(price.getMemberCouponClass())){
                object[13] = "新人优惠券";               //团员优惠券类别
            }
            if("211".equals(price.getMemberCouponClass())){
                object[13] = "指定商品适用券";           //团员优惠券类别
            }
            if("999".equals(price.getMemberCouponClass())){
                object[13] = "免邮券";                  //团员优惠券类别
            }
            object[14] = price.getMemberCouponQuota();  //团员优惠券限额
            object[15] = price.getMemberCoupon();       //团员优惠券面值
            if(price.getMemberCouponStartAt() != null){
                object[16] = price.getMemberCouponStartAt().toString().substring(0,19);//团员优惠券开始日期
            }
            if(price.getMemberCouponEndAt() != null){
                object[17] = price.getMemberCouponEndAt().toString().substring(0,19);  //团员优惠券届时日期
            }
            tieredList.add(object);
        }
        return ok(views.html.pingou.activityManualAdd.render(lang,pinSku,tieredList,(User) ctx().args.get("user")));
    }

    /**
     * 手动开团     Added by Tiffany Zhu 2016.02.15
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result activityManualSave(String lang){
        JsonNode json = request().body().asJson();
        PinSku pinSku = new PinSku();
        if(json.has("pinId")){
            pinSku = pingouService.getPinSkuById(json.get("pinId").asLong());
        }
        PinTieredPrice pinTieredPrice = new PinTieredPrice();
        if(json.has("tieredPriceId")){
            pinTieredPrice = pingouService.getTieredPriceByTieredId(json.get("tieredPriceId").asLong());
        }
        Logger.error(pinSku.toString());
        Logger.error(pinTieredPrice.toString());
        if(pinSku != null && pinTieredPrice != null){
            Long userId = new Long(1111111);            //机器人用户ID
            //添加拼购活动
            PinActivity pinActivity = new PinActivity();
            pinActivity.setPinUrl("");                              //分享链接
            pinActivity.setPinId(pinSku.getPinId());                //拼购商品ID
            pinActivity.setMasterUserId(userId);                    //机器人用户ID
            pinActivity.setPersonNum(pinTieredPrice.getPeopleNum());//拼购人数
            pinActivity.setJoinPersons(1);                          //参加拼购的人数
            pinActivity.setPinPrice(pinTieredPrice.getPrice());     //拼购价格
            pinActivity.setStatus("Y");                             //拼购状态--正常
            pinActivity.setEndAt(pinSku.getEndAt());                //截止时间
            pingouService.activityManualAdd(pinActivity);

            //拼购活动优惠券
            if(pinTieredPrice.getMasterCouponClass() != null || pinTieredPrice.getMemberCouponClass() != null){
                PinCoupon pinCoupon = new PinCoupon();
                pinCoupon.setPinActiveId(pinActivity.getPinActiveId());                     //拼购活动ID
                if(pinTieredPrice.getMasterCouponClass() == null){
                    pinCoupon.setMasterCoupon(null);                //团长返券额度
                    pinCoupon.setMasterCouponClass(null);      //团长返券类别
                    pinCoupon.setMasterCouponStartAt(null);  //团长优惠券开始时间
                    pinCoupon.setMasterCouponEndAt(null);      //团长优惠券结束时间
                    pinCoupon.setMasterCouponQuota(null);      //团长优惠券限额

                }else{
                    pinCoupon.setMasterCoupon(pinTieredPrice.getMasterCoupon());                //团长返券额度
                    pinCoupon.setMasterCouponClass(pinTieredPrice.getMasterCouponClass());      //团长返券类别
                    pinCoupon.setMasterCouponStartAt(pinTieredPrice.getMasterCouponStartAt());  //团长优惠券开始时间
                    pinCoupon.setMasterCouponEndAt(pinTieredPrice.getMasterCouponEndAt());      //团长优惠券结束时间
                    pinCoupon.setMasterCouponQuota(pinTieredPrice.getMasterCouponQuota());      //团长优惠券限额
                }
                if(pinTieredPrice.getMemberCouponClass() == null){
                    pinCoupon.setMemberCoupon(null);                //团员返券额度
                    pinCoupon.setMemberCouponClass(null);      //团员返券类别
                    pinCoupon.setMemberCouponStartAt(null);  //团员优惠券开始时间
                    pinCoupon.setMemberCouponEndAt(null);      //团员优惠券结束时间
                    pinCoupon.setMemberCouponQuota(null);      //团员优惠券限额
                }else {
                    pinCoupon.setMemberCoupon(pinTieredPrice.getMemberCoupon());                //团员返券额度
                    pinCoupon.setMemberCouponClass(pinTieredPrice.getMemberCouponClass());      //团员返券类别
                    pinCoupon.setMemberCouponStartAt(pinTieredPrice.getMemberCouponStartAt());  //团员优惠券开始时间
                    pinCoupon.setMemberCouponEndAt(pinTieredPrice.getMemberCouponEndAt());      //团员优惠券结束时间
                    pinCoupon.setMemberCouponQuota(pinTieredPrice.getMemberCouponQuota());      //团员优惠券限额
                }
                pingouService.activityManualAddCoupon(pinCoupon);
            }


            //拼购活动用户
            PinUser pinUser = new PinUser();
            pinUser.setUserId(userId);                              //用户ID
            pinUser.setOrMaster(true);                              //是否团长
            pinUser.setPinActiveId(pinActivity.getPinActiveId());   //拼购活动ID
            pinUser.setUserIp(request().remoteAddress());           //IP地址
            pinUser.setOrRobot(true);                               //是否机器人
            pinUser.setUserImg("");                                 //用户头像
            pingouService.pinUserAdd(pinUser);
        }
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 拼购活动查询       Added by Tiffany Zhu 2016.02.16
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pinActivitySearch(String lang){
        PinActivity pinActivity = new PinActivity();
        pinActivity.setPageSize(-1);
        pinActivity.setOffset(-1);
        int countNum = pingouService.getActivityAll().size();
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;
        if(countNum%ThemeCtrl.PAGE_SIZE != 0){
            pageCount =  countNum/ThemeCtrl.PAGE_SIZE + 1;
        }
        pinActivity.setPageSize(ThemeCtrl.PAGE_SIZE);
        pinActivity.setOffset(0);

        //拼购列表
        List<Object[]> rtnPinActivityList = new ArrayList<>();
        List<PinActivity> pinActivityList = pingouService.getPinActivityPage(pinActivity);
        for(PinActivity pinActivityTemp : pinActivityList){
            PinSku pinSku = pingouService.getPinSkuById(pinActivityTemp.getPinId());
            Object[] object = new Object[9];
            object[0] = pinActivityTemp.getPinActiveId();
            object[1] = pinActivityTemp.getPinId();
            object[2] = pinActivityTemp.getMasterUserId();
            object[3] = pinActivityTemp.getPersonNum();
            object[4] = pinActivityTemp.getPinPrice();
            object[5] = pinActivityTemp.getJoinPersons();
            object[6] = pinActivityTemp.getCreateAt();
            object[7] = pinActivityTemp.getEndAt();
            object[8] = pinSku.getPinTitle();
            rtnPinActivityList.add(object);
        }
        return ok(views.html.pingou.activitySearch.render(lang,ThemeCtrl.PAGE_SIZE,countNum,pageCount,rtnPinActivityList,(User) ctx().args.get("user")));
    }

    /**
     * 拼购活动 ajax 分页查询        Added by Tiffany Zhu 2016.02.16
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result getPinActivityPage(String lang,int pageNum){
        JsonNode json = request().body().asJson();
        Logger.error(json.toString());
        PinActivity pinActivity = Json.fromJson(json,PinActivity.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            pinActivity.setPageSize(-1);
            pinActivity.setOffset(-1);
            //取总数
            int countNum = pingouService.getPinActivityPage(pinActivity).size();
            //共分几页
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
            pinActivity.setPageSize(ThemeCtrl.PAGE_SIZE);
            pinActivity.setOffset(offset);

            List<PinActivity> activityList = pingouService.getPinActivityPage(pinActivity);
            List<PinActivity> rtnList = new ArrayList<>();
            for(PinActivity pinActivityTemp : activityList){
                PinSku pinSku = pingouService.getPinSkuById(pinActivityTemp.getPinId());
                pinActivityTemp.setPinTitle(pinSku.getPinTitle());
                rtnList.add(pinActivityTemp);
            }
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",rtnList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",ThemeCtrl.PAGE_SIZE);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 拼购活动详情       Added by Tiffany Zhu 2016.02.17
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result activityDetail(String lang,Long id){
        //拼购团基本信息
        PinActivity pinActivity = pingouService.getActivityById(id);
        PinSku pinSku = pingouService.getPinSkuById(pinActivity.getPinId());
        pinActivity.setPinTitle(pinSku.getPinTitle());
        //拼购团优惠券
        PinCoupon pinCoupon = pingouService.getCouponByActivityId(id);
        //团长优惠券类型
        if("153".equals(pinCoupon.getMasterCouponClass())){
            pinCoupon.setMasterCouponClass("化妆品类商品适用券"); //团长优惠券类别
        }
        if("172".equals(pinCoupon.getMasterCouponClass())){
            pinCoupon.setMasterCouponClass("配饰类商品适用券");  //团长优惠券类别
        }
        if("165".equals(pinCoupon.getMasterCouponClass())){
            pinCoupon.setMasterCouponClass("服饰类商品适用券");  //团长优惠券类别
        }
        if("555".equals(pinCoupon.getMasterCouponClass())){
            pinCoupon.setMasterCouponClass("全场通用券");       //团长优惠券类别
        }
        if("777".equals(pinCoupon.getMasterCouponClass())){
            pinCoupon.setMasterCouponClass("新人优惠券");       //团长优惠券类别
        }
        if("211".equals(pinCoupon.getMasterCouponClass())){
            pinCoupon.setMasterCouponClass("指定商品适用券");   //团长优惠券类别
        }
        if("999".equals(pinCoupon.getMasterCouponClass())){
            pinCoupon.setMasterCouponClass("免邮券");          //团长优惠券类别
        }
        //团员优惠券类型
        if("153".equals(pinCoupon.getMemberCouponClass())){
            pinCoupon.setMemberCouponClass("化妆品类商品适用券"); //团长优惠券类别
        }
        if("172".equals(pinCoupon.getMemberCouponClass())){
            pinCoupon.setMemberCouponClass("配饰类商品适用券");  //团长优惠券类别
        }
        if("165".equals(pinCoupon.getMemberCouponClass())){
            pinCoupon.setMemberCouponClass("服饰类商品适用券");  //团长优惠券类别
        }
        if("555".equals(pinCoupon.getMemberCouponClass())){
            pinCoupon.setMemberCouponClass("全场通用券");       //团长优惠券类别
        }
        if("777".equals(pinCoupon.getMemberCouponClass())){
            pinCoupon.setMemberCouponClass("新人优惠券");       //团长优惠券类别
        }
        if("211".equals(pinCoupon.getMemberCouponClass())){
            pinCoupon.setMemberCouponClass("指定商品适用券");   //团长优惠券类别
        }
        if("999".equals(pinCoupon.getMemberCouponClass())) {
            pinCoupon.setMemberCouponClass("免邮券");          //团长优惠券类别
        }
        //拼购团成员
        List<PinUser> pinUserList = pingouService.getUserByActivityId(id);

        String masterObject = "";
        String  memberObject = "";
        for(PinUser pinUser : pinUserList){
            if(pinUser.isOrMaster()){
                masterObject = pinUser.getUserId().toString();
                if(pinUser.isOrRobot()){
                    masterObject = masterObject + "(机器人)";
                }else{
                    masterObject = masterObject + "(用户)";
                }

            }else{
                memberObject = memberObject + pinUser.getUserId().toString();
                if(pinUser.isOrRobot()){
                    memberObject = memberObject + "(机器人)";
                }else{
                    memberObject = memberObject + "(用户)";
                }
            }
        }
        return ok(views.html.pingou.activityDetail.render(lang,pinActivity,pinCoupon,masterObject,memberObject,(User) ctx().args.get("user")));
    }

    /**
     * 向拼购团中添加机器人       Added by Tiffany Zhu 2016.02.17
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pinAddRobot(String lang){
        JsonNode json = request().body().asJson();
        int userNum = 0;
        Long pinActiveId = new  Long(0);
        if(json.has("userNum")){
            userNum = json.get("userNum").asInt();
        }
        if(json.has("pinActiveId")){
            pinActiveId = json.get("pinActiveId").asLong();
        }
        List<PinUser> pinUserList = new ArrayList<>();
        if(userNum > 0){
            Long userId = new Long(1111111);        //机器人用户ID
            PinUser pinUser = new PinUser();
            pinUser.setUserId(userId);              //用户ID
            pinUser.setOrMaster(false);             //是否团长
            pinUser.setPinActiveId(pinActiveId);    //拼购活动ID
            pinUser.setUserIp(request().remoteAddress());//IP地址
            pinUser.setOrRobot(true);               //是否机器人
            pinUser.setUserImg("");                 //用户头像

            for(int i=0;i<userNum;i++){
                pinUserList.add(pinUser);
            }
        }
        pingouService.pinUserAddList(pinUserList);
        //更新拼购团参加的人数
        HashMap hashMap = new HashMap();
        hashMap.put("userNum",userNum);
        hashMap.put("pinActiveId",pinActiveId);
        pingouService.updJoinPersonById(hashMap);

        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }
}
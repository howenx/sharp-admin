package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Throwables;
import domain.*;
import domain.order.Order;
import domain.pingou.*;
import filters.UserAuth;
import modules.NewScheduler;
import play.Logger;
import play.data.Form;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import service.IDService;
import service.InventoryService;
import service.OrderService;
import service.PingouService;
import util.MsgTypeEnum;
import util.Regex;
import util.SysParCom;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by tiffany on 16/1/19.
 */
public class PingouCtrl extends Controller {

    @Inject
    PingouService pingouService;

    @Inject
    private NewScheduler newScheduler;

    @Inject
    @Named("pingouOffShelfActor")
    private ActorRef pingouOffShelfActor;

    @Inject
    @Named("pingouOnShelfActor")
    private ActorRef pingouOnShelfActor;

    @Inject
    IDService idService;

    @Inject
    private InventoryService inventoryService;

    @Inject
    private OrderService orderService;

    @Inject
    private ActorSystem system;



    /**
     * 添加拼购     Added by Tiffany Zhu 2016.01.19
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result addPingou(String lang){

        return ok(views.html.pingou.pingouAdd.render("cn", SysParCom.IMAGE_URL,(User) ctx().args.get("user")));

    }

    /**
     * 保存拼购Sku Added by Tiffany Zhu 2016.01.21
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pingouSave(String lang){
        JsonNode json = request().body().asJson();
        //获取当前时间
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strNow = sdfDate.format(now);
        //当前时间 + 6个月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH,+6);
        String validDate = sdfDate.format(calendar.getTime());
        //数据验证-------------------------start
        if(json.has("pinSku")){
           JsonNode pinSkuJson = json.findValue("pinSku");
           PinSku pinSkuValidation = Json.fromJson(pinSkuJson,PinSku.class);
            Inventory inventory = inventoryService.getInventory(pinSkuValidation.getInvId());

           Form<PinSku> pinSkuForm = Form.form(PinSku.class).bind(pinSkuJson);
           if(pinSkuForm.hasErrors() || !(Regex.isJason(pinSkuValidation.getFloorPrice())) || !(Regex.isJason(pinSkuValidation.getPinImg())) ||
                   pinSkuValidation.getRestrictAmount() <= 0 || pinSkuValidation.getStartAt().compareTo(pinSkuValidation.getEndAt()) >= 0 ||
                   pinSkuValidation.getEndAt().compareTo(strNow) <=0 || pinSkuValidation.getStartAt().compareTo(validDate) > 0 ||
                   pinSkuValidation.getEndAt().compareTo(validDate) > 0 || inventory.getEndAt().toString().compareTo(pinSkuValidation.getEndAt().toString()) < 0){
               return badRequest();
           }
        }
        if(json.has("tieredPrice")) {
            JsonNode tieredPriceJson = json.findValue("tieredPrice");
            if (tieredPriceJson.size() > 0) {
                for (JsonNode price : tieredPriceJson) {
                    PinTieredPrice tPrice = Json.fromJson(price,PinTieredPrice.class);
                    Form<PinTieredPrice> pinTieredPriceForm = Form.form(PinTieredPrice.class).bind(price);
                    if(pinTieredPriceForm.hasErrors()){
                        return badRequest();
                    }
                }
            }
        }
        //数据验证-------------------------end

        PinSku pinSku = new PinSku();
        if(json.has("pinSku")){
            pinSku = Json.fromJson(json.findValue("pinSku"), PinSku.class);
            if(pinSku.getStartAt().compareTo(strNow) > 0){
                pinSku.setStatus("P");
            }else{
                pinSku.setStatus("Y");
            }
        }
        //更新拼购
        if(pinSku.getPinId() != null){
            //更新拼购
            pingouService.pinSkuSave(pinSku);
            //更新阶梯价格
            String updPriceId = "";
            List<PinTieredPrice> tieredPriceUpd = new ArrayList<>();
            List<PinTieredPrice> tieredPriceInst = new ArrayList<>();
            if(json.has("tieredPrice")){
                JsonNode tieredPriceJson = json.findValue("tieredPrice");
                if(tieredPriceJson.size()>0){
                    for(JsonNode price : tieredPriceJson){
                        PinTieredPrice pinTieredPrice = Json.fromJson(price,PinTieredPrice.class);
                        if(pinTieredPrice.getMasterCouponClass() == null || pinTieredPrice.getMasterCouponClass().equals("")){
                            pinTieredPrice.setMasterCouponClass(null);
                            pinTieredPrice.setMasterCoupon(null);
                            pinTieredPrice.setMasterCouponQuota(null);
                            pinTieredPrice.setMasterCouponStartAt(null);
                            pinTieredPrice.setMasterCouponEndAt(null);
                        }
                        if(pinTieredPrice.getMemberCouponClass() == null || pinTieredPrice.getMemberCouponClass().equals("")){
                            pinTieredPrice.setMemberCouponClass(null);
                            pinTieredPrice.setMemberCoupon(null);
                            pinTieredPrice.setMemberCouponQuota(null);
                            pinTieredPrice.setMemberCouponStartAt(null);
                            pinTieredPrice.setMemberCouponEndAt(null);
                        }
                        pinTieredPrice.setPinId(pinSku.getPinId());
                        //添加
                        if(pinTieredPrice.getId() == null){
                            tieredPriceInst.add(pinTieredPrice);
                        }
                        //更新
                        else{
                            updPriceId = updPriceId + "," + pinTieredPrice.getId().toString();
                            tieredPriceUpd.add(pinTieredPrice);
                        }
                    }
                    if(tieredPriceInst.size()>0){
                        pingouService.addTieredPrice(tieredPriceInst);
                    }
                    if (tieredPriceUpd.size()>0){
                        pingouService.updTieredPrice(tieredPriceUpd);
                    }
                    Logger.error(tieredPriceUpd.toString());
                    Logger.error(updPriceId);
                }
            }

            //删除阶梯价格
            if(json.has("beforeUpdPrice")){
                List<PinTieredPrice> delList = new ArrayList<>();
                JsonNode beforeUpdPriceJson = json.findValue("beforeUpdPrice");
                if(beforeUpdPriceJson.size() > 0){
                    for (JsonNode beforePrice : beforeUpdPriceJson){
                        PinTieredPrice beforeTieredPrice = Json.fromJson(beforePrice,PinTieredPrice.class);
                        if(!updPriceId.contains(beforeTieredPrice.getId().toString())){
                            delList.add(beforeTieredPrice);
                        }
                    }
                    pingouService.delTieredPrice(delList);
                }
            }
        }
        //添加拼购
        else{
            pingouService.pinSkuSave(pinSku);
            List<PinTieredPrice> tieredPriceList = new ArrayList<>();
            if(json.has("tieredPrice")){
                JsonNode tieredPriceJson = json.findValue("tieredPrice");
                for(JsonNode price : tieredPriceJson){
                    PinTieredPrice pinTieredPrice = Json.fromJson(price,PinTieredPrice.class);
                    if(pinTieredPrice.getMasterCouponClass() == null || pinTieredPrice.getMasterCouponClass().equals("")){
                        pinTieredPrice.setMasterCouponClass(null);
                        pinTieredPrice.setMasterCoupon(null);
                        pinTieredPrice.setMasterCouponQuota(null);
                        pinTieredPrice.setMasterCouponStartAt(null);
                        pinTieredPrice.setMasterCouponEndAt(null);
                    }
                    if(pinTieredPrice.getMemberCouponClass() == null || pinTieredPrice.getMemberCouponClass().equals("")){
                        pinTieredPrice.setMemberCouponClass(null);
                        pinTieredPrice.setMemberCoupon(null);
                        pinTieredPrice.setMemberCouponQuota(null);
                        pinTieredPrice.setMemberCouponStartAt(null);
                        pinTieredPrice.setMemberCouponEndAt(null);
                    }
                    pinTieredPrice.setPinId(pinSku.getPinId());
                    tieredPriceList.add(pinTieredPrice);
                }
            }
            pingouService.addTieredPrice(tieredPriceList);
        }

        //创建Scheduled Actor         ---start
        //part1     预售-->正常
        if("P".equals(pinSku.getStatus())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startAt = null;
            try{
                startAt = sdf.parse(pinSku.getStartAt());
            }catch(Exception e){
                e.printStackTrace();
                Logger.error(Throwables.getStackTraceAsString(e));

            }
            if(startAt != null && (startAt.getTime() - now.getTime() > 0)){
                FiniteDuration duration = Duration.create(startAt.getTime() - now.getTime() , TimeUnit.MILLISECONDS);
                newScheduler.scheduleOnce(duration,pingouOnShelfActor,pinSku.getPinId());
            }
        }else{
            //part2     正常-->下架
            //ActorRef pingouOffShelf = Akka.system().actorOf(Props.create(PingouOffShelfActor.class,pingouService));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endAt = null;
            try{
                endAt = sdf.parse(pinSku.getEndAt());
            }catch(Exception e){
                e.printStackTrace();
                Logger.error(Throwables.getStackTraceAsString(e));
            }
            if(endAt != null && (endAt.getTime() - now.getTime() > 0)){
                FiniteDuration duration = Duration.create(endAt.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
                newScheduler.scheduleOnce(duration,pingouOffShelfActor,pinSku.getPinId());
            }

        }
        //创建Scheduled Actor         ---end

        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 拼购列表 Added by Tiffany Zhu 2016.01.20
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pingouSearch(String lang){
        pingouService.updStatus();
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
            Object[] object = new Object[8];
            object[0] = pinSku.getPinId();      //活动ID
            object[1] = pinSku.getPinTitle();   //商品标题
            object[2] = pinSku.getStartAt();    //开始时间
            object[3] = pinSku.getEndAt();      //结束时间
            if("Y".equals(pinSku.getStatus())){
                object[4] = "正常";              //状态
            }
            if("D".equals(pinSku.getStatus())){
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
            //图片
            JsonNode imgJson = Json.parse(pinSku.getPinImg());
            String imgUrl =  imgJson.get("url").toString();
            object[6] = imgUrl.substring(1,imgUrl.length()-1);
            //最低价格
            JsonNode floorPrice = Json.parse(pinSku.getFloorPrice());
            object[7] = floorPrice.get("price");


            rtnPinSkuList.add(object);
        }
        return ok(views.html.pingou.pingouSearch.render("cn",ThemeCtrl.PAGE_SIZE,countNum,pageCount,rtnPinSkuList,SysParCom.IMAGE_URL,(User) ctx().args.get("user")));


    }

    /**
     * 拼购ajax查询     Added by Tiffany Zhu 2016.01.21
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result getPinSkuPage(String lang,int pageNum){
        pingouService.updStatus();
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
            List<PinSku> pinSkuList = pingouService.getPinSkuPage(pinSku);
            List<PinSku> rtnPinSkuList = new ArrayList<>();
            for (PinSku pinSkuTemp :pinSkuList){
                JsonNode imgJson = Json.parse(pinSkuTemp.getPinImg());
                String imgUrl =  imgJson.get("url").toString();
                pinSkuTemp.setPinImg(imgUrl.substring(1,imgUrl.length()-1));
                JsonNode floorPrice = Json.parse(pinSkuTemp.getFloorPrice());
                String fPrice = floorPrice.get("price").toString();
                pinSkuTemp.setFloorPrice(fPrice);
                rtnPinSkuList.add(pinSkuTemp);
            }
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",rtnPinSkuList);
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
        Inventory inventory = inventoryService.getInventory(pinSku.getInvId());
        String SkuEndDate = "";
        if(inventory != null){
            SkuEndDate = inventory.getEndAt().toString().substring(0,19);
        }
        if(pinSku != null){
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
            return ok(views.html.pingou.pingouUpdate.render("cn",pinSku,img,tieredList,SkuEndDate,SysParCom.IMAGE_URL,(User) ctx().args.get("user")));
        }
        return null;
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
        return ok(views.html.pingou.activityManualAdd.render("cn",pinSku,tieredList,(User) ctx().args.get("user")));
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
        if(pinSku != null && pinTieredPrice != null){
            Long userId = new Long(1111111);            //机器人用户ID
            //添加拼购活动
            PinActivity pinActivity = new PinActivity();
            pinActivity.setPinUrl("");                              //分享链接
            pinActivity.setPinTieredId(pinTieredPrice.getId());     //阶梯价格ID
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
        }else{
            return badRequest();
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
            Object[] object = new Object[10];
            object[0] = pinActivityTemp.getPinActiveId();
            object[1] = pinActivityTemp.getPinId();
            object[2] = pinActivityTemp.getMasterUserId();
            object[3] = pinActivityTemp.getPersonNum();
            object[4] = pinActivityTemp.getPinPrice();
            object[5] = pinActivityTemp.getJoinPersons();
            object[6] = pinActivityTemp.getCreateAt();
            object[7] = pinActivityTemp.getEndAt();
            if (pinSku != null){
                object[8] = pinSku.getPinTitle();
            }else {
                object[8] = "";
            }

            if("Y".equals(pinActivityTemp.getStatus())){
                object[9] = "正常";
            }
            if("N".equals(pinActivityTemp.getStatus())){
                object[9] = "取消";
            }
            if("C".equals(pinActivityTemp.getStatus())){
                object[9] = "完成";
            }
            if("F".equals(pinActivityTemp.getStatus())){
                object[9] = "失败";
            }
            rtnPinActivityList.add(object);
        }
        return ok(views.html.pingou.activitySearch.render("cn",ThemeCtrl.PAGE_SIZE,countNum,pageCount,rtnPinActivityList,(User) ctx().args.get("user")));
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
                if (pinSku != null){
                    pinActivityTemp.setPinTitle(pinSku.getPinTitle());
                }else {
                    pinActivityTemp.setPinTitle("");
                }

                if("Y".equals(pinActivityTemp.getStatus())){
                    pinActivityTemp.setStatus("正常");
                }
                if("N".equals(pinActivityTemp.getStatus())){
                    pinActivityTemp.setStatus("取消");
                }
                if("C".equals(pinActivityTemp.getStatus())){
                    pinActivityTemp.setStatus("完成");
                }
                if("F".equals(pinActivityTemp.getStatus())){
                    pinActivityTemp.setStatus("失败");
                }
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
        if(pinActivity != null){
            PinSku pinSku = pingouService.getPinSkuById(pinActivity.getPinId());
            pinActivity.setPinTitle(pinSku.getPinTitle());
            //拼购团优惠券
            PinCoupon pinCoupon = pingouService.getCouponByActivityId(id);
            if(pinCoupon != null) {
                //团长优惠券类型
                if ("153".equals(pinCoupon.getMasterCouponClass())) {
                    pinCoupon.setMasterCouponClass("化妆品类商品适用券"); //团长优惠券类别
                }
                if ("172".equals(pinCoupon.getMasterCouponClass())) {
                    pinCoupon.setMasterCouponClass("配饰类商品适用券");  //团长优惠券类别
                }
                if ("165".equals(pinCoupon.getMasterCouponClass())) {
                    pinCoupon.setMasterCouponClass("服饰类商品适用券");  //团长优惠券类别
                }
                if ("555".equals(pinCoupon.getMasterCouponClass())) {
                    pinCoupon.setMasterCouponClass("全场通用券");       //团长优惠券类别
                }
                if ("777".equals(pinCoupon.getMasterCouponClass())) {
                    pinCoupon.setMasterCouponClass("新人优惠券");       //团长优惠券类别
                }
                if ("211".equals(pinCoupon.getMasterCouponClass())) {
                    pinCoupon.setMasterCouponClass("指定商品适用券");   //团长优惠券类别
                }
                if ("999".equals(pinCoupon.getMasterCouponClass())) {
                    pinCoupon.setMasterCouponClass("免邮券");          //团长优惠券类别
                }
                //团员优惠券类型
                if ("153".equals(pinCoupon.getMemberCouponClass())) {
                    pinCoupon.setMemberCouponClass("化妆品类商品适用券"); //团长优惠券类别
                }
                if ("172".equals(pinCoupon.getMemberCouponClass())) {
                    pinCoupon.setMemberCouponClass("配饰类商品适用券");  //团长优惠券类别
                }
                if ("165".equals(pinCoupon.getMemberCouponClass())) {
                    pinCoupon.setMemberCouponClass("服饰类商品适用券");  //团长优惠券类别
                }
                if ("555".equals(pinCoupon.getMemberCouponClass())) {
                    pinCoupon.setMemberCouponClass("全场通用券");       //团长优惠券类别
                }
                if ("777".equals(pinCoupon.getMemberCouponClass())) {
                    pinCoupon.setMemberCouponClass("新人优惠券");       //团长优惠券类别
                }
                if ("211".equals(pinCoupon.getMemberCouponClass())) {
                    pinCoupon.setMemberCouponClass("指定商品适用券");   //团长优惠券类别
                }
                if ("999".equals(pinCoupon.getMemberCouponClass())) {
                    pinCoupon.setMemberCouponClass("免邮券");          //团长优惠券类别
                }
            }
            //拼购团成员
            List<PinUser> pinUserList = pingouService.getUserByActivityId(id);

            String masterObject = "";
            String  memberObject = "";
            for(PinUser pinUser : pinUserList){
                if(pinUser.isOrMaster()){

                    if(pinUser.isOrRobot()){
                        masterObject = pinUser.getUserId().toString();
                        masterObject = masterObject + "(机器人)";
                    }else{
                        ID userInfo = idService.getID(Integer.parseInt(pinUser.getUserId().toString()));
                        masterObject = userInfo.getPhoneNum();
                        masterObject = masterObject + "(用户)";
                    }

                }else{
                    if(pinUser.isOrRobot()){
                        memberObject = memberObject + pinUser.getUserId().toString();
                        memberObject = memberObject + "(机器人)";
                    }else{
                        ID userInfo = idService.getID(Integer.parseInt(pinUser.getUserId().toString()));
                        memberObject = memberObject + userInfo.getPhoneNum();
                        memberObject = memberObject + "(用户)";
                    }
                }
            }
            return ok(views.html.pingou.activityDetail.render("cn",pinActivity,pinCoupon,masterObject,memberObject,(User) ctx().args.get("user")));
        }
       return null;
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
        boolean isComplete = false;
        if(json.has("userNum")){
            userNum = json.get("userNum").asInt();
        }
        if(json.has("pinActiveId")){
            pinActiveId = json.get("pinActiveId").asLong();
        }
        if(json.has("isComplete")){
            isComplete = json.get("isComplete").asBoolean();
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
        HashMap<String,Object> hashMap = new HashMap<String,Object>();
        hashMap.put("userNum",userNum);
        hashMap.put("pinActiveId",pinActiveId);
        hashMap.put("isComplete",isComplete);
        pingouService.updJoinPersonById(hashMap);
        if(isComplete){
            //更新订单
            List<Order> orderList = orderService.getOrderByPinAtvId(pinActiveId);
            orderService.updPinOrderToSuccess(orderList);
        }

        //消息推送      Added by Tiffany Zhu 2016.08.31 ----------------------------------------start
        //获取拼团中的真实用户
        List<PinUser> pinRealUsers = pingouService.getRealUserByActId(pinActiveId);
        //有真实用户
        if (pinRealUsers != null){
            //所有真用户
            String[] realUsers = new String[pinRealUsers.size()];
            int index = 0;
            for (PinUser user : pinRealUsers){
                realUsers[index] = user.getUserId().toString();
                index++;
            }
            String msgTitle = "";
            String msgContent = "";
            //拼购完成
            if (isComplete){
                msgTitle = SysParCom.PIN_SUCCESS_MSG;
                msgContent = SysParCom.PIN_SUCCESS_MSG;
            }
            //添加机器人
            else {
                msgTitle = SysParCom.PIN_ADD_MSG;
                msgContent = SysParCom.PIN_ADD_MSG;
            }
            //跳转链接
            String redirectUrl = SysParCom.PROMOTION_SERVER_URL + "/promotion/pin/activity/" + pinActiveId;

            //给用户推送消息
            PushMsg pushMsg = new PushMsg();
            pushMsg.setTitle(msgTitle);             //标题
            pushMsg.setAlert(msgContent);           //内容
            pushMsg.setAudience("alias");           //给指定用户推送
            pushMsg.setAliasOrTag(realUsers);       //拼购团中的用户
            pushMsg.setTargetType("V");             //链接类型
            pushMsg.setUrl(redirectUrl);            //跳转链接
            system.actorSelection(SysParCom.MSG_PUSH).tell(pushMsg, ActorRef.noSender());

            //给用户发送消息(消息盒子)   循环调用Actor
            for (PinUser user : pinRealUsers){
                MsgRec msgRec = new MsgRec();
                msgRec.setUserId(user.getUserId());
                msgRec.setMsgTitle(msgTitle);                       //标题
                msgRec.setMsgContent(msgContent);                   //内容
                msgRec.setMsgRecType(1);
                msgRec.setMsgType(MsgTypeEnum.System.getMsgType());
                msgRec.setTargetType("V");                          //消息类型
                msgRec.setReadStatus(1);                            //1-未读 2-已读
                msgRec.setDelStatus(1);                             //1-未删 2-已删
                msgRec.setMsgUrl(redirectUrl);                      //跳转链接
                system.actorSelection(SysParCom.MSG_SEND).tell(msgRec, ActorRef.noSender());
            }
        }
        //消息推送      Added by Tiffany Zhu 2016.08.31 ----------------------------------------end

        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     *拼购活动已开的所有团        Added by Tiffany Zhu 2016.02.18
     * @param lang
     * @param pinId
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result pingouActivityList(String lang,Long pinId){
        List<PinActivity> pinActivityList = pingouService.getActivityByPinId(pinId);
        List<PinActivity> rtnActivityList = new ArrayList<>();
        for(PinActivity activity : pinActivityList){
            if("Y".equals(activity.getStatus())){
                activity.setStatus("正常");
            }
            if("N".equals(activity.getStatus())){
                activity.setStatus("取消");
            }
            if("C".equals(activity.getStatus())){
                activity.setStatus("完成");
            }
            if("F".equals(activity.getStatus())){
                activity.setStatus("失败");
            }
            rtnActivityList.add(activity);
        }
        PinSku pinSku = pingouService.getPinSkuById(pinId);
        return ok(views.html.pingou.pingouActivityList.render("cn",pinSku,rtnActivityList,(User) ctx().args.get("user")));
    }
}
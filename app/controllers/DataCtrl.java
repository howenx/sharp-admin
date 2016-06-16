package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Inventory;
import domain.SMSVo;
import domain.SystemParam;
import domain.User;
import domain.order.Order;
import filters.UserAuth;
import play.Configuration;
import play.Logger;
import play.data.Form;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.InventoryService;
import service.OrderService;
import service.SysParamService;
import util.SysParCom;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tiffany on 15/12/14.
 */
@SuppressWarnings("unchecked")
public class DataCtrl extends Controller {

    @Inject
    SysParamService sysParamService;

    @Inject
    InventoryService inventoryService;

    @Inject
    OrderService orderService;

    @Inject
    Configuration configuration;

    @Inject
    private ActorSystem system;

    /**
     * 系统参数列表
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result systemParameterSearch(String lang){
        return ok(views.html.data.sysparamsearch.render(lang, sysParamService.getParamAll(), (User) ctx().args.get("user")));
    }

    /**
     * 新增系统参数
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result systemParameterAdd(String lang){
        return ok(views.html.data.sysparamadd.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 添加系统参数
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result paramSave(String lang){
        JsonNode json = request().body().asJson();
        Form<SystemParam> systemParamForm = Form.form(SystemParam.class).bind(json);
        //数据验证
        if (systemParamForm.hasErrors()) {
            Logger.error("system param 表单数据有误.....");
            return badRequest();
        }
        sysParamService.insertParam(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 给用户发送短信          Added By Sunny Wu  2016.06.07
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result smsSend(String lang) {
        Map<String,String> tempList = new ObjectMapper().convertValue(configuration.getObject("sms_template"),HashMap.class);
        return ok(views.html.data.smsSend.render(lang, tempList, (User) ctx().args.get("user")));
    }

    /**
     * 保存给用户发送的短信    Added By Sunny Wu  2016.06.07
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result smsSave() {
        JsonNode json = request().body().asJson();
        for(final JsonNode jsonNode : json) {
            Form<SMSVo> smsVoForm = Form.form(SMSVo.class).bind(jsonNode);
            SMSVo smsVo  = Json.fromJson(jsonNode, SMSVo.class);
            //数据验证
            if (smsVoForm.hasErrors()) {
                Logger.error("smsVo 表单数据有误.....");
                return badRequest();
            }
            Logger.error("短信内容:"+smsVo.toString());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, BigInteger> paramMap = mapper.convertValue(jsonNode, Map.class);
            system.actorSelection(SysParCom.SMS_SEND).tell(paramMap, ActorRef.noSender());
        }
        return ok("保存成功");
    }

    /**
     * 销售数据       Added By Sunny Wu  2016.06.15
     * @param lang 语言
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result salesData(String lang) {
        Order order = new Order();
        order.setPageSize(-1);
        order.setOffset(-1);
        List<Order> orderList = orderService.getTradeOrder(order);
        int countNum = orderList.size();//取总数
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;//共分几页
        if (countNum%ThemeCtrl.PAGE_SIZE!=0) {
            pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
        }
        return ok(views.html.data.salesdata.render(lang, ThemeCtrl.PAGE_SIZE, countNum, pageCount, (User) ctx().args.get("user")));
    }

    /**
     * ajax分页查询         Added By Sunny Wu  2016.06.16
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result salesDataAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        Order order = Json.fromJson(json,Order.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            order.setPageSize(-1);
            order.setOffset(-1);
            List<Order> orderList = orderService.getTradeOrder(order);
            int countNum = orderList.size();//取总数
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;//共分几页
            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
            order.setPageSize(ThemeCtrl.PAGE_SIZE);
            order.setOffset(offset);
            order.setSort("order_create_at");
            order.setSort("DESC");
            orderList = orderService.getTradeOrder(order);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",orderList);
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
     * 库存数据       Added By Sunny Wu  2016.06.15
     * @param lang 语言
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result inventoryData(String lang) {
        Inventory inventory = new Inventory();
        inventory.setPageSize(-1);
        inventory.setOffset(-1);
        List<Inventory> inventoryList = inventoryService.invSearch(inventory);
        int countNum = inventoryList.size();//取总数
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;//共分几页
        if (countNum%ThemeCtrl.PAGE_SIZE!=0) {
            pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
        }
        return ok(views.html.data.inventorydata.render(lang, ThemeCtrl.PAGE_SIZE, countNum, pageCount,(User) ctx().args.get("user")));
    }

    /**
     * ajax分页查询         Added By Sunny Wu  2016.06.16
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result inventoryDataAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        Inventory inventory = Json.fromJson(json,Inventory.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            inventory.setPageSize(-1);
            inventory.setOffset(-1);
            List<Inventory> inventoryList = inventoryService.invSearch(inventory);
            int countNum = inventoryList.size();//取总数
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;//共分几页
            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
            inventory.setPageSize(ThemeCtrl.PAGE_SIZE);
            inventory.setOffset(offset);
            inventory.setSort("rest_amount");
            inventoryList = inventoryService.invSearch(inventory);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",inventoryList);
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

}

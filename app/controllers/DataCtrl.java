package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import domain.order.Order;
import domain.order.OrderLine;
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
import service.RefundService;
import service.SysParamService;
import util.SysParCom;

import javax.inject.Inject;
import java.math.BigDecimal;
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
    RefundService refundService;

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
        final String param = request().getQueryString("param");
        List<Order> orderList = null;
        List<Inventory> inventoryList = null;
        int countNum = 0;//总数
        if ("sales".equals(param)) {
            Order order = new Order();
            order.setPageSize(-1);
            order.setOffset(-1);
            orderList = orderService.getTradeOrder(order);
            countNum = orderList.size();
        }
        if ("trade".equals(param)) {
            Order order = new Order();
            order.setPageSize(-1);
            order.setOffset(-1);
            orderList = orderService.countTradeOrder(order);
            countNum = orderList.size();
        }
        if ("goods".equals(param)) {
            Inventory inventory = new Inventory();
            inventory.setPageSize(-1);
            inventory.setOffset(-1);
            inventoryList = inventoryService.invSearch(inventory);
            countNum = inventoryList.size();
        }
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;//共分几页
        if (countNum%ThemeCtrl.PAGE_SIZE!=0) {
            pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
        }
        return ok(views.html.data.salesdata.render(lang, ThemeCtrl.PAGE_SIZE, countNum, pageCount, (User) ctx().args.get("user")));
    }

    /**
     * 销售数据Ajax分页查询         Added By Sunny Wu  2016.06.16
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result salesDataAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        final String param = request().getQueryString("param");
        Order order = Json.fromJson(json,Order.class);
        order.setPageSize(-1);
        order.setOffset(-1);
        List<Order> orderList = null;
        List<OrderLine> orderLineList = null;
        int countNum = 0;//总数
        //组装返回数据
        Map<String,Object> returnMap=new HashMap<>();
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            if ("sales".equals(param)) {
                orderList = orderService.getTradeOrder(order);
                BigDecimal income = new BigDecimal(0.0);    //收款额
                BigDecimal expend = new BigDecimal(0.0);    //退款额
                BigDecimal profit = new BigDecimal(0.0);    //收入
                for(Order o : orderList) {
                    if ("T".equals(order.getOrderStatus())) {
                        //退款单, 退款额增加, 收入减少
                        Refund refund = refundService.getRefundByOrderId(o.getOrderId());
                        BigDecimal payBackFee = refund.getPayBackFee();
                        expend = expend.add(payBackFee);
                        profit = profit.subtract(payBackFee);
                    }
                    if (!"T".equals(order.getOrderStatus())) {
                        //付款单, 收款额增加, 收入增加
                        BigDecimal payTotal = o.getPayTotal();
                        income = income.add(payTotal);
                        profit = profit.add(payTotal);
                    }
                }
                countNum = orderList.size();
                order.setPageSize(ThemeCtrl.PAGE_SIZE);
                order.setOffset(offset);
                orderList = orderService.getTradeOrder(order);
                if (orderList.size()>0) {
                    orderList.get(0).setShipFee(income);
                    orderList.get(0).setPostalFee(expend);
                    orderList.get(0).setTotalFee(profit);
                }
                returnMap.put("topic",orderList);
//                Logger.error("sales:"+orderList);
            }
            if ("trade".equals(param)) {
                orderList = orderService.countTradeOrder(order);
                int tradeNum = 0;   //成交量
                BigDecimal tradeMoney = new BigDecimal(0.0);//成交额
                int retreatNum = 0;    //退换量
                for(Order o : orderList) {
                    //每日成交量, 成交额, 退换量累加
                    tradeNum = tradeNum + Integer.valueOf(o.getPayMethod());
                    tradeMoney = tradeMoney.add(o.getPayTotal());
                    if (null != o.getOrderType())
                        retreatNum = retreatNum + o.getOrderType();
                }
                countNum = orderList.size();
                order.setPageSize(ThemeCtrl.PAGE_SIZE);
                order.setOffset(offset);
                orderList = orderService.countTradeOrder(order);
                if (orderList.size()>0) {
                    orderList.get(0).setUserId((long) tradeNum);
                    orderList.get(0).setTotalFee(tradeMoney);
                    orderList.get(0).setOrderType(retreatNum);
                }
                returnMap.put("topic",orderList);
//                Logger.error("trade:"+orderList);
            }
            if ("goods".equals(param)) {
                orderLineList = orderService.countTradeGoods(order);
                countNum = orderLineList.size();
                order.setPageSize(ThemeCtrl.PAGE_SIZE);
                order.setOffset(offset);
                orderLineList = orderService.countTradeGoods(order);
                for(OrderLine orderLine : orderLineList) {
                    Long skuId = orderLine.getSkuId();
                    Inventory inventory = inventoryService.getInventory(skuId);
                    orderLine.setSkuType(inventory.getInvCode());//保存sku的编码
                }
                returnMap.put("topic",orderLineList);
//                Logger.error("goods:"+orderLineList);
            }
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;//共分几页
            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }
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

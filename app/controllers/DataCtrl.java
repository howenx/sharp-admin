package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import domain.order.Order;
import domain.order.OrderLine;
import filters.UserAuth;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.File;
import java.io.FileOutputStream;
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
            Logger.info("短信内容:"+smsVo.toString());
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
     * 报表导出功能
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result salesReport() {
        JsonNode json = request().body().asJson();
        String param = "";
        Order order = null;
        if (json.has("param")) {
            param = json.findValue("param").asText();
        }
        if (json.has("order")) {
            JsonNode jsonOrder = json.findValue("order");
            order = Json.fromJson(jsonOrder, Order.class);
        }
        if ("".equals(param) || null==order) {
            return badRequest();
        }
        order.setPageSize(-1);
        order.setOffset(-1);
        List<Order> orderList = null;
        List<OrderLine> orderLineList = null;
        //导出excel
        XSSFWorkbook wb = new XSSFWorkbook();//创建HSSFWorkbook对象(excel的文档对象)
        //输出Excel文件
        FileOutputStream output = null;
        File file = new File("aaa.xlsx");
        if ("sales".equals(param)) {
            orderList = orderService.getTradeOrder(order);
            Sheet sheet = wb.createSheet("销售收入统计");//建立新的sheet对象（excel的表单）
            Row row1 = sheet.createRow(0);//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            Cell cell = row1.createCell(0);//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            cell.setCellValue("销售收入统计表");//设置单元格内容
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            Row row2 = sheet.createRow(1);//在sheet里创建第二行
            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("日期");
            row2.createCell(1).setCellValue("订单号");
            row2.createCell(2).setCellValue("交易金额");
            row2.createCell(3).setCellValue("订单类型");
            row2.createCell(4).setCellValue("交易流水号");
            //在sheet里从第三行开始创建数据
            int r = 2;
            for(Order o : orderList) {
                Row row = sheet.createRow(r);
                row.createCell(0).setCellValue(o.getOrderCreateAt().toString().substring(0,10));
                row.createCell(1).setCellValue(o.getOrderId());
                row.createCell(2).setCellValue(o.getPayTotal().doubleValue());
                String orderStatus = o.getOrderStatus();
                if (!"pin".equals(orderStatus) && !"receive".equals(orderStatus) && !"deliver".equals(orderStatus))
                    orderStatus = "付款单";
                if ("pin".equals(orderStatus))
                    orderStatus = "拼购自动退款";
                if ("receive".equals(orderStatus))
                    orderStatus = "收货后申请退款";
                if ("deliver".equals(orderStatus))
                    orderStatus = "发货前退款";
                row.createCell(3).setCellValue(orderStatus);
                row.createCell(4).setCellValue(o.getPgTradeNo());
                r += 1;
            }
//            file = new File("/Users/sunny/Downloads/销售收入统计表.xlsx");
            file = new File("销售收入统计表.xlsx");
        }
        if ("trade".equals(param)) {
            orderList = orderService.countTradeOrder(order);
            Sheet sheet = wb.createSheet("商品销售情况");
            Row row1 = sheet.createRow(0);
            Cell cell = row1.createCell(0);
            cell.setCellValue("商品销售情况表");
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
            Row row2 = sheet.createRow(1);
            row2.createCell(0).setCellValue("日期");
            row2.createCell(1).setCellValue("订单成交量");
            row2.createCell(2).setCellValue("订单成交额");
            row2.createCell(3).setCellValue("商品退换量");
            int r = 2;
            for(Order o : orderList) {
                Row row = sheet.createRow(r);
                row.createCell(0).setCellValue(o.getSort().substring(0,10));
                row.createCell(1).setCellValue(o.getPayMethod());
                row.createCell(2).setCellValue(o.getPayTotal().doubleValue());
                int retreatNum = 0;
                if (null != o.getOrderType()) retreatNum = o.getOrderType();
                row.createCell(3).setCellValue(retreatNum);
                r += 1;
            }
//            file = new File("/Users/sunny/Downloads/商品销售情况表.xlsx");
            file = new File("商品销售情况表.xlsx");
        }
        if ("goods".equals(param)) {
            orderLineList = orderService.countTradeGoods(order);
            Sheet sheet = wb.createSheet("商品销售排行");
            Row row1 = sheet.createRow(0);
            Cell cell = row1.createCell(0);
            cell.setCellValue("商品销售排行表");
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
            Row row2 = sheet.createRow(1);
            row2.createCell(0).setCellValue("排名");
            row2.createCell(1).setCellValue("商品名称");
            row2.createCell(2).setCellValue("销售额");
            row2.createCell(3).setCellValue("销售量");
            row2.createCell(4).setCellValue("退换量");
            row2.createCell(5).setCellValue("商品编号");
            int r = 2;
            for(OrderLine ol : orderLineList) {
                Row row = sheet.createRow(r);
                row.createCell(0).setCellValue(ol.getLineId());
                row.createCell(1).setCellValue(ol.getSkuTitle() + " " + ol.getSkuColor() + " " + ol.getSkuSize());
                row.createCell(2).setCellValue(ol.getPrice().doubleValue());
                row.createCell(3).setCellValue(ol.getAmount());
                int retreatNum = 0;
                if (null != ol.getItemId()) retreatNum = ol.getItemId().intValue();
                row.createCell(4).setCellValue(retreatNum);
                Long skuId = ol.getSkuId();
                Inventory inventory = inventoryService.getInventory(skuId);
                row.createCell(5).setCellValue(inventory.getInvCode());
                r += 1;
            }
//            file = new File("/Users/sunny/Downloads/商品销售排行表.xlsx");
            file = new File("商品销售排行表.xlsx");
        }
        try {
            output = new FileOutputStream(file);
            wb.write(output);
            output.close();
        } catch (Exception e) {
            return badRequest();
        }
        Logger.info("报表导出成功");
        return ok("报表导出成功");
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

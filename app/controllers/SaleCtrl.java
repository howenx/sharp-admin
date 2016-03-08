package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import entity.Theme;
import entity.User;
import entity.sale.SaleInventory;
import entity.sale.SaleOrder;
import entity.sale.SaleProduct;
import entity.sale.SaleStatistics;
import filters.UserAuth;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.SaleService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static play.libs.Json.newObject;

/**
 * 销售报表
 * Created by sibyl.sun on 16/3/3.
 */
public class SaleCtrl extends Controller {
    //每页固定的取数
    public static final int PAGE_SIZE = 10;
    @Inject
    private SaleService saleService;
    @javax.inject.Inject
    Configuration configuration;

    /**
     *
     * @param name 商品名称
     * @param catagoryId 商品分类
     * @param skuCode 系统编码
     * @param productCode 货品编码
     * @param spec 规格
     * @param saleCount 总销量
     * @param inventory 库存量
     * @param productCost 商品成本
     * @param stockValue  库存商品价值
     * @param purchaseCount 进货量
     * @param noCard 无卡
     * @param damage 破损
     * @param lessDelivery 少配
     * @param lessProduct 少货
     * @param emptyBox 空盒
     * @return
     */
    private SaleProduct createSaleProduct(String name,Integer catagoryId,String skuCode,String productCode, String spec, Integer saleCount,
                                          Integer inventory, BigDecimal productCost,BigDecimal stockValue,Integer purchaseCount,Integer noCard,Integer damage,
                                          Integer lessDelivery, Integer lessProduct, Integer emptyBox,String invArea,Timestamp storageAt){
        SaleProduct saleProduct=new SaleProduct();
        setSaleProduct(saleProduct,name,catagoryId,skuCode,productCode,spec,saleCount,inventory,productCost,stockValue,purchaseCount,noCard,damage,
                lessDelivery,lessProduct,emptyBox,invArea,storageAt);
        if(saleService.insertSaleProduct(saleProduct)){
            return saleProduct;
        }
        return null;
    }
    private void setSaleProduct(SaleProduct saleProduct,
                                String name,Integer catagoryId,String skuCode,String productCode, String spec, Integer saleCount,
                                Integer inventory, BigDecimal productCost,BigDecimal stockValue,Integer purchaseCount,Integer noCard,Integer damage,
                                Integer lessDelivery, Integer lessProduct, Integer emptyBox,String invArea,Timestamp storageAt){
        saleProduct.setName(name);
        saleProduct.setCategoryId(catagoryId);
        saleProduct.setSkuCode(skuCode);
        saleProduct.setProductCode(productCode);
        saleProduct.setSpec(spec);
        saleProduct.setSaleCount(saleCount);
        saleProduct.setInventory(inventory);
        saleProduct.setProductCost(productCost);
        saleProduct.setStockValue(stockValue);
        saleProduct.setPurchaseCount(purchaseCount);
        saleProduct.setNoCard(noCard);
        saleProduct.setDamage(damage);
        saleProduct.setLessDelivery(lessDelivery);
        saleProduct.setLessProduct(lessProduct);
        saleProduct.setEmptyBox(emptyBox);
        saleProduct.setInvArea(invArea);
        saleProduct.setStorageAt(storageAt);
    }

    /**
     *
     * @param saleAt 日期
     * @param orderId 订单号
     * @param saleProductId 销售商品id
     * @param productName 品名
     * @param categoryId 商品分类
     * @param price 单价
     * @param saleCount 数量
     * @param discountAmount 优惠额
     * @param saleTotal 销售
     * @param jdRate 京东费率
     * @param jdFee 京东费用
     * @param cost 成本
     * @param shipFee 国内快递费
     * @param inteLogistics 国际物流
     * @param packFee 包装
     * @param postalFee 行邮税
     * @param postalTaxRate 行邮税税率，单位百分比，例如填入3，表示3%
     * @param profit 净利
     * @return
     */
    private SaleOrder createSaleOrder(Date saleAt,String orderId,Long saleProductId,String productName, Integer categoryId,BigDecimal price,Integer saleCount,
                                      BigDecimal discountAmount, BigDecimal saleTotal,BigDecimal jdRate,BigDecimal jdFee,BigDecimal cost,BigDecimal shipFee,
                                      BigDecimal inteLogistics, BigDecimal packFee,BigDecimal storageFee, BigDecimal postalFee, String postalTaxRate, BigDecimal profit){
        SaleOrder saleOrder=new SaleOrder();
        saleOrder.setSaleAt(saleAt);
        saleOrder.setOrderId(orderId);
        saleOrder.setSaleProductId(saleProductId);
        saleOrder.setProductName(productName);
        saleOrder.setCategoryId(categoryId);
        saleOrder.setPrice(price);
        saleOrder.setSaleCount(saleCount);
        saleOrder.setDiscountAmount(discountAmount);
        saleOrder.setSaleTotal(saleTotal);
        saleOrder.setJdRate(jdRate);
        saleOrder.setJdFee(jdFee);
        saleOrder.setCost(cost);
        saleOrder.setShipFee(shipFee);
        saleOrder.setInteLogistics(inteLogistics);
        saleOrder.setPackFee(packFee);
        saleOrder.setStorageFee(storageFee);
        saleOrder.setPostalFee(postalFee);
        saleOrder.setPostalTaxRate(postalTaxRate);
        saleOrder.setProfit(profit);
        if(saleService.insertSaleOrder(saleOrder)){
            return saleOrder;
        }
        return null;
    }

    /**
     * 数据导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result dataImport() {
        Map<String,String> area = new ObjectMapper().convertValue(configuration.getObject("area"),HashMap.class);
        return ok(views.html.sales.dataImport.render("cn", area,(User) ctx().args.get("user")));
    }


    /**
     * 数据导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result productSave() {
        JsonNode json = request().body().asJson();
        Logger.info("=====productSave==="+json);
        try {
        String name=json.findValue("name").asText();
        Integer categoryId=json.findValue("categoryId").asInt();
        String skuCode=json.findValue("skuCode").asText();
        String productCode=json.findValue("productCode").asText();
        String spec=json.findValue("spec").asText();
        Integer saleCount=0;  //总销量 TODO
        Integer inventory=0;//库存量 TODO
        BigDecimal productCost=new BigDecimal(json.findValue("productCost").asDouble());
        BigDecimal stockValue=new BigDecimal(0);  //库存商品价值 TODO
        Integer purchaseCount=json.findValue("purchaseCount").asInt();
        Integer noCard=0;
        if(json.has("noCard")){
            noCard=json.findValue("noCard").asInt();
        }
        Integer damage=0;
        if(json.has("damage")){
            damage=json.findValue("damage").asInt();
        }

        Integer lessDelivery=0;
        if(json.has("lessDelivery")){
            damage=json.findValue("lessDelivery").asInt();
        }

        Integer lessProduct=0;
        if(json.has("lessProduct")){
            lessProduct=json.findValue("lessProduct").asInt();
        }

        Integer emptyBox=0;
        if(json.has("emptyBox")) {
            json.findValue("emptyBox").asInt();
        }
        String invArea=json.findValue("invArea").asText();
        String storageAt = json.findValue("storageAt").asText();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(format.parse(storageAt).getTime());
        createSaleProduct(name,categoryId,skuCode,productCode,spec,saleCount,inventory,productCost,stockValue,purchaseCount,noCard,damage,
                lessDelivery,lessProduct,emptyBox,invArea,timestamp);
        } catch (Exception e) {
            Logger.error("product save exception "+e.getMessage());
            e.printStackTrace();
        }
        Map<String,String> area = new ObjectMapper().convertValue(configuration.getObject("area"),HashMap.class);
        return ok(views.html.sales.dataImport.render("cn", area,(User) ctx().args.get("user")));
    }

    /**
     * 当订单更新时更新产品中的总销售,库存量,库存商品价值等
     * @param saleProduct
     */
    private void updateProductAtUpdateOrder(SaleProduct saleProduct){
        //销售总量
        int saleCountTotal=saleService.getProductSaleCountTotal(saleProduct.getId());
        saleProduct.setSaleCount(saleCountTotal);
        Logger.info("总销售saleCountTotal="+saleCountTotal+",saleProduct="+saleProduct);
       //TODO...库存量

        saleService.updateSaleProduct(saleProduct);
    }

    /**
     * 数据查询
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result salesSearch() {
        SaleProduct saleProduct=new SaleProduct();
        saleProduct.setPageSize(-1);
        saleProduct.setOffset(-1);

        //取总数
        int countNum = saleService.getSaleProductPage(saleProduct).size();
        //共分几页
        int pageCount = countNum/PAGE_SIZE;

        if(countNum%PAGE_SIZE!=0){
            pageCount = countNum/PAGE_SIZE+1;
        }

        saleProduct.setPageSize(PAGE_SIZE);
        saleProduct.setOffset(0);

        List<SaleProduct> productList = saleService.getSaleProductPage(saleProduct);

        return ok(views.html.sales.dataSearch.render("cn",PAGE_SIZE,countNum,pageCount,productList,(User) ctx().args.get("user")));
    }

    /**
     * 销售订单导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderImport() {
        return ok(views.html.sales.saleOrderImport.render("cn", (User) ctx().args.get("user")));
    }

    /**
     * 销售订单导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderSave() {
        JsonNode json = request().body().asJson();
        try {
            Logger.info("=====saleOrderSave===" + json);
            String saleAt = json.findValue("saleAt").asText();
            String orderId = json.findValue("orderId").asText();
            Integer catagoryId = 1;
            Long saleProductId = json.findValue("saleProductId").asLong();
            String productName = json.findValue("productName").asText();
            BigDecimal price = new BigDecimal(json.findValue("price").asDouble());
            Integer saleCount = json.findValue("saleCount").asInt();
            BigDecimal discountAmount = new BigDecimal(json.findValue("discountAmount").asDouble());
            BigDecimal jdRate = new BigDecimal(json.findValue("jdRate").asDouble());
            BigDecimal cost = new BigDecimal(json.findValue("cost").asDouble());
            BigDecimal shipFee = new BigDecimal(json.findValue("shipFee").asDouble());
            BigDecimal inteLogistics = new BigDecimal(json.findValue("inteLogistics").asDouble());
            BigDecimal packFee = new BigDecimal(json.findValue("packFee").asDouble());
            BigDecimal storageFee = new BigDecimal(json.findValue("storageFee").asDouble());
            String postalTaxRate = json.findValue("postalTaxRate").asText();


            //总销售额=单价*数量-优惠额
            BigDecimal saleTotal = price.multiply(new BigDecimal(saleCount)).subtract(discountAmount);
            // 京东费用=总销售 额*京东费率
            BigDecimal jdFee = saleTotal.multiply(jdRate).divide(new BigDecimal(100));
            BigDecimal postalFee = new BigDecimal(0);
            //行邮税=如果总销售额>500元，=总销售额*行邮税率
            if (saleTotal.doubleValue() > 500) {   //分类别
                //TODO ...
                //   postalFee=saleTotal.multiply()
            }
            //净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费-包装费-仓储服务费-行邮税
            BigDecimal productCost = new BigDecimal(1);//成本 //TODO ..
            BigDecimal profit = saleTotal.subtract(jdFee).subtract((productCost.multiply(new BigDecimal(saleCount)))).
                    subtract(shipFee).subtract(inteLogistics).subtract(packFee).subtract(storageFee).subtract(postalFee);

            createSaleOrder(new SimpleDateFormat("yyyy-MM-dd").parse(saleAt), orderId, saleProductId, productName, catagoryId, price,saleCount, discountAmount, saleTotal, jdRate, jdFee, cost,
                    shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate, profit);

            SaleProduct saleProduct=saleService.getSaleProductById(saleProductId);
            if(null!=saleProduct){
                //订单更新后更新相关产品数据
                updateProductAtUpdateOrder(saleProduct);
            }
        }catch (Exception e){
            Logger.error("sale order save exception "+e.getMessage());
        }
        return ok(views.html.sales.saleOrderImport.render("cn", (User) ctx().args.get("user")));
    }

    /**
     * 销售订单导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleStatisticsView() {
        //saleStatistics:entity.Sale.SaleStatistics,
        SaleStatistics saleStatistics=null;
        try {
            //默认查询本月的销售
            SaleOrder saleOrder = new SaleOrder();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
            DateFormat format = new SimpleDateFormat(com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);
            saleOrder.setStarttime(format.format(c.getTime()));
            saleOrder.setEndtime(format.format(new Date()));

            List<SaleStatistics> saleStatisticsList = saleService.getSaleStatistics(saleOrder);
            if (null!=saleStatisticsList&&!saleStatisticsList.isEmpty()){
                saleStatistics=saleStatisticsList.get(0);
                Logger.info("===saleStatisticsView=="+saleStatistics);
            }
        }catch(Exception e){
            Logger.error("sale statistics exception"+e.getMessage());

        }

        return ok(views.html.sales.saleStatisticsView.render("cn", saleStatistics,(User) ctx().args.get("user")));
    }
    /**
     * 销售订单导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleStatistics() {
        JsonNode json = request().body().asJson();
        Logger.info("=====saleStatistics==============="+json);
        Integer categoryId=json.findValue("categoryId").asInt();
         List<SaleStatistics> saleStatisticsList=new ArrayList<SaleStatistics>() ;

        try {
            SaleOrder saleOrder = new SaleOrder();
            if (categoryId != -1) {
                saleOrder.setCategoryId(categoryId);
            }
            String starttime = "";
            if (json.has("starttime")) {
                starttime = json.findValue("starttime").asText();
                saleOrder.setStarttime(starttime);
            }
            String endtime = "";
            if (json.has("endtime")) {
                endtime = json.findValue("endtime").asText();
                saleOrder.setEndtime(endtime);
            }
           saleStatisticsList = saleService.getSaleStatistics(saleOrder);
            if (null!=saleStatisticsList&&!saleStatisticsList.isEmpty()){
                Logger.info("===saleStatistics==========="+saleStatisticsList.get(0));
            }

        }catch(Exception e){
            Logger.error("sale statistics exception"+e.getMessage());

        }

        return ok(Json.toJson(saleStatisticsList));
    }

    /***
     * 库存盘点
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleInventoryView() {
        return ok(views.html.sales.saleInventoryView.render("cn",(User) ctx().args.get("user")));
    }
    @Security.Authenticated(UserAuth.class)
    public Result saleInventory() {
        JsonNode json = request().body().asJson();
        Logger.info("=====saleInventory==============="+json);
        ObjectNode result = newObject();
        Long saleProductId=json.findValue("saleProductId").asLong();
        SaleOrder saleOrder = new SaleOrder();
        SaleProduct saleProduct=saleService.getSaleProductById(saleProductId);
        if (saleProductId != -1) {
            saleOrder.setSaleProductId(saleProductId);
        }
        String saleMonth = "";
        if (json.has("saleMonth")) {
            saleMonth = json.findValue("saleMonth").asText();
            saleOrder.setSaleMonth(saleMonth);
        }
        List<SaleInventory> saleInventoryList=saleService.getSaleInventory(saleOrder);
        int saleMonthTotal=0;
        if(null!=saleInventoryList&&!saleInventoryList.isEmpty()){
            for(SaleInventory saleInventory:saleInventoryList){
                saleMonthTotal+=saleInventory.getSaleCount();
            }
        }
        result.putPOJO("saleInventoryList",Json.toJson(saleInventoryList));
        result.putPOJO("saleProduct",Json.toJson(saleProduct));
        result.putPOJO("saleMonthTotal",saleMonthTotal);

        Logger.info("==saleInventory result====="+result);

        return ok(result);
    }

    /**
     * 订单查询
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderSearch(){
        //TODO ....
        SaleOrder saleOrder=new SaleOrder();
        saleOrder.setPageSize(-1);
        saleOrder.setOffset(-1);

        //取总数
        int countNum = saleService.getSaleOrderPage(saleOrder).size();
        //共分几页
        int pageCount = countNum/PAGE_SIZE;

        if(countNum%PAGE_SIZE!=0){
            pageCount = countNum/PAGE_SIZE+1;
        }

        saleOrder.setPageSize(PAGE_SIZE);
        saleOrder.setOffset(0);

        List<SaleOrder> orderList = saleService.getSaleOrderPage(saleOrder);

        return ok(views.html.sales.saleOrderSearch.render("cn",PAGE_SIZE,countNum,pageCount,orderList,(User) ctx().args.get("user")));
    }

}

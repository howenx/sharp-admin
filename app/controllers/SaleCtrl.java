package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import entity.Coupons;
import entity.Inventory;
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
     * @param categoryId 商品分类
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
    private SaleProduct createSaleProduct(String name,Integer categoryId,String skuCode,String productCode, String spec, Integer saleCount,
                                          Integer inventory, BigDecimal productCost,BigDecimal stockValue,Integer purchaseCount,Integer noCard,Integer damage,
                                          Integer lessDelivery, Integer lessProduct, Integer emptyBox,String invArea,Timestamp storageAt,Long customSkuId,Integer damageOther,
                                          String remark,Long createUserId,Long updateUserId){
        SaleProduct saleProduct=new SaleProduct();
        setSaleProduct(saleProduct,name,categoryId,skuCode,productCode,spec,saleCount,inventory,productCost,stockValue,purchaseCount,noCard,damage,
                lessDelivery,lessProduct,emptyBox,invArea,storageAt,customSkuId,damageOther,remark,createUserId,updateUserId);
        if(saleService.insertSaleProduct(saleProduct)){
            return saleProduct;
        }
        return null;
    }
    private void setSaleProduct(SaleProduct saleProduct,
                                String name,Integer categoryId,String skuCode,String productCode, String spec, Integer saleCount,
                                Integer inventory, BigDecimal productCost,BigDecimal stockValue,Integer purchaseCount,Integer noCard,Integer damage,
                                Integer lessDelivery, Integer lessProduct, Integer emptyBox,String invArea,Timestamp storageAt,Long customSkuId,Integer damageOther,String remark,
                                Long createUserId,Long updateUserId){
        saleProduct.setName(name);
        saleProduct.setCategoryId(categoryId);
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
        saleProduct.setCustomSkuId(customSkuId);
        saleProduct.setDamageOther(damageOther);
        saleProduct.setRemark(remark);
        saleProduct.setCreateUserId(createUserId);
        saleProduct.setUpdateUserId(updateUserId);
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
                                      BigDecimal inteLogistics, BigDecimal packFee,BigDecimal storageFee, BigDecimal postalFee, BigDecimal postalTaxRate, BigDecimal profit,
                                      String invArea,Integer remarkStatus,String remark,Long createUserId,Long updateUserId){
        SaleOrder saleOrder=new SaleOrder();
        setSaleOrder(saleOrder,saleAt,orderId, saleProductId, productName, categoryId, price, saleCount,
                discountAmount, saleTotal, jdRate, jdFee, cost,
                shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate, profit,invArea,remarkStatus,remark,createUserId,updateUserId);
        if(saleService.insertSaleOrder(saleOrder)){
            return saleOrder;
        }
        return null;
    }

    private void setSaleOrder(SaleOrder saleOrder,Date saleAt,String orderId,Long saleProductId,String productName, Integer categoryId,BigDecimal price,Integer saleCount,
                              BigDecimal discountAmount, BigDecimal saleTotal,BigDecimal jdRate,BigDecimal jdFee,BigDecimal cost,BigDecimal shipFee,
                              BigDecimal inteLogistics, BigDecimal packFee,BigDecimal storageFee, BigDecimal postalFee, BigDecimal postalTaxRate, BigDecimal profit,
                              String invArea,Integer remarkStatus,String remark,Long createUserId,Long updateUserId){
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
        saleOrder.setInvArea(invArea);
        saleOrder.setRemarkStatus(remarkStatus);
        saleOrder.setRemark(remark);
        saleOrder.setCreateUserId(createUserId);
        saleOrder.setUpdateUserId(updateUserId);
    }

    /**
     * 数据导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result dataImport() {
        Map<String,String> area = new ObjectMapper().convertValue(configuration.getObject("area"),HashMap.class);
        return ok(views.html.sales.dataImport.render("cn",area,(User) ctx().args.get("user")));
    }
    /**
     * 数据导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleProductFind(Long id) {
        Map<String,String> area = new ObjectMapper().convertValue(configuration.getObject("area"),HashMap.class);
        SaleProduct saleProduct=saleService.getSaleProductById(id);
        return ok(views.html.sales.dataUpdate.render("cn",saleProduct,area,(User) ctx().args.get("user")));
    }


    /**
     * 数据导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result productSave() {
        JsonNode json = request().body().asJson();
        Logger.info("=====productSave==="+json);
        SaleProduct saleProduct=null;
        try {
            User user = (User) ctx().args.get("user");
            Long userId=Long.valueOf(user.userId().get().toString());
            String name=json.findValue("name").asText();
            Integer categoryId=json.findValue("categoryId").asInt();
            String skuCode=json.findValue("skuCode").asText();
            String productCode=json.findValue("productCode").asText();
            Long customSkuId=json.findValue("customSkuId").asLong();
            String spec=json.findValue("spec").asText();
            BigDecimal productCost=new BigDecimal(json.findValue("productCost").asDouble());
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
                emptyBox=json.findValue("emptyBox").asInt();
            }

            Integer damageOther=0;
            if(json.has("damageOther")) {
                damageOther=json.findValue("damageOther").asInt();
            }
            String remark=json.findValue("remark").asText();
            String invArea=json.findValue("invArea").asText();
            String storageAt = json.findValue("storageAt").asText();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(format.parse(storageAt).getTime());
            String id=json.findValue("id").asText();
            Integer saleCount=0;  //总销量
            if(null!=id&&!"".equals(id)){
                saleProduct=saleService.getSaleProductById(Long.valueOf(id));
                saleCount=saleProduct.getSaleCount();//总销量
            }
            //库存量
            Integer inventory=purchaseCount-saleCount-noCard-damage-lessDelivery-lessProduct-emptyBox-damageOther;
            //库存商品价值
            BigDecimal stockValue=productCost.multiply(new BigDecimal(inventory));
            if(null==saleProduct){
                saleProduct=createSaleProduct(name,categoryId,skuCode,productCode,spec,saleCount,inventory,productCost,stockValue,purchaseCount,noCard,damage,
                        lessDelivery,lessProduct,emptyBox,invArea,timestamp,customSkuId,damageOther,remark,userId,userId);
            }
            else{
                List<SaleOrder> saleOrderList=null;
                if(saleProduct.getProductCost()!=productCost){//成本改变了
                    SaleOrder saleOrder=new SaleOrder();
                    saleOrder.setSaleProductId(Long.valueOf(id));
                    saleOrderList=saleService.getSaleOrder(saleOrder);
                }
                setSaleProduct(saleProduct,name,categoryId,skuCode,productCode,spec,saleCount,inventory,productCost,stockValue,purchaseCount,noCard,damage,
                        lessDelivery,lessProduct,emptyBox,invArea,timestamp,customSkuId,damageOther,remark,saleProduct.getCreateUserId(),userId);
                saleService.updateSaleProduct(saleProduct);

                //成本改变了更新订单中的成本,已经因为成本改变需要修改的数值,比如利润
                if(null!=saleOrderList&&!saleOrderList.isEmpty()){
                    for(SaleOrder saleOrder:saleOrderList){
                        saleOrder.setCost(productCost);
                        saleOrder.setProfit(getOrderProfit(saleOrder.getSaleTotal(),saleOrder.getJdFee(),saleOrder.getCost(),
                                saleOrder.getShipFee(),saleOrder.getInteLogistics(),saleOrder.getPackFee(),saleOrder.getStorageFee(),saleOrder.getPostalFee(),saleOrder.getSaleCount()));
                        Logger.info("profit="+saleOrder);
                        saleService.updateSaleOrder(saleOrder);

                    }
                }

            }


        } catch (Exception e) {
            Logger.error("product save exception "+e.getMessage());
            e.printStackTrace();
        }
        return ok(Json.toJson(saleProduct));
    }

    /**
     * 当订单更新时更新产品中的总销售,库存量,库存商品价值等
     * @param saleProduct
     */
    private void updateProductAtUpdateOrder(SaleProduct saleProduct){
        //销售总量
        int saleCountTotal=saleService.getProductSaleCountTotal(saleProduct.getId());
        saleProduct.setSaleCount(saleCountTotal);
        //更新库存
        int inventory=getProductInventory(saleProduct);
        saleProduct.setInventory(inventory);
        //库存商品价值
        saleProduct.setStockValue(saleProduct.getProductCost().multiply(new BigDecimal(saleProduct.getInventory())));

        Logger.info("总销售saleCountTotal="+saleCountTotal+",saleProduct="+saleProduct);

        saleService.updateSaleProduct(saleProduct);
    }

    /**
     * 获取库存量
     * @param saleProduct
     * @return
     */
    private int getProductInventory(SaleProduct saleProduct){
        return saleProduct.getPurchaseCount()-saleProduct.getSaleCount()-saleProduct.getNoCard()-saleProduct.getDamage()-saleProduct.getLessDelivery()
                -saleProduct.getLessProduct()-saleProduct.getEmptyBox()-saleProduct.getDamageOther();
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
     * 数据查询
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result salesSearchAjax(String lang,int pageNum) {
        JsonNode json = request().body().asJson();
        Logger.info("==salesSearchAjax===="+json);
        SaleProduct saleProduct=new SaleProduct();
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            String name=json.findValue("name").asText();
            if(null!=name&&!"".equals(name)){
                saleProduct.setName(name);
            }
            if (json.has("startTime")) {
                saleProduct.setStarttime(json.findValue("startTime").asText());
            }
            if (json.has("endTime")) {
                saleProduct.setEndtime(json.findValue("endTime").asText());
            }

            saleProduct.setPageSize(-1);
            saleProduct.setOffset(-1);
            List<SaleProduct> productList = saleService.getSaleProductPage(saleProduct);

            int countNum = productList.size();//取总数
            int pageCount = countNum/PAGE_SIZE;//共分几页
            if(countNum%PAGE_SIZE!=0){
                pageCount = countNum/PAGE_SIZE+1;
            }
            saleProduct.setPageSize(ThemeCtrl.PAGE_SIZE);
            saleProduct.setOffset(offset);
            productList = saleService.getSaleProductPage(saleProduct);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",productList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",ThemeCtrl.PAGE_SIZE);
            Logger.info("=salesSearchAjax=returnMap="+returnMap);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 销售订单导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderImport(Long id) {
        SaleProduct saleProduct=saleService.getSaleProductById(id);
        return ok(views.html.sales.saleOrderImport.render("cn", saleProduct,(User) ctx().args.get("user")));
    }

    /**
     * 销售订单导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderSave() {
        JsonNode json = request().body().asJson();
        ObjectNode result = newObject();
        SaleOrder saleOrder=null;
        try {
            Logger.info("=====saleOrderSave===" + json);
            User user = (User) ctx().args.get("user");
            Long userId=Long.valueOf(user.userId().get().toString());
            String saleAt = json.findValue("saleAt").asText();
            String orderId = json.findValue("orderId").asText();
            Long saleProductId = json.findValue("saleProductId").asLong();
            SaleProduct saleProduct=saleService.getSaleProductById(saleProductId);
            BigDecimal price = new BigDecimal(json.findValue("price").asDouble());
            Integer saleCount = json.findValue("saleCount").asInt();
            BigDecimal discountAmount = new BigDecimal(json.findValue("discountAmount").asDouble());
            BigDecimal jdRate = new BigDecimal(json.findValue("jdRate").asDouble());
            BigDecimal shipFee = new BigDecimal(json.findValue("shipFee").asDouble());
            BigDecimal inteLogistics = new BigDecimal(json.findValue("inteLogistics").asDouble());
            BigDecimal packFee = new BigDecimal(json.findValue("packFee").asDouble());
            BigDecimal storageFee = new BigDecimal(json.findValue("storageFee").asDouble());
            BigDecimal postalTaxRate = new BigDecimal(json.findValue("postalTaxRate").asDouble());
            Integer remarkStatus=json.findValue("remarkStatus").asInt();
            String remark=json.findValue("remark").asText();


            //总销售额=单价*数量-优惠额
            BigDecimal saleTotal = price.multiply(new BigDecimal(saleCount)).subtract(discountAmount);
            // 京东费用=总销售 额*京东费率
            BigDecimal jdFee = saleTotal.multiply(jdRate).divide(new BigDecimal(100));
            BigDecimal postalFee = new BigDecimal(0);
            int cate=saleProduct.getCategoryId();
            // 配饰 行邮税=如果总销售额>500元，=总销售额*行邮税率
            //化妆品 行邮税=如果总销售额>100元，=总销售额*行邮税率
            if((cate==1&&saleTotal.doubleValue() > 500)||(cate==2&&saleTotal.doubleValue() > 100)){
                postalFee=saleTotal.multiply(postalTaxRate).divide(new BigDecimal(100));
            }
            //净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费-包装费-仓储服务费-行邮税
            BigDecimal productCost = saleProduct.getProductCost();//成本
            BigDecimal profit = getOrderProfit(saleTotal,jdFee,productCost,shipFee,inteLogistics,packFee,storageFee,postalFee,saleCount);
            String id=json.findValue("id").asText();
            if(null==id||"".equals(id)) {
                saleOrder = createSaleOrder(new SimpleDateFormat("yyyy-MM-dd").parse(saleAt), orderId, saleProductId, saleProduct.getName(), cate, price, saleCount,
                        discountAmount, saleTotal, jdRate, jdFee, saleProduct.getProductCost(),
                        shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate, profit,saleProduct.getInvArea(),remarkStatus,remark,userId,userId);
            }else{
                saleOrder=saleService.getSaleOrderById(Long.valueOf(id));
                setSaleOrder(saleOrder,new SimpleDateFormat("yyyy-MM-dd").parse(saleAt), orderId, saleProductId, saleProduct.getName(), cate, price, saleCount,
                        discountAmount, saleTotal, jdRate, jdFee, saleProduct.getProductCost(),
                        shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate, profit,saleProduct.getInvArea(),remarkStatus,remark,saleOrder.getCreateUserId(),userId);
                saleService.updateSaleOrder(saleOrder);
            }

            if(null!=saleProduct){
                //订单更新后更新相关产品数据
                updateProductAtUpdateOrder(saleProduct);
            }
            result.putPOJO("order",saleOrder);
            result.putPOJO("product",saleProduct);
        }catch (Exception e){
            Logger.error("sale order save exception "+e.getMessage());
        }
        return ok(Json.toJson(result));
    }

    /**
     * 获取订单利润
     * @param saleTotal
     * @param jdFee
     * @param productCost
     * @param shipFee
     * @param inteLogistics
     * @param packFee
     * @param storageFee
     * @param postalFee
     * @param saleCount
     * @return
     */
    private BigDecimal getOrderProfit(BigDecimal saleTotal,BigDecimal jdFee,BigDecimal productCost,BigDecimal shipFee,
                                 BigDecimal inteLogistics, BigDecimal packFee,BigDecimal storageFee, BigDecimal postalFee, Integer saleCount){
        return saleTotal.subtract(jdFee).subtract((productCost.multiply(new BigDecimal(saleCount)))).
                subtract(shipFee).subtract(inteLogistics).subtract(packFee).subtract(storageFee).subtract(postalFee);
    }

    /**
     * 销售订单
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderFind(Long id) {
        SaleOrder saleOrder=saleService.getSaleOrderById(id);
        SaleProduct saleProduct=saleService.getSaleProductById(saleOrder.getSaleProductId());
        return ok(views.html.sales.saleOrderUpdate.render("cn", saleProduct,saleOrder,(User) ctx().args.get("user")));
    }


    /**
     * 销售订单导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleStatisticsView() {
        SaleStatistics saleStatistics=null;
        try {
            //默认查询本月的销售
            SaleOrder saleOrder = new SaleOrder();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
            c.set(Calendar.HOUR_OF_DAY, 0); //将小时至0
            c.set(Calendar.MINUTE, 0);//将分钟至0
            c.set(Calendar.SECOND,0);//将秒至0
            c.set(Calendar.MILLISECOND, 0);//将毫秒至0
            DateFormat format = new SimpleDateFormat(com.iwilley.b1ec2.api.Constants.DATE_TIME_FORMAT);
            saleOrder.setStarttime(format.format(c.getTime()));
            saleOrder.setEndtime(format.format(new Date()));

            List<SaleStatistics> saleStatisticsList = saleService.getSaleStatistics(saleOrder);
            if (null!=saleStatisticsList&&!saleStatisticsList.isEmpty()){
                saleStatistics=saleStatisticsList.get(0);
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
    public Result saleInventoryView(Long id) {
        SaleProduct saleProduct=saleService.getSaleProductById(id);
        if(null==saleProduct){
            return badRequest();
        }
        return ok(views.html.sales.saleInventoryView.render("cn",saleProduct,(User) ctx().args.get("user")));
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

    /**
     * 订单查询
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderSearchAjax(String lang,int pageNum){
        Logger.info("==saleOrderSearchAjax========================");
        JsonNode json = request().body().asJson();
        Logger.info("==saleOrderSearchAjax===="+json);
        SaleOrder saleOrder=new SaleOrder();
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            String orderId=json.findValue("orderId").asText();
            if(null!=orderId&&!"".equals(orderId)){
                saleOrder.setOrderId(orderId);
            }
            String name=json.findValue("name").asText();
            if(null!=name&&!"".equals(name)){
                saleOrder.setProductName(name);
            }
            if (json.has("startTime")) {
                saleOrder.setStarttime(json.findValue("startTime").asText());
            }
            if (json.has("endTime")) {
                saleOrder.setEndtime(json.findValue("endTime").asText());
            }

            saleOrder.setPageSize(-1);
            saleOrder.setOffset(-1);
            List<SaleOrder> orderList = saleService.getSaleOrderPage(saleOrder);

            int countNum = orderList.size();//取总数
            int pageCount = countNum/PAGE_SIZE;//共分几页
            if(countNum%PAGE_SIZE!=0){
                pageCount = countNum/PAGE_SIZE+1;
            }
            saleOrder.setPageSize(ThemeCtrl.PAGE_SIZE);
            saleOrder.setOffset(offset);
            orderList = saleService.getSaleOrderPage(saleOrder);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",orderList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",ThemeCtrl.PAGE_SIZE);
            Logger.info("=salesSearchAjax=returnMap="+returnMap);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 销售订单
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderDel(Long id) {
        SaleOrder saleOrder=saleService.getSaleOrderById(id);
        if(null!=saleOrder){
            SaleProduct saleProduct=saleService.getSaleProductById(saleOrder.getSaleProductId());
            if(saleService.delSaleOrderById(id)){
                if(null!=saleProduct){
                    //订单更新后更新相关产品数据
                    updateProductAtUpdateOrder(saleProduct);
                }
                return ok("success");
            }
        }

        return badRequest();

    }
}

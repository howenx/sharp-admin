package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import entity.User;
import entity.sale.SaleInventory;
import entity.sale.SaleOrder;
import entity.sale.SaleProduct;
import entity.sale.SaleStatistics;
import filters.UserAuth;
import play.Configuration;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import service.SaleService;
import util.ExcelHelper;

import java.io.File;
import java.io.IOException;
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
@SuppressWarnings("unchecked")
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
                                          String remark,Long createUserId,Long updateUserId,String jdSkuId,Integer saleFinishStatus){
        SaleProduct saleProduct=new SaleProduct();
        setSaleProduct(saleProduct,name,categoryId,skuCode,productCode,spec,saleCount,inventory,productCost,stockValue,purchaseCount,noCard,damage,
                lessDelivery,lessProduct,emptyBox,invArea,storageAt,customSkuId,damageOther,remark,createUserId,updateUserId,jdSkuId,saleFinishStatus);
        if(saleService.insertSaleProduct(saleProduct)){
            return saleProduct;
        }
        return null;
    }
    private void setSaleProduct(SaleProduct saleProduct,
                                String name,Integer categoryId,String skuCode,String productCode, String spec, Integer saleCount,
                                Integer inventory, BigDecimal productCost,BigDecimal stockValue,Integer purchaseCount,Integer noCard,Integer damage,
                                Integer lessDelivery, Integer lessProduct, Integer emptyBox,String invArea,Timestamp storageAt,Long customSkuId,Integer damageOther,String remark,
                                Long createUserId,Long updateUserId,String jdSkuId,Integer saleFinishStatus){
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
        saleProduct.setJdSkuId(jdSkuId);
        saleProduct.setSaleFinishStatus(saleFinishStatus);
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
                                      String invArea,Integer remarkStatus,String remark,Long createUserId,Long updateUserId,Integer shop,Integer inputType){
        SaleOrder saleOrder=new SaleOrder();
        setSaleOrder(saleOrder,saleAt,orderId, saleProductId, productName, categoryId, price, saleCount,
                discountAmount, saleTotal, jdRate, jdFee, cost,
                shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate, profit,invArea,remarkStatus,remark,createUserId,updateUserId,shop,inputType);
        if(saleService.insertSaleOrder(saleOrder)){
            return saleOrder;
        }
        return null;
    }

    private void setSaleOrder(SaleOrder saleOrder,Date saleAt,String orderId,Long saleProductId,String productName, Integer categoryId,BigDecimal price,Integer saleCount,
                              BigDecimal discountAmount, BigDecimal saleTotal,BigDecimal jdRate,BigDecimal jdFee,BigDecimal cost,BigDecimal shipFee,
                              BigDecimal inteLogistics, BigDecimal packFee,BigDecimal storageFee, BigDecimal postalFee, BigDecimal postalTaxRate, BigDecimal profit,
                              String invArea,Integer remarkStatus,String remark,Long createUserId,Long updateUserId,Integer shop,Integer inputType){
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
        saleOrder.setShop(shop);
        saleOrder.setInputType(inputType);
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
//        Logger.info("=====productSave==="+json);
        SaleProduct saleProduct=null;
        try {
            User user = (User) ctx().args.get("user");
            Long userId=Long.valueOf(user.userId().get().toString());
            String name=json.findValue("name").asText().trim();
            Integer categoryId=json.findValue("categoryId").asInt();
            String skuCode=json.findValue("skuCode").asText().trim().trim();
            String productCode=json.findValue("productCode").asText().trim();
            Long customSkuId=json.findValue("customSkuId").asLong();
            String jdSkuId=json.findValue("jdSkuId").asText().trim();
            String spec=json.findValue("spec").asText().trim();
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
                lessDelivery=json.findValue("lessDelivery").asInt();
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
            String remark=json.findValue("remark").asText().trim();
            String invArea=json.findValue("invArea").asText().trim();
            String storageAt = json.findValue("storageAt").asText().trim();
            Integer saleFinishStatus=json.findValue("saleFinishStatus").asInt();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(format.parse(storageAt).getTime());
            String id=json.findValue("id").asText().trim();
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
                        lessDelivery,lessProduct,emptyBox,invArea,timestamp,customSkuId,damageOther,remark,userId,userId,jdSkuId,saleFinishStatus);
            }
            else{
                List<SaleOrder> saleOrderList=null;
                if(saleProduct.getProductCost()!=productCost){//成本改变了
                    SaleOrder saleOrder=new SaleOrder();
                    saleOrder.setSaleProductId(Long.valueOf(id));
                    saleOrderList=saleService.getSaleOrder(saleOrder);
                }
//                Logger.info("===noCard=="+noCard+",damage="+damage+",lessDelivery="+lessDelivery);
                setSaleProduct(saleProduct,name,categoryId,skuCode,productCode,spec,saleCount,inventory,productCost,stockValue,purchaseCount,noCard,damage,
                        lessDelivery,lessProduct,emptyBox,invArea,timestamp,customSkuId,damageOther,remark,saleProduct.getCreateUserId(),userId,jdSkuId,saleFinishStatus);
                saleService.updateSaleProduct(saleProduct);

                //成本改变了更新订单中的成本,已经因为成本改变需要修改的数值,比如利润
                if(null!=saleOrderList&&!saleOrderList.isEmpty()){
                    for(SaleOrder saleOrder:saleOrderList){
                        saleOrder.setCost(productCost);
                        saleOrder.setProfit(getOrderProfit(saleOrder.getSaleTotal(),saleOrder.getJdFee(),saleOrder.getCost(),
                                saleOrder.getShipFee(),saleOrder.getInteLogistics(),saleOrder.getPackFee(),saleOrder.getStorageFee(),saleOrder.getPostalFee(),saleOrder.getSaleCount(),saleOrder.getRemarkStatus()));
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
        //退货数
        saleProduct.setBackCount(saleService.getProductBackCountTotal(saleProduct.getId()));

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
        SaleProduct saleProduct=new SaleProduct();
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            String name=json.findValue("name").asText().trim();
            if(null!=name&&!"".equals(name)){
                saleProduct.setName(name);
            }
            String jdSkuId=json.findValue("jdSkuId").asText().trim();
            if(null!=jdSkuId&&!"".equals(jdSkuId)){
                saleProduct.setJdSkuId(jdSkuId);
            }
            if (json.has("startTime")) {
                saleProduct.setStarttime(json.findValue("startTime").asText().trim());
            }
            if (json.has("endTime")) {
                saleProduct.setEndtime(json.findValue("endTime").asText().trim());
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
            User user = (User) ctx().args.get("user");
            Long userId=Long.valueOf(user.userId().get().toString());
            String saleAt = json.findValue("saleAt").asText().trim();
            String orderId = json.findValue("orderId").asText().trim();
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
            String remark=json.findValue("remark").asText().trim();
            Integer shop=json.findValue("shop").asInt();
            Integer inputType=json.findValue("inputType").asInt();


            //总销售额=单价*数量-优惠额
            BigDecimal saleTotal = saleTotal=price.multiply(new BigDecimal(saleCount)).subtract(discountAmount);
            // 京东费用=总销售 额*京东费率
            BigDecimal jdFee = saleTotal.multiply(jdRate).divide(new BigDecimal(100));
            BigDecimal postalFee = new BigDecimal(0);
            int cate=saleProduct.getCategoryId();
            // 配饰 行邮税=如果总销售额>500元，=总销售额*行邮税率
            //化妆品 行邮税=如果总销售额>100元，=总销售额*行邮税率
            if((cate==1&&saleTotal.doubleValue() > 500)||(cate==2&&saleTotal.doubleValue() > 100)){
                postalFee=saleTotal.multiply(postalTaxRate).divide(new BigDecimal(100));
            }
            if(remarkStatus==5){ //退货时总销售额为0,京东费用为0
                saleTotal=new BigDecimal(0);
                jdFee=new BigDecimal(0);

            }
            //净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
            BigDecimal productCost = saleProduct.getProductCost();//成本
            BigDecimal profit = getOrderProfit(saleTotal,jdFee,productCost,shipFee,inteLogistics,packFee,storageFee,postalFee,saleCount,remarkStatus);
            String id=json.findValue("id").asText().trim();
            if(null==id||"".equals(id)) {
                saleOrder = createSaleOrder(new SimpleDateFormat("yyyy-MM-dd").parse(saleAt), orderId, saleProductId, saleProduct.getName(), cate, price, saleCount,
                        discountAmount, saleTotal, jdRate, jdFee, saleProduct.getProductCost(),
                        shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate, profit,saleProduct.getInvArea(),remarkStatus,remark,userId,userId,shop,inputType);
            }else{
                saleOrder=saleService.getSaleOrderById(Long.valueOf(id));
                setSaleOrder(saleOrder,new SimpleDateFormat("yyyy-MM-dd").parse(saleAt), orderId, saleProductId, saleProduct.getName(), cate, price, saleCount,
                        discountAmount, saleTotal, jdRate, jdFee, saleProduct.getProductCost(),
                        shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate, profit,saleProduct.getInvArea(),remarkStatus,remark,saleOrder.getCreateUserId(),userId,shop,inputType);
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
                                 BigDecimal inteLogistics, BigDecimal packFee,BigDecimal storageFee, BigDecimal postalFee, Integer saleCount,Integer remarkStatus){
        BigDecimal count=new BigDecimal(saleCount);//数量
        if(remarkStatus==5){ //退单
            //净利润=-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
            return new BigDecimal(0).subtract((productCost.multiply(count))).
                    subtract(shipFee).subtract((inteLogistics.multiply(count))).subtract(packFee).subtract(storageFee).subtract(postalFee);
        }
        // 净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
        return saleTotal.subtract(jdFee).subtract((productCost.multiply(count))).
                subtract(shipFee).subtract((inteLogistics.multiply(count))).subtract(packFee).subtract(storageFee).subtract(postalFee);
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
        Integer categoryId=json.findValue("categoryId").asInt();
         List<SaleStatistics> saleStatisticsList=new ArrayList<SaleStatistics>() ;

        try {
            SaleOrder saleOrder = new SaleOrder();
            if (categoryId != -1) {
                saleOrder.setCategoryId(categoryId);
            }
            String starttime = "";
            if (json.has("starttime")) {
                starttime = json.findValue("starttime").asText().trim();
                saleOrder.setStarttime(starttime);
            }
            String endtime = "";
            if (json.has("endtime")) {
                endtime = json.findValue("endtime").asText().trim();
                saleOrder.setEndtime(endtime);
            }
            if (json.has("productName")) {
                saleOrder.setProductName(json.findValue("productName").asText().trim());
            }
            Integer shop=json.findValue("shop").asInt();
            if (shop>0) {
                saleOrder.setShop(shop);
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
        ObjectNode result = newObject();
        Long saleProductId=json.findValue("saleProductId").asLong();
        SaleOrder saleOrder = new SaleOrder();
        SaleProduct saleProduct=saleService.getSaleProductById(saleProductId);
        if (saleProductId != -1) {
            saleOrder.setSaleProductId(saleProductId);
        }
        String saleMonth = "";
        if (json.has("saleMonth")) {
            saleMonth = json.findValue("saleMonth").asText().trim();
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


        return ok(result);
    }

    /**
     * 订单查询
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderSearch(){
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
        JsonNode json = request().body().asJson();
        SaleOrder saleOrder=new SaleOrder();
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;
            String orderId=json.findValue("orderId").asText().trim();
            if(null!=orderId&&!"".equals(orderId)){
                saleOrder.setOrderId(orderId);
            }
            String name=json.findValue("name").asText().trim();
            if(null!=name&&!"".equals(name)){
                saleOrder.setProductName(name);
            }
            if (json.has("startTime")) {
                saleOrder.setStarttime(json.findValue("startTime").asText().trim());
            }
            if (json.has("endTime")) {
                saleOrder.setEndtime(json.findValue("endTime").asText().trim());
            }
            Integer shop=json.findValue("shop").asInt();
            if (shop>0) {
                saleOrder.setShop(shop);
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
    /**
     * 销售数据从excel导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleImport() {
        return ok(views.html.sales.saleImport.render("cn",(User) ctx().args.get("user")));
    }

    /**
     * 导入订单
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result uploadOrder() {
        User user = (User) ctx().args.get("user");
        Long userId=Long.valueOf(user.userId().get().toString());
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Map<String, String[]> stringMap = body.asFormUrlEncoded();
        Map<String, String> map = new HashMap<>();

        stringMap.forEach((k, v) -> map.put(k, v[0]));

        Http.MultipartFormData.FilePart filePart = body.getFile("orderFile");
        File file=filePart.getFile();
        List<String> list = null;
        try {
            list = ExcelHelper.exportListFromCsv(file);
        } catch (Exception e) {
            Logger.error("exception="+e);

        }

        StringBuffer sb=new StringBuffer();
        if(null!=list&&list.size()>0){

//            Logger.info(list.get(1));
            String[] str1=list.get(0).split(",");
            if(str1.length<30){
                Logger.error("导入表格长度不对"+str1.length);
                return badRequest();
            }
//            String[] str=list.get(1).split(",");
//            String[] str2=list.get(2).split(",");
//            for(int i=0;i<str.length;i++){
//                Logger.info(str[i]+"-->"+str1[i]);
//                sb.append(i+"-->"+str1[i]+"-->"+str[i]).append("\n");
//
//            }

            /**
             *
             0-->订单号-->11729384072
             1-->商品ID-->1953814331
             2-->商品名称-->"韩秘美 Crystalshop 施华洛世奇水晶 娜奥米珍珠银饰 套装"
             3-->订购数量-->1
             4-->支付方式-->在线支付
             5-->下单时间-->2015-12-31 23:19:56
             6-->京东价-->758.00
             7-->订单金额-->1097.00
             8-->结算金额-->932.45
             9-->余额支付-->0.00
             10-->应付金额-->932.45
             11-->订单状态-->(删除)等待出库
             12-->订单类型-->销售订单
             13-->下单帐号-->rds3199358
             14-->客户姓名-->"王冬阳"
             15-->客户地址-->"江西南昌市青山湖区昌东工业区南昌市青山湖区昌东工业区恒源科技园1栋仓库（荣德胜）"
             16-->联系电话-->"13767111772"
             17-->订单备注-->
             18-->发票类型-->
             19-->发票抬头-->
             20-->发票内容-->
             21-->商家备注-->"订单拆分为  11729439936    11749907506"
             22-->商家备注等级（等级1-5为由高到低）-->
             23-->运费金额-->0.00
             24-->付款确认时间-->2015-12-31 23:20:30
             25-->增值税发票-->
             26-->货号-->
             27-->订单完成时间-->
             28-->订单来源-->移动端订单
             29-->订单渠道-->暂无来源
             */
            StringBuffer skuErr=new StringBuffer();
            StringBuffer orderErr=new StringBuffer();
            StringBuffer suc=new StringBuffer();
            StringBuffer existErr=new StringBuffer();
            String[] str=null;
            for(int i=1;i<list.size();i++) {
                str= list.get(i).split(",");
                String orderId=str[0];
                Integer saleCount=Integer.valueOf(str[3]);
                SaleProduct saleProduct=new SaleProduct();
                String jdSkuId=str[1];
                saleProduct.setJdSkuId(jdSkuId);
                List<SaleProduct> productList=saleService.getSaleProduct(saleProduct);
                if(null==productList||productList.isEmpty()){
                    Logger.error("\n"+"第"+(i)+"行京东商品不存在jdSkuId="+jdSkuId+",orderId="+orderId);
                    skuErr.append("\n"+"第"+(i)+"行京东商品不存在jdSkuId="+jdSkuId+",orderId="+orderId);
                    continue;
                }else {
                    if(productList.size()>1){
                        int productNum=0;
                        for(SaleProduct temp:productList){
                            if(temp.getSaleFinishStatus()==1){ //未卖完
                                productNum++;
                                saleProduct=temp;
                            }
                            if(productNum>=2){
                                break;
                            }
                        }
                        if(productNum!=1){
                            Logger.error("\n"+"第"+(i)+"行京东商品存在至少"+productNum+"个jdSkuId="+jdSkuId+",orderId="+orderId);
                            existErr.append("\n"+"第"+(i)+"行京东商品存在至少"+productNum+"个jdSkuId="+jdSkuId+",orderId="+orderId);
                            continue;
                        }
                    }else{
                        saleProduct=productList.get(0);
                    }

                }

                SaleOrder saleOrder=new SaleOrder();
                saleOrder.setOrderId(orderId);
                saleOrder.setSaleProductId(saleProduct.getId()); //订单和商品id联合
                saleOrder.setInputType(2);

                List<SaleOrder> saleOrderList=saleService.getSaleOrder(saleOrder);
                if(null!=saleOrderList&&!saleOrderList.isEmpty()){
                    Logger.error("\n"+"第"+(i)+"行订单已经存在orderId="+orderId);
                    orderErr.append("\n"+"第"+(i)+"行订单已经存在orderId="+orderId);
                    continue;
                }


                BigDecimal price=new BigDecimal(str[6]); //TODO ..单价
                BigDecimal discountAmount=new BigDecimal(str[7]).subtract(new BigDecimal(str[10])); //优惠额
                BigDecimal jdRate=new BigDecimal(10);  //京东费率
                BigDecimal postalTaxRate=new BigDecimal(0); //行邮税
                BigDecimal shipFee = new BigDecimal(0);  //运费
                BigDecimal inteLogistics = new BigDecimal(0); //国际运费
                BigDecimal packFee = new BigDecimal(0); //包装费
                BigDecimal storageFee = new BigDecimal(0); //存储费

                Integer remarkStatus=1;
                Integer shop=Integer.valueOf(map.get("shop"));

                //总销售额=单价*数量-优惠额
                BigDecimal saleTotal=price.multiply(new BigDecimal(saleCount)).subtract(discountAmount);
                // 京东费用=总销售 额*京东费率
                BigDecimal jdFee = saleTotal.multiply(jdRate).divide(new BigDecimal(100));
                BigDecimal postalFee = new BigDecimal(0);
                int cate=saleProduct.getCategoryId();
                // 配饰 行邮税=如果总销售额>500元，=总销售额*行邮税率
                //化妆品 行邮税=如果总销售额>100元，=总销售额*行邮税率
                if((cate==1&&saleTotal.doubleValue() > 500)||(cate==2&&saleTotal.doubleValue() > 100)){
                    postalFee=saleTotal.multiply(postalTaxRate).divide(new BigDecimal(100));
                }
                if(remarkStatus==5){ //退货时总销售额为0,京东费用为0
                    saleTotal=new BigDecimal(0);
                    jdFee=new BigDecimal(0);

                }
                //净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
                BigDecimal productCost = saleProduct.getProductCost();//成本
                BigDecimal profit = getOrderProfit(saleTotal,jdFee,productCost,shipFee,inteLogistics,packFee,storageFee,postalFee,saleCount,remarkStatus);
                Logger.info(saleTotal+"=saleCount="+saleCount+",discountAmount="+discountAmount+",profit="+profit);

                try {
                    createSaleOrder(new SimpleDateFormat("yyyy-MM-dd").parse(str[24]),
                            str[0], saleProduct.getId(), str[2], saleProduct.getCategoryId(), price, saleCount,
                            discountAmount, saleTotal, jdRate, jdFee, saleProduct.getProductCost(),
                            shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate,
                            profit,saleProduct.getInvArea(),remarkStatus,str[21],userId,userId,shop,2);
                    Logger.info("\n"+"第"+(i)+"行成功,orderId="+orderId);
                    suc.append("\n"+"第"+(i)+"行成功,orderId="+orderId);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(skuErr.length()>0){
                sb.append("\n\n京东商品不存在:\n");
                sb.append(skuErr);
            }
            if(existErr.length()>0){
                sb.append("\n\n京东商品存在多个:\n");
                sb.append(existErr);
            }
            if(orderErr.length()>0){
                sb.append("\n\n订单已经存在:\n");
                sb.append(orderErr);
            }
            if(suc.length()>0){
                sb.append("\n导入成功:\n");
                sb.append(suc);
            }

        }
        //释放
        list.clear();
        list=null;
        return ok("订单费用导入成功\n"+sb);
    }

    /**
     * 导入订单费用
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result uploadOrderFee() {
        User user = (User) ctx().args.get("user");
        Long userId=Long.valueOf(user.userId().get().toString());
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("feeFile");
        File file=filePart.getFile();
        List<String> list = null;
        try {
            list = ExcelHelper.exportListFromCsv(file);
        } catch (Exception e) {
            Logger.error("exception="+e);

        }

        StringBuffer sb=new StringBuffer();
        if(null!=list&&list.size()>0){

            String[] str1=list.get(0).split(";");
            if(str1.length<16){
                Logger.error("导入表格长度不对"+str1.length);
                return badRequest();
            }
//            String[] str=list.get(1).split(";");
//            String[] str2=list.get(2).split(";");
//            for(int i=0;i<str.length;i++){
//                Logger.info(str[i]+"-->"+str1[i]);
//                sb.append(i+"-->"+str1[i]+"-->"+str[i]).append("\n");
//
//            }

            /***
             *
             0-->货主-->东方爱怡斯
             1-->出库单号（货主）-->12035103894
             2-->收货省-->湖北
             3-->收货市-->武汉市
             4-->收货区-->武汉经济技术开发区
             5-->发运时间-->2016/2/2 16:34:16
             6-->物流单号-->VB25924044591
             7-->装箱单号-->VL100000010231900001
             8-->包材编码-->HZBOX006
             9-->包材名称-->2号外包装箱
             10-->货品名称-->FASCY发希护肤三件套（野蛮蒂娜水分香气气垫粉15g＋水分炸弹护手霜80ml牛奶味＋野蛮蒂娜化妆包）
             11-->货品数量-->1
             12-->重量-->0.802
             13-->操作费-->10
             14-->包材费-->1
             15-->快递费-->10.2
             */
            String[] str = null;
            SaleOrder saleOrder=null;
            StringBuffer orderErr=new StringBuffer();
            StringBuffer suc=new StringBuffer();
            SaleOrder temp=null;
            for(int i=1;i<list.size();i++){
                str=list.get(i).split(";");
                temp=new SaleOrder();
                temp.setOrderId(str[1]);
                List<SaleOrder> saleOrderList=saleService.getSaleOrder(temp);

                if(null==saleOrderList||saleOrderList.isEmpty()){
                    Logger.error("\n"+i+"行订单不存在,orderId="+str[1]);
                    orderErr.append("\n"+i+"行订单不存在,orderId="+str[1]);
                    continue;
                }
                saleOrder=saleOrderList.get(0);
                saleOrder.setShipFee(new BigDecimal(str[15]));
                saleOrder.setPackFee(new BigDecimal(str[14]));
                saleOrder.setStorageFee(new BigDecimal(str[13]));

                saleOrder.setProfit(getOrderProfit(saleOrder.getSaleTotal(),saleOrder.getJdFee(),saleOrder.getCost(),
                        saleOrder.getShipFee(),saleOrder.getInteLogistics(),saleOrder.getPackFee(),saleOrder.getStorageFee(),saleOrder.getPostalFee(),saleOrder.getSaleCount(),saleOrder.getRemarkStatus()));
                saleService.updateSaleOrder(saleOrder);
                Logger.info("profit="+saleOrder);
                suc.append("\n"+i+"行订单费用导入成功,orderId="+str[1]);
            }

            if(orderErr.length()>0){
                sb.append("\n订单不存在:\n");
                sb.append(orderErr);
            }
            if(suc.length()>0){
                sb.append("\n导入成功:\n");
                sb.append(suc);
            }
        }
        //释放
        list.clear();
        list=null;
        return ok("订单费用导入操作成功\n"+sb);
    }

    /**
     * 妥投销货清单明细.csv
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result uploadOrderSaleDetail() {
        User user = (User) ctx().args.get("user");
        Long userId=Long.valueOf(user.userId().get().toString());
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("saleDetailFile");
        File file=filePart.getFile();
        List<String> list = null;
        try {
            list = ExcelHelper.exportListFromCsv(file);
        } catch (Exception e) {
            Logger.error("exception="+e);

        }

        StringBuffer sb=new StringBuffer();
        if(null!=list&&list.size()>0){

            String[] str1=list.get(0).split(",");
            if(str1.length<12){
                Logger.error("导入表格长度不对"+str1.length);
                return badRequest();
            }
            /***
             *
             0-->订单编号-->12278430604
             1-->结算金额-->23.86
             2-->商品应结金额-->25.93
             3-->商品佣金-->-2.07
             4-->一级类目-->珠宝首饰
             5-->二级类目-->银饰
             6-->三级类目-->银吊坠/项链
             7-->SKU编号-->1955765369
             8-->货号-->E_0014 N_0059
             9-->商品名称-->Crystalshop 施华洛世奇水晶珀蒂小皇冠珍珠8mm银饰套装(项链+耳钉)
             10-->SKU单价-->25.93
             11-->数量-->1
             */
            String[] str = null;
            SaleOrder saleOrder=null;
            StringBuffer orderErr=new StringBuffer();
            StringBuffer suc=new StringBuffer();
            SaleOrder temp=null;
            for(int i=1;i<list.size();i++){
                str=list.get(i).split(",");
                temp=new SaleOrder();
                String orderId=str[0];
                if("".equals(orderId)||null==orderId){
                    Logger.error("\n"+i+"行订单不存在,orderId="+orderId);
                    orderErr.append("\n"+i+"行订单不存在,orderId="+orderId);
                    continue;
                }
                temp.setOrderId(orderId);
                List<SaleOrder> saleOrderList=saleService.getSaleOrder(temp);

                if(null==saleOrderList||saleOrderList.isEmpty()){
                    Logger.error("\n"+i+"行订单不存在,orderId="+orderId);
                    orderErr.append("\n"+i+"行订单不存在,orderId="+orderId);
                    continue;
                }
                //商品应结金额*京东费率=商品佣金，用这个公式商品佣金/商品应结金额=京东费率
                saleOrder=saleOrderList.get(0);
                BigDecimal jdFee=new BigDecimal(str[3]);
                saleOrder.setJdFee(jdFee);
                BigDecimal jdRate=jdFee.multiply(new BigDecimal(100)).divide(new BigDecimal(str[2]),2); //jd rate 扩大了100倍
                saleOrder.setJdRate(jdRate);

                saleOrder.setProfit(getOrderProfit(saleOrder.getSaleTotal(),saleOrder.getJdFee(),saleOrder.getCost(),
                        saleOrder.getShipFee(),saleOrder.getInteLogistics(),saleOrder.getPackFee(),saleOrder.getStorageFee(),saleOrder.getPostalFee(),saleOrder.getSaleCount(),saleOrder.getRemarkStatus()));
                saleService.updateSaleOrder(saleOrder);
                suc.append("\n"+i+"行订单费用导入成功,orderId="+orderId);
            }

            if(orderErr.length()>0){
                sb.append("\n订单不存在:\n");
                sb.append(orderErr);
            }
            if(suc.length()>0){
                sb.append("\n导入成功:\n");
                sb.append(suc);
            }
        }
        //释放
        list.clear();
        list=null;
        return ok("妥投销货清单明细导入操作成功\n"+sb);
    }
}

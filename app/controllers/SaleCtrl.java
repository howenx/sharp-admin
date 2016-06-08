package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
import com.google.inject.Inject;
import domain.User;
import domain.sale.*;
import filters.UserAuth;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import service.SaleService;
import util.ExcelHelper;

import java.io.File;
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
    /**
     * 退货
     */
    private static final String ORDER_STATUS_REFUND="T";
    /**
     * 正常
     */
    private static final String ORDER_STATUS_SUC="S";
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
     * @param discountAmount 优惠额
     * @param saleTotal 销售
     * @param jdFeeSum 京东费用和
     * @param shipFee 国内快递费
     * @param inteLogistics 国际物流
     * @param packFee 包装
     * @param postalFee 行邮税
     * @param profit 净利
     * @return
     */
    private SaleOrder createSaleOrder(Date saleAt,String orderId,
                                      BigDecimal discountAmount, BigDecimal saleTotal,BigDecimal jdFeeSum,BigDecimal productCostSum,BigDecimal shipFee,
                                      BigDecimal inteLogistics, BigDecimal packFee,BigDecimal storageFee, BigDecimal postalFee, BigDecimal profit,
                                      String invArea,Integer remarkStatus,String remark,Long createUserId,Long updateUserId,Integer shop,Integer inputType,String orderStatus,Integer feeCategoryId){
        SaleOrder saleOrder=new SaleOrder();
        setSaleOrder(saleOrder,saleAt,orderId, discountAmount, saleTotal, jdFeeSum,productCostSum,
                shipFee, inteLogistics, packFee, storageFee, postalFee, profit,invArea,remarkStatus,remark,createUserId,updateUserId,shop,inputType,orderStatus, feeCategoryId);
        if(saleService.insertSaleOrder(saleOrder)){
            return saleOrder;
        }
        return null;
    }

    private void setSaleOrder(SaleOrder saleOrder,Date saleAt,String orderId,
                              BigDecimal discountAmount, BigDecimal saleTotal,BigDecimal jdFeeSum,BigDecimal productCostSum,BigDecimal shipFee,
                              BigDecimal inteLogistics, BigDecimal packFee,BigDecimal storageFee, BigDecimal postalFee, BigDecimal profit,
                              String invArea,Integer remarkStatus,String remark,Long createUserId,Long updateUserId,Integer shop,Integer inputType,String orderStatus,Integer feeCategoryId){
        saleOrder.setSaleAt(saleAt);
        saleOrder.setOrderId(orderId);
    //    saleOrder.setSaleProductId(saleProductId);
    //    saleOrder.setProductName(productName);
    //    saleOrder.setCategoryId(categoryId);
    //   saleOrder.setPrice(price);
    //   saleOrder.setSaleCount(saleCount);
        saleOrder.setDiscountAmount(discountAmount);
        saleOrder.setSaleTotal(saleTotal);
     //   saleOrder.setJdRate(jdRate);
        saleOrder.setJdFeeSum(jdFeeSum);
        saleOrder.setProductCostSum(productCostSum);
        saleOrder.setShipFee(shipFee);
        saleOrder.setInteLogistics(inteLogistics);
        saleOrder.setPackFee(packFee);
        saleOrder.setStorageFee(storageFee);
        saleOrder.setPostalFee(postalFee);
//        saleOrder.setPostalTaxRate(postalTaxRate);
        saleOrder.setProfit(profit);
        saleOrder.setInvArea(invArea);
        saleOrder.setRemarkStatus(remarkStatus);
        saleOrder.setRemark(remark);
        saleOrder.setCreateUserId(createUserId);
        saleOrder.setUpdateUserId(updateUserId);
        saleOrder.setShop(shop);
        saleOrder.setInputType(inputType);
        saleOrder.setOrderStatus(orderStatus);
        saleOrder.setFeeCategoryId(feeCategoryId);
     //   saleOrder.setJdSkuId(jdSkuId);
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
            BigDecimal productCost=new BigDecimal(json.findValue("productCost").asDouble()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
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

                BigDecimal oldProductCost=saleProduct.getProductCost().setScale(2,BigDecimal.ROUND_HALF_DOWN);
                setSaleProduct(saleProduct,name,categoryId,skuCode,productCode,spec,saleCount,inventory,productCost,stockValue,purchaseCount,noCard,damage,
                        lessDelivery,lessProduct,emptyBox,invArea,timestamp,customSkuId,damageOther,remark,saleProduct.getCreateUserId(),userId,jdSkuId,saleFinishStatus);
                saleService.updateSaleProduct(saleProduct);

                if(!oldProductCost.equals(productCost)){ //成本改变了

                    //根据saleProductId更新所有子订单的成本
                    SaleOrderLine saleOrderLine=new SaleOrderLine();
                    saleOrderLine.setSaleProductCost(productCost);
                    saleOrderLine.setCategoryId(categoryId);
                    saleOrderLine.setSaleProductId(saleProduct.getId());
                    saleService.updateOrderLineCostByProId(saleOrderLine);

                    //涉及的所有订单
                    List<SaleOrder> saleOrderList=saleService.getSaleOrderBySaleOrderLinePage(saleOrderLine);
                    //成本改变了更新订单中的成本,已经因为成本改变需要修改的数值,比如利润
                    if(null!=saleOrderList&&!saleOrderList.isEmpty()){
                        for(SaleOrder saleOrder:saleOrderList){
                            SaleOrderLine temp=new SaleOrderLine();
                            temp.setSaleOrderId(saleOrder.getId());
                            List<SaleOrderLine> saleOrderLineList=saleService.getSaleOrderLine(temp);
                            BigDecimal productCostSum=new BigDecimal(0);//成本总和
                            for(SaleOrderLine tempSaleOrderLine:saleOrderLineList){
                                productCostSum=productCostSum.add(tempSaleOrderLine.getSaleProductCost());
                            }
                            saleOrder.setProductCostSum(productCostSum);
                            saleOrder.setProfit(getOrderProfit(saleOrder));
                            saleService.updateSaleOrder(saleOrder);
                            Logger.info(saleProduct+"成本改变了更新订单中的成本"+saleOrder);

                        }
                    }

                }



            }

            if(null!=saleProduct){
                //更新后更新相关产品数据
                updateProductAtUpdateOrder(saleProduct);
            }


        } catch (Exception e) {
            Logger.error("product save exception "+Throwables.getStackTraceAsString(e));
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

//        Logger.info("总销售saleCountTotal="+saleCountTotal+",saleProduct="+saleProduct);

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
            int offset = (pageNum-1)*PAGE_SIZE;
            String name=json.findValue("name").asText().trim();
            if(null!=name&&!"".equals(name)){
                saleProduct.setName(name.replaceAll("'","''"));//查询时单引号转义
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
    public Result saleOrderImport() {
        return ok(views.html.sales.saleOrderImport.render("cn",(User) ctx().args.get("user")));
    }

    /**
     * 销售订单导入
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderSave() {
        JsonNode json = request().body().asJson();

        Logger.info("==销售订单导入===="+json);

        try {
            User user = (User) ctx().args.get("user");
            Long userId=Long.valueOf(user.userId().get().toString());
            String id=json.findValue("id").asText().trim();
            Date saleAt = new SimpleDateFormat("yyyy-MM-dd").parse(json.findValue("saleAt").asText().trim());
            String orderId = json.findValue("orderId").asText().trim();
//            Long saleProductId = json.findValue("saleProductId").asLong();
//            SaleProduct saleProduct=saleService.getSaleProductById(saleProductId);
//            BigDecimal price = new BigDecimal(json.findValue("price").asDouble());
//            Integer saleCount = json.findValue("saleCount").asInt();
            BigDecimal discountAmount = new BigDecimal(json.findValue("discountAmount").asDouble());
//            BigDecimal jdRate = new BigDecimal(json.findValue("jdRate").asDouble());
            BigDecimal shipFee = new BigDecimal(json.findValue("shipFee").asDouble());
            BigDecimal inteLogistics = new BigDecimal(json.findValue("inteLogistics").asDouble());
            BigDecimal packFee = new BigDecimal(json.findValue("packFee").asDouble());
            BigDecimal storageFee = new BigDecimal(json.findValue("storageFee").asDouble());
//            BigDecimal postalTaxRate = new BigDecimal(json.findValue("postalTaxRate").asDouble());
            Integer remarkStatus=json.findValue("remarkStatus").asInt();
            String remark=json.findValue("remark").asText().trim();
            Integer shop=json.findValue("shop").asInt();
            Integer inputType=json.findValue("inputType").asInt();
            String orderStatus=json.findValue("orderStatus").asText();
            //是否是退款
            boolean isRefund=ORDER_STATUS_REFUND.equals(orderStatus)?true:false;


            //订单数据
            SaleOrder saleOrder=null;
            if(null==id||"".equals(id)) {
                saleOrder = createSaleOrder(saleAt, orderId,
                        discountAmount, new BigDecimal(0), new BigDecimal(0),new BigDecimal(0),
                        shipFee, inteLogistics, packFee, storageFee, new BigDecimal(0), new BigDecimal(0),"H",remarkStatus,remark,userId,userId,shop,inputType,orderStatus,1);
            }else {
                saleOrder = saleService.getSaleOrderById(Long.valueOf(id));

            }
            BigDecimal productCostSum=new BigDecimal(0);
            BigDecimal jdSellSum=new BigDecimal(0);
            BigDecimal jdFeeSum=new BigDecimal(0);
            //子订单数据
            List<SaleOrderLineInfo> saleOrderLineInfoList=new ObjectMapper().readValue(json.get("saleOrderLineArray").toString(), new TypeReference<List<SaleOrderLineInfo>>() {});
            for(SaleOrderLineInfo saleOrderLineInfo:saleOrderLineInfoList){
                SaleProduct saleProduct=saleService.getSaleProductById(saleOrderLineInfo.getSaleProductId());
                if(null==saleProduct){
                    continue;
                }
                saleOrder.setInvArea(saleProduct.getInvArea());
                SaleOrderLine saleOrderLine=null;
                if(null==saleOrderLineInfo.getLineId()||"".equals(saleOrderLineInfo.getLineId())){
                    saleOrderLine= new SaleOrderLine();
                    saleOrderLine.setSaleProductId(saleProduct.getId());
                    saleOrderLine.setSaleProductName(saleProduct.getName());
                    saleOrderLine.setSaleProductCost(saleProduct.getProductCost());
                    saleOrderLine.setJdOrderId("");
                    saleOrderLine.setJdSkuId("");
                    saleOrderLine.setLineSaleTotal(new BigDecimal(0));
                    saleOrderLine.setSaleCount(saleOrderLineInfo.getSaleCount());
                    saleOrderLine.setJdPrice(saleOrderLineInfo.getJdPrice());
                    saleOrderLine.setCategoryId(saleProduct.getCategoryId());
                    saleOrderLine.setSaleAt(saleOrder.getSaleAt());
                    saleOrderLine.setJdRate(saleOrderLineInfo.getJdRate());

                    saleOrderLine.setOrderStatus(isRefund?ORDER_STATUS_REFUND:ORDER_STATUS_SUC);
                    saleOrderLine.setSaleOrderId(saleOrder.getId());
                    saleOrderLine.setDiscountAmount(saleOrderLineInfo.getDiscountAmountLine());
                    saleOrderLine.setJdFee(calJdFee(saleOrderLine));
                    saleOrderLine.setPostalTaxRate(getPostalTaxRate(saleOrderLine.getCategoryId()));
                    saleOrderLine.setSeq(99);
                    saleOrderLine.setShop(shop);

                    saleService.insertSaleOrderLine(saleOrderLine);
                }else{
                    SaleOrderLine temp=new SaleOrderLine();
                    temp.setId(saleOrderLineInfo.getLineId());
                    List<SaleOrderLine> saleOrderLineList=saleService.getSaleOrderLine(temp);
                    if(null!=saleOrderLineList&&!saleOrderLineList.isEmpty()){
                        saleOrderLine=saleOrderLineList.get(0);
                        saleOrderLine.setSaleCount(saleOrderLineInfo.getSaleCount());
                        saleOrderLine.setJdPrice(saleOrderLineInfo.getJdPrice());
                        saleOrderLine.setJdRate(saleOrderLineInfo.getJdRate());
                        saleOrderLine.setOrderStatus(isRefund?ORDER_STATUS_REFUND:ORDER_STATUS_SUC);
                        saleOrderLine.setDiscountAmount(saleOrderLineInfo.getDiscountAmountLine());
                        saleOrderLine.setJdFee(calJdFee(saleOrderLine));
                        saleOrderLine.setShop(shop);
                        saleService.updateSaleOrderLine(saleOrderLine);

                    }
                 //   Logger.info("=====saleOrderLine==="+saleOrderLine);
                }
                //更新商品
                updateProductAtUpdateOrder(saleProduct);
                //计算总成本
                productCostSum=productCostSum.add(saleOrderLine.getSaleProductCost().multiply(new BigDecimal(saleOrderLine.getSaleCount())));
                jdSellSum=jdSellSum.add(saleOrderLine.getJdPrice().multiply(new BigDecimal(saleOrderLine.getSaleCount())));

                if(ORDER_STATUS_REFUND.equals(saleOrderLine.getOrderStatus())){ //退款状态不计算
                    continue;
                }
                jdFeeSum=jdFeeSum.add(saleOrderLine.getJdFee());
            }

            SaleOrderLine tempSaleOrderLine=new SaleOrderLine();
            tempSaleOrderLine.setSaleOrderId(saleOrder.getId());
            List<SaleOrderLine> saleOrderLineList=saleService.getSaleOrderLine(tempSaleOrderLine);
            //总销售额=单价*数量-优惠额
            BigDecimal saleTotal=jdSellSum.subtract(discountAmount);

            setSaleOrder(saleOrder,saleAt, orderId,
                    discountAmount, saleTotal, jdFeeSum,productCostSum,
                    shipFee, inteLogistics, packFee, storageFee, saleOrder.getPostalFee(), saleOrder.getProfit(),saleOrder.getInvArea(),remarkStatus,remark,saleOrder.getCreateUserId(),userId,shop,inputType,orderStatus,1);

            //计算行邮税
            calPostalFee(saleOrder,saleOrderLineList);

            //订单有改变,但是jd的来源数据没有了,说明该订单下的所有子订单全部删除,此时需要修改订单状态为退货状态
            if(isRefund){
                Logger.info("订单有改变,但是jd的来源数据没有了,说明该订单下的所有子订单全部删除,此时需要修改订单状态为退货状态orderId="+saleOrder.getOrderId());
                //退货逻辑处理
                orderRefund(saleOrder);
                saleService.updateSaleOrder(saleOrder);
             //   Logger.info("====退款后订单====="+saleOrder);
                return ok("success");
            }
            //净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
            saleOrder.setProfit(getOrderProfit(saleOrder));
            saleService.updateSaleOrder(saleOrder);

          //  Logger.info("===订单====="+saleOrder);

        }catch (Exception e){
            Logger.error("sale order save exception "+e.getMessage());
        }
        return ok("success");
    }

    /**
     * 获取订单利润
     * @param saleTotal
     * @param jdFeeSum
     * @param productCostSum
     * @param shipFee
     * @param inteLogisticsSum
     * @param packFee
     * @param storageFee
     * @param postalFee
     * @return
     */
    private BigDecimal getOrderProfit(BigDecimal saleTotal,BigDecimal jdFeeSum,BigDecimal productCostSum,BigDecimal shipFee,
                                 BigDecimal inteLogisticsSum, BigDecimal packFee,BigDecimal storageFee, BigDecimal postalFee,Integer remarkStatus){
        if(remarkStatus==5){ //退单
            //净利润=-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
            return new BigDecimal(0).subtract(productCostSum).
                    subtract(shipFee).subtract(inteLogisticsSum).subtract(packFee).subtract(storageFee).subtract(postalFee);
        }
        // 净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
        return saleTotal.subtract(jdFeeSum).subtract(productCostSum).
                subtract(shipFee).subtract(inteLogisticsSum).subtract(packFee).subtract(storageFee).subtract(postalFee);
    }

    /**
     * 获取订单利润
     * @return
     */
    private BigDecimal getOrderProfit(SaleOrder saleOrder){
        if(ORDER_STATUS_REFUND.equals(saleOrder.getOrderStatus())){ //退单
            //净利润=-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
            return new BigDecimal(0).subtract(saleOrder.getProductCostSum()).
                    subtract(saleOrder.getShipFee()).subtract(saleOrder.getInteLogistics()).
                    subtract(saleOrder.getPackFee()).subtract(saleOrder.getStorageFee()).subtract(saleOrder.getPostalFee());
        }
        // 净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
        return saleOrder.getSaleTotal().subtract(saleOrder.getJdFeeSum()).subtract(saleOrder.getProductCostSum()).
                subtract(saleOrder.getShipFee()).subtract(saleOrder.getInteLogistics()).
                subtract(saleOrder.getPackFee()).subtract(saleOrder.getStorageFee()).subtract(saleOrder.getPostalFee());
    }

    /**
     * 销售订单
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderFind(Long id) {
        SaleOrder saleOrder=saleService.getSaleOrderById(id);

        SaleOrderDTO saleOrderDTO=new SaleOrderDTO();
        saleOrderDTO.setSaleOrder(saleOrder);

        SaleOrderLine saleOrderLine=new SaleOrderLine();
        saleOrderLine.setSaleOrderId(saleOrder.getId());
        List<SaleOrderLine> saleOrderLineList=saleService.getSaleOrderLine(saleOrderLine);
        saleOrderDTO.setSaleOrderLineList(saleOrderLineList); //子订单

        return ok(views.html.sales.saleOrderUpdate.render("cn",saleOrderDTO,(User) ctx().args.get("user")));
    }


    /**
     * 销售订单统计界面
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
            if (null!=saleStatisticsList&&!saleStatisticsList.isEmpty()&&saleStatisticsList.get(0).getSaleCountTotal()>0){
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
        SaleStatistics saleStatistics=null;
        List<SaleStatistics> saleStatisticsList=new ArrayList<SaleStatistics>() ;

        try {
            SaleOrder saleOrder = new SaleOrder();
            SaleOrderLine saleOrderLine=new SaleOrderLine();
            if (categoryId != -1) {
                saleOrder.setFeeCategoryId(categoryId);
                saleOrderLine.setCategoryId(categoryId);
            }
            String starttime = "";
            if (json.has("starttime")) {
                starttime = json.findValue("starttime").asText().trim();
                saleOrder.setStarttime(starttime);
                saleOrderLine.setStarttime(starttime);
            }
            String endtime = "";
            if (json.has("endtime")) {
                endtime = json.findValue("endtime").asText().trim();
                saleOrder.setEndtime(endtime);
                saleOrderLine.setEndtime(endtime);
            }

            Integer shop=json.findValue("shop").asInt();
            if (shop>0) {
                saleOrder.setShop(shop);
                saleOrderLine.setShop(shop);
            }

            saleStatisticsList = saleService.getSaleStatistics(saleOrder);
            if (null!=saleStatisticsList&&!saleStatisticsList.isEmpty()){
                saleStatistics=saleStatisticsList.get(0);
                if(saleStatistics.getSaleCountTotal()<=0){
                    saleStatistics=null;
                }
            }

            String productName="";
            if (json.has("productName")) {
                productName=json.findValue("productName").asText().trim();
                saleOrderLine.setSaleProductName(productName);
            }
            boolean isByName=(!"".equals(productName)&&null!=productName);
            if(categoryId!=-1||isByName){
                saleStatisticsList = saleService.getSaleStatisticsByLine(saleOrderLine);
                if (null!=saleStatisticsList&&!saleStatisticsList.isEmpty()){
                    SaleStatistics saleStatisticsLine=saleStatisticsList.get(0);
                    if(saleStatisticsLine.getSaleCountTotal()>0){
                        if(null==saleStatistics||isByName){
                            saleStatisticsLine.setInteLogisticsTotal(new BigDecimal(0));
                            saleStatisticsLine.setPackFeeTotal(new BigDecimal(0));
                            saleStatisticsLine.setShipFeeTotal(new BigDecimal(0));
                            saleStatisticsLine.setStorageFeeTotal(new BigDecimal(0));
                        }else{
                            saleStatisticsLine.setInteLogisticsTotal(saleStatistics.getInteLogisticsTotal());
                            saleStatisticsLine.setPackFeeTotal(saleStatistics.getPackFeeTotal());
                            saleStatisticsLine.setShipFeeTotal(saleStatistics.getShipFeeTotal());
                            saleStatisticsLine.setStorageFeeTotal(saleStatistics.getStorageFeeTotal());
                        }
                        BigDecimal profit=saleStatisticsLine.getSaleTotal().subtract(saleStatisticsLine.getJdFeeTotal())
                                .subtract(saleStatisticsLine.getInteLogisticsTotal()).subtract(saleStatisticsLine.getPackFeeTotal())
                                .subtract(saleStatisticsLine.getShipFeeTotal()).subtract(saleStatisticsLine.getStorageFeeTotal());
                        saleStatisticsLine.setProfitTotal(profit);
                        saleStatistics=saleStatisticsLine;
                    }

                }

            }



        }catch(Exception e){
            Logger.error("sale statistics exception"+e.getMessage());
            e.printStackTrace();
        }
        if(null==saleStatistics){
            saleStatistics=new SaleStatistics();
        }

        return ok(Json.toJson(saleStatistics));
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
        SaleOrderLine saleOrderLine = new SaleOrderLine();
        SaleProduct saleProduct=saleService.getSaleProductById(saleProductId);
        if (saleProductId != -1) {
            saleOrderLine.setSaleProductId(saleProductId);
        }
        String saleMonth = "";
        if (json.has("saleMonth")) {
            saleMonth = json.findValue("saleMonth").asText().trim();
            saleOrderLine.setSaleMonth(saleMonth);
        }
        List<SaleInventory> saleInventoryList=saleService.getSaleInventory(saleOrderLine);
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

        return ok(views.html.sales.saleOrderSearch.render("cn",PAGE_SIZE,countNum,pageCount,(User) ctx().args.get("user")));
    }

    /**
     * 订单查询
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderSearchAjax(String lang,int pageNum){
        JsonNode json = request().body().asJson();

        if(pageNum>=1){
            int countNum=0;
            int pageCount=0;
            //计算从第几条开始取数据
            int offset = (pageNum-1)*PAGE_SIZE;
            List<SaleOrder> orderList=null;
            String name = "",saleProductId="";
            if(json.has("name")){
                name=json.findValue("name").asText().trim();
            }
            if(json.has("saleProductId")){
                saleProductId=json.findValue("saleProductId").asText().trim();
            }

            if((null != name && !"".equals(name))||(null != saleProductId && !"".equals(saleProductId))){  //名字或者商品id时从子订单查询
                SaleOrderLine saleOrderLine=new SaleOrderLine();

                if (null != name && !"".equals(name)) {
                    saleOrderLine.setSaleProductName(name.replaceAll("'","''"));//查询时单引号转义
                }
                if (null != saleProductId && !"".equals(saleProductId)) {
                    saleOrderLine.setSaleProductId(json.findValue("saleProductId").asLong());
                }
                if (json.has("startTime")) {
                    saleOrderLine.setStarttime(json.findValue("startTime").asText().trim());
                }
                if (json.has("endTime")) {
                    saleOrderLine.setEndtime(json.findValue("endTime").asText().trim());
                }
                Integer shop = json.findValue("shop").asInt();
                if (shop > 0) {
                    saleOrderLine.setShop(shop);
                }

                saleOrderLine.setPageSize(-1);
                saleOrderLine.setOffset(-1);
                orderList = saleService.getSaleOrderBySaleOrderLinePage(saleOrderLine);

                countNum = orderList.size();//取总数
                pageCount = countNum / PAGE_SIZE;//共分几页
                if (countNum % PAGE_SIZE != 0) {
                    pageCount = countNum / PAGE_SIZE + 1;
                }
                saleOrderLine.setPageSize(ThemeCtrl.PAGE_SIZE);
                saleOrderLine.setOffset(offset);
                orderList = saleService.getSaleOrderBySaleOrderLinePage(saleOrderLine);

            }else {  //没有名字时从订单数据查询

                SaleOrder saleOrder=new SaleOrder();
                String orderId = json.findValue("orderId").asText().trim();
                if (null != orderId && !"".equals(orderId)) {
                    saleOrder.setOrderId(orderId);
                }
                if (json.has("startTime")) {
                    saleOrder.setStarttime(json.findValue("startTime").asText().trim());
                }
                if (json.has("endTime")) {
                    saleOrder.setEndtime(json.findValue("endTime").asText().trim());
                }
                Integer shop = json.findValue("shop").asInt();
                if (shop > 0) {
                    saleOrder.setShop(shop);
                }

                saleOrder.setPageSize(-1);
                saleOrder.setOffset(-1);
                orderList = saleService.getSaleOrderPage(saleOrder);

                countNum = orderList.size();//取总数
                pageCount = countNum / PAGE_SIZE;//共分几页
                if (countNum % PAGE_SIZE != 0) {
                    pageCount = countNum / PAGE_SIZE + 1;
                }
                saleOrder.setPageSize(ThemeCtrl.PAGE_SIZE);
                saleOrder.setOffset(offset);
                orderList = saleService.getSaleOrderPage(saleOrder);
            }


            List<SaleOrderDTO> orderDTOList=new ArrayList<>();
            if(null!=orderList&&orderList.size()>0){
                for(SaleOrder tempOrder:orderList){
                    SaleOrderDTO saleOrderDTO=new SaleOrderDTO();
                    saleOrderDTO.setSaleOrder(tempOrder); //订单

                    SaleOrderLine saleOrderLine=new SaleOrderLine();
                    saleOrderLine.setSaleOrderId(tempOrder.getId());
                    List<SaleOrderLine> saleOrderLineList=saleService.getSaleOrderLine(saleOrderLine);
                    saleOrderDTO.setSaleOrderLineList(saleOrderLineList); //子订单
                    orderDTOList.add(saleOrderDTO);
                }
            }
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",orderDTOList);
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
     * 销售订单
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleOrderDel(Long id) {
        SaleOrder saleOrder=saleService.getSaleOrderById(id);
        if(null!=saleOrder){
            SaleOrderLine saleOrderLine=new SaleOrderLine();
            saleOrderLine.setSaleOrderId(id);
            List<SaleOrderLine> saleOrderLineList=saleService.getSaleOrderLine(saleOrderLine);
            if(saleService.delSaleOrderById(id)){ //删除订单和对应子订单
                for(SaleOrderLine temp:saleOrderLineList){
                    SaleProduct saleProduct=saleService.getSaleProductById(temp.getSaleProductId());
                    if(null!=saleProduct){
                        //订单更新后更新相关产品数据
                        updateProductAtUpdateOrder(saleProduct);
                    }
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

//    /**
//     * 导入订单
//     * @return
//     */
//    @Security.Authenticated(UserAuth.class)
//    public Result uploadOrder() {
//        User user = (User) ctx().args.get("user");
//        Long userId=Long.valueOf(user.userId().get().toString());
//        Http.MultipartFormData body = request().body().asMultipartFormData();
//        Map<String, String[]> stringMap = body.asFormUrlEncoded();
//        Map<String, String> map = new HashMap<>();
//
//        stringMap.forEach((k, v) -> map.put(k, v[0]));
//
//        Http.MultipartFormData.FilePart filePart = body.getFile("orderFile");
//        File file=filePart.getFile();
//        List<String> list = null;
//        try {
//            list = ExcelHelper.exportListFromCsv(file);
//        } catch (Exception e) {
//            Logger.error("exception="+e);
//
//        }
//
//        StringBuffer sb=new StringBuffer();
//        if(null!=list&&list.size()>0){
//
////            Logger.info(list.get(1));
//            String[] str1=list.get(0).split(",");
//            if(str1.length<30){
//                Logger.error("导入表格长度不对"+str1.length);
//                return badRequest();
//            }
////            String[] str=list.get(1).split(",");
////            String[] str2=list.get(2).split(",");
////            for(int i=0;i<str.length;i++){
////                Logger.info(str[i]+"-->"+str1[i]);
////                sb.append(i+"-->"+str1[i]+"-->"+str[i]).append("\n");
////
////            }
//
//            /**
//             *
//             0-->订单号-->11729384072
//             1-->商品ID-->1953814331
//             2-->商品名称-->"韩秘美 Crystalshop 施华洛世奇水晶 娜奥米珍珠银饰 套装"
//             3-->订购数量-->1
//             4-->支付方式-->在线支付
//             5-->下单时间-->2015-12-31 23:19:56
//             6-->京东价-->758.00
//             7-->订单金额-->1097.00
//             8-->结算金额-->932.45
//             9-->余额支付-->0.00
//             10-->应付金额-->932.45
//             11-->订单状态-->(删除)等待出库
//             12-->订单类型-->销售订单
//             13-->下单帐号-->rds3199358
//             14-->客户姓名-->"王冬阳"
//             15-->客户地址-->"江西南昌市青山湖区昌东工业区南昌市青山湖区昌东工业区恒源科技园1栋仓库（荣德胜）"
//             16-->联系电话-->"13767111772"
//             17-->订单备注-->
//             18-->发票类型-->
//             19-->发票抬头-->
//             20-->发票内容-->
//             21-->商家备注-->"订单拆分为  11729439936    11749907506"
//             22-->商家备注等级（等级1-5为由高到低）-->
//             23-->运费金额-->0.00
//             24-->付款确认时间-->2015-12-31 23:20:30
//             25-->增值税发票-->
//             26-->货号-->
//             27-->订单完成时间-->
//             28-->订单来源-->移动端订单
//             29-->订单渠道-->暂无来源
//             */
//            StringBuffer skuErr=new StringBuffer();
//            StringBuffer orderErr=new StringBuffer();
//            StringBuffer suc=new StringBuffer();
//            StringBuffer existErr=new StringBuffer();
//            StringBuffer delErr=new StringBuffer();
//
//
//            //同一订单号  <京东订单id,List<SaleOrder>>
//            Map<String,List<SaleOrder>> orderListMap=new HashMap<>();
//            for(int i=1;i<list.size();i++) {
//                String[] str = list.get(i).split(",");
//                String orderId = str[0];
//                List<SaleOrder> orderList=orderListMap.get(orderId);
//                if(null==orderList){
//                    orderList=new ArrayList<>();
//                    orderListMap.put(orderId,orderList);
//                }
//                SaleOrder saleOrder=new SaleOrder();
//                saleOrder.setOrderId(orderId);
//                saleOrder.setJdSkuId(str[1]);  //京东商品Id
//                saleOrder.setProductName(str[2]); //商品名称
//                saleOrder.setSaleCount(Integer.valueOf(str[3]));  //订购数量
//                saleOrder.setPrice(new BigDecimal(str[6])); //京东价
//                BigDecimal discountAmount=new BigDecimal(str[7]).subtract(new BigDecimal(str[10])); //优惠额  订单金额-应付金额
//                saleOrder.setDiscountAmount(discountAmount);
//                saleOrder.setJdOrderStatus(str[11]); //订单状态
//                saleOrder.setRemark(str[21]);  //商家备注
//                try {
//                    if(orderId.equals("15267920677"))
//                         Logger.info(i+"================str[24]=="+str[24]);
//                    saleOrder.setSaleAt(new SimpleDateFormat("yyyy-MM-dd").parse(str[24])); //付款确认时间
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                orderList.add(saleOrder);
//            }
//
//            List<SaleOrder> orderList=null;
//            for(Map.Entry<String,List<SaleOrder>> entry:orderListMap.entrySet()){
//                orderList=entry.getValue();
//                SaleOrder tempOrder=null;
//                BigDecimal totalValue=new BigDecimal(0);//总订单额
//                for(int i=0;i<orderList.size();i++){
//                    totalValue=totalValue.add((orderList.get(i).getPrice().multiply(new BigDecimal(orderList.get(i).getSaleCount()))));
//                }
//                Logger.info(entry.getKey()+"==totalValue=="+totalValue);
//                for(int i=0;i<orderList.size();i++){
//                    tempOrder=orderList.get(i);
//                    String orderId=tempOrder.getOrderId();
//                    String jdSkuId=tempOrder.getJdSkuId();
//                    Integer saleCount=tempOrder.getSaleCount();
//                    SaleProduct selSaleProduct=new SaleProduct();
//                    selSaleProduct.setJdSkuId(jdSkuId);
//                    selSaleProduct.setSort("custom_sku_id");
//                    selSaleProduct.setOrder("asc");
//                    List<SaleProduct> productList=saleService.getSaleProduct(selSaleProduct);
//                    SaleProduct saleProduct=null;
//                    if(null==productList||productList.isEmpty()){
//                        Logger.error("<br/>"+"第"+(i)+"行京东商品不存在jdSkuId="+jdSkuId+",orderId="+orderId);
//                        skuErr.append("<br/>"+"第"+(i)+"行京东商品不存在jdSkuId="+jdSkuId+",orderId="+orderId);
//                        continue;
//                    }else {
//                        if(productList.size()>1){
//                            for(SaleProduct temp:productList){
//                                if(temp.getInventory()>0&&temp.getInventory()>=saleCount){
//                                    saleProduct=temp; //有库存
//                                    Logger.info(saleProduct.getId()+"=="+saleProduct.getCustomSkuId()+"=saleCount=="+saleProduct.getInventory()+"===="+saleCount);
//                                    break;
//                                }
//                            }
//                        }else{
//                            saleProduct=productList.get(0);
//                        }
//
//                    }
//                    if(null==saleProduct){
//                        Logger.error("<br/>"+"第"+(i)+"行京东商品不存在或者库存没有了jdSkuId="+jdSkuId+",orderId="+orderId);
//                        skuErr.append("<br/>"+"第"+(i)+"行京东商品不存在或者库存没有了jdSkuId="+jdSkuId+",orderId="+orderId);
//                        continue;
//                    }
//
//                    SaleOrder saleOrder=new SaleOrder();
//                    saleOrder.setOrderId(orderId);
//                    saleOrder.setSaleProductId(saleProduct.getId()); //订单和商品id联合
//                    saleOrder.setInputType(2);
//
//                    List<SaleOrder> saleOrderList=saleService.getSaleOrder(saleOrder);
//                    if(null!=saleOrderList&&!saleOrderList.isEmpty()){
//                        if(tempOrder.getJdOrderStatus().indexOf("删除")>0){ //订单状态由原来的正常状态变为删除状态
//                            if(saleService.delSaleOrderById(saleOrderList.get(0).getId())){
//                                if(null!=saleProduct){
//                                    //订单更新后更新相关产品数据
//                                    updateProductAtUpdateOrder(saleProduct);
//                                }
//                                Logger.error("<br/>"+"第"+(i)+"行订单已经存在orderId="+orderId+",但是订单状态已经修改为删除");
//                                orderErr.append("<br/>"+"第"+(i)+"行订单已经存在orderId="+orderId+",但是订单状态已经修改为删除");
//                            }
//
//                        }else{
//                            Logger.error("<br/>"+"第"+(i)+"行订单已经存在orderId="+orderId);
//                            orderErr.append("<br/>"+"第"+(i)+"行订单已经存在orderId="+orderId);
//                        }
//                        continue;
//                    }
//
//                    if(tempOrder.getJdOrderStatus().indexOf("删除")>0){
//                        Logger.error("<br/>"+"第"+(i)+"行订单删除jdSkuId="+jdSkuId+",orderId="+orderId);
//                        delErr.append("<br/>"+"第"+(i)+"行订单删除jdSkuId="+jdSkuId+",orderId="+orderId);
//                        continue;
//                    }
//
//
//                    BigDecimal price=tempOrder.getPrice();
//                    BigDecimal saleCountBig=new BigDecimal(tempOrder.getSaleCount());
//                    BigDecimal discountAmount=tempOrder.getDiscountAmount().multiply(price).multiply(saleCountBig).divide(totalValue,2);//优惠额
//
//                    BigDecimal jdRate=new BigDecimal(0);  //京东费率
//                    BigDecimal postalTaxRate=new BigDecimal(0); //行邮税
//                    BigDecimal shipFee = new BigDecimal(0);  //运费
//                    BigDecimal inteLogistics = new BigDecimal(0); //国际运费
//                    BigDecimal packFee = new BigDecimal(0); //包装费
//                    BigDecimal storageFee = new BigDecimal(0); //存储费
//
//                    Integer remarkStatus=1;
//                    Integer shop=Integer.valueOf(map.get("shop"));
//
//                    //总销售额=单价*数量-优惠额
//                    BigDecimal saleTotal=price.multiply(saleCountBig).subtract(discountAmount);
//                    // 京东费用=总销售 额*京东费率
//                    BigDecimal jdFee = saleTotal.multiply(jdRate).divide(new BigDecimal(100));
//                    BigDecimal postalFee = new BigDecimal(0);
//                    int cate=saleProduct.getCategoryId();
//                    // 配饰 行邮税=如果总销售额>500元，=总销售额*行邮税率
//                    //化妆品 行邮税=如果总销售额>100元，=总销售额*行邮税率
//                    if((cate==1&&saleTotal.doubleValue() > 500)||(cate==2&&saleTotal.doubleValue() > 100)){
//                        postalFee=saleTotal.multiply(postalTaxRate).divide(new BigDecimal(100));
//                    }
//                    if(remarkStatus==5){ //退货时总销售额为0,京东费用为0
//                        saleTotal=new BigDecimal(0);
//                        jdFee=new BigDecimal(0);
//
//                    }
//                    //净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
//                    BigDecimal productCost = saleProduct.getProductCost();//成本
//                    BigDecimal profit = getOrderProfit(saleTotal,jdFee,productCost,shipFee,inteLogistics,packFee,storageFee,postalFee,saleCount,remarkStatus);
//                    //  Logger.info(saleTotal+"=saleCount="+saleCount+",discountAmount="+discountAmount+",profit="+profit);
//                    createSaleOrder(tempOrder.getSaleAt(),
//                                orderId, saleProduct.getId(), tempOrder.getProductName(), saleProduct.getCategoryId(), price, saleCount,
//                                discountAmount, saleTotal, jdRate, jdFee, saleProduct.getProductCost(),
//                                shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate,
//                                profit,saleProduct.getInvArea(),remarkStatus,tempOrder.getRemark(),userId,userId,shop,2,jdSkuId);
//                        Logger.info("<br/>"+"第"+(i)+"行成功,orderId="+orderId);
//                        suc.append("<br/>"+"第"+(i)+"行成功,orderId="+orderId);
//                    if(null!=saleProduct){
//                        //订单更新后更新相关产品数据
//                        updateProductAtUpdateOrder(saleProduct);
//                    }
//                }
//            }
//
//
//
//
//
////            for(int i=1;i<list.size();i++) {
////                str= list.get(i).split(",");
////                String orderId=str[0];
////                String jdSkuId=str[1];
////
////
////                Integer saleCount=Integer.valueOf(str[3]);
////                SaleProduct selSaleProduct=new SaleProduct();
////
////                selSaleProduct.setJdSkuId(jdSkuId);
////                selSaleProduct.setSort("custom_sku_id");
////                selSaleProduct.setOrder("asc");
////                List<SaleProduct> productList=saleService.getSaleProduct(selSaleProduct);
////                SaleProduct saleProduct=null;
////                if(null==productList||productList.isEmpty()){
////                    Logger.error("<br/>"+"第"+(i)+"行京东商品不存在jdSkuId="+jdSkuId+",orderId="+orderId);
////                    skuErr.append("<br/>"+"第"+(i)+"行京东商品不存在jdSkuId="+jdSkuId+",orderId="+orderId);
////                    continue;
////                }else {
////                    if(productList.size()>1){
////                      //  int productNum=0;
////                        for(SaleProduct temp:productList){
////                            if(temp.getInventory()>0&&temp.getInventory()>=saleCount){
////                                saleProduct=temp; //有库存
////                                Logger.info(saleProduct.getId()+"=="+saleProduct.getCustomSkuId()+"=saleCount=="+saleProduct.getInventory()+"===="+saleCount);
////                                break;
////                            }
//////                            if(temp.getSaleFinishStatus()==1){ //未卖完
//////                                productNum++;
//////                                saleProduct=temp;
//////                            }
//////                            if(productNum>=2){
//////                                break;
//////                            }
////                        }
//////                        if(productNum!=1){
//////                            Logger.error("<br/>"+"第"+(i)+"行京东商品存在至少"+productNum+"个jdSkuId="+jdSkuId+",orderId="+orderId);
//////                            existErr.append("<br/>"+"第"+(i)+"行京东商品存在至少"+productNum+"个jdSkuId="+jdSkuId+",orderId="+orderId);
//////                            continue;
//////                        }
////                    }else{
////                        saleProduct=productList.get(0);
////                    }
////
////                }
////                if(null==saleProduct){
////                    Logger.error("<br/>"+"第"+(i)+"行京东商品不存在或者库存没有了jdSkuId="+jdSkuId+",orderId="+orderId);
////                    skuErr.append("<br/>"+"第"+(i)+"行京东商品不存在或者库存没有了jdSkuId="+jdSkuId+",orderId="+orderId);
////                    continue;
////                }
////
////                SaleOrder saleOrder=new SaleOrder();
////                saleOrder.setOrderId(orderId);
////                saleOrder.setSaleProductId(saleProduct.getId()); //订单和商品id联合
////                saleOrder.setInputType(2);
////
////                List<SaleOrder> saleOrderList=saleService.getSaleOrder(saleOrder);
////                if(null!=saleOrderList&&!saleOrderList.isEmpty()){
////                    if(str[11].indexOf("删除")>0){ //订单状态由原来的正常状态变为删除状态
////                        if(saleService.delSaleOrderById(saleOrderList.get(0).getId())){
////                            if(null!=saleProduct){
////                                //订单更新后更新相关产品数据
////                                updateProductAtUpdateOrder(saleProduct);
////                            }
////                            Logger.error("<br/>"+"第"+(i)+"行订单已经存在orderId="+orderId+",但是订单状态已经修改为删除");
////                            orderErr.append("<br/>"+"第"+(i)+"行订单已经存在orderId="+orderId+",但是订单状态已经修改为删除");
////                        }
////
////                    }else{
////                        Logger.error("<br/>"+"第"+(i)+"行订单已经存在orderId="+orderId);
////                        orderErr.append("<br/>"+"第"+(i)+"行订单已经存在orderId="+orderId);
////                    }
////                    continue;
////                }
////
////                if(str[11].indexOf("删除")>0){
////                    Logger.error("<br/>"+"第"+(i)+"行订单删除jdSkuId="+jdSkuId+",orderId="+orderId);
////                    delErr.append("<br/>"+"第"+(i)+"行订单删除jdSkuId="+jdSkuId+",orderId="+orderId);
////                    continue;
////                }
////
////
////                BigDecimal price=new BigDecimal(str[6]); //TODO ..单价
////                BigDecimal discountAmount=new BigDecimal(str[7]).subtract(new BigDecimal(str[10])); //优惠额
////                BigDecimal jdRate=new BigDecimal(0);  //京东费率
////                BigDecimal postalTaxRate=new BigDecimal(0); //行邮税
////                BigDecimal shipFee = new BigDecimal(0);  //运费
////                BigDecimal inteLogistics = new BigDecimal(0); //国际运费
////                BigDecimal packFee = new BigDecimal(0); //包装费
////                BigDecimal storageFee = new BigDecimal(0); //存储费
////
////                Integer remarkStatus=1;
////                Integer shop=Integer.valueOf(map.get("shop"));
////
////                //总销售额=单价*数量-优惠额
////                BigDecimal saleTotal=price.multiply(new BigDecimal(saleCount)).subtract(discountAmount);
////                // 京东费用=总销售 额*京东费率
////                BigDecimal jdFee = saleTotal.multiply(jdRate).divide(new BigDecimal(100));
////                BigDecimal postalFee = new BigDecimal(0);
////                int cate=saleProduct.getCategoryId();
////                // 配饰 行邮税=如果总销售额>500元，=总销售额*行邮税率
////                //化妆品 行邮税=如果总销售额>100元，=总销售额*行邮税率
////                if((cate==1&&saleTotal.doubleValue() > 500)||(cate==2&&saleTotal.doubleValue() > 100)){
////                    postalFee=saleTotal.multiply(postalTaxRate).divide(new BigDecimal(100));
////                }
////                if(remarkStatus==5){ //退货时总销售额为0,京东费用为0
////                    saleTotal=new BigDecimal(0);
////                    jdFee=new BigDecimal(0);
////
////                }
////                //净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
////                BigDecimal productCost = saleProduct.getProductCost();//成本
////                BigDecimal profit = getOrderProfit(saleTotal,jdFee,productCost,shipFee,inteLogistics,packFee,storageFee,postalFee,saleCount,remarkStatus);
////              //  Logger.info(saleTotal+"=saleCount="+saleCount+",discountAmount="+discountAmount+",profit="+profit);
////
////                try {
////                    createSaleOrder(new SimpleDateFormat("yyyy-MM-dd").parse(str[24]),
////                            str[0], saleProduct.getId(), str[2], saleProduct.getCategoryId(), price, saleCount,
////                            discountAmount, saleTotal, jdRate, jdFee, saleProduct.getProductCost(),
////                            shipFee, inteLogistics, packFee, storageFee, postalFee, postalTaxRate,
////                            profit,saleProduct.getInvArea(),remarkStatus,str[21],userId,userId,shop,2,jdSkuId);
////                    Logger.info("<br/>"+"第"+(i)+"行成功,orderId="+orderId);
////                    suc.append("<br/>"+"第"+(i)+"行成功,orderId="+orderId);
////
////                } catch (ParseException e) {
////                    e.printStackTrace();
////                    Logger.error(Throwables.getStackTraceAsString(e));
////                }
////
////                if(null!=saleProduct){
////                    //订单更新后更新相关产品数据
////                    updateProductAtUpdateOrder(saleProduct);
////                }
////            }
//            if(skuErr.length()>0){
//                sb.append("<br/><br/>京东商品不存在:<br/>");
//                sb.append(skuErr);
//            }
//            if(existErr.length()>0){
//                sb.append("<br/><br/>京东商品存在多个:<br/>");
//                sb.append(existErr);
//            }
//            if(orderErr.length()>0){
//                sb.append("<br/><br/>订单已经存在:<br/>");
//                sb.append(orderErr);
//            }
//            if(delErr.length()>0){
//                sb.append("<br/>订单删除:<br/>");
//                sb.append(delErr);
//            }
//            if(suc.length()>0){
//                sb.append("<br/>导入成功:<br/>");
//                sb.append(suc);
//            }
//
//        }
//        //释放
//        list.clear();
//        list=null;
//
//        String importResult="订单费用导入成功<br/>"+sb.toString();
//        return ok(views.html.sales.saleImportResult.render("cn",(User) ctx().args.get("user"),importResult));
//
//    }

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
        Integer shop=Integer.valueOf(map.get("shop"));

        StringBuffer sb=new StringBuffer();
        if(null!=list&&list.size()>0){
            String[] str1=list.get(0).split(",");
            if(str1.length<30){
                Logger.error("导入表格长度不对"+str1.length);
                return badRequest();
            }
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
            StringBuffer delErr=new StringBuffer();


            //同一订单号  <京东订单id,List<SaleJdOrderLineInfo>> 获取京东表格中每行数据
            Map<String,List<SaleJdOrderLineInfo>> orderListMap=new HashMap<>();
            for(int i=1;i<list.size();i++) {
                String[] str = list.get(i).split(",");  //TODO ...引号当中可能含有,
                String orderId = str[0];
                List<SaleJdOrderLineInfo> orderList=orderListMap.get(orderId);
                if(null==orderList){
                    orderList=new ArrayList<>();
                    orderListMap.put(orderId,orderList);
                }
                SaleJdOrderLineInfo orderLineInfo=new SaleJdOrderLineInfo();
                orderLineInfo.setJdOrderId(orderId);
                orderLineInfo.setJdSkuId(str[1]);  //京东商品Id
                orderLineInfo.setSaleCount(Integer.valueOf(str[3]));  //订购数量
                orderLineInfo.setPrice(new BigDecimal(str[6])); //京东价
                orderLineInfo.setOrderValue(new BigDecimal(str[7])); //订单金额
                orderLineInfo.setSettleValue(new BigDecimal(str[8])); //结算金额
                orderLineInfo.setPayValue(new BigDecimal(str[10])); //应付金额
                orderLineInfo.setJdOrderStatus(str[11]); //订单状态
//                if(orderId.equals("17549305237")){
//                    orderLineInfo.setJdOrderStatus("删除"); //订单状态
//                }
                orderLineInfo.setRemark(str[21]);  //商家备注
                if(!"".equals(str[22])&&null!=str[22]){
                    orderLineInfo.setRemarkStatus(Integer.valueOf(str[22]));//商家备注等级（等级1-5为由高到低）
                }else{
                    orderLineInfo.setRemarkStatus(1);
                }

                if("".equals(str[23])||null==str[23]){
                    orderLineInfo.setShipFee(new BigDecimal(0));     //运费金额
                }else{
                    orderLineInfo.setShipFee(new BigDecimal(str[23]));     //运费金额
                }
                try {
//                    Logger.info("#############str[24])##########"+str[24]);
                    if(!"".equals(str[24])&&null!=str[24]){

                        orderLineInfo.setSaleAt(new SimpleDateFormat("yyyy-MM-dd").parse(str[24])); //付款确认时间
                    }else{
                        Logger.error("第"+(i)+"行获取付款时间为空orderId"+orderId);
                        orderLineInfo.setSaleAt(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01")); //付款确认时间
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                orderList.add(orderLineInfo);
            }
            /******************采集数据结束******************************/

            /**
             *  按照每个京东订单号处理采集的数据
             */
            List<SaleJdOrderLineInfo> jdOrderLineInfoList=null;
         //   int num=1;
            for(Map.Entry<String,List<SaleJdOrderLineInfo>> entry:orderListMap.entrySet()){
//                if(num++>50){
//                    break;
//                }
                jdOrderLineInfoList=entry.getValue();  //同一个订单号数据
                String jdOrderId=jdOrderLineInfoList.get(0).getJdOrderId();
                //订单数据
                SaleOrder saleOrder=new SaleOrder();
                saleOrder.setOrderId(jdOrderId);
                saleOrder.setInputType(2);
                List<SaleOrder> saleOrderList=saleService.getSaleOrder(saleOrder);
                if(null!=saleOrderList&&saleOrderList.size()>0){
                    saleOrder=saleOrderList.get(0);
                }else {
                    //插入
                    saleOrder=createSaleOrder(jdOrderLineInfoList.get(0).getSaleAt(),
                            jdOrderLineInfoList.get(0).getJdOrderId(),
                            new BigDecimal(0), new BigDecimal(0), new BigDecimal(0),new BigDecimal(0),
                            new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0),
                            new BigDecimal(0),"H",jdOrderLineInfoList.get(0).getRemarkStatus(),
                            jdOrderLineInfoList.get(0).getRemark(),userId,userId,shop,2,ORDER_STATUS_SUC,1);
                }

                SaleJdOrderLineInfo  jdOrderLineInfoSourceDate=null;//可用的一条京东来源数据
                SaleJdOrderLineInfo jdOrderLineInfo=null;
                TreeMap<Integer,Integer> cateMap=new TreeMap<>();//该订单下的所有类别

                boolean isHaveOrderChange=false; //是否有订单的改变
                for(int i=0;i<jdOrderLineInfoList.size();i++) {
                    jdOrderLineInfo = jdOrderLineInfoList.get(i);
                    String jdSkuId = jdOrderLineInfo.getJdSkuId();
                    Integer saleCount = jdOrderLineInfo.getSaleCount();
                    //获取该关联的商品信息
                    SaleProduct selSaleProduct = new SaleProduct();
                    selSaleProduct.setJdSkuId(jdSkuId);
                    selSaleProduct.setSort("custom_sku_id");
                    selSaleProduct.setOrder("asc");
                    List<SaleProduct> productList = saleService.getSaleProduct(selSaleProduct);
                    SaleProduct saleProduct = null;
                    if (null == productList || productList.isEmpty()) {
                        Logger.error("<br/>京东商品不存在jdSkuId=" + jdSkuId + ",orderId=" + jdOrderId);
                        skuErr.append("<br/>京东商品不存在jdSkuId=" + jdSkuId + ",orderId=" + jdOrderId);
                        continue;
                    } else {
                        if (productList.size() > 1) {
                            for (SaleProduct temp : productList) {
                                if (temp.getInventory() > 0 && temp.getInventory() >= saleCount) {
                                    saleProduct = temp; //有库存
                                    Logger.info(saleProduct.getId() + "==" + saleProduct.getCustomSkuId() + "=saleCount==" + saleProduct.getInventory() + "====" + saleCount);
                                    break;
                                }
                            }
                        } else {
                            saleProduct = productList.get(0);
                        }

                    }
                    if (null == saleProduct) {
                        Logger.error("<br/>京东商品不存在或者库存没有了jdSkuId=" + jdSkuId + ",orderId=" + jdOrderId);
                        skuErr.append("<br/>京东商品不存在或者库存没有了jdSkuId=" + jdSkuId + ",orderId=" + jdOrderId);
                        continue;
                    }
                    cateMap.put(saleProduct.getCategoryId(),saleProduct.getCategoryId());
                    //是否是退货
                    boolean isRefund=jdOrderLineInfo.getJdOrderStatus().indexOf("删除")>=0;
                    saleOrder.setInvArea(saleProduct.getInvArea());
                    jdOrderLineInfoSourceDate = jdOrderLineInfo;
                    //子订单
                    SaleOrderLine tempOrderLine = new SaleOrderLine();
                    tempOrderLine.setJdOrderId(jdOrderId);
                    tempOrderLine.setJdSkuId(jdSkuId);

                    List<SaleOrderLine> saleOrderLineList = saleService.getSaleOrderLine(tempOrderLine);  //查询指定一条子订单
                    if (null != saleOrderLineList && !saleOrderLineList.isEmpty()) { //该条子订单已经存在
                        SaleOrderLine tempLine=saleOrderLineList.get(0);
                        if (!ORDER_STATUS_REFUND.equals(tempLine.getOrderStatus())&&isRefund) { //子订单状态由原来的正常状态变为退货状态,即退货了
                            isHaveOrderChange = true; //有订单状态的改变 子订单状态改为退货
                            tempLine.setOrderStatus(ORDER_STATUS_REFUND);
                            saleService.updateSaleOrderLine(tempLine);
                            Logger.error("<br/>" + "订单已经存在jdSkuId="+jdSkuId+",orderId="+jdOrderId+ ",但是订单状态已经修改为删除");
                            orderErr.append("<br/>" + "订单已经存在jdSkuId="+jdSkuId+",orderId="+jdOrderId+ ",但是订单状态已经修改为删除");

                        } else {
                            Logger.error("<br/>" + "订单已经存在jdSkuId="+jdSkuId+",orderId="+jdOrderId);
                            orderErr.append("<br/>" + "订单已经存在jdSkuId="+jdSkuId+",orderId="+jdOrderId);
                        }
                        continue;
                    }

                    isHaveOrderChange = true; //有订单的改变  新增子订单
                    SaleOrderLine saleOrderLine = new SaleOrderLine();
                    saleOrderLine.setSaleProductId(saleProduct.getId());
                    saleOrderLine.setSaleProductName(saleProduct.getName());
                    saleOrderLine.setSaleProductCost(saleProduct.getProductCost());
                    saleOrderLine.setJdOrderId(jdOrderLineInfo.getJdOrderId());
                    saleOrderLine.setJdSkuId(jdOrderLineInfo.getJdSkuId());
                    saleOrderLine.setSaleCount(jdOrderLineInfo.getSaleCount());
                    saleOrderLine.setJdPrice(jdOrderLineInfo.getPrice());
                    saleOrderLine.setCategoryId(saleProduct.getCategoryId());
                    saleOrderLine.setSaleAt(jdOrderLineInfo.getSaleAt());
                    saleOrderLine.setJdRate(new BigDecimal(0));
                    saleOrderLine.setJdFee(new BigDecimal(0));
                    saleOrderLine.setLineSaleTotal(new BigDecimal(0));
                    saleOrderLine.setOrderStatus(isRefund?ORDER_STATUS_REFUND:ORDER_STATUS_SUC);
                    saleOrderLine.setSaleOrderId(saleOrder.getId());
                    saleOrderLine.setPostalTaxRate(getPostalTaxRate(saleOrderLine.getCategoryId()));
                    saleOrderLine.setDiscountAmount(new BigDecimal(0)); //每个子订单的优惠额,从优惠额中导入
                    saleOrderLine.setSeq((i+1));
                    saleOrderLine.setShop(shop);
                    saleService.insertSaleOrderLine(saleOrderLine);

                }

                /**************子订单数据修改完毕*******************/
                if(!isHaveOrderChange){ //该订单号下的订单没有任何变化,即重复导入没有改变的数据
                    continue;
                }

                //更新订单的数据
                SaleOrderLine tempOrderLine = new SaleOrderLine();
                tempOrderLine.setJdOrderId(jdOrderId);
                //该订单下的所有子订单数据
                List<SaleOrderLine> orderLineList=saleService.getSaleOrderLine(tempOrderLine);
                //更新商品数据
                for(SaleOrderLine saleOrderLine:orderLineList){
                    SaleProduct saleProduct=saleService.getSaleProductById(saleOrderLine.getSaleProductId());
                    if(null!=saleProduct){
                        //订单更新后更新相关产品数据
                        updateProductAtUpdateOrder(saleProduct);
                    }
                }

                saleOrder.setFeeCategoryId(cateMap.firstKey());//默认类别小的

                //优惠额
                BigDecimal discountAmount=jdOrderLineInfoSourceDate.getOrderValue().subtract(jdOrderLineInfoSourceDate.getSettleValue()); //优惠额=订单金额-结算金额
                saleOrder.setDiscountAmount(discountAmount);
                // 京东费用和  京东费用=总销售 额*京东费率
                BigDecimal jdFeeSum = new BigDecimal(0);
                //成本总和
                BigDecimal productCostSum=new BigDecimal(0);
                //京东卖出的价格总和
                BigDecimal jdSellSum=new BigDecimal(0);
                boolean isAllRefund=true;//是否全部退货
                for(SaleOrderLine saleOrderLine:orderLineList){
                    //TODO ... 部分退货的怎么处理
                    //计算总成本
                    productCostSum=productCostSum.add(saleOrderLine.getSaleProductCost().multiply(new BigDecimal(saleOrderLine.getSaleCount())));
                    jdSellSum=jdSellSum.add(saleOrderLine.getJdPrice().multiply(new BigDecimal(saleOrderLine.getSaleCount())));

                    if(ORDER_STATUS_REFUND.equals(saleOrderLine.getOrderStatus())){ //退款状态不计算
                        continue;
                    }
                    jdFeeSum=jdFeeSum.add(saleOrderLine.getJdFee());
                    isAllRefund=false;
                }
                saleOrder.setProductCostSum(productCostSum);
                saleOrder.setJdFeeSum(jdFeeSum);


                //订单有改变,但是jd的来源数据没有了,说明该订单下的所有子订单全部删除,此时需要修改订单状态为退货状态
                if(isAllRefund){
                    Logger.info("订单有改变,但是jd的来源数据没有了,说明该订单下的所有子订单全部删除,此时需要修改订单状态为退货状态orderId="+saleOrder.getOrderId());
                    //退货逻辑处理
                    orderRefund(saleOrder);
                    saleService.updateSaleOrder(saleOrder);

                    Logger.info("====退款后订单====="+saleOrder);
                    continue;
                }

                //总销售额=单价*数量-优惠额
                BigDecimal saleTotal=jdSellSum.subtract(discountAmount);
                saleOrder.setSaleTotal(saleTotal);
                //计算行邮税
                calPostalFee(saleOrder,orderLineList);

                //净利润=总销售额-京东费用-成本*数量-国内快递费-国际物流费*数量-包装费-仓储服务费-行邮税
                saleOrder.setProfit(getOrderProfit(saleOrder));
                saleService.updateSaleOrder(saleOrder);

                Logger.info("<br/>"+"成功,orderId="+jdOrderId);
                suc.append("<br/>"+"成功,orderId="+jdOrderId);
            }

            if(skuErr.length()>0){
                sb.append("<br/><br/>京东商品不存在:<br/>");
                sb.append(skuErr);
            }
            if(existErr.length()>0){
                sb.append("<br/><br/>京东商品存在多个:<br/>");
                sb.append(existErr);
            }
            if(orderErr.length()>0){
                sb.append("<br/><br/>订单已经存在:<br/>");
                sb.append(orderErr);
            }
            if(delErr.length()>0){
                sb.append("<br/>订单删除:<br/>");
                sb.append(delErr);
            }
            if(suc.length()>0){
                sb.append("<br/>导入成功:<br/>");
                sb.append(suc);
            }

        }
        //释放
        list.clear();
        list=null;

        String importResult="订单费用导入成功<br/>"+sb.toString();
        return ok(views.html.sales.saleImportResult.render("cn",(User) ctx().args.get("user"),importResult));

    }

    /**
     * 处理退货
     * @param saleOrder
     */
    private void orderRefund(SaleOrder saleOrder){
        saleOrder.setOrderStatus(ORDER_STATUS_REFUND);
        saleOrder.setJdFeeSum(new BigDecimal(0));//退货时总销售额为0,京东费用为0
        saleOrder.setSaleTotal(new BigDecimal(0));
        saleOrder.setProfit(getOrderProfit(saleOrder));
    }

    /***
     * 京东费用
     * @param saleOrderLine
     * @return
     */
    private BigDecimal calJdFee(SaleOrderLine saleOrderLine){
        BigDecimal saleTotal=getSaleOrderLineSaleTotal(saleOrderLine);
        return saleTotal.multiply(saleOrderLine.getJdRate()).divide(new BigDecimal(100));
    }

    /**
     * 获取子订单的销售总额
     * @param saleOrderLine
     * @return
     */
    private BigDecimal getSaleOrderLineSaleTotal(SaleOrderLine saleOrderLine){
        BigDecimal lineSaleTotal=new BigDecimal(0);
        if(ORDER_STATUS_SUC.equals(saleOrderLine.getOrderStatus())) {
            lineSaleTotal=saleOrderLine.getJdPrice().multiply(new BigDecimal(saleOrderLine.getSaleCount())).subtract(saleOrderLine.getDiscountAmount());
        }
        saleOrderLine.setLineSaleTotal(lineSaleTotal);
        return lineSaleTotal;

    }

    /**
     * 计算行邮税
     * @param saleOrder
     * @param saleOrderLineList
     */
    private void calPostalFee(SaleOrder saleOrder,List<SaleOrderLine> saleOrderLineList){

        Date date=null;
        try {
            date=new SimpleDateFormat("yyyy-MM-dd").parse("2016-04-08");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(saleOrder.getSaleAt().before(date)){ //
            /**
             *  化妆品订单总额超过100元，收订单总额的50%，配饰超过500元，收订单总额的10%
             *  投影仪是按订单总额的10%收
             */
            //行邮税
            BigDecimal postalFee = new BigDecimal(0);
            for(SaleOrderLine saleOrderLine:saleOrderLineList){
                BigDecimal lineSaleTotal=getSaleOrderLineSaleTotal(saleOrderLine);
                if(saleOrderLine.getCategoryId()==1&&lineSaleTotal.doubleValue()>500){ // 配饰 行邮税=如果总销售额>500元，=总销售额*行邮税率
                    postalFee=postalFee.add(lineSaleTotal.multiply(saleOrderLine.getPostalTaxRate()).divide(new BigDecimal(100)));
                }
                if(saleOrderLine.getCategoryId()==2&&lineSaleTotal.doubleValue()>100){ //化妆品 行邮税=如果总销售额>100元，=总销售额*行邮税率
                    postalFee=postalFee.add(lineSaleTotal.multiply(saleOrderLine.getPostalTaxRate()).divide(new BigDecimal(100)));
                }
                if(saleOrderLine.getCategoryId()==3){
                    postalFee=postalFee.add(lineSaleTotal.multiply(saleOrderLine.getPostalTaxRate()).divide(new BigDecimal(100)));
                }
            }
            saleOrder.setPostalFee(postalFee);


        }else{
            /**
             * 化妆品按订单总额的50%收，配饰按订单总额的10%收，两项和如果未超过50元，免收，超过50元，按实际收取
             */

            //行邮税
            BigDecimal postalFee = new BigDecimal(0);
            BigDecimal postalFee1 = new BigDecimal(0);
            BigDecimal postalFee2 = new BigDecimal(0);
            for(SaleOrderLine saleOrderLine:saleOrderLineList){
                BigDecimal lineSaleTotal=getSaleOrderLineSaleTotal(saleOrderLine);
                if(saleOrderLine.getCategoryId()==1){ // 配饰 行邮税=如果总销售额>500元，=总销售额*行邮税率
                    postalFee1=postalFee1.add(lineSaleTotal.multiply(saleOrderLine.getPostalTaxRate()).divide(new BigDecimal(100)));
                }
                if(saleOrderLine.getCategoryId()==2){ //化妆品 行邮税=如果总销售额>100元，=总销售额*行邮税率
                    postalFee2=postalFee2.add(lineSaleTotal.multiply(saleOrderLine.getPostalTaxRate()).divide(new BigDecimal(100)));
                }
                if(saleOrderLine.getCategoryId()==3){
                    postalFee=postalFee.add(lineSaleTotal.multiply(saleOrderLine.getPostalTaxRate()).divide(new BigDecimal(100)));
                }
            }
            BigDecimal postalFee12=postalFee1.add(postalFee2);
            if(postalFee12.doubleValue()>50){
                postalFee=postalFee.add(postalFee12);
            }
            saleOrder.setPostalFee(postalFee);
        }


    }

    /**
     * 行邮税率
     * @param categoryId
     * @return
     */
    private BigDecimal getPostalTaxRate(int categoryId){
        if(categoryId==1){ // 化妆品订单总额超过100元，收订单总额的50%，
            return new BigDecimal(50);
        }else if(categoryId==2){ //配饰超过500元，收订单总额的10%
            return new BigDecimal(10);
        }else if(categoryId==3){ //投影仪是按订单总额的10%收
            return new BigDecimal(10);
        }
        return new BigDecimal(0);
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
                    Logger.error("<br/>"+(i+1)+"行订单不存在,orderId="+str[1]);
                    orderErr.append("<br/>"+(i+1)+"行订单不存在,orderId="+str[1]);
                    continue;
                }
                saleOrder=saleOrderList.get(0);
                saleOrder.setShipFee(new BigDecimal(str[15])); //运费
                saleOrder.setPackFee(new BigDecimal(str[14])); //包装费
                saleOrder.setStorageFee(new BigDecimal(str[13])); //存储费用
                saleOrder.setProfit(getOrderProfit(saleOrder));
                saleService.updateSaleOrder(saleOrder);
                Logger.info("profit="+saleOrder);
                suc.append("<br/>"+(i+1)+"行订单费用导入成功,orderId="+str[1]);
            }

            if(orderErr.length()>0){
                sb.append("<br/>订单不存在:<br/>");
                sb.append(orderErr);
            }
            if(suc.length()>0){
                sb.append("<br/>导入成功:<br/>");
                sb.append(suc);
            }
        }
        //释放
        list.clear();
        list=null;
        String importResult="订单费用导入操作成功<br/>"+sb.toString();
        return ok(views.html.sales.saleImportResult.render("cn",(User) ctx().args.get("user"),importResult));
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
            SaleOrderLine saleOrderLine=null;
            StringBuffer orderErr=new StringBuffer();
            StringBuffer suc=new StringBuffer();
            SaleOrderLine temp=null;
            for(int i=1;i<list.size();i++){
                str=list.get(i).split(",");
                temp=new SaleOrderLine();
                String orderId=str[0];
                if(str[1].equals("合计")){
                    continue;
                }
                if("".equals(str[0])&&"".equals(str[1])){
                    for(int j=i-1;j>1;j--){
                        orderId=list.get(j).split(",")[0]; //,,类型的取上一个orderId
                        if(!"".equals(orderId)&&null!=orderId){
                            break;
                        }
                    }
                }
                if(null==orderId||"".equals(orderId.trim())){
                    Logger.error("<br/>"+(i+1)+"行订单不存在,orderId="+orderId);
                    orderErr.append("<br/>"+(i+1)+"行订单不存在,orderId="+orderId);
                    continue;
                }
                temp.setJdOrderId(orderId);
                temp.setJdSkuId(str[7]);

                List<SaleOrderLine> saleOrderLineList=saleService.getSaleOrderLine(temp);
                if(null==saleOrderLineList||saleOrderLineList.isEmpty()){
                    Logger.error("<br/>"+(i+1)+"行订单不存在,orderId="+orderId);
                    orderErr.append("<br/>"+(i+1)+"行订单不存在,orderId="+orderId);
                    continue;
                }
                //商品应结金额*京东费率=商品佣金，用这个公式商品佣金/商品应结金额=京东费率
                saleOrderLine=saleOrderLineList.get(0);
                BigDecimal jdFeeDollar=new BigDecimal(str[3]).setScale(2,BigDecimal.ROUND_HALF_DOWN);
                if(jdFeeDollar.compareTo(new BigDecimal(0))<0){
                    jdFeeDollar=jdFeeDollar.multiply(new BigDecimal(-1));
                }

                BigDecimal jdRate=new BigDecimal(Math.round(jdFeeDollar.multiply(new BigDecimal(100)).divide(new BigDecimal(str[2]),2).doubleValue())).setScale(2,BigDecimal.ROUND_HALF_DOWN); //jd rate 扩大了100倍 四舍五入
//                Logger.info(saleOrderLine+"========jdFee="+jdFee+"===jdRate="+jdRate+"==="+(saleOrderLine.getJdFee().equals(jdFee))+"=="+(saleOrderLine.getJdRate().equals(jdRate)));
                if(saleOrderLine.getJdFee().equals(jdFeeDollar)&&saleOrderLine.getJdRate().equals(jdRate)){
                    Logger.info("<br/>"+(i+1)+"行订单费用没有变化,orderId="+orderId);
                    continue;
                }

                BigDecimal saleTotal=getSaleOrderLineSaleTotal(saleOrderLine);
                //京东费用
                BigDecimal jdFee=saleTotal.multiply(jdRate).divide(new BigDecimal(100));
                saleOrderLine.setJdFee(jdFee);
                saleOrderLine.setJdRate(jdRate);
                saleService.updateSaleOrderLine(saleOrderLine);

                SaleOrder saleOrder=saleService.getSaleOrderById(saleOrderLine.getSaleOrderId());
                if(null!=saleOrder){
                    SaleOrderLine tempOrderLine = new SaleOrderLine();
                    tempOrderLine.setJdOrderId(orderId);
                    //该订单下的所有子订单数据
                    BigDecimal jdFeeSum=new BigDecimal(0);
                    List<SaleOrderLine> orderLineList=saleService.getSaleOrderLine(tempOrderLine);
                    for(SaleOrderLine tempLine:orderLineList){
                        jdFeeSum=jdFeeSum.add(tempLine.getJdFee());
                    }
                    saleOrder.setJdFeeSum(jdFeeSum);
                    saleOrder.setProfit(getOrderProfit(saleOrder));
                    saleService.updateSaleOrder(saleOrder);
             //       Logger.info("========"+saleOrder);
                }


                suc.append("<br/>"+(i+1)+"行订单费用导入成功,orderId="+orderId);
            }

            if(orderErr.length()>0){
                sb.append("<br/>订单不存在:<br/>");
                sb.append(orderErr);
            }
            if(suc.length()>0){
                sb.append("<br/>导入成功:<br/>");
                sb.append(suc);
            }
        }
        //释放
        list.clear();
        list=null;
        String importResult="妥投销货清单明细导入操作成功<br/>"+sb.toString();
        return ok(views.html.sales.saleImportResult.render("cn",(User) ctx().args.get("user"),importResult));
    }

    /**
     * 导入优惠信息
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result uploadOrderSaleCoupon(){
        Logger.info("===uploadOrderSaleCoupon=");
        User user = (User) ctx().args.get("user");
        Long userId=Long.valueOf(user.userId().get().toString());
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("saleCouponFile");
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
            if(str1.length<9){
                Logger.error("导入表格长度不对"+str1.length);
                return badRequest();
            }

//            String[] str=list.get(152).split(",");
//            for(int i=0;i<str.length;i++){
//                Logger.info(str[i]+"-->"+str1[i]);
//                sb.append(i+"-->"+str1[i]+"-->"+str[i]).append("\n");
//            }

            /***
             *
             0-->订单号-->11543933616
             1-->下单时间-->2015-12-16 16:43:56
             2-->订单金额-->689.00
             3-->结算金额-->378.90
             4-->京东券优惠-->0.00
             5-->礼品卡优惠-->0.00
             6-->优惠明细（A表示含有订单优惠，B表示只有单品优惠，C表示无优惠）-->A【满返满送(返现):3000】【单品促销优惠（skuId）：1400】【单品促销优惠（skuId）：10010】【单品促销优惠（skuId）：13600】【单品促销优惠（skuId）：3000】
             7-->订单状态-->完成
             8-->订单类型-->销售订单
             */
            String[] str = null;
            SaleOrder saleOrder=null;
            StringBuffer suc=new StringBuffer();

            for(int i=1;i<list.size();i++){
                str=list.get(i).split(",");
                String jdOrderId=str[0];
                String couponInfo=str[6];

                if(couponInfo.startsWith("C")) { //C表示无优惠
                    suc.append("<br/>"+(i+1)+"行无优惠,orderId="+jdOrderId);
                    continue;
                }
                Logger.info(jdOrderId+"====="+couponInfo);
                int orderDiscountSum=0;//订单优惠
                SaleOrderLine tempSaleOrderLine=new SaleOrderLine();
                tempSaleOrderLine.setJdOrderId(jdOrderId);
                //所有子订单
                List<SaleOrderLine> saleOrderLineList=saleService.getSaleOrderLine(tempSaleOrderLine);
                if(null==saleOrderLineList||saleOrderLineList.isEmpty()){
                    suc.append("<br/>"+(i+1)+"行订单不存在,orderId="+jdOrderId);
                    continue;
                }

                List<Integer> selfDiscountList=new ArrayList<>(); //顺序
                if(couponInfo.startsWith("B")) {  //B表示只有单品优惠
                    String[] discountStr=couponInfo.split("】");
                    for(String item:discountStr){
                        selfDiscountList.add(Integer.valueOf(item.split("：")[1]));
                    }
                }

                if(couponInfo.startsWith("A")) {  //A表示含有订单优惠
                    String[] discountStr=couponInfo.split("】");
                    for(String item:discountStr){
                        if(item.contains("单品促销优惠")){
                            selfDiscountList.add(Integer.valueOf(item.split("：")[1]));
                        }
                        else{
                            orderDiscountSum+=Integer.valueOf(item.split(":")[1]); //订单优惠
                        }

                    }
                }



                BigDecimal total=new BigDecimal(0);
                BigDecimal orderDiscount=new BigDecimal(orderDiscountSum).divide(new BigDecimal(100));
                if(orderDiscountSum>0){
                    for(SaleOrderLine saleOrderLine:saleOrderLineList){
                        Logger.info(total+"====="+saleOrderLine);
                        total=total.add((saleOrderLine.getJdPrice().multiply(new BigDecimal(saleOrderLine.getSaleCount()))));
                    }
                }

                BigDecimal jdFeeSum=new BigDecimal(0);
                for(int j=0;j<saleOrderLineList.size();j++){
                    SaleOrderLine saleOrderLine=saleOrderLineList.get(j);
                    if(selfDiscountList.size()>j){

                        BigDecimal discount=new BigDecimal(selfDiscountList.get(j)).divide(new BigDecimal(100));
                        if(orderDiscountSum>0){
                            //订单按比例优惠
                            BigDecimal averageDiscount=saleOrderLine.getJdPrice().multiply(new BigDecimal(saleOrderLine.getSaleCount())).multiply(orderDiscount).divide(total,2,BigDecimal.ROUND_HALF_DOWN);

                            discount=discount.add(averageDiscount);
                            Logger.info(orderDiscount+"====averageDiscount="+averageDiscount+"==="+discount);
                        }
                        saleOrderLine.setDiscountAmount(discount);
                        saleOrderLine.setJdFee(calJdFee(saleOrderLine)); //计算京东费用
                        saleService.updateSaleOrderLine(saleOrderLine);
                    }
                    jdFeeSum.add(saleOrderLine.getJdFee());
                }

                saleOrder=saleService.getSaleOrderById(saleOrderLineList.get(0).getSaleOrderId());
                saleOrder.setJdFeeSum(jdFeeSum);//京东费用和
                saleOrder.setProfit(getOrderProfit(saleOrder)); //计算利润
                saleService.updateSaleOrder(saleOrder);
            }
        }
        String importResult="订单费用导入操作成功<br/>"+sb.toString();
        return ok(views.html.sales.saleImportResult.render("cn",(User) ctx().args.get("user"),importResult));
    }


    /**
     * 销售商品删除
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result saleProductDel(Long id) {
        SaleOrderLine saleOrderLine=new SaleOrderLine();
        saleOrderLine.setSaleProductId(id);
        List<SaleOrderLine> saleOrderList=saleService.getSaleOrderLine(saleOrderLine); //存在关联的订单不允许删除
        if(null!=saleOrderList&&saleOrderList.size()>0){
            return ok("exist");
        }

        if(saleService.delSaleProductById(id)){
            return ok("success");
        }
        return badRequest();

    }
    public Result saleProductDatapub() {
        return ok(views.html.sales.datapub.render());
    }
}

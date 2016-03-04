package controllers;

import com.google.inject.Inject;
import entity.User;
import entity.sale.SaleOrder;
import entity.sale.SaleProduct;
import filters.UserAuth;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.SaleService;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 销售报表
 * Created by sibyl.sun on 16/3/3.
 */
public class SaleCtrl extends Controller {
    @Inject
    private SaleService saleService;

    /**
     *
     * @param name 商品名称
     * @param catagoryId 商品分类
     * @param sysCode 系统编码
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
    private SaleProduct createSaleProduct(String name,Integer catagoryId,String sysCode,String productCode, String spec, Integer saleCount,
                                          Integer inventory, BigDecimal productCost,BigDecimal stockValue,Integer purchaseCount,Integer noCard,Integer damage,
                                          Integer lessDelivery, Integer lessProduct, Integer emptyBox){
        SaleProduct saleProduct=new SaleProduct();
        setSaleProduct(saleProduct,name,catagoryId,sysCode,productCode,spec,saleCount,inventory,productCost,stockValue,purchaseCount,noCard,damage,
                lessDelivery,lessProduct,emptyBox);
        if(saleService.insertSaleProduct(saleProduct)){
            return saleProduct;
        }
        return null;
    }
    private void setSaleProduct(SaleProduct saleProduct,
                                String name,Integer catagoryId,String sysCode,String productCode, String spec, Integer saleCount,
                                Integer inventory, BigDecimal productCost,BigDecimal stockValue,Integer purchaseCount,Integer noCard,Integer damage,
                                Integer lessDelivery, Integer lessProduct, Integer emptyBox){
        saleProduct.setName(name);
        saleProduct.setCategoryId(catagoryId);
        saleProduct.setSysCode(sysCode);
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
    }

    /**
     *
     * @param saleAt 日期
     * @param orderId 订单号
     * @param saleProductId 销售商品id
     * @param productName 品名
     * @param categoryId 商品分类
     * @param price 单价
     * @param count 数量
     * @param discountAmount 优惠额
     * @param saleCount 销售
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
    private SaleOrder createSaleOrder(Timestamp saleAt,String orderId,Long saleProductId,String productName, Integer categoryId,BigDecimal price,Integer count,
                                      BigDecimal discountAmount, Integer saleCount,BigDecimal jdRate,BigDecimal jdFee,BigDecimal cost,BigDecimal shipFee,
                                      BigDecimal inteLogistics, BigDecimal packFee, BigDecimal postalFee, String postalTaxRate, BigDecimal profit){
        SaleOrder saleOrder=new SaleOrder();
        saleOrder.setSaleAt(saleAt);
        saleOrder.setOrderId(orderId);
        saleOrder.setSaleProductId(saleProductId);
        saleOrder.setProductName(productName);
        saleOrder.setCategoryId(categoryId);
        saleOrder.setPrice(price);
        saleOrder.setCount(count);
        saleOrder.setDiscountAmount(discountAmount);
        saleOrder.setSaleCount(saleCount);
        saleOrder.setJdRate(jdRate);
        saleOrder.setJdFee(jdFee);
        saleOrder.setCost(cost);
        saleOrder.setShipFee(shipFee);
        saleOrder.setInteLogistics(inteLogistics);
        saleOrder.setPackFee(packFee);
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
        return ok(views.html.sales.dataImport.render("cn", (User) ctx().args.get("user")));
    }

    /**
     * 数据查询
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result salesSearch() {
        return ok(views.html.sales.dataSearch.render("cn", (User) ctx().args.get("user")));
    }

}

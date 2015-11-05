package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Gender;
import entity.Products;
import entity.User;
import entity.User_Type;
import modules.OSSClientProvider;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;
import play.mvc.Security;
import service.ItemService;
import views.html.item.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Controller for commodity create,update,modify,search.
 *
 * @author Sunny Wu
 */
@Singleton
public class Item extends Controller {

    /**
     * inject ItemService here.
     */
    @Inject
    private ItemService itemService;

    @Inject
    private OSSClientProvider oss_provider;

    /**
     * Item create page controller.
     *
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemCreate(String lang) {
        Logger.debug(oss_provider.get().toString());
        return ok(prodsadd.render(lang,itemService.getAllBrands(),itemService.getParentCates(),(User) ctx().args.get("user")));
    }

    /**
     * Ajax for get sub category.
     *
     * @return Result
     *
     */

    public Result getSubCategory() {
        DynamicForm form = Form.form().bindFromRequest();
        Integer pcid = Integer.parseInt(form.get("pcid"));
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("parentCateId", pcid);
        return ok(Json.toJson(itemService.getSubCates(hashMap)));
    }

    /**
     * get all products
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result prodsList(String lang) {
        return ok(prodslist.render(lang,itemService.getAllProducts(),(User) ctx().args.get("user")));
    }

    /**
     * insert products
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result insertProducts() {
        ObjectNode result = Json.newObject();
        result.put("result",false);
        try {
            DynamicForm form = Form.form().bindFromRequest();
            MultipartFormData body = request().body().asMultipartFormData();
            Products products = new Products();
            products.setMerchId(1001);
            products.setMerchName(form.get("merchName"));
            products.setLanguage(form.get("language"));
            String cateId = form.get("cateId");
            String cateName = form.get("cateName");
            if (null != cateId && !"".equals(cateId)) {
                products.setCateId(Integer.parseInt(cateId));
            } else if (null != cateName && !"".equals(cateName) ) {
                products.setCateName(cateName);
            } else {
                result.put("cate", "请选择或添加类别");
                return ok(result);
            }
            String brandId = form.get("brandId");
            String brandName = form.get("brandName");
            if (null != brandId && !"".equals(brandId)) {
                products.setBrandId(Integer.parseInt(brandId));
            } else if (null != brandName && !"".equals(brandName) ) {
                products.setBrandName(brandName);
            } else {
                result.put("brand", "请选择或添加品牌");
                return ok(result);
            }
            products.setProductName(form.get("productName"));
            products.setProductColor(form.get("productColor"));
            products.setProductSize(form.get("productSize"));
            products.setProductDesc(form.get("productDesc"));
            products.setStoreArea(form.get("storeArea"));
            products.setSourceArea(form.get("sourceArea"));
            try {
                products.setSellOnDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(form.get("sellOnDate")).getTime()));
                products.setSellOffDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(form.get("sellOffDate")).getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            products.setProductAmount(Integer.parseInt(form.get("productAmount")));
            products.setProductPrice(new BigDecimal(form.get("productPrice")));
            products.setRecommendPrice(new BigDecimal(form.get("recommendPrice")));
            products.setMasterImg(form.get("masterImg"));
            products.setPreviewImgs(form.get("previewImgs"));
            products.setDetailImgs(form.get("detailImgs"));
            products.setFeatures(form.get("features"));
            products.setProductState(form.get("productState"));
            products.setProductState("Y");  //商品状态 'Y' 正常
            products.setDestory(false);
            if (products.getLanguage().isEmpty()||products.getProductName().isEmpty()||products.getProductColor().isEmpty()||products.getMasterImg().isEmpty()
                    ||products.getProductSize().isEmpty()||products.getSourceArea().isEmpty()
                    ||products.getProductAmount().toString().isEmpty()||products.getProductPrice().toString().isEmpty()||products.getRecommendPrice().toString().isEmpty()
                    ||products.getSellOnDate().toString().isEmpty()||products.getSellOffDate().toString().isEmpty()) {
                result.put("error", "必填项不能为空!");
                return ok(result);
            }
            Integer id = itemService.insertProducts(products);
            if (null != id) {
                result.put("id", products.getId());
                result.put("result", true);
                return ok(result);
            } else {
                result.put("error", "添加失败!");
                return ok(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result.put("error", "存储有异常!");
            return ok(result);
        }
    }

}


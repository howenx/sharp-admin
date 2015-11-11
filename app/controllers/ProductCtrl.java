package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Products;
import entity.User;
import modules.OSSClientProvider;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.ProdService;
import views.html.prod.prodsadd;
import views.html.prod.prodslist;
import views.html.prod.prodsdetail;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for commodity create,update,modify,search.
 *
 * @author Sunny Wu
 */
@Singleton
public class ProductCtrl extends Controller {

    //每页固定的取数
    public static final int PAGE_SIZE = 4;

    //图片服务器url
    public static final String IMAGE_URL = play.Play.application().configuration().getString("image.server.url");

    //发布服务器url
    public static final String DEPLOY_URL = play.Play.application().configuration().getString("deploy.server.url");

    /**
     * inject ProdService here.
     */
    @Inject
    private ProdService prodService;

    @Inject
    private OSSClientProvider oss_provider;

    /**
     * ProductCtrl create page controller.
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result prodCreate(String lang) {
        Logger.debug(oss_provider.get().toString());
        return ok(prodsadd.render(lang, prodService.getAllBrands(), prodService.getParentCates(),(User) ctx().args.get("user")));
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
        return ok(Json.toJson(prodService.getSubCates(hashMap)));
    }

    /**
     * get all products
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result prodsList(String lang) {
        Products products = new Products();
        products.setPageSize(-1);
        products.setOffset(-1);
        //取总数
        int countNum = prodService.getAllProducts(products).size();
        //共分几页
        int pageCount = countNum/PAGE_SIZE;

        if(countNum%PAGE_SIZE!=0){
            pageCount = countNum/PAGE_SIZE+1;
        }
        products.setPageSize(PAGE_SIZE);
        products.setOffset(1);
        return ok(prodslist.render(lang,IMAGE_URL,PAGE_SIZE,countNum,pageCount,(User) ctx().args.get("user"), prodService.getAllProducts(products)));
    }

    /**
     * ajax分页查询
     * @param lang 语言
     * @param currPage 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result prodSearchAjax(String lang,int currPage) {
        JsonNode json = request().body().asJson();
        Products products = Json.fromJson(json,Products.class);
        if(currPage>=1){
            //每页开始记录数
            int offset = (currPage-1)*PAGE_SIZE;
            products.setPageSize(-1);
            products.setOffset(-1);
            //取总数
            int countNum = prodService.getAllProducts(products).size();
            //共分几页
            int pageCount = countNum/PAGE_SIZE;
            if(countNum%PAGE_SIZE!=0){
                pageCount = countNum/PAGE_SIZE+1;
            }
            products.setPageSize(PAGE_SIZE);
            products.setOffset(offset);
            Logger.error(products.toString());
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("prods",prodService.getAllProducts(products));
            returnMap.put("currPage",currPage);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",PAGE_SIZE);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     *
     * @param lang 语言
     * @param id
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result prodsDetail(String lang,Long id) {
        if(!"".equals(id) && null!=id) {
            Products products = prodService.getProducts(id);
            return ok(prodsdetail.render(products,lang,(User) ctx().args.get("user")));
        } else {
            return badRequest();
        }
    }


    /**
     * insert products
     * @return Result
     */
    public Result insertProducts() {
        ObjectNode result = Json.newObject();
        result.put("result", false);
        DynamicForm form = Form.form().bindFromRequest();
        String multiProducts = form.get("multiProducts");

        JsonNode json =Json.parse(multiProducts);
        Logger.error(json.toString());

        List<Long> list = prodService.insertProducts(json);
        return ok(list.toString());
    }
}


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
import service.ItemService;
import views.html.prod.prodsadd;
import views.html.prod.prodsdetail;
import views.html.prod.prodslist;

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
    public static final int PAGE_SIZE = 2;

    //图片服务器url
    public static final String IMAGE_URL = play.Play.application().configuration().getString("image.server.url");

    //发布服务器url
    public static final String DEPLOY_URL = play.Play.application().configuration().getString("deploy.server.url");

    /**
     * inject ItemService here.
     */
    @Inject
    private ItemService itemService;

    @Inject
    private OSSClientProvider oss_provider;

    /**
     * ProductCtrl create page controller.
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
     *
     * @param currentPage 当前页
     * @param size  每页的记录数
     * @param pageCount 总页数
     * @return int[]
     */
    public static Integer[] getStep(int currentPage, int size, int pageCount) {

        //总页面数小于跨度
        if (pageCount <= size) {
            return new Integer[]{1,pageCount};
        }
        //1.currentPage位置在前面
        if (currentPage <= size/2) {
            return new Integer[]{1, size};
        }
        //2.currentPage位置在后面
        if (currentPage > pageCount-size/2) {
            return new Integer[]{pageCount-size+1, pageCount};
        }
        //3.currentPage位置在中间
        int first = currentPage-size/2;
        return new Integer[]{first, first+size-1};
    }

    /**
     * get all products
     * @param lang
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result prodsList(String lang,Integer currentPage) {
        Map<String,Integer> paramsMap = new HashMap<String,Integer>();
        paramsMap.put("pageSize", -1);
        paramsMap.put("offset", -1);

        //取总数
        int countNum = itemService.getAllProducts(paramsMap).size();
        //共分几页
        int pageCount = countNum/PAGE_SIZE;

        if(countNum%PAGE_SIZE!=0){
            pageCount = countNum/PAGE_SIZE+1;
        }

        if  (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
        Integer offSet = (currentPage-1)*PAGE_SIZE;

        paramsMap.put("pageSize", PAGE_SIZE);
        paramsMap.put("offset", offSet);

        Integer[] step = getStep(currentPage, 3, pageCount);
        String start = step[0].toString();
        Integer end = step[1];
        Logger.error(start.toString());
        Logger.error(end.toString());
        Logger.error(currentPage.toString());
        return ok(prodslist.render(start,end,lang,IMAGE_URL,PAGE_SIZE,countNum,pageCount,currentPage,(User) ctx().args.get("user"),itemService.getAllProducts(paramsMap)));
    }

    /**
     *
     * @param id
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result prodsDetail(String lang,Long id) {
        if(!"".equals(id) && null!=id) {
            Products products = itemService.getProducts(id);
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

        List<Long> list = itemService.insertProducts(json);
        return ok(list.toString());
    }
}


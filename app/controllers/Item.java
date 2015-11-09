package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import views.html.item.prodsadd;
import views.html.item.prodslist;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;

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
    public Result insertProducts() {
        ObjectNode result = Json.newObject();
        result.put("result", false);
        DynamicForm form = Form.form().bindFromRequest();
        String multiProducts = form.get("multiProducts");
        System.out.print(multiProducts);

        JsonNode json =Json.parse(multiProducts);
        Logger.error(json.toString());

        List<Long> list = itemService.insertProducts(json);
        return ok(list.toString());
    }
}


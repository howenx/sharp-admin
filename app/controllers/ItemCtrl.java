package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Item;
import entity.User;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.ItemService;
import service.ProdService;
import service.ThemeService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理
 * Created by howen on 15/11/11.
 */
public class ItemCtrl extends Controller {

    @Inject
    private ItemService service;

    @Inject
    private ProdService prodService;

    @Inject
    private ThemeService themeService;

    /**
     * 商品列表
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemlist(String lang){

        Item item =new Item();

        item.setPageSize(-1);
        item.setOffset(-1);

        //取总数
        int countNum = service.itemSearch(item).size();
        //共分几页
        int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

        if(countNum%ThemeCtrl.PAGE_SIZE!=0){
            pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
        }

        item.setPageSize(ThemeCtrl.PAGE_SIZE);
        item.setOffset(1);

        return ok(views.html.item.itemlist.render(lang,ThemeCtrl.IMAGE_URL,ThemeCtrl.PAGE_SIZE,countNum,pageCount,service.itemSearch(item),(User) ctx().args.get("user")));
    }

    /**
     * ajax分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemsearchAjax(String lang,int pageNum) {

        JsonNode json = request().body().asJson();

        Item theme = Json.fromJson(json,Item.class);

        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*ThemeCtrl.PAGE_SIZE;

            theme.setPageSize(-1);
            theme.setOffset(-1);

            //取总数
            int countNum = service.itemSearch(theme).size();
            //共分几页
            int pageCount = countNum/ThemeCtrl.PAGE_SIZE;

            if(countNum%ThemeCtrl.PAGE_SIZE!=0){
                pageCount = countNum/ThemeCtrl.PAGE_SIZE+1;
            }

            theme.setPageSize(ThemeCtrl.PAGE_SIZE);
            theme.setOffset(offset);

            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",service.itemSearch(theme));
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
     * 商品查询弹窗
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemSearchPopup(String lang){
        return ok(views.html.item.itemlistPop.render(lang));
    }

    /**
     * 商品录入
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemCreate(String lang) {

        return ok(views.html.item.itemadd.render(lang,prodService.getAllBrands(),prodService.getParentCates(),(User) ctx().args.get("user")));
    }
    /**
     * 添加商品
     * @return Result
     */
    public Result insertItem() {
        DynamicForm form = Form.form().bindFromRequest();
        String items = form.get("items");
        String inventories = form.get("inventories");
        JsonNode jsonItem =Json.parse(items);
        JsonNode jsonInventories =Json.parse(inventories);
        Logger.error(jsonItem.toString());
        Logger.error(jsonInventories.toString());

        List<Long> list = service.insertItem(jsonItem,jsonInventories);
        return ok(list.toString());
    }
}

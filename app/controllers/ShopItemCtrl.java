package controllers;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.domain.ShopItem;
import entity.Item;
import entity.User;
import entity.erp.ShopItemOperate;
import filters.UserAuth;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.InventoryService;
import service.ItemService;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Sunny Wu 15/12/22.
 * <p>
 * 查询ERP中所有的商品信息
 */
public class ShopItemCtrl extends Controller {

    @Inject
    private ItemService itemService;

    @Inject
    private InventoryService inventoryService;

    /**
     * ERP 商品资料查询
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result shopItemList(String lang) throws ApiException, ParseException {
        ShopItemOperate shopItemOperate = new ShopItemOperate();
        String startTime = "2016-02-20 00:00:00";
        String endTime = "2016-07-07 00:00:00";
        List<ShopItem> shopItemList = shopItemOperate.ShopItemQuery(startTime, endTime);
        int pageSize = 10;
        //取总数
        int countNum = shopItemList.size();
        //共分几页
        int pageCount = (int) Math.ceil((double) countNum / pageSize);
        Logger.error(shopItemList.toString());
        return ok(views.html.item.erp_itemlist.render(lang,shopItemList,ThemeCtrl.PAGE_SIZE,countNum,pageCount, (User)ctx().args.get("user")));
    }

    @Security.Authenticated(UserAuth.class)
    public Result shopItemPush() throws ParseException, ApiException {
        ShopItemOperate shopItemOperate = new ShopItemOperate();
        String startTime = "2016-02-20 00:00:00";
        String endTime = "2016-07-07 00:00:00";
        List<ShopItem> shopItemList = shopItemOperate.ShopItemQuery(startTime, endTime);
        for(ShopItem shopItem : shopItemList) {
            Item item = new Item();
        }
        return ok();
    }

    /**
     * ERP 商品资料分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result shopItemSearchAjax(String lang, int pageNum) {

        return ok();
    }
}

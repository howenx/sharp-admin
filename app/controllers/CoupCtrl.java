package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.ID;
import entity.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.CouponsService;
import service.IDService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class CoupCtrl extends Controller {

    @Inject
    private CouponsService couponsService;

    @Inject
    private IDService idService;

    /**
     * 发放优惠券
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupCreate(String lang) {
        return ok(views.html.coupon.coupadd.render(lang, (User) ctx().args.get("user")));
    }


    /**
     * 发放优惠券,选择用户列表弹窗
     * @return Result
     */
    public Result coupaddPop() {
        List<ID> idList = idService.getAllID();
        String IMAGE_URL = "http://img.hanmimei.com";
        return ok(views.html.coupon.coupaddPop.render(idList,IMAGE_URL));
    }

    /**
     * 保存优惠券
     * @return Result
     */
    public Result coupSave() {
        JsonNode json = request().body().asJson();
        couponsService.couponsSave(json);
        return ok();
    }

}

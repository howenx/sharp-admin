package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.User;
import filters.UserAuth;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.CouponsService;

import javax.inject.Inject;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class CoupCtrl extends Controller {

    @Inject
    private CouponsService couponsService;

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
     * 保存优惠券
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result coupSave() {
        JsonNode json = request().body().asJson();
        couponsService.couponsSave(json);
        return ok("保存成功");
    }

}

package controllers;

import domain.User;
import filters.UserAuth;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.CouponVoService;

import javax.inject.Inject;

/**
 * Created by Sunny Wu on 16/4/25.
 * kakao china.
 * Coupon系统的操作
 */
public class CouponCtrl extends Controller {

    @Inject
    private CouponVoService couponVoService;

    @Security.Authenticated(UserAuth.class)
    public Result couponImport(String lang) {

        return ok(views.html.couponSystem.couponImport.render(lang, (User) ctx().args.get("user")));

    }

    @Security.Authenticated(UserAuth.class)
    public Result couponDataImport(String lang) {

        return ok("upload succ");

    }



}

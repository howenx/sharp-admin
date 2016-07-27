package controllers;


import domain.User;
import filters.UserAuth;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import util.SysParCom;

import static play.mvc.Http.Context.Implicit.ctx;

/**
 * Created by tiffany on 16/7/27.
 */
public class CategoryCtrl extends Controller {
    /**
     * 分类入口 Added by Tiffany Zhu 2016.07.27
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result category(String lang) {
        return ok(views.html.category.category.render(lang,(User) ctx().args.get("user")));
    }
}

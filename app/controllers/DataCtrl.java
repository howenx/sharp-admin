package controllers;

import entity.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by tiffany on 15/12/14.
 */
public class DataCtrl extends Controller {
    /**
     * 系统参数列表
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result systemParameterSearch(String lang){
        return ok(views.html.data.parametersearch.render(lang,(User) ctx().args.get("user")));
    }

    /**
     * 新增系统参数
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result systemParameterAdd(String lang){
        return ok(views.html.data.parameteradd.render(lang,(User) ctx().args.get("user")));
    }
}

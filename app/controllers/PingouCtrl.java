package controllers;

import entity.User;
import play.mvc.Controller;

import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by tiffany on 16/1/19.
 */
public class PingouCtrl extends Controller {

    /**
     * 添加拼购     Added by Tiffany Zhu 2016.01.19
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result addPingou(String lang){

        return ok(views.html.pingou.pingouAdd.render(lang,(User) ctx().args.get("user")));
    }



}

package controllers;

import domain.User;
import filters.UserAuth;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by tiffany on 16/4/19.
 */
public class SupplyCtrl extends Controller {

    /**
     * 供货商处理订单      Added by Tiffany Zhu 2016.04.19
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderProcess(String lang){
        User user = (User) ctx().args.get("user");
        Logger.error("user信息:" +user.toString());


        return ok(views.html.supply.supplierProcess.render(lang, (User) ctx().args.get("user")));
    }


}

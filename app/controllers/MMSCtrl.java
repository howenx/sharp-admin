package controllers;

import domain.User;
import filters.UserAuth;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import util.SysParCom;

/**
 * Created by tiffany on 16/9/19.
 */
public class MMSCtrl extends Controller {
    @Security.Authenticated(UserAuth.class)
    public Result addMerchandise(String lang){
        return ok(views.html.MMS.merchandiseAdd.render(lang, SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }

}

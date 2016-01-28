package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.ID;
import entity.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.AdminUserService;
import service.IDAdminService;
import service.IDService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/28.
 * kakao china.
 */
public class AdminUserCtrl extends Controller {

    @Inject
    private AdminUserService adminUserService;

    @Inject
    private IDAdminService idAdminService;

    @Inject
    private IDService idService;

    /**
     * 添加管理员用户
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserCreate(String lang) {
        return ok(views.html.adduser.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 保存用户信息
     * @return Result
     */
    public Result adminUserSave(){
        JsonNode json = request().body().asJson();
        return ok();
    }

    /**
     * 选择用户列表弹窗(发放优惠券功能)
     * @return Result
     */
    public Result addIDUserPop() {
        List<ID> idList = idService.getAllID();
        String IMAGE_URL = "http://img.hanmimei.com";
        return ok(views.html.coupon.coupaddPop.render(idList,IMAGE_URL));
    }
}

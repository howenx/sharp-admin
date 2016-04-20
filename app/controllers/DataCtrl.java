package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import domain.SystemParam;
import domain.User;
import filters.UserAuth;
import play.Logger;
import play.data.Form;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.SysParamService;

import javax.inject.Inject;

/**
 * Created by tiffany on 15/12/14.
 */
public class DataCtrl extends Controller {
    @Inject
    SysParamService sysParamService;
    /**
     * 系统参数列表
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result systemParameterSearch(String lang){
        return ok(views.html.data.sysparamsearch.render(lang,sysParamService.getParamAll(),(User) ctx().args.get("user")));
    }

    /**
     * 新增系统参数
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result systemParameterAdd(String lang){
        return ok(views.html.data.sysparamadd.render(lang,(User) ctx().args.get("user")));
    }

    /**
     * 添加系统参数
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result paramSave(String lang){
        JsonNode json = request().body().asJson();
        Form<SystemParam> systemParamForm = Form.form(SystemParam.class).bind(json);
        //数据验证
        if (systemParamForm.hasErrors()) {
            Logger.error("表单数据有误.....");
            return badRequest();
        }
        sysParamService.insertParam(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }
}

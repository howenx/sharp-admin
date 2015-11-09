package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Slider;
import play.Logger;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.ThemeService;

import javax.inject.Inject;
import entity.User;

import java.io.IOException;

/**
 * Theme management.
 * Created by howen on 15/10/28.
 */
public class Theme extends Controller {

    //图片服务器url
    public static final String IMAGE_URL = play.Play.application().configuration().getString("image.server.url");

    //发布服务器url
    public static final String DEPLOY_URL = play.Play.application().configuration().getString("deploy.server.url");

    @Inject
    private ThemeService service;

    /**
     * 滚动条管理
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result slider(String lang) {
        flash("success", session("username"));
        return ok(views.html.theme.slider.render(lang,service.sliderAll(),IMAGE_URL,(User) ctx().args.get("user")));
    }

    /**
     * 主题录入
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result thadd(String lang) {
//        flash("success", session("username"));
        return ok(views.html.theme.thadd.render(lang,IMAGE_URL,(User) ctx().args.get("user")));
    }


    /**
     * 滚动条变更保存
     * @param lang  语言
     * @return  view
     */
    @Security.Authenticated(UserAuth.class)
    public Result sliderSave(String lang){
        JsonNode json = request().body().asJson();

        service.sliderSave(json);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 商品查询弹窗
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemSearchPopup(String lang){
        return ok(views.html.theme.itemsearchPopup.render(lang));
    }

    /**
     * 主题查询
     * @param lang 语言
     * @return  view
     */
    @Security.Authenticated(UserAuth.class)
    public Result thsearch(String lang){
        return ok(views.html.theme.thsearch.render(lang,IMAGE_URL,(User) ctx().args.get("user")));
    }
}

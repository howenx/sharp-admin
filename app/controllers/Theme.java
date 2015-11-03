package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import service.ThemeService;

import javax.inject.Inject;

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

    public Result slider(String lang) {
        Logger.debug(service.sliderAll().toString());
        flash("username", session("username"));
        flash("success", session("username"));
        return ok(views.html.theme.slider.render(lang,service.sliderAll(),IMAGE_URL));
    }
}

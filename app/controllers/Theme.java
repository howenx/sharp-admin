package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Theme management.
 * Created by howen on 15/10/28.
 */
public class Theme extends Controller {

    public Result slider(String lang) {

        return ok(views.html.theme.slider.render(lang));
    }
}

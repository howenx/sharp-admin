package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class IDCtrl extends Controller {

    /**
     * 用户列表查询
     * @param lang 语言
     * @return Result
     */
    public Result userSearch(String lang) {
        return ok();
    }
}

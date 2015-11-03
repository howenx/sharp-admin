package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Singleton;

/**
 * Created by handy on 15/11/3.
 * kakao china
 */
@Singleton
public class Test extends Controller{

    @Security.Authenticated(UserAuth.class)
    public  Result test() {
        Logger.debug(ctx().args.get("user").toString());
        return ok("ok");
    }
}

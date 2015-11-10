package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Singleton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by handy on 15/11/3.
 * kakao china
 */
@Singleton
public class Test extends Controller{

    @Security.Authenticated(UserAuth.class)
    public  Result test() {
        Logger.debug(ctx().args.get("user").toString());
        Pattern p = Pattern.compile("\\b[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\b");
        Matcher m = p.matcher("kkmc.skein+cn@gmail.com");
        if(m.matches()) {
            Logger.debug("match");
        }else {
            Logger.debug("not match");
        }
        return ok("ok");
    }
}

package controllers;


import entity.User;
import play.Logger;
import play.cache.Cache;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by handy on 15/11/3.
 * kakao china
 */
public class UserAuth extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        String username =  ctx.session().get("username");
        if(username != null) {
            Logger.debug("userAuth user " + username);
            User user = (User) Cache.get(username.trim());
            if(user != null) {
                ctx.args.put("user",user);
            }
            else {
                return null;
            }


        }
        return username;
    }


    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect("/front");
    }

}

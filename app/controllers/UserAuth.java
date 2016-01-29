package controllers;


import com.typesafe.config.ConfigFactory;
import entity.User;
import play.Configuration;
import play.Logger;
import play.cache.Cache;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by handy on 15/11/3.
 * kakao china
 */
public class UserAuth extends Security.Authenticator {

    @Inject
    Configuration configuration;

    @Override
    public String getUsername(Http.Context ctx) {

        Optional<String> header = Optional.ofNullable(ctx.request().getHeader("Referer"));
        if (header.isPresent()) {

            String username =  ctx.session().get("username");
            if(username != null) {
                Logger.debug("userAuth user " + username);
                User user = (User) Cache.get(username.trim());
                configuration.getStringList()


                if(user != null) {
                    ctx.args.put("user",user);
                }
                else {
                    return null;
                }
            }
            return username;
        }else return null;


    }


    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect("/front");
    }

}

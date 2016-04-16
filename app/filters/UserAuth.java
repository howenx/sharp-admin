package filters;


import domain.User;
import play.Configuration;
import play.Logger;
import play.Routes;
import play.cache.Cache;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by handy on 15/11/3.
 * Modified By Sunny.Wu 2016/01/29
 * kakao china
 */
@SuppressWarnings("unchecked")
public class UserAuth extends Security.Authenticator {

    @Inject
    Configuration configuration;

    @Override
    public String getUsername(Http.Context ctx) {
        //url
        Optional<String> header = Optional.ofNullable(ctx.request().uri());
        //method
        Optional<String> header2 = Optional.of(ctx._requestHeader().tags().get(Routes.ROUTE_CONTROLLER).get()+"."+ctx._requestHeader().tags().get(Routes.ROUTE_ACTION_METHOD).get());
        Logger.debug("url:"+header.get());
        Logger.debug("method:"+header2.get());
        if (header.isPresent()) {
            String username =  ctx.session().get("username");
            if(username != null) {
                User user = (User) Cache.get(username.trim());
                if(user != null) {
                    Logger.debug("user:"+user.userType().get());
                    if (configuration.getStringList(String.valueOf(user.userType().get())).contains(header.get())){
                        ctx.args.put("user",user);
                    } else if (configuration.getStringList(String.valueOf(user.userType().get())).contains(header2.get())) {
                        ctx.args.put("user",user);
                    } else return null;
                }
                else {
                    return null;
                }
            }
            return username;
        }else return null;
    }

    /**
     * 未找到用户登录认证的情况下，将页面跳转到登录页面
     * @param ctx
     * @return
     */
    @Override
    public Result onUnauthorized(Http.Context ctx) {
//        return redirect("/front");
        // Modified By Sunny.Wu 2016/01/29
        return redirect("/");
    }

}

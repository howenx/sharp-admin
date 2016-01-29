package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.AdminUser;
import entity.ID;
import entity.User;
import entity.User_Type;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import play.Logger;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.Option;
import service.AdminUserService;
import service.IDAdminService;
import service.IDService;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * Created by Sunny Wu on 16/1/28.
 * kakao china.
 */
public class AdminUserCtrl extends Controller {

    @Inject
    private AdminUserService adminUserService;

    @Inject
    private IDAdminService idAdminService;

    @Inject
    private IDService idService;

    /**
     * 添加管理员用户
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserCreate(String lang) {
        return ok(views.html.adminuser.adduser.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 保存用户信息(用户注册)
     * @return Result
     */
    public Result adminUserSave(){
        JsonNode json = request().body().asJson();
        AdminUser adminUser  = Json.fromJson(json, AdminUser.class);
        adminUser.setEmail(adminUser.getEmail()+"@kakaocorp.com");
        //先验证是否已注册
        AdminUser adu  = adminUserService.getUserBy(adminUser);
        Logger.error("用户信息:"+adu);
        if (null!=adu && !"".equals(adu)) {
            return ok("该用户已注册!");
        } else {
            String defPwd = adminUser.CreateCode(8);//给邮箱发送的8位随机默认密码
            String regIp = request().remoteAddress();
            adminUser.setPasswd("defPwd");
            adminUser.setRegIp(regIp);
            adminUser.setActiveYN("N");
            adminUser.setStatus("N");
            adminUser.setLastLoginIp(regIp);
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName("smtp.kakaocorp.com");//设置邮件服务器
            email.setSmtpPort(465);//设置SMTP协议端口
            email.setAuthenticator(new DefaultAuthenticator("aaa.aa@kakaocorp.com", "12345678"));//登陆邮件服务器的用户名和密码
            email.setSSLOnConnect(true);
            try {
                email.setSubject("激活码查收...");//邮件标题
                email.setMsg(adminUser.getEnNm()+"请用密码登录:"+defPwd);//邮件内容
                email.setFrom("sunny.wu@kakaocorp.com");//发送人
                email.addTo("sunny.wu@kakaocorp.com");//收件人
                email.send();
                Logger.debug("邮件发送成功!");
            } catch (EmailException e) {
                e.printStackTrace();
                Logger.error("发送邮件错误"+e);
            }
            Logger.error("用户信息:"+adminUser);
            adminUserService.insertUser(adminUser);
            return ok();
        }
    }

    /**
     * 用户登录
     * @return views
     */
    public Result adminUserLogin() {
        String lang = request().getQueryString("lang");
        return ok(views.html.adminuser.userlogin.render());
    }

    /**
     * 用户登录验证
     * @return
     */
    public Result authLogin() {
        JsonNode json = request().body().asJson();
        String loginIp = request().remoteAddress();
        AdminUser adu  = Json.fromJson(json, AdminUser.class);//查询条件adu
//        Logger.error("登录条件:"+adu.toString());
        AdminUser adminUser = adminUserService.getUserBy(adu);
        if (null!=adminUser && !"".equals(adminUser)) {
            adminUser.setActiveYN("Y");
            adminUser.setLastLoginIp(loginIp);
            adminUser.setLastLoginDt(new Timestamp(new Date().getTime()));
            adminUser.setStatus("Y");
            User user = new User (adminUser.getUserId(), null,null, null , User_Type.withName(adminUser.getUserType()),
                    Option.apply(adminUser.getUserId()), Option.apply(adminUser.getEnNm()), Option.apply(adminUser.getChNm()), Option.apply(adminUser.getEmail()),
                    Option.apply(adminUser.getUserType()), null, null, null,
                    null, null, null,null, null,null);
//            user.chNm() = adminUser.getChNm();
//            Logger.error("更新后:"+adminUser.toString());
            adminUserService.updateUser(adminUser);
            //登录成功后用户信息存在cache 和 session中
            Cache.set(adminUser.getEnNm().trim(), user);
            session().put("username",adminUser.getEnNm().trim());
            return ok("登录成功!");
        }
        else return ok("登录失败!");
    }

    public Result summary() {
        String username = session().get("username");
        User user = (User) Cache.get(username.trim());
        return ok(views.html.summary.summary.render("cn",user));
    }

    /**
     * 用户个人中心
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserInfo(String lang) {
        return ok(views.html.adminuser.userinfo.render(lang, (User) ctx().args.get("user")));
    }


    /**
     * 用户列表弹窗(发放优惠券选择用户功能)
     * @return Result
     */
    public Result addIDUserPop() {
        List<ID> idList = idService.getAllID();
        String IMAGE_URL = "http://img.hanmimei.com";
        return ok(views.html.coupon.coupaddPop.render(idList,IMAGE_URL));
    }
}

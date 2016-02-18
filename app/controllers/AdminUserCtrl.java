package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.AdminUser;
import entity.ID;
import entity.User;
import entity.User_Type;
import filters.UserAuth;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import play.Configuration;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Sunny Wu on 16/1/28.
 * kakao china.
 */
@SuppressWarnings("unchecked")
public class AdminUserCtrl extends Controller {

    @Inject
    private AdminUserService adminUserService;

    @Inject
    private IDAdminService idAdminService;

    @Inject
    private IDService idService;

    @Inject
    Configuration configuration;

    /**
     * 添加管理员用户
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserCreate(String lang) {
        //用户的角色从配置文件中读取
        Map<String,String> userTypeList = new ObjectMapper().convertValue(configuration.getObject("role"),HashMap.class);
//        Map<String,String> menus = new ObjectMapper().convertValue(configuration.getObject("menu"),HashMap.class);
//        Logger.error(userTypeList.toString());
        return ok(views.html.adminuser.adduser.render(lang, userTypeList, (User) ctx().args.get("user")));
    }

    /**
     * 保存用户信息(用户注册)
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserSave(){
        JsonNode json = request().body().asJson();
        AdminUser adminUser  = Json.fromJson(json, AdminUser.class);
        adminUser.setEmail(adminUser.getEmail()+"@kakaocorp.com");
        //先验证是否已注册
        AdminUser adu  = adminUserService.getUserBy(adminUser);
//        Logger.error("用户信息:"+adu);
        if (null!=adu && !"".equals(adu)) {
            return ok("该用户已注册!");
        } else {
            String defPwd = adminUser.CreateCode(8);//给邮箱发送的8位随机默认密码
            String regIp = request().remoteAddress();
            adminUser.setPasswd("11111111");
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
//            Logger.error("用户信息:"+adminUser);
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
//        Logger.error("语言"+lang);
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
        Logger.error("login user:"+adu.toString());
        Logger.debug("login user:"+adminUser.toString());
        //登录后返回信息
        String data = "";
        if (null!=adminUser && !"".equals(adminUser)) {
            adminUser.setActiveYN("Y");
            adminUser.setLastLoginIp(loginIp);
            adminUser.setLastLoginDt(new Timestamp(new Date().getTime()));
            adminUser.setStatus("Y");
//            Logger.error("更新后:"+adminUser.toString());
            adminUserService.updateUser(adminUser);
            User user = new User (adminUser.getUserId(), null,null, null , User_Type.ADMIN(),
                    Option.apply(adminUser.getUserId()), Option.apply(adminUser.getEnNm()), Option.apply(adminUser.getChNm()), Option.apply(adminUser.getEmail()),
                    Option.apply(adminUser.getUserType()), null, null, null,
                    null, null, null,null, null,null);
            //登录成功后用户信息存在cache 和 session中
            Cache.set(adminUser.getEnNm().trim(), user);
            session().put("username",adminUser.getEnNm().trim());
            Logger.debug("user login... "+user.enNm());
            if (adminUser.getUserType().equals("SADMIN") || adminUser.getUserType().equals("SUPPLIER") || adminUser.getUserType().equals("TRANSLATION") || adminUser.getUserType().equals("AUDIT")) {
                data = "供应商登录";
            }
            else data = "登录成功";
        }
        else {
            Logger.debug("not admin user");
            data = "登录失败";
        }
        return ok(data);
    }

    /**
     * 首页
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result summary(String lang) {
        String username = session().get("username");
        User user = (User) Cache.get(username.trim());
        return ok(views.html.summary.summary.render(lang, user));
    }

    /**
     * 用户个人中心
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserInfo(String lang) {
        Map<String,String> userTypeList = new ObjectMapper().convertValue(configuration.getObject("role"),HashMap.class);
        return ok(views.html.adminuser.userinfo.render(lang, userTypeList, (User) ctx().args.get("user")));
    }

    /**
     * 修改用户信息(中文名)
     * @param lang 语言
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserUpdate(String lang) {
        JsonNode json = request().body().asJson();
        AdminUser adu  = Json.fromJson(json, AdminUser.class);
        //通过userId 查询用户信息
        AdminUser adminUser = adminUserService.getUserBy(adu);
        //更新用户信息
        adminUser.setChNm(adu.getChNm());
        Boolean bool = adminUserService.updateUser(adminUser);
        User user = new User (adminUser.getUserId(), null,null, null , User_Type.ADMIN(),
                Option.apply(adminUser.getUserId()), Option.apply(adminUser.getEnNm()), Option.apply(adminUser.getChNm()), Option.apply(adminUser.getEmail()),
                Option.apply(adminUser.getUserType()), null, null, null,
                null, null, null,null, null,null);
        //更新cache 和 session中用户信息
        Cache.set(adminUser.getEnNm().trim(), user);
        session().put("username",adminUser.getEnNm().trim());
        if (bool) {
            return ok("修改成功");
        }
        else return ok("修改失败");
    }

    /**
     * 修改密码
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserChgPwd(String lang) {
        return ok(views.html.adminuser.userpwdchg.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 保存新密码
     * @param lang 语言
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserResetPwd(String lang) {
        JsonNode  json = request().body().asJson();
        if (json.has("adminUser")) {
            JsonNode aduNode = json.findValue("adminUser");
            AdminUser adu  = Json.fromJson(aduNode, AdminUser.class);
            AdminUser adminUser = adminUserService.getUserBy(adu);
            //根据用户名和密码查询用户
            if (null!=adminUser && !"".equals(adminUser)) {
                if (json.has("newPwd")) {
                    String newPwd = json.findValue("newPwd").asText();
                    adminUser.setPasswd(newPwd);
                    adminUser.setLastPwdChgDt(new Timestamp(new Date().getTime()));
                    //修改密码
                    adminUserService.chgPwd(adminUser);
                    return ok("密码修改成功");
                }
            } else return ok("密码错误");
        }
        return ok("false");
    }

    /**
     * 用户查询
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserSearch(String lang) {
        List<AdminUser> adminUserList = adminUserService.getAllUsers();
        return ok(views.html.adminuser.usersearch.render(lang, adminUserList, (User) ctx().args.get("user")));
    }

    /**
     * 由用户id删除一条用户
     * @param lang 语言
     * @param id 用户id
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserDelUser(String lang, Long id) {
        adminUserService.delUserById(id);
        return ok("删除成功");
    }


    /**
     * 用户列表弹窗(发放优惠券选择用户功能)
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result addIDUserPop() {
        List<ID> idList = idService.getAllID();
        String IMAGE_URL = "http://img.hanmimei.com";
        return ok(views.html.coupon.coupaddPop.render(idList,IMAGE_URL));
    }
}

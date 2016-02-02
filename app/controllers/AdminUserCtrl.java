package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.AdminUser;
import entity.ID;
import entity.User;
import entity.User_Type;
import filters.UserAuth;
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
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;


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
        //用户的角色从配置文件中读取
//        Properties prop = new Properties();
//        InputStream in = getClass().getResourceAsStream("/conf/user_type.properties");
//        Map<String, Object> ut = new HashMap<>();
//        try {
//            prop.load(in);
//            Set keyValue = prop.keySet();
//            for(Iterator it = keyValue.iterator();it.hasNext();) {
//                String key = (String)it.next();
//                String value = prop.getProperty(key);
//                ut.put(key,value);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Logger.error("读取资源文件时出错!");
//        }
//        Logger.error("用户类型:"+ut);
        return ok(views.html.adminuser.adduser.render(lang, (User) ctx().args.get("user")));
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
            adminUser.setPasswd(defPwd);
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
//            Logger.error("更新后:"+adminUser.toString());
            adminUserService.updateUser(adminUser);
            User user = new User (adminUser.getUserId(), adminUser.getEnNm(), null, null , User_Type.withName(adminUser.getUserType()),
                    Option.apply(adminUser.getUserId()), Option.apply(adminUser.getEnNm()), Option.apply(adminUser.getChNm()), Option.apply(adminUser.getEmail()),
                    Option.apply(adminUser.getUserType()), null, null, null,
                    null, null, null,null, null,null);
            //登录成功后用户信息存在cache 和 session中
            Cache.set(adminUser.getEnNm().trim(), user);
            session().put("username",adminUser.getEnNm().trim());
            Logger.debug("user login... "+user.enNm());
            return ok("登录成功!");
        }
        else {
            Logger.debug("not admin user");
            return ok("登录失败!");
        }
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
        return ok(views.html.adminuser.userinfo.render(lang, (User) ctx().args.get("user")));
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
        User user = new User (adminUser.getUserId(), null,null, null , User_Type.withName(adminUser.getUserType()),
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

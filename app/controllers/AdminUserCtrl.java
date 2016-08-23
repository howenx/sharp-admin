package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import domain.pingou.PinActivity;
import filters.UserAuth;
import play.Configuration;
import play.Logger;
import play.cache.Cache;
import play.data.Form;
import play.libs.Json;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.Option;
import service.AdminSupplierService;
import service.AdminUserService;
import service.IDService;
import service.PingouService;
import util.SysParCom;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.*;


/**
 * Created by Sunny Wu on 16/1/28.
 * kakao china.
 */
@SuppressWarnings("unchecked")
public class AdminUserCtrl extends Controller {

    @Inject
    private AdminUserService adminUserService;

    @Inject
    private AdminSupplierService adminSupplierService;

    @Inject
    private IDService idService;

    @Inject
    private PingouService pingouService;

    @Inject
    private Configuration configuration;

    @Inject
    private MailerClient mailerClient;

//    public static final Timeout TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);

    private static final int PAGE_SIZE = 10;

    /**
     * 添加管理员用户
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserCreate(String lang) {
        //用户的角色从配置文件中读取
        Map<String,String> userTypeList = new HashMap<>();
        Map<String,String> userTypeList1 = new ObjectMapper().convertValue(configuration.getObject("role1"),HashMap.class);
        Map<String,String> userTypeList2 = new ObjectMapper().convertValue(configuration.getObject("role2"),HashMap.class);
        Map<String,String> userTypeList3 = new ObjectMapper().convertValue(configuration.getObject("role3"),HashMap.class);
        Map<String,String> userTypeList4 = new ObjectMapper().convertValue(configuration.getObject("role4"),HashMap.class);
        for(Map.Entry<String, String> ut:userTypeList1.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList2.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList3.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList4.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
//        Map<String,String> menus = new ObjectMapper().convertValue(configuration.getObject("menu"),HashMap.class);
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
        Form<AdminUser> adminUserForm = Form.form(AdminUser.class).bind(json);
        //数据验证
        if (adminUserForm.hasErrors()) {
            Logger.error("adminUser 表单数据有误.....");
            return badRequest();
        }
        adminUser.setEmail(adminUser.getEmail()+"@kakaocorp.com");
        //先验证是否已注册
        AdminUser adu  = adminUserService.getUserBy(adminUser);
        if (null!=adu && !"".equals(adu.toString())) {
            return ok("该用户已注册!");
        } else {
            String defPwd = AdminUser.CreateCode(8);//给邮箱发送的8位随机默认密码
            String regIp = request().remoteAddress();
//            adminUser.setPasswd("11111111");
            adminUser.setPasswd(defPwd);
            adminUser.setRegIp(regIp);
            adminUser.setActiveYN("N");
            adminUser.setStatus("N");
            adminUser.setLastLoginIp(regIp);

            Email email = new Email()
                    .setSubject("KakaoGift后台密码查收")
                    .setFrom("developer@hanmimei.com")
                    .addTo(adminUser.getEmail())
                    .setBodyText("A text message")
                    .setBodyHtml("<html><body><p>KakaoGift后台, 用户名:"+adminUser.getEnNm()+", 请用密码登录:"+defPwd+"</p></body></html>");
            mailerClient.send(email);
            Logger.debug("邮件发送成功!");

//            MultiPartEmail email = new MultiPartEmail();
//            email.setHostName("smtp.hanmimei.com");//设置邮件服务器smtp.aliyun.com
//            email.setSmtpPort(465);//设置SMTP协议端口
//            email.setAuthenticator(new DefaultAuthenticator("developer@hanmimei.com", "DAumkakao123"));//登陆邮件服务器的用户名和密码
//            email.setSSLOnConnect(true);
//            try {
//                email.setSubject("密码查收...");//邮件标题
//                email.setMsg(adminUser.getEnNm()+"请用密码登录:"+defPwd);//邮件内容
//                email.setFrom("developer@hanmimei.com");//发送人
//                email.addTo(adminUser.getEmail());//收件人
//                email.send();
//                Logger.debug("邮件发送成功!");
//            } catch (EmailException e) {
//                e.printStackTrace();
//                Logger.error("发送邮件错误"+e);
//            }
            adminUserService.insertUser(adminUser);
            //如果添加的是供应商,admin_supplier表添加一条数据
            if (adminUser.getUserType().contains("SUPPLIER")) {
                AdminUser ad = adminUserService.getUserBy(adminUser);
                AdminSupplier adminSupplier = new AdminSupplier();
                adminSupplier.setUserId(ad.getUserId());
                adminSupplier.setSupplyMerch(adminUser.getEnNm());
                adminSupplier.setSupplyName(adminUser.getChNm());
                adminSupplierService.insertSupplier(adminSupplier);
            }
            return ok("保存成功");
        }
    }

    /**
     * 用户登录
     * @return views
     */
    public Result adminUserLogin() {
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
        AdminUser adminUser = adminUserService.getUserBy(adu);
        //登录后返回信息
        String data = "";
        if (null!=adminUser && !"".equals(adminUser.toString())) {
            adminUser.setActiveYN("Y");
            adminUser.setLastLoginIp(loginIp);
            adminUser.setLastLoginDt(new Timestamp(new Date().getTime()));
            adminUser.setStatus("Y");
            adminUserService.updateUser(adminUser);
            User user = new User (adminUser.getUserId(), adminUser.getEnNm(),null, null , User_Type.ADMIN(),
                    Option.apply(adminUser.getUserId()), Option.apply(adminUser.getEnNm()), Option.apply(adminUser.getChNm()), Option.apply(adminUser.getEmail()),
                    Option.apply(adminUser.getUserType()), null, null, null,
                    null, null, null,null, null,null);
            //登录成功后用户信息存在cache 和 session中
            Cache.set(adminUser.getEnNm().trim(), user);
            session().put("username",adminUser.getEnNm().trim());
            Logger.info("user login... "+user.enNm().get());
            Map<String,String> userTypeList1 = new ObjectMapper().convertValue(configuration.getObject("role1"),HashMap.class);
            Map<String,String> userTypeList2 = new ObjectMapper().convertValue(configuration.getObject("role2"),HashMap.class);
            Map<String,String> userTypeList3 = new ObjectMapper().convertValue(configuration.getObject("role3"),HashMap.class);
            Map<String,String> userTypeList4 = new ObjectMapper().convertValue(configuration.getObject("role4"),HashMap.class);
            //后台管理用户
            for(Map.Entry<String, String> ut:userTypeList1.entrySet()) {
                if (adminUser.getUserType().contains(ut.getKey())) {
                    data = "1";
                }
            }
            //供应系统用户
            for(Map.Entry<String, String> ut:userTypeList2.entrySet()) {
                if (adminUser.getUserType().contains(ut.getKey())) {
                    data = "2";
                }
            }
            //其他类型用户
            for(Map.Entry<String, String> ut:userTypeList3.entrySet()) {
                if (adminUser.getUserType().contains(ut.getKey())) {
                    data = "2";
                }
            }
            //Coupon系统用户
            for(Map.Entry<String, String> ut:userTypeList4.entrySet()) {
                if (adminUser.getUserType().contains(ut.getKey())) {
                    data = "2";
                }
            }
        }
        else {
            Logger.debug("not admin user");
            data = "login_error";
        }
        return ok(data);
    }

    /**
     * 首页
     * @param lang 语言
     * @return views
     */
//    @Security.Authenticated(UserAuth.class)
//    public Result summary(String lang) {
//        String username = session().get("username");
//        User user = (User) Cache.get(username.trim());
//        return ok(views.html.summary.summary.render(lang, user));
//    }

    /**
     * 用户个人中心
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserInfo(String lang) {
        Map<String,String> userTypeList = new HashMap<>();
        Map<String,String> userTypeList1 = new ObjectMapper().convertValue(configuration.getObject("role1"),HashMap.class);
        Map<String,String> userTypeList2 = new ObjectMapper().convertValue(configuration.getObject("role2"),HashMap.class);
        Map<String,String> userTypeList3 = new ObjectMapper().convertValue(configuration.getObject("role3"),HashMap.class);
        Map<String,String> userTypeList4 = new ObjectMapper().convertValue(configuration.getObject("role4"),HashMap.class);
        for(Map.Entry<String, String> ut:userTypeList1.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList2.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList3.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList4.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
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
        if (null != adu.getUserType()) {
            adminUser.setUserType(adu.getUserType());
        }
        Boolean bool = adminUserService.updateUser(adminUser);
        User user = new User (adminUser.getUserId(), null,null, null , User_Type.ADMIN(),
                Option.apply(adminUser.getUserId()), Option.apply(adminUser.getEnNm()), Option.apply(adminUser.getChNm()), Option.apply(adminUser.getEmail()),
                Option.apply(adminUser.getUserType()), null, null, null,
                null, null, null,null, null,null);
        //(修改登录用户)更新cache 和 session中用户信息
        if (((User) ctx().args.get("user")).userId().get().equals(adu.getUserId())) {
            Cache.set(adminUser.getEnNm().trim(), user);
            session().put("username",adminUser.getEnNm().trim());
        }
        if (bool) {
            return ok("修改成功");
        }
        else return ok("修改失败");
    }

    /**
     *
     * @param lang 语言               Added By Sunny.Wu 2016.05.23
     * @param id 用户id
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result adminUserDetail(String lang, Long id) {
        AdminUser adminUser = new AdminUser();
        adminUser.setUserId(id);
        adminUser  = adminUserService.getUserBy(adminUser);
        Map<String,String> userTypeList = new HashMap<>();
        Map<String,String> userTypeList1 = new ObjectMapper().convertValue(configuration.getObject("role1"),HashMap.class);
        Map<String,String> userTypeList2 = new ObjectMapper().convertValue(configuration.getObject("role2"),HashMap.class);
        Map<String,String> userTypeList3 = new ObjectMapper().convertValue(configuration.getObject("role3"),HashMap.class);
        Map<String,String> userTypeList4 = new ObjectMapper().convertValue(configuration.getObject("role4"),HashMap.class);
        for(Map.Entry<String, String> ut:userTypeList1.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList2.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList3.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList4.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        return ok(views.html.adminuser.userdetail.render(adminUser, lang, userTypeList, (User) ctx().args.get("user")));
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
            if (null!=adminUser && !"".equals(adminUser.toString())) {
                if (json.has("newPwd") && json.findValue("newPwd").asText().length()>=8) {
                    String newPwd = json.findValue("newPwd").asText();
                    adminUser.setPasswd(newPwd);
                    adminUser.setLastPwdChgDt(new Timestamp(new Date().getTime()));
                    //修改密码
                    adminUserService.chgPwd(adminUser);
                    return ok("密码修改成功");
                } else return badRequest();
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
        Map<String,String> userTypeList = new HashMap<>();
        Map<String,String> userTypeList1 = new ObjectMapper().convertValue(configuration.getObject("role1"),HashMap.class);
        Map<String,String> userTypeList2 = new ObjectMapper().convertValue(configuration.getObject("role2"),HashMap.class);
        Map<String,String> userTypeList3 = new ObjectMapper().convertValue(configuration.getObject("role3"),HashMap.class);
        Map<String,String> userTypeList4 = new ObjectMapper().convertValue(configuration.getObject("role4"),HashMap.class);
        for(Map.Entry<String, String> ut:userTypeList1.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList2.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList3.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList4.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        return ok(views.html.adminuser.usersearch.render(lang, adminUserList, userTypeList, (User) ctx().args.get("user")));
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
        return ok(views.html.coupon.coupaddPop.render(idList, SysParCom.IMAGE_URL));
    }

    /**
     * 成功收获的团长用户列表弹窗      Added By Sunny.Wu 2016.07.27
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result addMasterPop() {
        List<PinActivity> pinActivityList = pingouService.getSuccActivityMaster();
        List<ID> idList = new ArrayList<>();
        for(PinActivity pinActivity : pinActivityList) {
            Integer userId = pinActivity.getMasterUserId().intValue();
            ID id = idService.getID(userId);
            idList.add(id);
        }
//        List<ID> idList = idService.getAllID();
        return ok(views.html.coupon.coupaddPop.render(idList, SysParCom.IMAGE_URL));
    }

    /**
     * APP用户查询
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result appUserSearch(String lang) {
        ID id = new ID();
        id.setPageSize(-1);
        id.setOffset(-1);
        int countNum = idService.getAllID().size();//取总数
        int pageCount = countNum/PAGE_SIZE;//共分几页
        if (countNum%PAGE_SIZE!=0) {
            pageCount = countNum/PAGE_SIZE+1;
        }
        id.setPageSize(PAGE_SIZE);
        id.setOffset(0);
        return ok(views.html.adminuser.appusersearch.render(lang, PAGE_SIZE, countNum, pageCount, idService.getIDPage(id),SysParCom.IMAGE_URL, (User) ctx().args.get("user")));
    }

    /**
     * APP用户ajax分页查询
     * @param lang 语言
     * @param pageNum 请求页数
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result appUserSearchAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        ID id = Json.fromJson(json,ID.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*PAGE_SIZE;
            id.setPageSize(-1);
            id.setOffset(-1);
            //取总数
            int countNum = idService.getIDPage(id).size();
            //共分几页
            int pageCount = countNum/PAGE_SIZE;

            if(countNum%PAGE_SIZE!=0){
                pageCount = countNum/PAGE_SIZE+1;
            }
            id.setPageSize(PAGE_SIZE);
            id.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",idService.getIDPage(id));
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",PAGE_SIZE);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

}

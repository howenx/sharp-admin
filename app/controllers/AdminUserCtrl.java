package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import filters.UserAuth;
import modules.LevelFactory;
import modules.NewScheduler;
import play.Configuration;
import play.Logger;
import play.api.libs.Codecs;
import play.cache.Cache;
import play.data.Form;
import play.libs.Json;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.Option;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import service.AdminUserService;
import service.IDService;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by Sunny Wu on 16/1/28.
 * kakao china.
 */
@SuppressWarnings("unchecked")
public class AdminUserCtrl extends Controller {

    @Inject
    private AdminUserService adminUserService;

    @Inject
    private IDService idService;

    @Inject
    Configuration configuration;

    @Inject
    private NewScheduler newScheduler;

    @Inject
    private LevelFactory levelFactory;

    @Inject
    private ActorSystem system;

    @Inject
    MailerClient mailerClient;

    public static final Timeout TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);

    private int pageSize = 3;

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
        for(Map.Entry<String, String> ut:userTypeList1.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList2.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList3.entrySet()) {
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
            adminUser.setPasswd(defPwd);
            adminUser.setRegIp(regIp);
            adminUser.setActiveYN("N");
            adminUser.setStatus("N");
            adminUser.setLastLoginIp(regIp);

            Email email = new Email()
                    .setSubject("密码查收")
                    .setFrom("developer@hanmimei.com")
                    .addTo(adminUser.getEmail())
                    .setBodyText("A text message")
                    .setBodyHtml("<html><body><p>"+adminUser.getEnNm()+"请用密码登录:"+defPwd+"</p></body></html>");
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
            return ok("保存成功");
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
        AdminUser adminUser = adminUserService.getUserBy(adu);
//        Logger.debug("login user:"+adu.toString());
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
            Logger.info("user login... "+user.enNm());
            Map<String,String> userTypeList1 = new ObjectMapper().convertValue(configuration.getObject("role1"),HashMap.class);
            Map<String,String> userTypeList2 = new ObjectMapper().convertValue(configuration.getObject("role2"),HashMap.class);
            Map<String,String> userTypeList3 = new ObjectMapper().convertValue(configuration.getObject("role3"),HashMap.class);
            for(Map.Entry<String, String> ut:userTypeList1.entrySet()) {
                if (adminUser.getUserType().equals(ut.getKey())) {
                    data = "后台用户登录成功";
                }
            }
            for(Map.Entry<String, String> ut:userTypeList2.entrySet()) {
                if (adminUser.getUserType().equals(ut.getKey())) {
                    data = "供应商登录成功";
                }
            }
            for(Map.Entry<String, String> ut:userTypeList3.entrySet()) {
                if (adminUser.getUserType().equals(ut.getKey())) {
                    data = "其他用户登录";
                }
            }
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
        for(Map.Entry<String, String> ut:userTypeList1.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList2.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList3.entrySet()) {
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
        for(Map.Entry<String, String> ut:userTypeList1.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList2.entrySet()) {
            userTypeList.put(ut.getKey(),ut.getValue());
        }
        for(Map.Entry<String, String> ut:userTypeList3.entrySet()) {
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
        return ok(views.html.coupon.coupaddPop.render(idList,ThemeCtrl.IMAGE_URL));
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
        int pageCount = countNum/pageSize;//共分几页
        if (countNum%pageSize!=0) {
            pageCount = countNum/pageSize+1;
        }
        id.setPageSize(pageSize);
        id.setOffset(0);
        return ok(views.html.adminuser.appusersearch.render(lang, pageSize, countNum, pageCount, idService.getIDPage(id),ThemeCtrl.IMAGE_URL, (User) ctx().args.get("user")));
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
            int offset = (pageNum-1)*pageSize;
            id.setPageSize(-1);
            id.setOffset(-1);
            //取总数
            int countNum = idService.getIDPage(id).size();
            //共分几页
            int pageCount = countNum/pageSize;

            if(countNum%pageSize!=0){
                pageCount = countNum/pageSize+1;
            }
            id.setPageSize(pageSize);
            id.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",idService.getIDPage(id));
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",pageSize);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 处理系统启动时候去做第一次请求,完成对定时任务的执行
     *
     * @return string
     */
    public Result getFirstApp(String cipher) {
        if (Codecs.md5("hmm-100901".getBytes()).equals(cipher)) {
            List<Persist> persists;
            try {
                persists = levelFactory.iterator();
                if (persists != null && persists.size() > 0) {
                    Logger.info("遍历所有持久化schedule---->\n" + persists);
                    for (Persist p : persists) {

                        ActorSelection sel = system.actorSelection(p.getActorPath());
                        Future<ActorRef> fut = sel.resolveOne(TIMEOUT);
                        ActorRef ref = Await.result(fut, TIMEOUT.duration());

                        if (p.getType().equals("scheduleOnce")){
                            Long time = p.getDelay() - (new Date().getTime() - p.getCreateAt().getTime());
                            Logger.info("重启后scheduleOnce执行时间---> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime()+time)));
                            if (time > 0) {
                                newScheduler.scheduleOnce(Duration.create(time, TimeUnit.MILLISECONDS), ref, p.getMessage());
                            } else {
                                levelFactory.delete(p.getMessage());
                                system.actorSelection(p.getActorPath()).tell(p.getMessage(), ActorRef.noSender());
                            }
                        }else if (p.getType().equals("schedule")){
                            newScheduler.schedule(Duration.create(p.getInitialDelay(), TimeUnit.MILLISECONDS),Duration.create(p.getDelay(), TimeUnit.MILLISECONDS), ref, p.getMessage());
                            Logger.info("重启后schedule执行---> 每隔 " + Duration.create(p.getDelay(), TimeUnit.MILLISECONDS).toMinutes()+" 分钟执行一次");
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return notFound("error");
            }
            return ok("success");
        } else throw new NullPointerException(cipher);
    }
}

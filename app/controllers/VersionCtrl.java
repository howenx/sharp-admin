package controllers;

import actor.StyleVersionDeployActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesRequest;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import domain.AdminUser;
import domain.User;
import domain.VersionVo;
import filters.UserAuth;
import middle.VersionMiddle;
import play.Configuration;
import play.Logger;
import play.data.Form;
import play.libs.Akka;
import play.libs.Comet;
import play.libs.F;
import play.libs.Json;
import play.mvc.*;
import scala.concurrent.duration.Duration;
import scala.concurrent.impl.Promise;
import service.AdminUserService;
import service.ItemService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static play.libs.Json.newObject;

/**
 * 版本管理
 * Created by howen on 16/1/29.
 */
public class VersionCtrl extends Controller {

    @Inject
    private ItemService itemService;

    @Inject
    private AdminUserService idService;

    @Inject
    private VersionMiddle versionMiddle;

    @Inject
    private Configuration configuration;

    @Inject
    private ActorSystem system;


    @Security.Authenticated(UserAuth.class)
    public Result release() {
        return ok(views.html.versioning.release.render("cn", (User) ctx().args.get("user")));
    }

    /**
     * 版本历史页面
     *
     * @return result
     */
    @Security.Authenticated(UserAuth.class)
    public Result releaseList() {
        VersionVo versionVo = new VersionVo();
        versionVo.setProductType("A");

        List<VersionVo> androidVersion = dealVersionVo(versionVo);

        versionVo.setProductType("I");

        List<VersionVo> iosVersion = dealVersionVo(versionVo);

        return ok(views.html.versioning.releaselist.render("cn", (User) ctx().args.get("user"), androidVersion, iosVersion));
    }

    /**
     * 处理List<VersionVo>查询结果
     *
     * @param versionVo versionVo
     * @return List VersionVo
     */
    private List<VersionVo> dealVersionVo(VersionVo versionVo) {
        List<VersionVo> androidVersion = itemService.getVersioning(versionVo);
        AdminUser adminUser = new AdminUser();
        return androidVersion.stream().map(av -> {
            adminUser.setUserId(av.getAdminUserId());
            AdminUser d = idService.getUserBy(adminUser);
            Logger.error("发布人:"+d.toString());
            if (d.getChNm().isEmpty())
                av.setAdminUserNm(d.getEnNm());
            else av.setAdminUserNm(d.getChNm());
            return av;
        }).collect(Collectors.toList());

    }

    /**
     * 提交发布版本表单
     *
     * @return result
     */
    @Security.Authenticated(UserAuth.class)
    @BodyParser.Of(value = BodyParser.MultipartFormData.class, maxLength = 200 * 1024 * 1024)
    public Result publicRelease() {
        try {
            Http.MultipartFormData body = request().body().asMultipartFormData();

            List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();

            Form<VersionVo> userForm = Form.form(VersionVo.class).bindFromRequest();

            if (userForm.hasErrors()){
                Logger.error("表单提交错误:"+userForm.errorsAsJson().toString());
                return ok("error");
            }else {
                User user = (User) ctx().args.get("user");

                VersionVo versionVo = userForm.get();

                versionVo.setAdminUserId(Long.valueOf(user.userId().get().toString()));

                versionMiddle.publicRelease(versionVo, fileParts.get(0).getFile());

                return ok("success");
            }
        }catch (Exception ex){
            Logger.error("发布版本出错:"+ex.getMessage());
            return badRequest("error");
        }
    }

    @Security.Authenticated(UserAuth.class)
    public Result apiReleasePage(){
        return ok(views.html.versioning.apiRelease.render("cn",(User) ctx().args.get("user")));

    }

    @Security.Authenticated(UserAuth.class)
    @BodyParser.Of(value = BodyParser.MultipartFormData.class, maxLength = 200 * 1024 * 1024)
    public Result apiReleasePublic(){
        try {
            Http.MultipartFormData body = request().body().asMultipartFormData();

            List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();

            Form<VersionVo> userForm = Form.form(VersionVo.class).bindFromRequest();

            if (userForm.hasErrors()){
                Logger.error("表单提交错误:"+userForm.errorsAsJson().toString());
                return ok("error");
            }else {
                User user = (User) ctx().args.get("user");

                VersionVo versionVo = userForm.get();

                versionVo.setAdminUserId(Long.valueOf(user.userId().get().toString()));

                versionMiddle.apiPublicRelease(versionVo, fileParts.get(0).getFile());

                return ok("success");
            }
        }catch (Exception ex){
            Logger.error("发布版本出错:"+ex.getMessage());
            return badRequest("error");
        }
    }

    /**
     * 获取API版本历史        Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public  Result APIVersionList(String lang){

//        VersionVo versionVo = new VersionVo();
//        versionVo.setProductType("A");
//
//        List<VersionVo> androidVersion = dealVersionVo(versionVo);
//
//        versionVo.setProductType("I");
//
//        List<VersionVo> iosVersion = dealVersionVo(versionVo);

        return ok(views.html.versioning.APIVersionList.render(lang, (User) ctx().args.get("user")));
    }




    /**
     * 刷新CDN
     * @return result
     */
    @Security.Authenticated(UserAuth.class)
    public Result refreshCdn() {
        try {
            String endpoint = configuration.getString("cdn.endpoint");
            String key = configuration.getString("oss.access_key");
            String secret = configuration.getString("oss.access_secret");

            DefaultProfile.addEndpoint(endpoint, endpoint, "Cdn", "cdn.aliyuncs.com");
            IClientProfile profile = DefaultProfile.getProfile(endpoint, key, secret);
            IAcsClient client = new DefaultAcsClient(profile);


            RefreshObjectCachesRequest describeCdnServiceRequest = new RefreshObjectCachesRequest();

            describeCdnServiceRequest.setAcceptFormat(FormatType.JSON); //指定api返回格式
            describeCdnServiceRequest.setRegionId(endpoint);
            describeCdnServiceRequest.setObjectPath(configuration.getString("cdn.directory"));
            describeCdnServiceRequest.setObjectType("Directory");

            RefreshObjectCachesResponse describeCdnServiceResponse = client.getAcsResponse(describeCdnServiceRequest);

            Logger.info("刷新cdn:" + describeCdnServiceResponse.getRefreshTaskId());
            return ok("success");
        } catch (ClientException ex) {
            Logger.error("刷新CDN出错:" + ex.getMessage());
            ex.printStackTrace();
            return badRequest("error");
        }
    }
}

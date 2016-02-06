package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.AdminUser;
import entity.User;
import entity.VersionVo;
import filters.UserAuth;
import middle.VersionMiddle;
import modules.OSSClientProvider;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.mvc.*;
import service.AdminUserService;
import service.ItemService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static play.libs.Json.newObject;

/**
 * 版本管理
 * Created by howen on 16/1/29.
 */
public class VersionCtrl extends Controller {


    private ItemService itemService;

    private AdminUserService idService;


    private VersionMiddle versionMiddle;

    private Configuration configuration;

    private OSSClientProvider oss_client;

    @Inject
    public VersionCtrl(ItemService itemService,AdminUserService idService, Configuration configuration, OSSClientProvider oss_client ){
        this.itemService = itemService;
        this.configuration = configuration;
        this.oss_client = oss_client;
        this.idService = idService;
        versionMiddle = new VersionMiddle(itemService,configuration,oss_client);
    }

    @Security.Authenticated(UserAuth.class)
    public Result release(){
        return ok(views.html.versioning.release.render("cn",(User) ctx().args.get("user")));
    }

    @Security.Authenticated(UserAuth.class)
    public Result releaseList(){
        VersionVo versionVo = new VersionVo();
        versionVo.setProductType("A");
        List<VersionVo> androidVersion = itemService.getVersioning(versionVo);
        AdminUser adminUser = new AdminUser();
        androidVersion =androidVersion.stream().map(av->{
            adminUser.setUserId(av.getAdminUserId());
            AdminUser d = idService.getUserBy(adminUser);
            if (d.getChNm().isEmpty())
            av.setAdminUserNm(d.getEnNm());
            else av.setAdminUserNm(d.getChNm());
            return av;
        }).collect(Collectors.toList());


        versionVo.setProductType("I");
        List<VersionVo> iosVersion = itemService.getVersioning(versionVo);
        iosVersion =iosVersion.stream().map(ios->{
            adminUser.setUserId(ios.getAdminUserId());
            AdminUser d = idService.getUserBy(adminUser);
            if (d.getChNm().isEmpty())
                ios.setAdminUserNm(d.getEnNm());
            else ios.setAdminUserNm(d.getChNm());
            return ios;
        }).collect(Collectors.toList());

        return ok(views.html.versioning.releaselist.render("cn",(User) ctx().args.get("user"),androidVersion,iosVersion));
    }

    @Security.Authenticated(UserAuth.class)
    @BodyParser.Of(value = BodyParser.MultipartFormData.class, maxLength = 200 * 1024 * 1024)
    public Result publicRelease() {
        ObjectNode result = newObject();
        try {
            Http.MultipartFormData body = request().body().asMultipartFormData();

            List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();

            Map<String,String[]> stringMap = body.asFormUrlEncoded();
            Map<String,String> map = new HashMap<>();
            stringMap.forEach((k, v) -> map.put(k,v[0]));

            Optional<JsonNode> json = Optional.ofNullable(Json.toJson(map));

            User user = (User) ctx().args.get("user");

            VersionVo versionVo = Json.fromJson(json.get(),VersionVo.class);

            versionVo.setAdminUserId(Long.valueOf(user.userId().get().toString()));

            versionMiddle.publicRelease(versionVo,fileParts.get(0).getFile());
            return ok("success");
        }catch (Exception ex){
            Logger.error("发布版本出错:"+ex.getMessage());
            return badRequest("error");
        }
    }
}
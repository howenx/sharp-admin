package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.User;
import entity.VersionVo;
import middle.VersionMiddle;
import modules.OSSClientProvider;
import play.Configuration;
import play.libs.Json;
import play.mvc.*;
import service.ItemService;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static play.libs.Json.newObject;

/**
 * 版本管理
 * Created by howen on 16/1/29.
 */
public class VersionCtrl extends Controller {


    private ItemService itemService;


    private VersionMiddle versionMiddle;

    private Configuration configuration;

    private OSSClientProvider oss_client;


    @Inject
    public VersionCtrl(ItemService itemService, Configuration configuration, OSSClientProvider oss_client ){
        this.itemService = itemService;
        this.configuration = configuration;
        this.oss_client = oss_client;
        versionMiddle = new VersionMiddle(itemService);
    }

    @Security.Authenticated(UserAuth.class)
    public Result release(){
        return ok(views.html.versioning.release.render("cn",(User) ctx().args.get("user")));
    }

    @Security.Authenticated(UserAuth.class)
    public Result releaseList(){
        return ok(views.html.versioning.releaselist.render("cn",(User) ctx().args.get("user")));
    }


    @Security.Authenticated(UserAuth.class)
    @BodyParser.Of(value = BodyParser.MultipartFormData.class, maxLength = 200 * 1024 * 1024)
    public Result publicRelease() throws FileNotFoundException {

        ObjectNode result = newObject();

        Http.MultipartFormData body = request().body().asMultipartFormData();

        List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();

        Map<String,String[]> stringMap = body.asFormUrlEncoded();
        Map<String,String> map = new HashMap<>();
        stringMap.forEach((k, v) -> map.put(k,v[0]));

        Optional<JsonNode> json = Optional.ofNullable(Json.toJson(map));

        Long userId = (Long) ctx().args.get("userId");

        VersionVo versionVo = Json.fromJson(json.get(),VersionVo.class);

        versionVo.setAdminUserId(userId);

        versionMiddle.publicRelease(versionVo,fileParts.get(0).getFile());

        return ok("success");
    }
}

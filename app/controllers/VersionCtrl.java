package controllers;

import domain.AdminUser;
import domain.User;
import domain.VersionVo;
import filters.UserAuth;
import middle.VersionMiddle;
import play.Configuration;
import play.Logger;
import play.data.Form;
import play.mvc.*;
import service.AdminUserService;
import service.ItemService;
import util.SysParCom;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static util.SysParCom.REDIS_SUBSCRIBE;
import static util.SysParCom.VERSION_PROJECT;

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
            Logger.error("发布人:" + d.toString());
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

            if (userForm.hasErrors()) {
                Logger.error("表单提交错误:" + userForm.errorsAsJson().toString());
                return ok("error");
            } else {
                User user = (User) ctx().args.get("user");

                VersionVo versionVo = userForm.get();

                versionVo.setAdminUserId(Long.valueOf(user.userId().get().toString()));

                versionMiddle.publicRelease(versionVo, fileParts.get(0).getFile());

                return ok("success");
            }
        } catch (Exception ex) {
            Logger.error("发布版本出错:" + ex.getMessage());
            return badRequest("error");
        }
    }

    @Security.Authenticated(UserAuth.class)
    public Result apiReleasePage() {
        return ok(views.html.versioning.apiRelease.render("cn", (User) ctx().args.get("user")));

    }

    @Security.Authenticated(UserAuth.class)
    @BodyParser.Of(value = BodyParser.MultipartFormData.class, maxLength = 200 * 1024 * 1024)
    public Result apiReleasePublic() {
        try {
            Http.MultipartFormData body = request().body().asMultipartFormData();

            List<Http.MultipartFormData.FilePart> fileParts = body.getFiles();

            Form<VersionVo> userForm = Form.form(VersionVo.class).bindFromRequest();

            if (userForm.hasErrors()) {
                Logger.error("表单提交错误:" + userForm.errorsAsJson().toString());
                return ok("error");
            } else {
                User user = (User) ctx().args.get("user");

                VersionVo versionVo = userForm.get();

                versionVo.setAdminUserId(Long.valueOf(user.userId().get().toString()));

                versionMiddle.apiPublicRelease(versionVo, fileParts.get(0).getFile());

                return ok("success");
            }
        } catch (Exception ex) {
            Logger.error("发布版本出错:" + ex.getMessage());
            return badRequest("error");
        }
    }

    /**
     * 获取版本历史        Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result APIVersionList(String lang) {
        List<String> project = VERSION_PROJECT;
        VersionVo versionVo = new VersionVo();
        List<VersionVo> versionList = dealVersionVo(versionVo);
        return ok(views.html.versioning.APIVersionList.render(lang, project, versionList, (User) ctx().args.get("user")));
    }

    @Security.Authenticated(UserAuth.class)
    public Result logview() {
        String socketUrl = null;
        if (configuration.getString("websocket.url") != null) {
            socketUrl = configuration.getString("websocket.url");
        }
        return ok(views.html.versioning.logview.render("cn", (User) ctx().args.get("user"), REDIS_SUBSCRIBE, socketUrl));
    }

    @Security.Authenticated(UserAuth.class)
    public WebSocket<String> logsocket() {

        return WebSocket.whenReady((in, out) -> {
            SysParCom.WEBSOCKET_OUT_LIST.add(out);
            in.onMessage(System.out::println);
            in.onClose(() -> {
                out.close();
                if (SysParCom.WEBSOCKET_OUT_LIST.contains(out)) {
                    SysParCom.WEBSOCKET_OUT_LIST.remove(out);
                }
                System.err.println("Disconnected");
            });
        });
    }
}

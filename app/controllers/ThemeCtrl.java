package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Theme;
import entity.User;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.ThemeService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * ThemeCtrl management.
 * Created by howen on 15/10/28.
 */
public class ThemeCtrl extends Controller {

    //每页固定的取数
    public static final int PAGE_SIZE = 4;

    //图片服务器url
    public static final String IMAGE_URL = play.Play.application().configuration().getString("image.server.url");

    //发布服务器url
    public static final String DEPLOY_URL = play.Play.application().configuration().getString("deploy.server.url");

    @Inject
    private ThemeService service;

    /**
     * 滚动条管理
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result slider(String lang) {
        flash("success", session("username"));
        return ok(views.html.theme.slider.render(lang,service.sliderAll(),IMAGE_URL,(User) ctx().args.get("user")));
    }

    /**
     * 主题录入
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result thadd(String lang) {
//        flash("success", session("username"));
        return ok(views.html.theme.thadd.render(lang,IMAGE_URL,(User) ctx().args.get("user")));
    }


    /**
     * 滚动条变更保存
     * @param lang  语言
     * @return  view
     */
    @Security.Authenticated(UserAuth.class)
    public Result sliderSave(String lang){
        JsonNode json = request().body().asJson();
        service.sliderSave(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }


    /**
     * 主题查询
     * @param lang 语言
     * @return  view
     */
    @Security.Authenticated(UserAuth.class)
    public Result thsearch(String lang){

        Theme theme =new Theme();

        theme.setPageSize(-1);
        theme.setOffset(-1);

        //取总数
        int countNum = service.themeSearch(theme).size();
        //共分几页
        int pageCount = countNum/PAGE_SIZE;

        if(countNum%PAGE_SIZE!=0){
            pageCount = countNum/PAGE_SIZE+1;
        }

        theme.setPageSize(PAGE_SIZE);
        theme.setOffset(1);

        return ok(views.html.theme.thsearch.render(lang,IMAGE_URL,PAGE_SIZE,countNum,pageCount,service.themeSearch(theme),(User) ctx().args.get("user")));
    }

    /**
     * ajax分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result thsearchAjax(String lang,int pageNum) {

        JsonNode json = request().body().asJson();

        Theme theme = Json.fromJson(json,Theme.class);

        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*PAGE_SIZE;

            theme.setPageSize(-1);
            theme.setOffset(-1);

            //取总数
            int countNum = service.themeSearch(theme).size();
            //共分几页
            int pageCount = countNum/PAGE_SIZE;

            if(countNum%PAGE_SIZE!=0){
                pageCount = countNum/PAGE_SIZE+1;
            }

            theme.setPageSize(PAGE_SIZE);
            theme.setOffset(offset);

            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",service.themeSearch(theme));
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

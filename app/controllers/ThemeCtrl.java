package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.Slider;
import play.Logger;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.ThemeService;

import javax.inject.Inject;
import entity.User;

import java.io.IOException;
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

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 商品查询弹窗
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemSearchPopup(String lang){
        return ok(views.html.theme.itemsearchPopup.render(lang));
    }

    /**
     * 主题查询
     * @param lang 语言
     * @return  view
     */
    @Security.Authenticated(UserAuth.class)
    public Result thsearch(String lang){

        Map<String,Integer> paramsMap = new HashMap<String,Integer>();
        paramsMap.put("pageSize", -1);
        paramsMap.put("offset", -1);

        //取总数
        int countNum = service.themeSearch(paramsMap).size();
        //共分几页
        int pageCount = countNum/PAGE_SIZE;

        if(countNum%PAGE_SIZE!=0){
            pageCount = countNum/PAGE_SIZE+1;
        }

        paramsMap.put("pageSize", PAGE_SIZE);
        paramsMap.put("offset", 1);

        return ok(views.html.theme.thsearch.render(lang,IMAGE_URL,PAGE_SIZE,countNum,pageCount,service.themeSearch(paramsMap),(User) ctx().args.get("user")));
    }

    @Security.Authenticated(UserAuth.class)
    public Result thsearchAjax(String lang,int pageNum){
        JsonNode json = request().body().asJson();
        Logger.error("当前页面数:"+json.toString());
        Logger.error("当前页面数:"+pageNum);

        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*PAGE_SIZE;
            Map<String,Integer> paramsMap = new HashMap<>();
            paramsMap.put("pageSize", PAGE_SIZE);
            paramsMap.put("offset", offset);

            if(null!=json.findValue("id") && "".equals(json.findValue("id"))){
                paramsMap.put("id", json.findValue("id").asInt());
            }


            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",service.themeSearch(paramsMap));

            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }
}

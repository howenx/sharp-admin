package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import domain.User;
import domain.UserLog;
import filters.UserAuth;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.UserLogService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sunny Wu on 16/4/21.
 * kakao china.
 */
public class UserLogCtrl extends Controller {

    @Inject
    private UserLogService userLogService;

    public static final int pageSize = 10;

    /**
     * 日志查询列表
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result userLogSearch(String lang) {
        UserLog userLog = new UserLog();
        userLog.setPageSize(-1);
        userLog.setOffset(-1);
        int countNum = userLogService.getUserLogPage(userLog).size();
        int pageCount = countNum/pageSize;
        if (countNum/pageSize !=0) {
            pageCount = countNum/pageSize + 1;
        }
        userLog.setPageSize(pageSize);
        userLog.setOffset(0);
        return ok(views.html.datalog.userlogsearch.render(lang, pageSize, countNum, pageCount, userLogService.getUserLogPage(userLog), (User) ctx().args.get("user")));
    }

    /**
     * ajax分页查询
     * @param lang 语言
     * @param pageNum
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result userLogSearchAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        UserLog userLog = Json.fromJson(json,UserLog.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*pageSize;
            userLog.setPageSize(-1);
            userLog.setOffset(-1);
            //取总数
            int countNum = userLogService.getUserLogPage(userLog).size();
            //共分几页
            int pageCount = countNum/pageSize;

            if(countNum%pageSize!=0){
                pageCount = countNum/pageSize+1;
            }
            userLog.setPageSize(pageSize);
            userLog.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",userLogService.getUserLogPage(userLog));
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

    @Security.Authenticated(UserAuth.class)
    public Result findUserLogById(String lang, Long id) {
        UserLog userLog = userLogService.getUserLog(id);
        return ok(views.html.datalog.userlogdetail.render(lang, userLog, (User) ctx().args.get("user")));
    }
}

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

    private static final int PAGE_SIZE = 50;

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
        int pageCount = countNum/PAGE_SIZE;
        if (countNum%PAGE_SIZE !=0) {
            pageCount = countNum/PAGE_SIZE + 1;
        }
        userLog.setPageSize(PAGE_SIZE);
        userLog.setOffset(0);
        return ok(views.html.datalog.userlogsearch.render("cn", PAGE_SIZE, countNum, pageCount, userLogService.getUserLogPage(userLog), (User) ctx().args.get("user")));
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
            int offset = (pageNum-1)*PAGE_SIZE;
            userLog.setPageSize(-1);
            userLog.setOffset(-1);
            //取总数
            int countNum = userLogService.getUserLogPage(userLog).size();
            //共分几页
            int pageCount = countNum/PAGE_SIZE;

            if(countNum%PAGE_SIZE!=0){
                pageCount = countNum/PAGE_SIZE+1;
            }
            userLog.setPageSize(PAGE_SIZE);
            userLog.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",userLogService.getUserLogPage(userLog));
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

    @Security.Authenticated(UserAuth.class)
    public Result findUserLogById(String lang, Long id) {
        UserLog userLog = userLogService.getUserLog(id);
        return ok(views.html.datalog.userlogdetail.render("cn", userLog, (User) ctx().args.get("user")));
    }
}

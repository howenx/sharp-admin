package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import entity.DataLog;
import entity.User;
import filters.UserAuth;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.DataLogService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sunny Wu on 16/1/15.
 * kakao china.
 */
public class DataLogCtrl extends Controller {

    @Inject
    private DataLogService dataLogService;

    private int pageSize = 10;

    /**
     * 日志查询列表
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result dataLogSearch(String lang) {

        DataLog dataLog = new DataLog();
        dataLog.setPageSize(-1);
        dataLog.setOffset(-1);
        int countNum = dataLogService.getDataLogPage(dataLog).size();
        int pageCount = countNum/pageSize;
        if (countNum/pageSize !=0) {
            pageCount = countNum/pageSize + 1;
        }
        dataLog.setPageSize(pageSize);
        dataLog.setOffset(0);
        return ok(views.html.datalog.datalogserarch.render(lang, pageSize, countNum, pageCount, dataLogService.getDataLogPage(dataLog), (User) ctx().args.get("user")));
    }

    /**
     * ajax分页查询
     * @param lang 语言
     * @param pageNum
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result dataLogSearchAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        DataLog dataLog = Json.fromJson(json,DataLog.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*pageSize;
            dataLog.setPageSize(-1);
            dataLog.setOffset(-1);
            //取总数
            int countNum = dataLogService.getDataLogPage(dataLog).size();
            //共分几页
            int pageCount = countNum/pageSize;

            if(countNum%pageSize!=0){
                pageCount = countNum/pageSize+1;
            }
            dataLog.setPageSize(pageSize);
            dataLog.setOffset(offset);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",dataLogService.getDataLogPage(dataLog));
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
    public Result findDataLogById(String lang, Long id) {
        DataLog dataLog = dataLogService.getDataLog(id);
        return ok(views.html.datalog.datalogdetail.render(lang, dataLog, (User) ctx().args.get("user")));
    }

}

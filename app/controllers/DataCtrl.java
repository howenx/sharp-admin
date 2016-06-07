package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.SMSVo;
import domain.SystemParam;
import domain.User;
import filters.UserAuth;
import play.Configuration;
import play.Logger;
import play.data.Form;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.SysParamService;
import util.SysParCom;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tiffany on 15/12/14.
 */
@SuppressWarnings("unchecked")
public class DataCtrl extends Controller {

    @Inject
    SysParamService sysParamService;

    @Inject
    Configuration configuration;

    @Inject
    private ActorSystem system;

    /**
     * 系统参数列表
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result systemParameterSearch(String lang){
        return ok(views.html.data.sysparamsearch.render(lang,sysParamService.getParamAll(),(User) ctx().args.get("user")));
    }

    /**
     * 新增系统参数
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result systemParameterAdd(String lang){
        return ok(views.html.data.sysparamadd.render(lang,(User) ctx().args.get("user")));
    }

    /**
     * 添加系统参数
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result paramSave(String lang){
        JsonNode json = request().body().asJson();
        Form<SystemParam> systemParamForm = Form.form(SystemParam.class).bind(json);
        //数据验证
        if (systemParamForm.hasErrors()) {
            Logger.error("system param 表单数据有误.....");
            return badRequest();
        }
        sysParamService.insertParam(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 给用户发送短息          Added By Sunny WU  2016.06.07
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result smsSend(String lang) {
        Map<String,String> tempList = new ObjectMapper().convertValue(configuration.getObject("sms_template"),HashMap.class);
        return ok(views.html.data.smsSend.render(lang, tempList, (User) ctx().args.get("user")));
    }

    /**
     * 保存给用户发送的短信    Added By Sunny WU  2016.06.07
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result smsSave() {
        JsonNode json = request().body().asJson();
        for(final JsonNode jsonNode : json) {
            Form<SMSVo> smsVoForm = Form.form(SMSVo.class).bind(jsonNode);
            SMSVo smsVo  = Json.fromJson(jsonNode, SMSVo.class);
            //数据验证
            if (smsVoForm.hasErrors()) {
                Logger.error("smsVo 表单数据有误.....");
                return badRequest();
            }
            Logger.error("短信内容:"+smsVo.toString());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, BigInteger> paramMap = mapper.convertValue(jsonNode, Map.class);
            system.actorSelection(SysParCom.SMS_SEND).tell(paramMap, ActorRef.noSender());
        }
        return ok("保存成功");
    }

}

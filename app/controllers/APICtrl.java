package controllers;

import order.GetLogistics;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by tiffany on 16/4/13.
 */
public class APICtrl extends Controller {

    public Result getLogisticsAPI(String nu,String show,String order){
        if(nu == null || nu.equals("")){
            return ok(Json.toJson("快递单号为空!"));
        }
        if(show == null || show.equals("")){
            show = "0";
        }
        if(order == null || order.equals("")){
            order = "desc";
        }
        String result = GetLogistics.sendGet(nu,show,order);
        if (show.equals("0")){
            return ok(Json.toJson(result));
        }else {
            return ok(result);
        }
    }

}

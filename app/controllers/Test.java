package controllers;


import akka.actor.ActorRef;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;


/**
 * Created by handy on 15/11/3.
 * kakao china
 */
@Singleton
public class Test extends Controller{

    @Inject @Named("send")
    private ActorRef send;

    @Security.Authenticated(UserAuth.class)
    public  Result test() {

        //ActorRef ar = Akka.system().actorOf(Props.create(SendActor.class));
//        GetActor.GetData data = new GetActor.SendData("test",0);
//        Akka.system().scheduler().scheduleOnce(Duration.create(10, TimeUnit.SECONDS), send, data, Akka.system().dispatcher(), ActorRef.noSender());


//        Map params = new HashMap<>();
        //       params.put("StartTime","2015-01-01 01:00:00");
        //       params.put  ("EndTime","2015-11-01 01:00:00");
        //       params.put("ShopId","1");
//        Integer shopId = 1;
//        Integer orderStatus = 10;

//        Map params = new HashMap<>();
//        params.put("StartTime","2015-01-01 01:00:00");
//        params.put  ("EndTime","2015-11-01 01:00:00");

//        params.put("ShopOrderNo", "1000007");
//        params.put("ShopId",1);
//        params.put("MemberNick", "1000012");
//        params.put("OrderStatus", 10);
//        params.put("ShopCreatedTime", new Date());


//        Logger.debug(B1EC2Client.post("http://121.43.186.32","B1EC2.ShopOrder.Push",params, JsonNode.class).toString());
        // Logger.debug(B1EC2Client.post("http://121.43.186.32","B1EC2.SalesOrder.Query",params, JsonNode.class).toString());

       // String returnVal =B1EC2Client.post("http://121.43.186.32","B1EC2.Express.Query",params, JsonNode.class).toString();

       // Logger.debug(returnVal);
//        Logger.debug(B1EC2Client.post("http://121.43.186.32","B1EC2.SalesOrder.Query",params, JsonNode.class).toString());

//        Logger.debug(ctx().args.get("user").toString());
//        Pattern p = Pattern.compile("\\b[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\b");
//        Matcher m = p.matcher("kkmc.skein+cn@gmail.com");
//        if(m.matches()) {
//            Logger.debug("match");
//        }else {
//            Logger.debug("not match");
//        }

        return ok("1");

    }

    public  Result upload() {
        return redirect("http://192.168.31.142:3008/upload");
//        return movedPermanently("http://192.168.31.142:3008/upload");
    }

}
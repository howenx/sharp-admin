package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.japi.pf.ReceiveBuilder;
import domain.Persist;
import domain.VersionVo;
import modules.LevelFactory;
import play.Configuration;
import play.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * 用于处理自动上传
 * Created by howen on 16/04/18.
 */
public class AutoDeployActor extends AbstractActor {

    @Inject
    public AutoDeployActor(Configuration configuration,ActorSystem system) {
        receive(ReceiveBuilder.match(Object.class, message -> {

            if (message instanceof VersionVo){
                List<String> actorPath = configuration.getStringList("style."+((VersionVo) message).getProductType());
                if (actorPath!=null && !actorPath.isEmpty()){
                    if(((VersionVo) message).getProductType().equals("id") || ((VersionVo) message).getProductType().equals("web")){
                        for (String ap:actorPath) {
                            system.actorSelection(ap).tell(message, ActorRef.noSender());
                        }
                    }else{
                        for (String ap:actorPath) {
                            system.actorSelection(ap).tell(((VersionVo) message).getId(), ActorRef.noSender());
                        }
                    }
                }else{
                    Logger.error("Configuration is not found,{}","style."+((VersionVo) message).getProductType());
                }
            }
        }).matchAny(s -> {
            Logger.error("AutoDeplayActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}

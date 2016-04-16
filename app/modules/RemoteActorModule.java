package modules;

import actor.MsgActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * RemoteActorModule
 * Created by sibyl.sun on 16/3/1.
 */
@Singleton
public class RemoteActorModule {

    public static ActorRef cancelOrderActor;
    public static ActorRef refundActor;

    @Inject
    public RemoteActorModule(ActorSystem system, Configuration configuration) {
        String pushPath = configuration.getString("shopping.cancelOrderActor");
        cancelOrderActor = system.actorOf(
                Props.create(MsgActor.class, pushPath), "cancelOrderActor");
        System.out.println("Started cancelOrderActor,pushPath=" + pushPath + ",path=" + cancelOrderActor.path());

        String msgPath = configuration.getString("shopping.refundActor");
        refundActor = system.actorOf(
                Props.create(MsgActor.class, msgPath), "refundActor");
        System.out.println("Started refundActor,msgPath=" + msgPath + ",path=" + refundActor.path());
    }
}

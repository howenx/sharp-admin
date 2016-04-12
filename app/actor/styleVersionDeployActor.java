package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import play.Logger;


/**
 * Created by tiffany on 16/4/12.
 */
public class StyleVersionDeployActor extends AbstractActor {
    public StyleVersionDeployActor() {
        receive(ReceiveBuilder.match(String.class, deployServer -> {
            String ipAdress;
            String userName;
            String password;
            final int DEFAULT_SSH_PORT = 22;
            
















            Logger.error("发布到服务器:" + deployServer);

        }).matchAny(s -> {
            unhandled(s);
            Logger.error("" + s.toString());
        }).build());

    }
}

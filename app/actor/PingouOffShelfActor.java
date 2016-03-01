package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import play.Logger;
import service.PingouService;
import service.ThemeService;

import javax.inject.Inject;

/**
 * Created by tiffany on 16/2/29.
 */
public class PingouOffShelfActor extends AbstractActor {
    @Inject
    public PingouOffShelfActor(PingouService pingouService) {

        receive(ReceiveBuilder.match(Long.class, message -> {
            pingouService.updStatusById(message);
            Logger.error("" + message.toString());
        }).matchAny(s -> {
            unhandled(s);
        }).build());
    }
}

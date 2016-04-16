package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import domain.pingou.PinSku;
import play.Logger;
import service.PingouService;
import javax.inject.Inject;

/**
 * Created by tiffany on 16/2/29.
 */
public class PingouOffShelfActor extends AbstractActor {
    @Inject
    PingouService pingouService;

    public PingouOffShelfActor() {
        receive(ReceiveBuilder.match(Long.class, message -> {
            PinSku pinSku = new PinSku();
            pinSku.setPinId(message);
            pinSku.setStatus("D");
            pingouService.updStatusById(pinSku);
            Logger.error("" + message.toString());
        }).matchAny(s -> {
            unhandled(s);
            Logger.error("" + s.toString());
        }).build());
    }
}

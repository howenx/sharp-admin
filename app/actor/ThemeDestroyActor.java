package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import play.Logger;
import service.ThemeService;

import javax.inject.Inject;

/**
 * Created by tiffany on 16/2/29.
 */
public class ThemeDestroyActor extends AbstractActor {

    @Inject
    public ThemeDestroyActor(ThemeService themeService) {

        receive(ReceiveBuilder.match(Long.class, message -> {
           themeService.updThemeDestroy(message);
            Logger.error("" + message.toString());
        }).matchAny(this::unhandled).build());
    }
}

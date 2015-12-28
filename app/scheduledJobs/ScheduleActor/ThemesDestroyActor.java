package scheduledJobs.ScheduleActor;

import akka.actor.AbstractActor;
import akka.actor.UntypedActor;
import akka.japi.pf.ReceiveBuilder;
import play.Logger;
import service.ThemeService;
import service.ThemeServiceImpl;

import javax.inject.Inject;

/**
 * Created by tiffany on 15/12/25.
 */
 /*
public class ThemesDestroyActor extends AbstractActor {


    @Inject
    public ThemesDestroyActor(ThemeService themeService) {
        receive(ReceiveBuilder.match(String.class, Logger::error).matchAny(s -> {
            Logger.error("CancelOrderActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());

    }
}
*/

public class ThemesDestroyActor extends UntypedActor{
    @Override
    public void onReceive(Object message) throws Exception {

    }
}

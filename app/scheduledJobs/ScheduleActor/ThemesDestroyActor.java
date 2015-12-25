package scheduledJobs.ScheduleActor;

import akka.actor.UntypedActor;
import service.ThemeService;

import javax.inject.Inject;

/**
 * Created by tiffany on 15/12/25.
 */
public class ThemesDestroyActor extends UntypedActor{

    @Inject
    ThemeService themeService;
    public ThemesDestroyActor() {
    }

    @Override
    public void onReceive(Object arg) throws Exception {
        System.out.print("<<<<<<<<<<");

        System.out.print(">>>>>>>>>>");
    }


}

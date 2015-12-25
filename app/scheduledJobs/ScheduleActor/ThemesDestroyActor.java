package scheduledJobs.ScheduleActor;

import akka.actor.UntypedActor;
import service.ThemeService;

import javax.inject.Inject;

/**
 * Created by tiffany on 15/12/25.
 */
public class ThemesDestroyActor extends UntypedActor{

    @Inject
    private final ThemeService themeService;

    public ThemesDestroyActor(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Override
    public void onReceive(Object arg) throws Exception {
        System.out.print("<<<<<<<<<<");
        System.out.println(themeService);
        System.out.print(">>>>>>>>>>");
    }


}

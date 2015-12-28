package modules;


import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;
import scheduledJobs.ScheduleActor.ThemesDestroyActor;


/**
 * Created by tiffany on 15/12/25.
 */
public class ActorModule extends AbstractModule implements AkkaGuiceSupport {
    @Override
    protected void configure() {
//        bindActor(ThemesDestroyActor.class,"themeDestroyActor");
    }

}

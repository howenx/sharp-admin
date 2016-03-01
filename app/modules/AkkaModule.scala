package modules

<<<<<<< HEAD
import actor.{InventoryAutoShelvesActor, DelScheduleActor, SchedulerCancelOrderActor, OSSActor}
=======
import actor._
>>>>>>> 141c379777fc53d17874696fbc2d6474a1821a1b
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

/**
  * Created by handy on 15/11/17.
  * kakao china
  */
class AkkaModule extends AbstractModule with AkkaGuiceSupport {
  override def configure() ={
    bindActor[OSSActor] ("oss")
    bindActor[SchedulerCancelOrderActor] ("schedulerCancelOrderActor")
    bindActor[DelScheduleActor]("delScheduleActor")
<<<<<<< HEAD




    bindActor[InventoryAutoShelvesActor]("inventoryAutoShelvesActor")
=======
    bindActor[ThemeDestroyActor]("themeDestroyActor")
    bindActor[PingouOffShelfActor]("pingouOffShelfActor")

>>>>>>> 141c379777fc53d17874696fbc2d6474a1821a1b
  }
}

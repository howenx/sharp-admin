package modules

import actor.{MnsActor, _}
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
    bindActor[InventoryAutoShelvesActor]("inventoryAutoShelvesActor")
    bindActor[ThemeDestroyActor]("themeDestroyActor")
    bindActor[PingouOffShelfActor]("pingouOffShelfActor")
    bindActor[PingouOnShelfActor]("pingouOnShelfActor")
//    bindActor[ShopOrderPushActor]("shopOrderPushActor")
//    bindActor[SalesOrderQueryActor]("salesOrderQueryActor")
    bindActor[CouponInvalidActor]("couponInvalidActor")
    bindActor[StyleVersionDeployActor]("StyleVersionDeployActor")
    bindActor[AdminRunActor]("adminRunActor")
    bindActor[AutoDeployActor]("autoDeployActor")
    bindActor[MnsActor]("mnsActor")
    bindActor[CouponSendActor]("couponSendActor")
  }
}
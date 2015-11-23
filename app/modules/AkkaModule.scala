package modules

import actor.OSSActor
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

/**
  * Created by handy on 15/11/17.
  * kakao china
  */
class AkkaModule extends AbstractModule with AkkaGuiceSupport {
  override def configure() ={
    bindActor[OSSActor] ("oss")
  }
}
package actor

import java.util.concurrent.TimeUnit

import akka.actor.{FSM, Actor}
import play.Logger
import play.api.libs.ws.WS

import scala.concurrent.duration._
import scala.concurrent.forkjoin.ThreadLocalRandom
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by handy on 15/12/23.
  * kakao china
  */
object GetActor {

  sealed trait GetState

  case object Idle extends GetState

  case object Getting extends GetState

  case object Success extends GetState

  case object Failure extends GetState


  sealed trait GetData

  case object NoData extends GetData

  case class SendData(url: String, var retry_time: Int) extends GetData

}

class SendActor extends Actor with FSM[GetActor.GetState,GetActor.GetData]{
   import GetActor._
  /**
    * 重新尝试时间
    * @return
    */
  def next_try: FiniteDuration = FiniteDuration((1.0 + ThreadLocalRandom.current.nextDouble() * 9.0).toInt, TimeUnit.SECONDS)

//  startWith(Idle, NoData)
//
//  when(Idle) {
//    case Event(e,s ) => {
//      Logger.debug("idle")
//      Logger.debug(e.toString)
//      Logger.debug(s.toString)
//      goto(Success)
//    }
//  }

  override def receive = {
    case data:GetData => {
      Logger.debug(data.toString)
      //goto(Idle) using data
    }

  }


//  when(Success) {
//
//    case Event(e,s ) => {
//      Logger.debug("success")
//      //Logger.debug(e.toString)
//      //Logger.debug(s.toString)
//      goto(Idle) using(SendData("aa",0))
//    }
//  }
//
//
//  when(Failure) {
//    case Event(e,s ) => {
//      Logger.debug("failure")
//      Logger.debug(e.toString)
//      Logger.debug(s.toString)
//      goto(Success)
//    }
//  }
//
//  initialize()




}

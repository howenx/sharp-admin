package filters

import play.api.Logger
import play.api.mvc.{Filter, RequestHeader, Result}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by handy on 15/10/23.
 * Daumkakao china
 */
class AccessLoggingFilter extends Filter {
  val accessLogger = Logger("access")

  def apply(next: (RequestHeader) => Future[Result])(request: RequestHeader): Future[Result] = {
    val resultFuture = next(request)

    resultFuture.foreach(result => {
      request.method match {
        case "HEAD" =>
          None
        case _ =>
          val msg = s"method=${request.method} uri=${request.uri} remote-address=${request.remoteAddress}" +
            s" status=${result.header.status}"+s" x-remote-address=${request.getQueryString("X-Real-IP")}"
          accessLogger.info(msg)
      }

    })

    resultFuture
  }
}

package filters

import controllers.routes
import domain.User
import play.api.Logger
import play.api.Play.current
import play.api.cache.Cache
import play.api.mvc._

/**
 * Created by handy on 15/10/28.
 * kakao china
 */
trait Secured {

  def username(request: RequestHeader) = request.session.get("username")

  def onUnauthorized(request: RequestHeader) = {
//    Results.Redirect(routes.Auth.login)
    //Modified By Sunny.Wu 2016/02/19
    Results.Redirect(routes.AdminUserCtrl.adminUserLogin())
  }

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  def withUser(f: User => Request[AnyContent] => Result) = withAuth { username =>
    implicit request =>
      Logger.debug("withUser $username")
      Cache.getAs[User](username.trim).map { user =>
        f(user)(request)
      }.getOrElse(onUnauthorized(request))

  }

  def isAuthenticated(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(username, onUnauthorized) { user =>
         Action(request => f(user)(request))
       }
      }

}

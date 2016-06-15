package filters

import javax.inject.Inject

import controllers.routes
import domain.User
import play.Configuration
import play.api.Logger
import play.api.Play.current
import play.api.cache.Cache
import play.api.mvc._

/**
 * Created by handy on 15/10/28.
 * kakao china
 */
trait Secured {

  @Inject private[filters] var configuration: Configuration = null

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


//      Logger.error(Cache.getAs[User](username.trim).get.userType.get+"登录的用户的类型::::::")
//      val userType: String =  Cache.getAs[User](username.trim).get.userType.get
//      val userTypeArr: Array[String] = userType.split(",")
//      var flag: Boolean = false
//      val header: Optional[String] = Optional.of(request.path)
//      Logger.error(header.get()+"请求的方法")
//      for (userType <- userTypeArr) {
//        if (configuration.getStringList(userType).contains(header.get())) {
//          flag = true
//        }
//      }
//      if (flag)
//        Cache.getAs[User](username.trim).map { user =>
//          f(user)(request)
//        }
//      else onUnauthorized(request)



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

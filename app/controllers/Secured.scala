package controllers

import play.api.cache.Cache
import play.api.mvc._
import entity.User
import play.api.Play.current
import play.api.Logger

/**
 * Created by handy on 15/10/28.
 * kakao china
 */
trait Secured {

  def username(request: RequestHeader) = request.session.get("username")

  def onUnauthorized(request: RequestHeader) = {
    Results.Redirect(routes.Auth.login)
  }

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(username, onUnauthorized) { user =>
      Logger.debug(s"auth user $user")
      Action(request => f(user)(request))
    }
  }

  def withUser(f: User => Request[AnyContent] => Result) = withAuth { username =>
    implicit request =>
      Cache.getAs[User](username).map { user =>
        Logger.debug(s"cache user $user")
        f(user)(request)
      }.getOrElse(onUnauthorized(request))
  }

//  def isAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
//    Logger.debug(s"is $user")
//    Action(request => f(user)(request))
//  }

  def isAuthenticated(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(username, onUnauthorized) { user =>
         Action(request => f(user)(request))
       }
      }

}

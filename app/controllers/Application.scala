package controllers


import javax.inject.Singleton
import play.api.mvc.{Controller}
import play.api.Logger
/**
 * Created by handy on 15/10/29.
 * kakao china
 */
@Singleton
class Application extends Controller with Secured{

  def welcome = isAuthenticated { user => {
    implicit  request => {
      Logger.debug(s" user is $user")
      Ok(views.html.welcome())
    }
  }
  }

}
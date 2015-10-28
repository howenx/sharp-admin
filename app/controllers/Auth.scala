package controllers



import javax.inject.Singleton
import play.api.cache.Cache
import play.api.Play.current
import entity.User
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.Logger
case class LoginInfo(name:String, passwd: String)


/**
 * Created by handy on 15/10/23.
 * kakao china
 */
@Singleton
class Auth extends Controller with Secured{

  var login_form = Form(mapping(
  "name" -> nonEmptyText(minLength = 3),
  "passwd" -> nonEmptyText(minLength=6)
  )(LoginInfo.apply)(LoginInfo.unapply)
//  )(LoginInfo.apply)(LoginInfo.unapply) verifying("name and password error", result => result match {
//    case login_info => check(login_info.name, login_info.passwd).isDefined
//  })
  )

  /**
   * 从数据库检查用户是否存在,如果存在,返回用户
   * @param name
   * @param password
   * @return
   */
  def check(name: String, password: String) : Option[User]= {
    val p = "^1[3-8][0-9]{9}".r
    name match {
      case p(_*) =>
        //电话号码查询
        Some (User (111,"test", "tst","test") )
      case _ =>
        //用户昵称查询
        Some (User (111,"test", "tst","test") )
    }
  }

  def login = Action { implicit  request =>
    val bind_form = login_form.bind(Map("name"->"","passwd"->""))
    Ok(views.html.login(bind_form))

  }


  /**
   * 用户登录接口
   * @return
   */
  def authenticate = Action { implicit request =>
    val user_info = login_form.bindFromRequest
    user_info.fold (
      formWithErrors => {
        Logger.debug("to login page...")
        Logger.debug(formWithErrors.toString)
        Ok(views.html.login(formWithErrors))
      },
      login_info => {
        check(login_info.name, login_info.passwd) match {
          case None =>
            Ok(views.html.login(user_info))
          case Some(user) =>
            Logger.debug("user login... to admin age")
            Cache.set(user.nickname, user.id.toString)
            Logger.debug(Cache.get(user.nickname).toString)
            Redirect(routes.Application.welcome()).withSession( request.session + ("username"-> user.nickname))

        }

      }
    )

  }

  def logout = isAuthenticated { user => {
    implicit  request => {
      val bind_form = login_form.bind(Map("name"->"","passwd"->""))
      Ok(views.html.login(bind_form)).withSession(request.session - ("username"))
    }
  }
  }

}

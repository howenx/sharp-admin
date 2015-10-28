package controllers



import javax.inject.Singleton


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
class Auth extends Controller {

  var login_form = Form(mapping(
  "name" -> nonEmptyText(minLength = 3),
  "passwd" -> nonEmptyText(minLength=6)
  )(LoginInfo.apply)(LoginInfo.unapply) verifying("error", result => result match {
    case login_info => check(login_info.name, login_info.passwd).isDefined
  })
  )

  /**
   * 从数据库检查用户是否存在,如果存在,返回用户
   * @param name
   * @param password
   * @return
   */
  def check(name: String, password: String) = {
    val p = "^1[3-8][0-9]{9}".r
    name match {
      case p(_*) =>
        //电话号码查询
        Some (LoginInfo (name, password) )
      case _ =>
        //用户昵称查询
        Some (LoginInfo (name, password) )
    }
  }

  def login = Action { implicit request =>
    val user_info = login_form.bindFromRequest
    user_info.fold (
      formWithErrors => {
        Logger.debug("to login page...")
        Logger.debug(formWithErrors.toString)
        Ok(views.html.login(formWithErrors))
      },
      user => {
        Logger.debug("user login... to admin age")
        Redirect(routes.Application.welcome())
      }
    )



  }

}

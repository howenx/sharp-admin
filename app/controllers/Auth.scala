package controllers



import javax.inject.{Inject, Singleton}
import filters.Secured
import play.api.cache.Cache
import play.api.Play.current
import entity.User
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{Lang, MessagesApi, I18nSupport}
import play.api.mvc.{Results, Action, Controller}
import play.api.Logger
case class LoginInfo(name:String, passwd: String)


/**
 * Created by handy on 15/10/23.
 * kakao china
 */
@Singleton
class Auth @Inject() (val messagesApi: MessagesApi) extends Controller with Secured with I18nSupport{

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
 *
   * @param name
   * @param password
   * @return
   */
  def check(name: String, password: String) : Option[User]= {
    val p = "^1[3-8][0-9]{9}".r
    name match {
      case p(_*) =>
        //电话号码查询
        User.find_by_phone(name,password)
      case _ =>
        //用户昵称查询
        User.find_by_name(name,password)

    }
  }

  def login = Action { implicit  request =>
    val bind_form = login_form.bind(Map("name"->"","passwd"->""))

    val lang = request.getQueryString("lang") match {
      case Some(l) =>
        Lang.apply(l)
      case l =>
        Lang.preferred(request.acceptLanguages)
    }

    Ok(views.html.login(bind_form)).withLang(lang)

  }


  /**
   * 用户登录接口
 *
   * @return
   */
  def authenticate = Action { implicit request =>
    val user_info = login_form.bindFromRequest
    user_info.fold (
      formWithErrors => {
        Logger.debug("form error, to login page...")
        Logger.debug(formWithErrors.toString)
        Ok(views.html.login(formWithErrors))
      },
      login_info => {
        check(login_info.name, login_info.passwd) match {
          case None =>
            Logger.debug("not admin user")
            Ok(views.html.login(user_info))
          case Some(user) =>
            Logger.debug(s"user login... to admin age  $user")
            Cache.set(user.nickname.trim, user)
            val lang = request.getQueryString("lang") match {
              case Some(l) =>
                Lang.apply(l)
              case l =>
                Lang.preferred(request.acceptLanguages)
            }
            Redirect(routes.Application.welcome(lang.code)).withSession( request.session + ("username"-> user.nickname))

        }

      }
    )

  }

  /**
    * 登出
 *
    * @return
    */
  def logout = withUser { user => {
    implicit  request => {

      //清理cache
      Cache.remove(if(user.nickname==null) user.enNm.get else user.nickname);
      //清理session
//      Results.Redirect(routes.Auth.login).withSession(request.session - ("username"))

      //Modified By Sunny.Wu 2016/01/29
      Results.Redirect(routes.AdminUserCtrl.adminUserLogin).withSession(request.session - ("username"))

    }
  }
  }

}

package controllers


import javax.inject.{Inject, Singleton}
import play.api.mvc.{Controller}
import play.api.Logger
import play.api.Play.current
import play.api.i18n.{Lang, MessagesApi, I18nSupport}
/**
 * Created by handy on 15/10/29.
 * kakao china
 */
@Singleton
@Inject
class Application @Inject()(val messagesApi: MessagesApi) extends Controller with Secured with I18nSupport{

  def welcome()  = isAuthenticated { user => {
    implicit  request => {
      val lang = request.getQueryString("lang") match {
        case Some(l) =>
          Lang.apply(l)
        case l =>
          Lang.preferred(request.acceptLanguages)
      }

      //Ok(views.html.welcome("cn",request.session.get("username").getOrElse(""))).withLang(lang)
      Ok(views.html.welcome(lang.code,request.session.get("username").getOrElse("")))
    }
  }
  }


  /**
    * 供应商提供信息表格
    * @return
    */
  def supply = isAuthenticated { user => {
    implicit request => {
      Ok("ok")
    }

  }

  }

}
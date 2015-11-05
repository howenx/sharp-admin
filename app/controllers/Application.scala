package controllers


import javax.inject.{Inject, Singleton}
import entity.User_Type
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
class Application @Inject()(val messagesApi: MessagesApi) extends Controller with Secured with I18nSupport {

  def welcome(lang:String) = withUser { user => {
    implicit request => {
//      val lang = request.getQueryString("lang") match {
//        case Some(l) =>
//          Lang.apply(l)
//        case l =>
//          Lang.preferred(request.acceptLanguages)
//      }


      //Ok(views.html.welcome("cn",request.session.get("username").getOrElse(""))).withLang(lang)
      user.role match {
        case User_Type.SELLER =>
          Redirect(routes.Application.supply())
        case _ =>
          Ok(views.html.welcome(lang, user))
      }

    }
  }
  }

//  def welcome() = withUser { user => {
//    implicit request => {
//      val lang = request.getQueryString("lang") match {
//        case Some(l) =>
//          Lang.apply(l)
//        case l =>
//          Lang.preferred(request.acceptLanguages)
//      }
//
//      //Ok(views.html.welcome("cn",request.session.get("username").getOrElse(""))).withLang(lang)
//      Ok(views.html.welcome(lang.code, user.nickname))
//    }
//  }
//  }

  /**
    * 供应商提供信息表格
    * @return
    */
  def supply = withUser { user => {
    implicit request => {
      Logger.debug(s" $user")
      Logger.debug(request.host)
      Ok(views.html.supply("cn", user))
    }

  }

  }

}
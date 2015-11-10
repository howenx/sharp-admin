package controllers


import javax.inject.{Inject, Singleton}
import entity.{Prod_Type, Prod, User_Type}
import play.api.libs.json.Json
import play.api.mvc.{Controller}
import play.api.Logger
import play.api.Play.current
import play.api.i18n.{Lang, MessagesApi, I18nSupport}

import scala.util.Try

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

  def list_supply = withUser { user => {
    implicit  request => {
      val ret = Prod.list(Prod_Type.hzp)
      Logger.debug(ret.toString())
      Ok(views.html.supply.list("cn",user, ret))
    }
  }

  }

  /**
    * 供应商提供信息表格
    * @return
    */
  def supply = withUser { user => {
    implicit request => {
      Logger.debug(s" $user")

      request.body.asFormUrlEncoded match {
        case Some(map) =>
          //商品名称
          //转化一下内容
          var m  = map.map{case(k,v) => k->v.head}
          val p_type = m("p_type")
          m -= ("p_type")
          Prod_Type.withName(p_type) match {
            case Prod_Type.hzp =>
              Logger.debug(m.toString())

              //得到名字属性
              val name = m("name")
              m -= "name"

              //标签
              val tags = """{"化妆品"}"""

              //成本价格
              val price = Try(m("price").toInt).getOrElse(0)
              m -= "price"

              //市场价格
              val market_price = Try(m("market_price").toInt).getOrElse(0)
              m -= "market_price"

              //数量
              val amount = Try(m("amount").toInt).getOrElse(0)
              m -= "amount"

              //得到属性
              //Logger.debug(m.filter(_._1.ex "批次").toString())
              //Logger.debug(Json.toJson(m.filter(_._1 == "批次")).toString())
              val attr_set = Set("批次")
              val attr = Json.toJson(m.filterKeys(attr_set.contains(_))).toString()
              m --= attr_set

              //得到图片
              val images_set = ("image")
              val images = m.filterKeys(images_set.contains(_)).map(_._2).mkString("{",",","}")
              m -= ("image")

              //得到规格
              val spec_set = Set("主要成分","类别","功效")
              val spec = Json.toJson(m.filterKeys(spec_set.contains(_))).toString()
              m --= spec_set

              //得到扩展数据
              val extra = Json.toJson(m).toString()

              Prod.insert(Prod_Type.hzp, name, tags, attr, spec, images, price, market_price, amount, extra)
            //val price = map(price)
              Redirect(routes.Application.supply())

            //map -= (name","price")

            case Prod_Type.ps =>
              Logger.debug(m.toString())
              //得到名字属性
              val name = m("name")
              m -= "name"

              val tags = """{"配饰"}"""

              val price = Try(m("price").toInt).getOrElse(0)
              m -= "price"

              val market_price = Try(m("market_price").toInt).getOrElse(0)
              m -= "market_price"

              val amount = Try(m("amount").toInt).getOrElse(0)
              m -= "amount"

              //得到图片
              val images_set = ("image")
              val images = m.filterKeys(images_set.contains(_)).map(_._2).mkString("{",",","}")
              m -= ("image")

              //得到属性
              val attr_set = Set("批次")
              val attr = Json.toJson(m.filterKeys(attr_set.contains(_))).toString()
              m --= attr_set

              //得到规格
              val spec_set = Set("品牌","材质","款式")
              val spec = Json.toJson(m.filterKeys(spec_set.contains(_))).toString()
              m --= spec_set

              Logger.debug(spec)

              //得到扩展数据
              val extra = Json.toJson(m).toString()

              Logger.debug(extra)

              Prod.insert(Prod_Type.ps, name, tags, attr, spec, images, price, market_price, amount, extra)

              Redirect(routes.Application.supply())

            case Prod_Type.fs =>
              Logger.debug(m.toString())

              Logger.debug(m.toString())
              //得到名字属性
              val name = m("name")
              m -= "name"

              val tags = """{"服饰"}"""

              val price = Try(m("price").toInt).getOrElse(0)
              m -= "price"

              val market_price = Try(m("market_price").toInt).getOrElse(0)
              m -= "market_price"

              val amount = Try(m("amount").toInt).getOrElse(0)
              m -= "amount"

              //得到图片
              val images_set = ("image")
              val images = m.filterKeys(images_set.contains(_)).map(_._2).mkString("{",",","}")
              m -= ("image")

              //得到属性
              val attr_set = Set("批次","一级分类","二级分类","三级分类")
              val attr = Json.toJson(m.filterKeys(attr_set.contains(_))).toString()
              m --= attr_set

              //得到规格
              val spec_set = Set("型号","颜色","功能")
              val spec = Json.toJson(m.filterKeys(spec_set.contains(_))).toString()
              m --= spec_set

              Logger.debug(spec)

              //得到扩展数据
              val extra = Json.toJson(m).toString()

              Logger.debug(extra)

              Prod.insert(Prod_Type.fs, name, tags, attr, spec, images, price, market_price, amount, extra)

              Redirect(routes.Application.supply())
          }


        case None =>

          Ok(views.html.supply.supply("cn", user))


      }

    }

  }

  }

}
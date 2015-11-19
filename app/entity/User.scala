package entity

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import AnormEnumerationExtension._
import scala.language.implicitConversions

case class User (id: Long, nickname: String, gender: Gender.gender, photo_url: String ,  role: User_Type.user_type )

/**
 * Created by handy on 15/10/27.
 * kakao china
 */
object User {

  val user = {
    get[Long]("user_id") ~
      get[String]("nickname") ~
      get[Gender.gender]("gender") ~
      get[String]("photo_url") ~
      get[User_Type.user_type]("role") map {
      case user_id ~ nickname ~ gender ~ photo_url ~ role => User(user_id, nickname, gender, photo_url, role )
    }

  }

  def find_by_phone (phone:String, passwd: String):Option[User] = {
    DB.withConnection("account")  { implicit conn =>
    val sql = SQL( """select id.user_id, id.nickname, id.gender, id.photo_url, admin.role from "ID" as id, "ID_ADMIN" as adm where id.user_id = adm.user_id and id.phone_num = {phone_num} and id.passwd = user_passwd(id.user_id, {passwd}) and adm.status = 'Y' """).on("phone_num" -> phone, "passwd" -> passwd)
    sql.as(user.*).headOption
   }
  }

  def find_by_name (nickname:String, passwd: String):Option[User] = {
    DB.withConnection("account")  { implicit conn =>
      val sql = SQL( """select id.user_id, id.nickname, id.gender, id.photo_url, adm.role from "ID" as id, "ID_ADMIN" as adm where id.user_id = adm.user_id and id.nickname = {nickname} and id.passwd = user_passwd(id.user_id, {passwd}) and adm.status = 'Y' """).on("nickname" -> nickname, "passwd" -> passwd)
      sql.as(user.*).headOption
    }
  }


}

/**
 * 用户类型:供应商,翻译人员,管理员,系统管理员
 */
object User_Type extends Enumeration {
  type user_type = Value
  val SELLER, TRANSLATION, ADMIN, SYSTEM = Value
}


/**
  * 用户性别: 男:M, 女:F
  */
object Gender extends Enumeration {

  type gender = Value
  val M, F = Value
}


import java.lang.reflect.InvocationTargetException
import scala.reflect.runtime.universe._

/**
  * 映射db enum的值到scala的enum中
  */
object AnormEnumerationExtension {


  private lazy val rm = scala.reflect.runtime.currentMirror

  implicit def enumToParameterValue[E <: Enumeration: TypeTag](enumValue: E#Value): ParameterValue = enumValue.toString

  implicit def rowToEnumValue[E <: Enumeration: TypeTag]: Column[E#Value] = Column.nonNull1 { (value, meta) =>
    val MetaDataItem(qualified, _, _) = meta
    value match {
      case string: String => {
        val methodSym = typeTag[E].tpe.member(TermName("withName")).asMethod
        val im = rm.reflect(rm.reflectModule(typeOf[E].termSymbol.asModule).instance)
        try {
          Right(im.reflectMethod(methodSym)(string).asInstanceOf[E#Value])
        }
        catch {
          case e: InvocationTargetException if e.getCause.isInstanceOf[NoSuchElementException] =>
            Left(SqlMappingError(s"Can't convert '$string' to ${typeTag[E].tpe}, because it isn't a valid value: $qualified"))
        }
      }
      case _ => Left(TypeDoesNotMatch(s"Can't convert [$value:${value.asInstanceOf[AnyRef].getClass}] to ${typeTag[E].tpe}: $qualified"))
    }
  }
}

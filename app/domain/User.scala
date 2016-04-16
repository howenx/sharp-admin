package domain

import java.sql.Timestamp

import anorm.SqlParser._
import anorm._
import domain.AnormEnumerationExtension._
import play.api.Play.current
import play.api.db.DB

import scala.language.implicitConversions

case class User (id: Long, nickname: String, gender: Gender.gender, photo_url: String ,  role: User_Type.user_type,
                 userId:Option[Long], enNm:Option[String], chNm:Option[String], email:Option[String], userType:Option[String], passwd:Option[String], regIp:Option[String], regDt:Option[Timestamp],
                 activeYN:Option[String], alterDt:Option[Timestamp], lastLoginDt:Option[Timestamp], lastLoginIp:Option[String], lastPwdChgDt:Option[Timestamp],status:Option[String]
                  )

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
      case user_id ~ nickname ~ gender ~ photo_url ~ role => User(user_id, nickname, gender, photo_url, role, None,None,None,None, None,None,None,None,None,None,None,None, None,None)
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

  /**
    * 根据表的内容取得数据
    */
  val parser: RowParser[Map[String, Any]] =
    SqlParser.folder(Map.empty[String, Any]) { (map, value, meta) =>
      Right(map + (meta.column.qualified -> value))
    }


//  def test(nickname:String) = {
//    DB.withConnection("account") { implicit conn =>
//      SQL( """ select id.user_id as iid, adm.user_id as aid from "ID" as id left join "ID_ADMIN" as adm on id.user_id = adm.user_id where id.nickname = {nickname} """).on("nickname" -> nickname).as((get[Int]("ID.iid") ~ get[Option[Int]]("ID_ADMIN.aid")).map(flatten).*).headOption
//      //SQL( """ select user_id, nickname from "ID"  where nickname = {nickname} """).on("nickname" -> nickname).as((int(1) ~ str(2)).map(flatten).*).toMap
//    }
//  }

//  def test () = {
//    DB.withConnection("account") { implicit conn =>
//      SQL(""" select id, value from test """).as((get[Int]("id") ~ get[Double]("value")).map(flatten).*)
//
//    }
//  }


}

/**
 * 用户类型:供应商,翻译人员,管理员,系统管理员
 */
object User_Type extends Enumeration {
  type user_type = Value
  val SELLER, TRANSLATION,
  OPERATE, FINANCE, SERVICE, ADMIN, SYSTEM = Value
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

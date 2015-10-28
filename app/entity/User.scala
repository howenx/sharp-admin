package entity

import anorm.{~, SQL}
import anorm.SqlParser._
import play.api.cache.Cache
import play.api.db.DB
import play.api.Play.current


case class User (id: Long, nickname: String, gender: String, photo_url: String)
/**
 * Created by handy on 15/10/27.
 * kakao china
 */
object User {

  val user = {
    get[Long]("user_id") ~
      get[String]("nickname") ~
      get[String]("gender") ~
      get[String]("photo_url") map {
      case user_id ~ nickname ~ gender ~ photo_url => User(user_id, nickname, gender, photo_url)
    }

  }

  def find_by_phone (phone:String, passwd: String):Option[User] = {
    DB.withConnection("account")  { implicit conn =>
    val sql = SQL( """select user_id, nickname from "ID" where phone_num = {phone_num} and passwd = user_passwd(user_id, {passwd}) and status = 'Y' """).on("phone_num" -> phone, "passwd" -> passwd)
    sql.as(user *).headOption
   }
  }

  def find_by_name (nickname:String, passwd: String):Option[User] = {
    DB.withConnection("account")  { implicit conn =>
      val sql = SQL( """select user_id, nickname from "ID" where nickname = {nickname} and passwd = user_passwd(user_id, {passwd}) and status = 'Y' """).on("nickname" -> nickname, "passwd" -> passwd)
      sql.as(user *).headOption
    }
  }


}

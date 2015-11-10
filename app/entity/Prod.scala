package entity

import play.api.db.DB
import anorm._
import play.api.Play.current
import play.api.Logger

/**
  * Created by handy on 15/11/5.
  * kakao china
  */
object Prod {

  /**
    * 商品数据添加
    * @return
    */
  def insert(p_type: Prod_Type.prod_type,  p_name:String, tags:String, attr: String , spec: String, images :String, price:Int, maret_pirce:Int, amount:Int, extra:String) = {
    DB.withConnection("products") { implicit  conn =>

      val catetory_id = p_type.id + 2

      Logger.debug("insert products")

      SQL("""insert into products (name, category_id, tags, attr, spec, images, price, market_price, amount,extra ) values ({name},{category_id},array_to_json({tags}::character varying[]), {attr}::json, {spec}::json, array_to_json({images}::character varying[]), {price},{market_price}, {amount}, {extra}::json )""").on("name"->p_name, "category_id"->catetory_id , "tags"->tags, "attr"->attr, "spec"->spec, "images"->images, "price"->price, "market_price"->maret_pirce, "amount"->amount, "extra"->extra).execute()

    }
  }


  /**
    * 根据表的内容取得数据
    */
  val parser: RowParser[Map[String, Any]] =
    SqlParser.folder(Map.empty[String, Any]) { (map, value, meta) =>
      Right(map + (meta.column.qualified -> value))
    }


  /**
    * 产品列表
    * @param p_type
    * @return
    */
  def list(p_type: Prod_Type.prod_type) : List[Map[String, Any]] = {
    DB.withConnection("products") { implicit conn =>
      val catetory_id = p_type.id + 2
      SQL(""" select * from products where category_id = {category_id}""").on("category_id"->catetory_id).as(parser.*)

    }
  }

}

object Prod_Type extends Enumeration {
  type prod_type = Value
  val hzp, ps, fs = Value
}

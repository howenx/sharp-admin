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
  def update(product_id:Long , p_type: Prod_Type.prod_type,  p_name:String, tags:String, attr: String , spec: String, images :String, price:Int, maret_pirce:Int, amount:Int, extra:String) = {
    DB.withConnection("products") { implicit  conn =>

      val catetory_id = p_type.id

      val status = "T"

      SQL(""" update products set name = {name}, category_id = {category_id}, tags = array_to_json({tags}::character varying[])::jsonb, attr = {attr}::jsonb, spec = {spec}::jsonb, images = array_to_json({images}::character varying[])::jsonb, price = {price}, market_price = {market_price}, amount = {amount}, extra = {extra}::jsonb, status = {status} where product_id = {product_id} """).on("name"->p_name, "category_id"->catetory_id , "tags"->tags, "attr"->attr, "spec"->spec, "images"->images, "price"->price, "market_price"->maret_pirce, "amount"->amount, "extra"->extra, "status"->status, "product_id"->product_id).execute()

    }
  }


  def init (p_type: Prod_Type.prod_type, p_name:String, kr_string:String) = {
    DB.withConnection("products") { implicit  conn =>
      val category_id = p_type.id
      SQL(""" insert into products ( name, category_id, kr_string )values ( {name}, {category_id}, {kr_string}::jsonb) """).on("name"->p_name, "category_id"->category_id, "kr_string"->kr_string).execute()

    }
  }

  def update_krstring(product_id:Long , kr_string:String): Unit = {
    DB.withConnection("products") { implicit  conn =>

      SQL(""" update products set kr_string = {kr_string}::jsonb where product_id = {product_id}""").on("kr_string"->kr_string, "product_id"->product_id).execute()

    }
  }

  def append(product_id:Long, audit_string:String) = {
    DB.withConnection("products") { implicit  conn =>

      SQL(""" update products set extra = jsonb_merge(extra , {audit_string}::jsonb) where product_id = {product_id}""").on("audit_string"->audit_string, "product_id"->product_id).execute()

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
  def list(p_type: Prod_Type.prod_type, start :Int, size: Int) : List[Map[String, Any]] = {
    DB.withConnection("products") { implicit conn =>
      val catetory_id = p_type.id

      SQL(""" select count(*) over () as count , * from products where category_id = {category_id} order by product_id desc offset ({start} -1) * {size} limit {size} """).on("category_id"->catetory_id, "start"->start, "size"->size).as(parser.*)

    }
  }

  def download_list () : List[Map[String, Any]] = {
    DB.withConnection("products") { implicit conn =>

      SQL(""" select  * from products where status = 'T' order by product_id desc """).as(parser.*)

    }
  }

  def find_by_id(id:Long) : Option[Map[String,Any]]= {
    DB.withConnection("products") { implicit  conn =>

      SQL(""" select * from products where product_id = {id} """).on("id"->id).as(parser.*).headOption

    }
  }

}

object Prod_Type extends Enumeration {
  type prod_type = Value
  val all, hzp, ps, fs = Value
}

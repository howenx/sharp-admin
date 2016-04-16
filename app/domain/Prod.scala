package domain

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
  def update(product_id:Long , p_type: Prod_Type.prod_type,  p_name:String, tags:String, attr: String , spec: String, images :String, price:Int, amount:Int, extra:String) = {
    DB.withConnection("products") { implicit  conn =>

      val catetory_id = p_type.id

      //val status = "T"

      SQL(""" update products set name = {name}, category_id = {category_id}, tags = array_to_json({tags}::character varying[])::jsonb, attr = {attr}::jsonb, spec = {spec}::jsonb, images = array_to_json({images}::character varying[])::jsonb, price = {price},  amount = {amount}, extra = {extra}::jsonb , status = 'T' where product_id = {product_id} """).on("name"->p_name, "category_id"->catetory_id , "tags"->tags, "attr"->attr, "spec"->spec, "images"->images, "price"->price,  "amount"->amount, "extra"->extra,  "product_id"->product_id).execute()

    }
  }


  def init (p_type: Prod_Type.prod_type, p_name:String, bar_code:String, amount:Int, kr_string:String, user_id:Long) = {
    DB.withConnection("products") { implicit  conn =>
      val category_id = p_type.id
      SQL(""" insert into products ( name, category_id, bar_code, amount, kr_string, created_id )values ( {name}, {category_id}, {amount}, {kr_string}::jsonb, {created_id}) """).on("name"->p_name, "category_id"->category_id, "bar_code"->bar_code, "amount"->amount, "kr_string"->kr_string, "created_id"->user_id).execute()

    }
  }

  def update_krstring(product_id:Long , name:String, bar_code:String, amount:Int, kr_string:String): Unit = {
    DB.withConnection("products") { implicit  conn =>

      SQL(""" update products set name = {name}, bar_code = {bar_code}, amount = {amount}, kr_string = {kr_string}::jsonb, update_dt = now()::timestamp(0) without time zone where product_id = {product_id}""").on("name"->name, "bar_code"->bar_code, "amount"->amount, "kr_string"->kr_string, "product_id"->product_id).execute()

    }
  }

  def append(product_id:Long, audit_string:String) = {
    DB.withConnection("products") { implicit  conn =>

      SQL(""" update products set extra = jsonb_merge(extra , {audit_string}::jsonb), status = 'A' where product_id = {product_id}""").on("audit_string"->audit_string, "product_id"->product_id).execute()

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
  def list(p_type: Prod_Type.prod_type, status:Option[String], start :Int, size: Int) : List[Map[String, Any]] = {
    DB.withConnection("products") { implicit conn =>
      val catetory_id = p_type.id

      val s = start match {
        case 0 =>
          1
        case _ =>
          start
      }
      status match {
        case Some(stat) =>
          SQL( """ select row_number() over(ORDER BY product_id desc ) as row, count(product_id) over () as count , * from products where category_id = {category_id} and status = {stat} and status <> 'N' order by product_id desc offset ({start} -1) * {size} limit {size} """).on("category_id" -> catetory_id, "stat" -> stat, "start" -> s, "size" -> size).as(parser.*)
        case None =>
          SQL( """ select row_number() over(ORDER BY product_id desc ) as row, count(product_id) over () as count , * from products where category_id = {category_id}  and status <> 'N' order by product_id desc offset ({start} -1) * {size} limit {size} """).on("category_id" -> catetory_id, "start" -> s, "size" -> size).as(parser.*)
      }

    }
  }

  /**
    * 根据用户产品列表
    * @param p_type
    * @return
    */
  def list(p_type: Prod_Type.prod_type, user_id:Long, status:Option[String],start :Int, size: Int) : List[Map[String, Any]] = {
    DB.withConnection("products") { implicit conn =>
      val catetory_id = p_type.id
      val s = start match {
        case 0 =>
          1
        case _ =>
          start
      }

      status match {
        case Some(stat) =>
          SQL(""" select row_number() over(ORDER BY product_id desc) as row, count(product_id) over () as count , * from products where category_id = {category_id} and created_id = {user_id} and status = {stat} and status <> 'N'  order by product_id desc offset ({start} -1) * {size} limit {size} """).on("category_id"->catetory_id, "user_id"->user_id, "stat"->stat, "start"->s, "size"->size).as(parser.*)
        case None =>
          SQL(""" select row_number() over(ORDER BY product_id desc) as row, count(product_id) over () as count , * from products where category_id = {category_id} and created_id = {user_id} and status <> 'N'  order by product_id desc offset ({start} -1) * {size} limit {size} """).on("category_id"->catetory_id, "user_id"->user_id, "start"->s, "size"->size).as(parser.*)
      }



    }
  }

  def list_admin(p_type: Prod_Type.prod_type,  status:Option[String],code:Option[String], name:Option[String],start :Int, size: Int) : List[Map[String, Any]] = {
    val ret = DB.withConnection("products") { implicit conn =>
      val catetory_id = p_type.id
      val s = start match {
        case 0 =>
          1
        case _ =>
          start
      }

      status match {
        case Some(stat) =>
          SQL( """ select row_number() over(ORDER BY product_id desc ) as row, count(product_id) over () as count , * from products where category_id = {category_id} and status = {stat} and status <> 'N' order by product_id desc offset ({start} -1) * {size} limit {size} """).on("category_id" -> catetory_id, "stat" -> stat, "start" -> s, "size" -> size).as(parser.*)
        case None =>
          code match {
            case Some(c) =>

              SQL( """ select row_number() over(ORDER BY product_id desc ) as row, count(product_id) over () as count , * from products where bar_code like '%' || {bar_code} || '%' and category_id = {category_id}  and status <> 'N' order by product_id desc offset ({start} -1) * {size} limit {size} """).on("bar_code"->code, "category_id" -> catetory_id, "start" -> s, "size" -> size).as(parser.*)
            case None =>
              name match {
                case Some(n) =>
                  SQL( """ select row_number() over(ORDER BY product_id desc ) as row, count(product_id) over () as count , * from products where name like '%' || {name} || '%' and category_id = {category_id}  and status <> 'N' order by product_id desc offset ({start} -1) * {size} limit {size} """).on("name"->n, "category_id" -> catetory_id, "start" -> s, "size" -> size).as(parser.*)
                case None =>
                  SQL( """ select row_number() over(ORDER BY product_id desc ) as row, count(product_id) over () as count , * from products where category_id = {category_id}  and status <> 'N' order by product_id desc offset ({start} -1) * {size} limit {size} """).on("category_id" -> catetory_id, "start" -> s, "size" -> size).as(parser.*)

              }
          }


      }


    }

    var results:List[Map[String, Any]] = List()
    DB.withConnection("account") { implicit conn =>
      ret.map { m =>
         val created_id = m("products.created_id").toString.toInt
         val user = SQL( """ select nickname from "ID" where user_id = {user_id} and status = 'Y'  """).on("user_id"->created_id).as(parser.*).headOption
        //Logger.debug(user.get.toString())

        val r = m ++ user.get

        //Logger.debug(r.toString())

        results = results ::: List(r)

      }
      Logger.debug(results.toString())
      results

    }

  }



  def delete (product_id:Long) :Option[Int]= {
    DB.withConnection("products") { implicit conn =>
      val ret = SQL(""" update products set status = 'N' where product_id = {product_id} returning category_id """).on("product_id"->product_id).as(parser.*)
      ret.headOption map {head =>
        //Prod_Type.apply(head("products.category_id").toString.toInt)
        head("products.category_id").toString.toInt
      }


    }
  }

  def download_list () : List[Map[String, Any]] = {
    DB.withConnection("products") { implicit conn =>

      SQL(""" select  * from products where status = 'A' order by product_id desc """).as(parser.*)

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

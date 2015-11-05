package entity

import play.api.db.DB
import anorm._
import play.api.Play.current
/**
  * Created by handy on 15/11/5.
  * kakao china
  */
object Prod {

  /**
    * 商品数据添加
    * @return
    */
  def insert() = {
    DB.withConnection("products") { implicit  conn =>
      SQL("""insert into product (sku, name, category_id,images,price, market_price ) values ()""").on()

    }
  }

}

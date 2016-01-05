package controllers


import java.io.{FileInputStream, File, FileOutputStream}
import java.net.{URI, URLEncoder}
import javax.inject.{Named, Inject, Singleton}
import actor.OSS
import akka.actor.ActorRef
import com.fasterxml.jackson.databind.JsonNode
import entity.{User, Prod_Type, Prod, User_Type}
import modules.OSSClientProvider

import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.usermodel.{Cell, CellStyle, IndexedColors}
import org.apache.poi.xssf.usermodel.{XSSFCellStyle, XSSFWorkbook}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.Json
import play.api.mvc.{Controller}
import play.api.{Configuration, Logger}
import play.api.Play.current
import play.api.i18n.{Lang, MessagesApi, I18nSupport}


import scala.collection.immutable.HashMap
import scala.util.Try

/**
  * Created by handy on 15/10/29.
  * kakao china
  */
@Singleton
@Inject
class Application @Inject()(val messagesApi: MessagesApi, val oss_client: OSSClientProvider, configuration: Configuration, @Named("oss") oss: ActorRef) extends Controller with Secured with I18nSupport {

  val size = 15

  def welcome(lang: String) = withUser { user => {
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
          Redirect(routes.Application.list_supply(None,None))
        case User_Type.TRANSLATION =>
          Redirect(routes.Application.list(None,None))
        case User_Type.ADMIN =>
          Redirect(routes.Application.list_admin(None,None))
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
    * 供应商list
    * @param id
    * @param stat
    * @param start
    * @return
    */
  def list_supply(id: Option[Int], stat:Option[String],start: Int) = withUser { user => {
    implicit request => {

      val m = request.queryString.map { case (k, v) => k -> v.head }
      val status:Option[String] = m.get("stat") match {
        case Some("") =>
          None
        case s =>
          s
      }

      id match {
        case Some(category_id) =>
          if (Prod_Type.values.exists(_.id == id.get)) {
            val ret = Prod.list(Prod_Type.apply(id.get), user.id, status, start, size)
            Logger.debug(ret.toString())
            Logger.debug(s"status is $status---")
            val r: List[Map[String, Any]] = ret match {
              case Nil =>
                //val any_id:Any = id.get
                //初始化一个空的内容
                List(Map("products.category_id" -> id.get, ".row" ->"",".count" -> 0, "products.product_id" -> "", "products.name" -> "", "products.status" -> "","products.update_dt"->""))
              case _ =>
                ret
            }
            start match {
              case 0 =>

                Ok(views.html.supply.my_list("cn", user, ret, start))
              case _ =>
                Ok(views.html.supply.my_list_data("cn", user, r, status, start))
            }
          }
          else {
            BadRequest("no such element")
          }

        case None =>
          val ret = Prod.list(Prod_Type.hzp, user.id,None, start, size)
          //Logger.debug(ret.toString())
          Ok(views.html.supply.my_list("cn", user, ret, start))
      }
    }
  }

  }

  /**
    * 翻译人员list
    * @param id
    * @param stat
    * @param start
    * @return
    */
  def list(id: Option[Int], stat:Option[String], start: Int) = withUser { user => {
    implicit request => {
      val m = request.queryString.map { case (k, v) => k -> v.head }

      val status:Option[String] = m.get("stat") match {
        case Some("") =>
          None
        case s =>
          s
      }
      id match {
        case Some(category_id) =>
          if (Prod_Type.values.exists(_.id == id.get)) {
            val ret = Prod.list(Prod_Type.apply(id.get), status, start, size)
            Logger.debug(ret.toString())
            val r:List[Map[String, Any]] = ret match {
              case Nil =>
                //val any_id:Any = id.get
                //初始化一个空的内容
                List(Map("products.category_id" -> id.get, ".row" ->"",".count" -> 0, "products.product_id" -> "", "products.name" -> "", "products.status" -> "","products.update_dt"->""))
              case _ =>
                ret
            }

            start match {
              case 0 =>
                Ok(views.html.supply.list("cn", user, ret, start))
              case _ =>
                Ok(views.html.supply.list_data("cn", user, r,status, start))
            }
          }
          else {
            Ok(views.html.error404())
          }
        case None =>
          val ret = Prod.list(Prod_Type.hzp, None, start, size)
          //Logger.debug(ret.toString())
          Ok(views.html.supply.list("cn", user, ret, start))
      }
    }
  }

  }

  /**
    *admin list
    * @param id
    * @param stat
    * @param start
    * @return
    */
  def list_admin(id: Option[Int], stat:Option[String], start: Int) = withUser { user => {
    implicit request => {
      val m = request.queryString.map { case (k, v) => k -> v.head }
      Logger.debug(m.toString())
      val status:Option[String] = m.get("stat") match {
        case Some("") =>
          None
        case s =>
          s
      }

      val code:Option[String] = m.get("code") match {
        case Some("") =>
          None
        case c =>
          c
      }

      val name:Option[String] = m.get("name") match {
        case Some("") =>
          None
        case n =>
          n
      }

      id match {
        case Some(category_id) =>
          if (Prod_Type.values.exists(_.id == id.get)) {
            val ret = Prod.list_admin(Prod_Type.apply(id.get), status, code, name, start, size)
            //Logger.debug(ret.toString())
            val r:List[Map[String, Any]] = ret match {
              case Nil =>
                //val any_id:Any = id.get
                //初始化一个空的内容
                List(Map("products.category_id" -> id.get, ".row" ->"",".count" -> 0, "products.product_id" -> "", "products.name" -> "", "products.status" -> "","products.update_dt"->"","ID.nickname"->""))
              case _ =>
                ret
            }

            start match {
              case 0 =>
                Ok(views.html.supply.admin_list("cn", user, ret, start))
              case _ =>
                Ok(views.html.supply.admin_list_data("cn", user, r,status, start))
            }
          }
          else {
            Ok(views.html.error404())
          }
        case None =>
          val ret = Prod.list_admin(Prod_Type.hzp, None, None, None, start, size)
          //Logger.debug(ret.toString())
          Ok(views.html.supply.admin_list("cn", user, ret, start))
      }
    }
  }

  }

  /**
    * 追加
    * @param id
    * @return
    */
  def append(id: Long) = withUser { user => {
    implicit request => {
      Prod.find_by_id(id) match {
        case Some(p) =>

          Ok(views.html.supply.append("cn", user, p))
        case None =>
          BadRequest("no product.")
      }
    }
  }

  }


  /**
    * 杭州报关单下载
    * @return
    */
  def download_hangzhou() = withUser { user => {
    implicit request => {
      val file = new File("/tmp/hangzhou.xlsx")
      val fileOut = new FileOutputStream(file);
      val wb = new XSSFWorkbook
      val sheet = wb.createSheet("备案")

      //创建
      var rNum = 0
      var row = sheet.createRow(rNum)

      var cNum: Int = 0

      var cell = row.createCell(cNum)
      cell.setCellValue("备案明细表")

      rNum += 1
      row = sheet.createRow(rNum)
      cell = row.createCell(cNum)
      cell.setCellValue("流水号:")

      rNum += 1
      row = sheet.createRow(rNum)
      cell = row.createCell(cNum)
      cell.setCellValue("帐册号:")

      rNum += 1
      row = sheet.createRow(rNum)
      cell = row.createCell(cNum)
      cell.setCellValue("序列号")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("商品条码")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("商品编码")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("行邮税号")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("品名")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("规范申报(品牌、型号、成分含量等)")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("规格")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("申报单位")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("币制")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("成交数量")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("单价")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("总价")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("原产国")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("净重")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("毛重")

      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("第一/第二计量单位")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("功能用途")

      sheet.addMergedRegion(CellRangeAddress.valueOf("A1:Q1"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("B2:Q2"))

      var idx = 1;
      Prod.download_list().map { map =>
        rNum += 1
        val extra = Json.parse(map("products.extra").toString)
        val kr_string = Json.parse(map("products.kr_string").toString)

        val spec = Json.parse(map("products.spec").toString)
        val attr = Json.parse(map("products.attr").toString)
        cNum = 0
        val r = sheet.createRow(rNum)
        cell = r.createCell(cNum)

        cell.setCellValue("" + idx)

        idx +=1

        //cNum +=1
        //cell = r.createCell(cNum)
        //cell.setCellValue(map("products.product_id").toString)

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "商品条码").asOpt[String].getOrElse(""))

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "商品编码").asOpt[String].getOrElse(""))

        cNum += 1
        cell = r.createCell(cNum)
        map("products.category_id") match {
          case 1 =>
            //hzp
            cell.setCellValue("09000000")
          case 2 =>
            //ps
            cell.setCellValue("08010000")
          case 3 =>
            cell.setCellValue("04019900")
        }

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue(map("products.name").toString)

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "规范申报").asOpt[String].getOrElse(""))

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "规格").asOpt[String].getOrElse(""))

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "单位").asOpt[String].getOrElse(""))

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue("美元")

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue(map("products.amount").toString)

//        cNum += 1
//        cell = r.createCell(cNum)
//        cell.setCellValue(extra \ "market_price_usd").asOpt[String].getOrElse("0").toDouble * map("products.amount").toString.toInt)

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((kr_string \ "usd_price").asOpt[String].getOrElse(""))

        cNum += 1
        cell = r.createCell(cNum)
        //val total = Try((kr_string \ "usd_price").asOpt[String].getOrElse("0").toDouble).getOrElse(0) * map("products.amount").toString.toInt

        val total = (kr_string \ "usd_price").asOpt[String] match {
          case Some(f) =>
            if(f.isEmpty) {
              0
            }
            else {
              f.toDouble * map("products.amount").toString.toInt
            }
          case None =>
            0
        }

        cell.setCellValue(total.toString)

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "原产地").asOpt[String].getOrElse("韩国"))

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "毛重").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "净重").asOpt[String].getOrElse(""))

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue("g")

        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "功能").asOpt[String].getOrElse(""))


      }

      wb.write(fileOut);
      fileOut.close();

      Ok.sendFile(content = file, fileName = _ => file.getName)
    }
  }

  }

  def download_shanghai() = withUser { user => {
    implicit request => {
      //val file = new File("上海备案.xlsx")
      val file = new File("/tmp/shanghai.xlsx")
      val fileOut = new FileOutputStream(file);
      val wb = new XSSFWorkbook
      val sheet = wb.createSheet("商品备案表")
      var rNum = 0
      val row = sheet.createRow(rNum)
      //row.setHeightInPoints(40);
      rNum = rNum + 1;
      val row1 = sheet.createRow(rNum)
      val style = wb.createCellStyle()
      //style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex())
      //style.setFillPattern(CellStyle.SOLID_FOREGROUND);
      var cNum: Int = 0

      var cell = row.createCell(cNum)
      cell.setCellValue("*编号")
      //cell.setCellStyle(style)
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("*品牌")
      //cell.setCellStyle(style)
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("*商品名称\t(中文)")
      //cell.setCellStyle(style)
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("*商品名称\t(英文)")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("其他名称(非必填)")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("商品描述")
      cell = row1.createCell(cNum)
      cell.setCellValue("*型号")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("*规格")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("*产地")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("*功能")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("*用途")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("*成份")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("出厂日期")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("其他备注")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("商品图片(100*100)")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("*商品单位(计税单位)")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("*商品数量")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("*商品单价")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("*业务类型")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("*申报关区")
      cNum += 1

      cell = row.createCell(cNum)
      cell.setCellValue("自贸专区商品完成区内备案后必填")
      cell = row1.createCell(cNum)
      cell.setCellValue("货号")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("物资序号")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("申报单位")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("申报数量")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("毛重KG")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("净重KG")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("规则型号")
      cNum += 1
      cell = row1.createCell(cNum)
      cell.setCellValue("货主编码")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("税则号")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("税率")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("完税价格")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("预估关税")
      cNum += 1
      cell = row.createCell(cNum)
      cell.setCellValue("商品含税价")

      sheet.addMergedRegion(CellRangeAddress.valueOf("F1:M1"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("T1:AA1"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("B1:B2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("C1:C2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("D1:D2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("E1:E2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("N1:N2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("O1:O2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("P1:P2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("Q1:Q2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("R1:R2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("S1:S2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("AB1:AB2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("AC1:AC2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("AD1:AD2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("AE1:AE2"))
      sheet.addMergedRegion(CellRangeAddress.valueOf("AF1:AF2"))

      //load from database
      //import data
      Prod.download_list().map { map =>
        //Logger.debug(map.toString())
        //        map.map { m=>
        //          m("products.extra")
        //        }
        rNum += 1
        val extra = Json.parse(map("products.extra").toString)
        val spec = Json.parse(map("products.spec").toString)
        val attr = Json.parse(map("products.attr").toString)
        val kr_string = Json.parse(map("products.kr_string").toString)
        cNum = 0
        val r = sheet.createRow(rNum)
        cell = r.createCell(cNum)
        //京东SKU
        cell.setCellValue((extra \ "商品编码").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "品牌").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue(map("products.name").toString)
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((kr_string \ "英文名称").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "其他名称").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "型号").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "规格").asOpt[String].getOrElse("件"))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "原产地").asOpt[String].getOrElse("韩国"))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "用途").asOpt[String].getOrElse("装饰"))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "用途").asOpt[String].getOrElse("佩戴"))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "成份").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "出厂日期").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "其他备注").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "商品图片").asOpt[String].getOrElse(""))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((spec \ "单位").asOpt[String].getOrElse("件"))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue(map("products.amount").toString)
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue(map("products.market_price").toString)
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "业务类型").asOpt[String].getOrElse("一般进口"))
        cNum += 1
        cell = r.createCell(cNum)
        cell.setCellValue((extra \ "申报关区").asOpt[String].getOrElse("2244"))

        cNum += 5
        //cell = r.createCell(cNum)
        //cell.setCellValue((extra \ "毛重").asOpt[String].getOrElse(""))
        cNum += 1
        //cell = r.createCell(cNum)
        //cell.setCellValue((extra \ "净重").asOpt[String].getOrElse(""))
        cNum += 3
        cell = r.createCell(cNum)
        map("products.category_id") match {
          case 1 =>
            //hzp
            cell.setCellValue("09000000")
          case 2 =>
            //ps
            cell.setCellValue("08010000")
          case 3 =>
            cell.setCellValue("04019900")
        }



      }

      wb.write(fileOut);
      fileOut.close();
      //val f_name = URLEncoder.encode("上海备案.xlsx","utf-8")
      //val uri = new URI(null, null, "上海备案.xlsx", null);
      //val f_name = uri.toASCIIString();
      //      val f = "上海备案.xlsx"
      //      val bytes = f.getBytes("utf-8")
      //      val buff = new StringBuilder(bytes.length << 2)
      //      buff.append("=?UTF-8?Q?")
      //      bytes.map { b=>
      //        val unsignedByte = b & 0xFF
      //        buff.append('=').append(HEX_CHARS(unsignedByte >> 4)).append(HEX_CHARS(unsignedByte & 0xF))
      //      }
      //      val f_name = buff.append("?=").toString()
      //Ok.sendFile( content = file, fileName = _ => f_name).withHeaders("Content-Type" -> "application/x-download; character=utf8")
      Logger.debug(file.getName)
      Ok.sendFile(content = file, fileName = _ => file.getName)
    }
  }


  }

  def upload() = withUser { user => {
    implicit request => {
      request.body.asMultipartFormData match {

        case Some(map) =>
          //var filename: String = null
          map.files.map { f =>
            Logger.debug("update file " + f.filename)
            val filein = new FileInputStream(f.ref.file)
            val workbook = new XSSFWorkbook(filein)
            val sheet = workbook.getSheetAt(0)
            val row_iter = sheet.rowIterator()
            while (row_iter.hasNext()) {
              val row = row_iter.next()
              if(row.getRowNum == 0) {
                //key interator
                val cell_iter = row.cellIterator()
                //val header =
              }else {
                // value interator
                val cell_iter = row.cellIterator()
                while(cell_iter.hasNext()) {
                  val cell = cell_iter.next()
                  cell.getCellType match {
                    case Cell.CELL_TYPE_NUMERIC =>
                      Logger.debug(cell.getNumericCellValue.toString)
                    case Cell.CELL_TYPE_STRING =>
                      Logger.debug(cell.getStringCellValue)
                  }
                }
              }

            }

          }
      }
    }
      Redirect(routes.Application.list_supply(None,None))
  }
  }

  val hzp_map =  HashMap("URL"->"url", "상품명칭"->"name", "상품명칭(english)"->"英文名称", "브랜드"->"品牌", "바코드"->"bar_code", "원가（KRW）"->"price","판매가격（KRW）"->"market_price", "원가（USD）"->"")

  //val HEX_CHARS = Array( '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' )

  def append_add() = withUser { user => {
    implicit request => {
      request.body.asFormUrlEncoded match {
        case Some(map) =>
          var m = map.map { case (k, v) => k -> v.head }
          val product_id = m("product_id")
          m -= ("product_id")
          val p_type = m("p_type")
          m -= ("p_type")
          Logger.debug(s"$m")
          val audit_string = Json.toJson(m).toString()
          Prod.append(product_id.toLong, audit_string)
          Redirect(routes.Application.list(Some(Prod_Type.withName(p_type).id), None))
        case None =>
          BadRequest("append error")
      }
    }

  }
  }

  def translation(id: Long) = withUser { user => {
    implicit request => {
      Prod.find_by_id(id) match {
        case Some(p) =>

          Ok(views.html.supply.translation("cn", user, p))
        case None =>
          BadRequest("no product.")
      }

    }
  }

  }

  def show(id: Long) = withUser { user => {
    implicit request => {
      Ok("ok")
    }
  }

  }

  def supply_update(id: Long) = withUser { user => {
    implicit request => {
      Prod.find_by_id(id) match {
        case Some(p) =>

          Ok(views.html.supply.update("cn", user, p))
        case None =>
          BadRequest("no product.")
      }
    }
  }

  }

  def supply_delete() = withUser { user => {
    implicit request => {
      request.body.asFormUrlEncoded match {
        case Some(map) =>
          val m = map.map { case (k, v) => k -> v.head }
          val id = m("id").toString().toLong;
          val cat = Prod.delete(id)
          Ok(cat.getOrElse("").toString)
        case _ =>
          Ok("")
      }
    }
  }

  }

  def supply_init(id:Long) = withUser { user => {
    implicit request => {
      Ok(views.html.supply.supply("cn", user, id.toInt))
      }
      //
    }
  }



  def supply_init_add() = withUser { user => {
    implicit request => {
      var ptype:Int =0
      request.body.asMultipartFormData match {

        case Some(map) =>
          var filename: String = null
          map.files.map { f =>
            Logger.debug("update file " + f.filename)
            filename = "supply" + "/" + DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime) + "/" + System.currentTimeMillis + f.filename.replaceFirst("^[^.]*", "")

            oss ! OSS(f.ref, filename)
          }

          map.asFormUrlEncoded match {
            case map =>
              var m = map.map { case (k, v) => k -> v.head }
              if (filename != null) {
                filename = configuration.getString("oss.prefix").get + filename
                m += ("image" -> filename)
              }
              Logger.debug(m.toString())
              val p_type = m("p_type")
              m -= ("p_type")
              val amount = m("amount").toInt
              //m -= ("amount")
              val name = m("name")

              val bar_code = m("bar_code")
              //m -= ("bar_code")
              //m += ("添加时间" -> DateTimeFormat.longDateTime().print(new DateTime()))
              //m += ("添加人" -> user.id.toString)
              val kr_string = Json.toJson(m).toString()
              ptype = Prod_Type.withName(p_type).id;
              Prod.init(Prod_Type.withName(p_type), name, bar_code, amount, kr_string,user.id)

          }

          Redirect(routes.Application.list_supply(Some(ptype),None))
        case None =>
          Ok(views.html.supply.supply("cn", user, 0))
      }
      //
    }
  }

  }

  def update_kr() = withUser { user => {
    implicit request => {
      var ptype:Int =0
      request.body.asMultipartFormData match {
        case Some(map) =>
          var filename: String = null
          map.files.map { f =>
            Logger.debug("update file " + f.filename)
            filename = "supply" + "/" + DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime) + "/" + System.currentTimeMillis + f.filename.replaceFirst("^[^.]*", "")
            oss ! OSS(f.ref, filename)
          }
          map.asFormUrlEncoded match {
            case map =>
              var m = map.map { case (k, v) => k -> v.head }
              if (filename != null) {
                filename = configuration.getString("oss.prefix").get + filename
                m += ("image" -> filename)
              }
              Logger.debug(m.toString())
              val p_type = m("p_type")
              m -= ("p_type")
              ptype = Prod_Type.withName(p_type).id
              val name = m("name")
              val product_id = m("product_id")
              m -= ("product_id")
              val amount = m("amount").toInt

              val bar_code = m("bar_code")
              m += ("修改时间" -> DateTimeFormat.longDateTime().print(new DateTime()))
              val kr_string = Json.toJson(m).toString()
              Prod.update_krstring(product_id.toLong, name, bar_code, amount, kr_string)
              Redirect(routes.Application.list_supply(Some(ptype),None))
          }

        case None =>
          BadRequest("error")
      }

    }
  }

  }

  /**
    * 供应商提供信息表格
    * @return
    */
  def supply() = withUser { user => {
    implicit request => {
      request.body.asMultipartFormData match {
        case Some(map) =>
          var filename: String = null
          map.files.map { f =>
            Logger.debug("update file " + f.filename)
            filename = "supply" + "/" + DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime) + "/" + System.currentTimeMillis + f.filename.replaceFirst("^[^.]*", "")
            oss ! OSS(f.ref, filename)
          }
          map.asFormUrlEncoded match {
            case map =>
              //商品名称
              //转化一下内容
              var m = map.map { case (k, v) => k -> v.head }
              val p_type = m("p_type")
              m -= ("p_type")
              val product_id = m("product_id")
              m -= ("product_id")
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
                  ///val market_price = Try(m("market_price").toInt).getOrElse(0)
                  //m -= "market_price"

                  //数量
                  val amount = Try(m("amount").toInt).getOrElse(0)
                  m -= "amount"

                  //得到属性
                  //Logger.debug(m.filter(_._1.ex "批次").toString())
                  //Logger.debug(Json.toJson(m.filter(_._1 == "批次")).toString())
                  val attr_set = Set("颜色")
                  val attr = Json.toJson(m.filterKeys(attr_set.contains(_))).toString()
                  m --= attr_set

                  //得到图片
                  //val images_set = ("image")
                  //val images = m.filterKeys(images_set.contains(_)).map(_._2).mkString("{", ",", "}")
                  //m -= ("image")
                  if (filename != null) {
                    filename = configuration.getString("oss.prefix").get + filename
                  }else {
                    filename = ""
                  }
                  val images = s"{$filename}";

                  //得到规格
                  val spec_set = Set("品牌", "成份", "肤质", "类别", "功能", "型号", "规格", "用途", "原产地", "单位", "尺寸")
                  val spec = Json.toJson(m.filterKeys(spec_set.contains(_))).toString()
                  m --= spec_set

                  //得到扩展数据
                  val extra = Json.toJson(m).toString()

                  Prod.update(product_id.toLong, Prod_Type.hzp, name, tags, attr, spec, images, price, amount, extra)
                  //val price = map(price)
                  Redirect(routes.Application.list(Some(1), None))
                  //map -= (name","price")

                case Prod_Type.ps =>
                  Logger.debug(m.toString())
                  //得到名字属性
                  val name = m("name")
                  m -= "name"

                  val tags = """{"配饰"}"""

                  val price = Try(m("price").toInt).getOrElse(0)
                  m -= "price"

                  //val market_price = Try(m("market_price").toInt).getOrElse(0)
                  ///m -= "market_price"

                  val amount = Try(m("amount").toInt).getOrElse(0)
                  m -= "amount"

                  //得到图片
                  if (filename != null) {
                    filename = configuration.getString("oss.prefix").get + filename
                  }else {
                    filename = ""
                  }
                  val images = s"{$filename}";

                  //得到属性
                  val attr_set = Set("批次")
                  val attr = Json.toJson(m.filterKeys(attr_set.contains(_))).toString()
                  m --= attr_set

                  //得到规格
                  val spec_set = Set("品牌", "成份", "肤质", "类别", "功能", "型号", "规格", "用途", "原产地", "单位", "尺寸","款式","适用人群")
                  val spec = Json.toJson(m.filterKeys(spec_set.contains(_))).toString()
                  m --= spec_set

                  Logger.debug(spec)

                  //得到扩展数据
                  val extra = Json.toJson(m).toString()

                  Logger.debug(extra)

                  Prod.update(product_id.toLong, Prod_Type.ps, name, tags, attr, spec, images, price, amount, extra)

                  Redirect(routes.Application.list(Some(2), None))

                case Prod_Type.fs =>
                  Logger.debug(m.toString())

                  //得到名字属性
                  val name = m("name")
                  m -= "name"

                  val tags = """{"服饰"}"""

                  val price = Try(m("price").toInt).getOrElse(0)
                  m -= "price"

                  //val market_price = Try(m("market_price").toInt).getOrElse(0)
                  //m -= "market_price"

                  val amount = Try(m("amount").toInt).getOrElse(0)
                  m -= "amount"

                  //得到图片
//                  val images_set = ("image")
//                  val images = m.filterKeys(images_set.contains(_)).map(_._2).mkString("{", ",", "}")
//                  m -= ("image")

                  //得到图片
                  if (filename != null) {
                    filename = configuration.getString("oss.prefix").get + filename
                  }else {
                    filename = ""
                  }
                  val images = s"{$filename}";

                  //得到属性
                  val attr_set = Set("颜色")
                  val attr = Json.toJson(m.filterKeys(attr_set.contains(_))).toString()
                  m --= attr_set

                  //得到规格
                  val spec_set = Set("品牌","一级分类", "二级分类", "三级分类","成份", "型号", "规格", "用途", "原产地", "单位", "版型", "上市时间", "里料分类", "图案", "适用年龄", "领型", "衣长", "袖型", "衣门襟", "尺码", "适用类型","风格","流行元素")
                  val spec = Json.toJson(m.filterKeys(spec_set.contains(_))).toString()
                  m --= spec_set

                  Logger.debug(spec)

                  //得到扩展数据
                  val extra = Json.toJson(m).toString()

                  Logger.debug(extra)

                  Prod.update(product_id.toLong, Prod_Type.fs, name, tags, attr, spec, images, price,  amount, extra)

                  Redirect(routes.Application.list(Some(3), None))
              }


          }

        case None =>
          BadRequest("error")

      }

    }

  }

  }
}
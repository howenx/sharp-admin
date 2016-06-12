package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import domain.CouponVo;
import domain.CouponVoDropLog;
import domain.Image;
import domain.User;
import filters.UserAuth;
import net.spy.memcached.MemcachedClient;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import service.CouponVoDropLogService;
import service.CouponVoService;
import service.ImageService;
import util.Ctrip;
import util.ExcelHelper;
import javax.inject.Inject;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static util.SysParCom.*;

/**
 * Created by Sunny Wu on 16/4/25.
 * kakao china.
 * Coupon系统的操作
 */
public class CouponCtrl extends Controller {

    @Inject
    private CouponVoService couponVoService;

    @Inject
    private CouponVoDropLogService couponVoDropLogService;

    @Inject
    private MemcachedClient cache;

    @Inject
    private Ctrip ctrip;

    @Inject
    private ImageService imageService;

    private int pageSize = 10;

    /**
     * 优惠券导入
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result couponImport(String lang) {
        return ok(views.html.couponSystem.couponImport.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 导入优惠券数据
     * @param lang 语言
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result couponDataImport(String lang) {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Map<String, String[]> stringMap = body.asFormUrlEncoded();
        Map<String, String> map = new HashMap<>();
        stringMap.forEach((k,v) -> map.put(k, v[0]));
        Http.MultipartFormData.FilePart filePart = body.getFile("couponFile");
        File file = filePart.getFile();
        List<String> list = null;
        try {
            list = ExcelHelper.exportListFromCsv(file);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (null!=list && list.size()>0) {
            String[] str = list.get(0).split(";");
            if (str.length<11) {
                Logger.error("导入表格的列数"+str.length+"不对");
                return badRequest();
            }
            /**
             * 0-->编码(couponNumber)-->880100000001
             * 1-->名称(couponName)-->스타벅스 아메리카노20140506100431
             * 2-->类型(couponType)-->EXCHANGE
             * 3-->品牌编码(brandCode)-->00CP010100101
             * 4-->品牌名称(brandName)-->Starbucks
             * 5-->商品编码(code)-->A101
             * 6-->原价(standardPrice)-->88
             * 7-->折扣价(price)-->99
             * 8-->发行日期(issuedAt)-->20140506100431
             * 9-->有效期(expiredAt)-->20141230000000
             * 10-->最大有效期(maxExpiredAt)-->20141230000000
             */
            String[] data = null;
            StringBuffer succ=new StringBuffer();
            StringBuffer existErr=new StringBuffer();
            for(int i=1; i<list.size();i++) {
                data = list.get(i).split(";");
                CouponVo couponVo = new CouponVo();
                //数据验证(格式是否正确, 数据是否存在)



                //TODO................




                couponVo.setCouponNumber(Long.valueOf(data[0]));
                couponVo.setCouponName(data[1]);
                couponVo.setCouponType(data[2]);
                couponVo.setBrandCode(data[3]);
                couponVo.setBrandName(data[4]);
                couponVo.setCode(data[5]);
                couponVo.setStandardPrice((Integer.parseInt(data[6])));
                couponVo.setPrice(Integer.parseInt(data[7]));
                couponVo.setIssuedAt(data[8]);
                couponVo.setExpiredAt(data[9]);
                couponVo.setMaximumExpiredAt(data[10]);
                couponVo.setStatus("NOT_USED");
                couponVoService.insertCoupon(couponVo);
                Logger.info("<br/>"+"第"+(i)+"行成功,couponNumber="+couponVo.getCouponNumber());
                succ.append("<br/>" + "第").append(i).append("行成功,couponNumber=").append(couponVo.getCouponNumber());
            }
            if(succ.length()>0){
                stringBuilder.append("<br/>优惠券导入成功:<br/>");
                stringBuilder.append(succ);
            }
        }
        //释放
        assert list != null;
        list.clear();
        String importResult = stringBuilder.toString();
        return ok(views.html.couponSystem.couponImportResult.render("cn",(User) ctx().args.get("user"),importResult));

    }

    /**
     * 优惠券查询
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result couponSearch(String lang) {
        CouponVo couponVo = new CouponVo();
        couponVo.setPageSize(-1);
        couponVo.setOffset(-1);
        int countNum = couponVoService.getCouponsPage(couponVo).size();//取总数
        int pageCount = countNum/pageSize;//共分几页
        if (countNum%pageSize!=0) {
            pageCount = countNum/pageSize+1;
        }
        couponVo.setPageSize(pageSize);
        couponVo.setOffset(0);
        List<CouponVo> couponVoList = couponVoService.getCouponsPage(couponVo);
        return ok(views.html.couponSystem.couponSearch.render(lang, pageSize, countNum, pageCount, couponVoList, (User) ctx().args.get("user")));
    }

    /**
     * 分页查询优惠券信息
     * @param pageNum 请求页数
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result couponSearchAjax(String lang, int pageNum) {
        JsonNode json = request().body().asJson();
        CouponVo couponVo = Json.fromJson(json,CouponVo.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*pageSize;
            couponVo.setPageSize(-1);
            couponVo.setOffset(-1);
            int countNum = couponVoService.getCouponsPage(couponVo).size();//取总数
            int pageCount = countNum/pageSize;//共分几页
            if (countNum%pageSize!=0) {
                pageCount = countNum/pageSize+1;
            }
            couponVo.setPageSize(pageSize);
            couponVo.setOffset(offset);
            List<CouponVo> couponVoList = couponVoService.getCouponsPage(couponVo);
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",couponVoList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",pageSize);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 废弃优惠券        Added by Tiffany Zhu 2016.06.02
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result dropCoupon(String lang){
        //当前时间
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        String strNow = sdfDate.format(now);
        Logger.error("优惠券编码~~~:" + request().body().asJson());

        Long couponNum =Long.valueOf(request().body().asJson().asText());
        CouponVo pCoupon = new CouponVo();
        pCoupon.setCouponNumber(couponNum);
        List<CouponVo> couponVoList = couponVoService.getCoupon(pCoupon);
        pCoupon = couponVoList.get(0);
        pCoupon.setStatus("DROPPED");
        pCoupon.setModifiedAt(strNow);

        CouponVoDropLog couponVoDropLog = new CouponVoDropLog();
        couponVoDropLog.setNoncestr("admin");
        couponVoDropLog.setTimestamp(Long.valueOf(strNow));
        couponVoDropLog.setStatus("Y");
        couponVoDropLog.setParams(couponNum.toString());
        if(couponVoService.updateCoupon(pCoupon) && couponVoDropLogService.addCouponVoDropLog(couponVoDropLog)){
            return ok("success");
        }else {
            return ok("error");
        }

    }

    /**
     * 获取携程url 请求参数token       Added by Tiffany Zhu 2016.06.12
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result getCtripParam(String lang) {
        try {
            Optional<Object> accessToken = Optional.ofNullable(cache.get("AccessToken"));
            if(!accessToken.isPresent()){
                ctrip.getAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        String token = cache.get("AccessToken").toString();
        Logger.error("获取的token是~~~~~:" + token);

        Map<String,String> params = new HashMap<>();
        params.put("AID",AID);                              //联盟账号
        params.put("SID",SID);                              //联盟账号
        params.put("ICODE","");                             //接口名称
        params.put("token",token);                          //验证参数
        params.put("UUID",UUID.randomUUID().toString());    //每次请求唯一值
        params.put("mode","1");                             //接口请求模式
        params.put("format","json");                        //请求体返回体编码格式
        return ok(Json.toJson(params));
    }

    /**
     * 上传图片的临时页面        Added by Tiffany Zhu 2016.06.08
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result image(String lang){
        return ok(views.html.couponSystem.imageUpload.render(lang,IMG_UPLOAD_URL,CTRIPURL,(User) ctx().args.get("user")));
    }

    /**
     * 保存图片上传路径(OSS和ctrip)      Added by Tiffany Zhu 2016.06.12
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result imageSave(String lang){
        JsonNode json = request().body().asJson();
        Image image = Json.fromJson(json,Image.class);
        imageService.addImage(image);
        return ok("SUCCESS");
    }

}

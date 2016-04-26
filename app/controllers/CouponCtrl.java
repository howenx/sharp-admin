package controllers;

import domain.CouponVo;
import domain.User;
import filters.UserAuth;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import service.CouponVoService;
import util.ExcelHelper;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sunny Wu on 16/4/25.
 * kakao china.
 * Coupon系统的操作
 */
public class CouponCtrl extends Controller {

    @Inject
    private CouponVoService couponVoService;

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
             * 6-->原价(price)-->99
             * 7-->折扣价(standardPrice)-->88
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
                couponVo.setCouponNumber(data[0]);
                couponVo.setCouponName(data[1]);
                couponVo.setCouponType(data[2]);
                couponVo.setBrandCode(data[3]);
                couponVo.setBrandName(data[4]);
                couponVo.setCode(data[5]);
                couponVo.setPrice(Integer.parseInt(data[6]));
                couponVo.setStandardPrice((Integer.parseInt(data[7])));
                couponVo.setIssuedAt(data[8]);
                couponVo.setExpiredAt(data[9]);
                couponVo.setMaxExpiredAt(data[10]);
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



}

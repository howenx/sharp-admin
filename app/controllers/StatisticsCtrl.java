package controllers;

import com.google.inject.Inject;
import entity.SID;
import entity.SOrder;
import entity.User;
import filters.UserAuth;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.SIDService;
import service.SOrderLineService;
import service.SOrderService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class StatisticsCtrl extends Controller {

    @Inject
    private SIDService sidService;

    @Inject
    private SOrderService sOrderService;

    @Inject
    private SOrderLineService sOrderLineService;

    /**
     * 首页统计数据
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result summary(String lang) {
        //当前日期
        String today = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(new Date().getTime()));
        //根据当前日期查询统计表今日新增用户
        SID sid = new SID();
        sid.setsDate(today);
        List<SID> sidList = sidService.getSIDBy(sid);
        Integer userNum = sidList.size();
        //根据当前日期查询统计表今日新增订单
        SOrder sOrder = new SOrder();
        sOrder.setsDate(today);
        List<SOrder> sOrderList = sOrderService.getSOrderBy(sOrder);
        Integer orderNum = sOrderList.size();
        //根据当前日期查询今日销售量
        Double salesTotalFee = 0.00d;
        for(SOrder so : sOrderList) {
            BigDecimal payTotal = so.getPayTotal();
            salesTotalFee += payTotal.doubleValue();
        }
        Logger.error("新增用户:"+userNum+", 新增订单:"+orderNum+", 销售量:"+salesTotalFee);
        return ok(views.html.summary.summary.render(lang, (User) ctx().args.get("user"), userNum, orderNum, salesTotalFee));
    }


}

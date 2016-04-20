package controllers;

import domain.User;
import domain.order.Order;
import filters.UserAuth;
import middle.SupplierMiddle;
import play.i18n.Lang;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiffany on 16/4/19.
 */
public class SupplyCtrl extends Controller {

    @Inject
    SupplierMiddle supplierMiddle;
    /**
     * 供货商处理订单      Added by Tiffany Zhu 2016.04.19
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result orderProcess(String lang){
        User user = (User) ctx().args.get("user");
        //全部订单
        List<Object[]> objectList = supplierMiddle.getOrderAll(lang,user);
        //未处理订单
        List<Object[]> unProcessedList = new ArrayList<>();
        for(Object[] object :objectList){
            String orderStatus = object[4].toString();
            //未处理状态
            if(orderStatus.equals(Messages.get(new Lang(Lang.forCode(lang)),"order.supplier.state5"))){
                unProcessedList.add(object);
            }
        }












        return ok(views.html.supply.supplierProcess.render(lang,objectList,unProcessedList,(User) ctx().args.get("user")));
    }


}

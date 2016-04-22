package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import domain.SupplyOrder;
import domain.User;
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
        if(objectList != null){
            for(Object[] object :objectList){
                String orderStatus = object[4].toString();
                //未处理状态
                if(orderStatus.equals(Messages.get(new Lang(Lang.forCode(lang)),"order.supplier.state5"))){
                    unProcessedList.add(object);
                }
            }
        }

        //查询用的实例
        SupplyOrder supplyOrderPara = new SupplyOrder();
        supplyOrderPara.setUpdateUser(user.id());
        //已打包
        supplyOrderPara.setState("P");
        List<Object[]> packagedSupplyOrder = supplierMiddle.getSupplyOrder(lang,supplyOrderPara);
        //已出库
        supplyOrderPara.setState("S");
        List<Object[]> outSupplyOrder = supplierMiddle.getSupplyOrder(lang,supplyOrderPara);
        //已登机
        supplyOrderPara.setState("O");
        List<Object[]> onBoardSupplyOrder = supplierMiddle.getSupplyOrder(lang,supplyOrderPara);
        //已登机
        supplyOrderPara.setState("A");
        List<Object[]> arrivedSupplyOrder = supplierMiddle.getSupplyOrder(lang,supplyOrderPara);

        return ok(views.html.supply.supplierProcess.render(lang,objectList,unProcessedList,packagedSupplyOrder,outSupplyOrder,onBoardSupplyOrder,arrivedSupplyOrder,(User) ctx().args.get("user")));
    }


    /**
     * 保存supplier 订单的状态     Added by Tiffany Zhu 2016.04.21
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result statusSave(String lang,String status){
        JsonNode json = request().body().asJson();
        User user = (User) ctx().args.get("user");
        boolean result = supplierMiddle.supplyOrderStatusSave(json,status,user);

        if(result){
            return ok("SUCCESS");
        }else{
            return  badRequest();
        }
    }
}

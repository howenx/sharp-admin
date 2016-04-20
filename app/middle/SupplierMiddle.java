package middle;


import domain.*;
import domain.order.Order;
import domain.order.OrderLine;
import play.Logger;

import play.i18n.Lang;
import play.i18n.Messages;
import service.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiffany on 16/4/20.
 */
public class SupplierMiddle {
    @Inject
    private OrderService orderService;

    @Inject
    private OrderLineService orderLineService;

    @Inject
    private ItemService itemService;

    @Inject
    private SupplyOrderService supplyOrderService;

    @Inject
    private AdminSupplierService adminSupplierService;

    @Inject
    private IDService idService;


    /**
     * 全部的订单
     * @param user
     * @return
     */
    public List<Object[]> getOrderAll(String lang,User user){

        List<Object[]> objectList = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        //supplier
        AdminSupplier adminSupplier = adminSupplierService.getSupplierByUserId(user.id());
        if(adminSupplier == null){
            Logger.error("The supplier does not exist. name=" + user.nickname());
            return null;
        }
        //supplier的商品
        List<Item> itemList = itemService.getItemBySupplier(adminSupplier.getSupplyMerch());
        if(itemList != null){
            //订单中的商品
            List<OrderLine> orderLineList = orderLineService.getLineByItems(itemList);
            if(orderLineList == null){
                Logger.error("No order for current supplier.");
            }else{
                orderList = orderService.getOrderByIds(orderLineList);
                if(orderList != null){
                    for(Order order : orderList){
                        Object[] object = new Object[5];
                        object[0] = order.getOrderId();
                        ID userInfo = idService.getID(Integer.valueOf(order.getUserId().toString()));
                        if(userInfo != null){
                            object[1] = userInfo.getPhoneNum();
                        }else {
                            object[1] = "";
                        }
                        object[2] = order.getOrderCreateAt().toString().substring(0,19);
                        object[3] = order.getTotalFee();
                        SupplyOrder supplyOrder = supplyOrderService.getSupplyByOrderId(order.getOrderId());
                        if(supplyOrder == null){
                            object[4] = Messages.get(new Lang(Lang.forCode(lang)),"order.supplier.state5");
                        }else{
                            switch (supplyOrder.getState()){
                                case "P":
                                    object[4] = Messages.get(new Lang(Lang.forCode(lang)),"order.supplier.state1");
                                    break;
                                case "S":
                                    object[4] = Messages.get(new Lang(Lang.forCode(lang)),"order.supplier.state2");
                                    break;
                                case "O":
                                    object[4] = Messages.get(new Lang(Lang.forCode(lang)),"order.supplier.state3");
                                    break;
                                case "A":
                                    object[4] = Messages.get(new Lang(Lang.forCode(lang)),"order.supplier.state3");
                                    break;
                                default:
                                    object[4] = "";
                            }
                        }
                        objectList.add(object);
                    }
                }
            }
        }else {
            Logger.error("There is no goods of the supplier. name=" + user.nickname());
            return null;
        }

        return  objectList;
    }




}

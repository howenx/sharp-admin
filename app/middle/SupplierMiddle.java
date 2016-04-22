package middle;


import com.fasterxml.jackson.databind.JsonNode;
import domain.*;
import domain.order.Order;
import domain.order.OrderLine;
import play.Logger;

import play.i18n.Lang;
import play.i18n.Messages;
import service.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
     * 全部的订单        Added by Tiffany Zhu 2016.04.20
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
        }else{
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
                                        object[4] = Messages.get(new Lang(Lang.forCode(lang)),"order.supplier.state4");
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
            }
        }
        return  objectList;
    }

    /**
     * 获取SupplyOrder        Added by Tiffany Zhu 2016.04.20
     * @param supplyOrder
     * @return
     */
    public List<Object[]> getSupplyOrder(String lang,SupplyOrder supplyOrder){
        List<Object[]> resultSupplyOrder = new ArrayList<>();
        List<SupplyOrder> supplyOrderList = supplyOrderService.getSupplyOrder(supplyOrder);
        for(SupplyOrder supplyOrderTemp :supplyOrderList){
            Object[] object = new Object[5];
            object[0] = supplyOrderTemp.getOrderId();
            Order order = orderService.getOrderById(supplyOrderTemp.getOrderId());
            if(order != null){
                ID userInfo = idService.getID(Integer.valueOf(order.getUserId().toString()));
                if(userInfo != null){
                    object[1] = userInfo.getPhoneNum();
                }else {
                    object[1] = "";
                }
                object[2] = order.getOrderCreateAt().toString().substring(0,19);
                object[3] = order.getTotalFee();
            }
            else{
                object[1] = "";
                object[2] = "";
                object[3] = "";
                Logger.error("The original order of  Supply Order:"  + supplyOrderTemp.getId() + " does not exist.");
            }

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
                    object[4] = Messages.get(new Lang(Lang.forCode(lang)),"order.supplier.state4");
                    break;
                default:
                    object[4] = "";
            }

            resultSupplyOrder.add(object);
        }
        Collections.sort(resultSupplyOrder, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] time2, Object[] time1) {
                return time2[2].toString().compareTo(time1[2].toString());
            }
        });



        return resultSupplyOrder;
    }


    public boolean supplyOrderStatusSave(JsonNode json, String status,User user){
        if(json != null){
            //创建供应商订单
            if(status.equals("P")){
                List<SupplyOrder> supplyOrderList = new ArrayList<>();
                for (int i = 0; i < json.size(); i++) {
                    Order order = orderService.getOrderById(json.get(i).asLong());
                    SupplyOrder supplyOrder = new SupplyOrder();
                    if(order != null){
                        supplyOrder.setOrderId(order.getOrderId());
                        supplyOrder.setUpdateUser(user.id());
                        supplyOrder.setState(status);
                    }
                    supplyOrderList.add(supplyOrder);
                }
                return supplyOrderService.addSupplyOrder(supplyOrderList);

            }
            //修改供应商订单状态
            else{
                List<SupplyOrder> supplyOrderList = new ArrayList<>();
                for (int i = 0; i < json.size(); i++) {
                    SupplyOrder supplyOrder = supplyOrderService.getSupplyByOrderId(json.get(i).asLong());
                    if(supplyOrder != null){
                        supplyOrder.setState(status);
                        supplyOrder.setUpdateUser(user.id());
                        supplyOrderList.add(supplyOrder);
                    }
                }
                return supplyOrderService.updSupplyOrderStatus(supplyOrderList);
            }
        }else{
            Logger.error("The passed values are blank, please check it.");
            return false;
        }
    }
}

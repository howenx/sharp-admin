package middle;

import domain.AdminSupplier;
import domain.Item;
import domain.User;
import domain.order.Order;
import domain.order.OrderLine;
import play.Logger;
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

    public List<Order> getOrder(User user){

        List<Order> orderList = new ArrayList<>();

        //全部的supplier订单
        List<SupplyOrder> supplyOrderList = supplyOrderService.getSupplyOrderAll();

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
            }




        }else {
            Logger.error("There is no goods of supplier's. name=" + user.nickname());
            return null;
        }



        return  orderList;
    }




}

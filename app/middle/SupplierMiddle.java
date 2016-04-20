package middle;

import domain.AdminSupplier;
import domain.Inventory;
import domain.Item;
import domain.User;
import domain.order.Order;
import domain.order.OrderLine;
import play.Logger;
import service.*;

import javax.inject.Inject;
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

    public List<Order> getOrder(User user, Order order){

        AdminSupplier adminSupplier = adminSupplierService.getSupplierByUserId(user.id());
        if(adminSupplier == null){
            Logger.error("该供应商不存在,name=" + user.nickname());
            return null;
        }

        List<Item> itemList = itemService.getItemBySupplier(adminSupplier.getSupplyMerch());
        if(itemList != null){
            List<OrderLine> orderLineList = orderLineService.getLineByItems(itemList);
            Logger.error("没有最新的订单---");

        }
       List<Order> orderList = orderService.getOrder(order);


        return  orderList;
    }




}

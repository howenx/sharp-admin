package domain.sale;

import java.io.Serializable;
import java.util.List;

/**
 * 订单展示数据
 * Created by sibyl.sun on 16/6/3.
 */
public class SaleOrderDTO implements Serializable {
    private SaleOrder saleOrder;
    private List<SaleOrderLine> saleOrderLineList;

    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }

    public List<SaleOrderLine> getSaleOrderLineList() {
        return saleOrderLineList;
    }

    public void setSaleOrderLineList(List<SaleOrderLine> saleOrderLineList) {
        this.saleOrderLineList = saleOrderLineList;
    }
}

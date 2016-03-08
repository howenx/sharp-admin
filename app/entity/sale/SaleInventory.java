package entity.sale;

import java.io.Serializable;

/**
 * 库存盘点
 * Created by sibyl.sun on 16/3/7.
 */
public class SaleInventory implements Serializable {
    /**
     * 日期
     */
    private String saleDate;
    /**
     * 销售数量
     */
    private Integer saleCount;

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    @Override
    public String toString(){
        return "[SaleInventory saleDate="+saleDate+",saleCount="+saleCount+"]";
    }
}

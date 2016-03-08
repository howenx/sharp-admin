package entity.sale;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 销售汇总表
 * Created by sibyl.sun on 16/3/7.
 */
public class SaleStatistics implements Serializable {
    private BigDecimal saleTotal;
    private BigDecimal jdFeeTotal ;
    private BigDecimal shipFeeTotal;
    private BigDecimal inteLogisticsTotal;
    private BigDecimal packFeeTotal;
    private BigDecimal storageFeeTotal ;
    private BigDecimal profitTotal;

    public BigDecimal getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(BigDecimal saleTotal) {
        this.saleTotal = saleTotal;
    }

    public BigDecimal getJdFeeTotal() {
        return jdFeeTotal;
    }

    public void setJdFeeTotal(BigDecimal jdFeeTotal) {
        this.jdFeeTotal = jdFeeTotal;
    }

    public BigDecimal getShipFeeTotal() {
        return shipFeeTotal;
    }

    public void setShipFeeTotal(BigDecimal shipFeeTotal) {
        this.shipFeeTotal = shipFeeTotal;
    }

    public BigDecimal getInteLogisticsTotal() {
        return inteLogisticsTotal;
    }

    public void setInteLogisticsTotal(BigDecimal inteLogisticsTotal) {
        this.inteLogisticsTotal = inteLogisticsTotal;
    }

    public BigDecimal getPackFeeTotal() {
        return packFeeTotal;
    }

    public void setPackFeeTotal(BigDecimal packFeeTotal) {
        this.packFeeTotal = packFeeTotal;
    }

    public BigDecimal getStorageFeeTotal() {
        return storageFeeTotal;
    }

    public void setStorageFeeTotal(BigDecimal storageFeeTotal) {
        this.storageFeeTotal = storageFeeTotal;
    }

    public BigDecimal getProfitTotal() {
        return profitTotal;
    }

    public void setProfitTotal(BigDecimal profitTotal) {
        this.profitTotal = profitTotal;
    }
    @Override
    public String toString(){
        return "[SaleStatistics: saleTotal="+saleTotal+",jdFeeTotal="+jdFeeTotal+",shipFeeTotal="+shipFeeTotal+
                ",inteLogisticsTotal="+inteLogisticsTotal+",packFeeTotal="+packFeeTotal+",storageFeeTotal="+storageFeeTotal+
                ",profitTotal="+profitTotal;
    }
}

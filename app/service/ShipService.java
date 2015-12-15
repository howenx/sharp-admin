package service;

import entity.Ship;

/**
 * Created by tiffany on 15/12/14.
 */
public interface ShipService {
    /**
     * 由订单编号获取物流
     * @param orderId
     * @return
     */
    public Ship getShipByOrderId(Long orderId);
}

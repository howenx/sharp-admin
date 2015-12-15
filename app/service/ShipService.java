package service;

import entity.Ship;

/**
 * Created by tiffany on 15/12/14.
 */
public interface ShipService {
    public Ship getShipByOrderId(Long orderId);
}

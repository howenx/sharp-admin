package service;

import entity.Ship;
import mapper.ShipMapper;

/**
 * Created by tiffany on 15/12/14.
 */
public class ShipServiceImpl implements ShipService{
    private ShipMapper shipMapper;
    @Override
    public Ship getShipByOrderId(Long orderId) {
        return shipMapper.getShipByOrderId(orderId);
    }
}

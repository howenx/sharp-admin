package service;

import entity.Ship;
import mapper.ShipMapper;

import javax.inject.Inject;

/**
 * Created by tiffany on 15/12/14.
 */
public class ShipServiceImpl implements ShipService{
    @Inject
    private ShipMapper shipMapper;
    @Override
    public Ship getShipByOrderId(Long orderId) {return shipMapper.getShipByOrderId(orderId);}
}

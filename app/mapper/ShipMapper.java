package mapper;

import entity.Ship;

/**
 * Created by tiffany on 15/12/14.
 */
public interface ShipMapper {
    /**
     * 由订单号获取物流单号
     * @param orderId
     * @return
     */
    Ship getShipByOrderId(Long orderId);
}

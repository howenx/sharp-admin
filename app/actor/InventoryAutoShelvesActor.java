package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import middle.ItemMiddle;
import play.Logger;

import javax.inject.Inject;

/**
 * 商品定时上架/下架的Actor
 * Created by Sunny Wu on 16/2/29.
 * kakao china.
 */
public class InventoryAutoShelvesActor extends AbstractActor {

    @Inject
    public InventoryAutoShelvesActor(ItemMiddle itemMiddle) {
        receive(ReceiveBuilder.match(Long.class, invId -> {
            itemMiddle.updateState(invId);
            Logger.debug(invId.toString()+"autoShelves actor");
        }).matchAny(s -> {
            Logger.error("InventoryAutoShelvesActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}

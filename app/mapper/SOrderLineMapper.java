package mapper;

import entity.SOrderLine;

import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public interface SOrderLineMapper {

    List<SOrderLine> getSOrderLineBy(SOrderLine sOrderLine);

    List<SOrderLine> getAllSOrderLine();

}

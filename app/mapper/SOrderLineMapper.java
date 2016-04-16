package mapper;

import domain.statistics.SOrderLine;

import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public interface SOrderLineMapper {

    List<SOrderLine> getSOrderLineBy(SOrderLine sOrderLine);

    List<SOrderLine> getAllSOrderLine();

}

package service;

import domain.statistics.SOrderLine;

import java.util.List;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public interface SOrderLineService {

    List<SOrderLine> getSOrderLineBy(SOrderLine sOrderLine);

    List<SOrderLine> getAllSOrderLine();

}

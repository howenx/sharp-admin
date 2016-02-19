package controllers;

import com.google.inject.Inject;
import play.mvc.Controller;
import service.SIDService;
import service.SOrderLineService;
import service.SOrderService;

/**
 * Created by Sunny Wu on 16/2/19.
 * kakao china.
 */
public class StatisticsCtrl extends Controller {

    @Inject
    private SIDService sidService;

    @Inject
    private SOrderService sOrderService;

    @Inject
    private SOrderLineService sOrderLineService;

    
}

package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import domain.Skus;
import domain.Slider;
import domain.Theme;
import domain.User;
import filters.UserAuth;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.InventoryService;
import service.ThemeService;
import util.SysParCom;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by tiffany on 16/7/27.
 */
public class CategoryCtrl extends Controller {
    @Inject
    private ThemeService service;
    @Inject
    private InventoryService inventoryService;

    /**
     * 分类入口 Added by Tiffany Zhu 2016.07.27
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result category(String lang) {
        List<Slider> sliderList = service.sliderAll();
        for (Slider slider : sliderList) {
            JsonNode img = Json.parse(slider.getImg());
            String imgUrl = "0";
            String width = "0";
            String height = "0";
            if (null!=img.get("url") && null!=img.get("width") && null!=img.get("height")) {
                imgUrl = img.get("url").asText();
                width = img.get("width").asText();
                height = img.get("height").asText();
            }
            slider.setImg(imgUrl+","+width+","+height);
        }
        return ok(views.html.category.category.render(lang,sliderList,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,(User) ctx().args.get("user")));
    }

    /**
     * 分类入口的弹窗  主题列表和商品列表       Added by Tiffany Zhu 2016.07.27
     * @return sliderPop.scala.html
     */
    @Security.Authenticated(UserAuth.class)
    public Result categoryPop(){
        //主题列表
        List<Theme> themeList = service.getThemesAll();
        List<Theme> tList = new ArrayList<>();
        String strNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//现在时间
        for(Theme theme : themeList) {
            if (theme.getEndAt().compareTo(strNow)>0) {
                theme.setThemeImg(Json.parse(theme.getThemeImg()).get("url").asText());
                tList.add(theme);
            }
        }
        List<Skus> skusList = inventoryService.getAllSkus();
        List<Skus> slist = new ArrayList<>();
        for(Skus skus : skusList) {//商品列表为(除自定义价格和多样化价格的预售和正常商品)
            if (!skus.getSkuType().equals("customize")&&!skus.getSkuType().equals("vary")&&(skus.getSkuTypeStatus().equals("P")||skus.getSkuTypeStatus().equals("Y"))) {
                skus.setSkuTypeImg(Json.parse(skus.getSkuTypeImg()).get("url").asText());
                slist.add(skus);
            }
        }
        if (themeList.size()>0 && skusList.size()>0) {
            return ok(views.html.category.categoryPop.render(tList,slist,SysParCom.IMAGE_URL));
        }
        else
            return ok("没有数据");
    }
}

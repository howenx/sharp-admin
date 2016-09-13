package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
import domain.*;
import domain.order.Order;
import filters.UserAuth;
import middle.ItemMiddle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import play.Configuration;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.concurrent.duration.Duration;
import service.*;
import util.Regex;
import util.SysParCom;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 商品管理
 * Created by howen on 15/11/11.
 */
@SuppressWarnings("unchecked")
public class ItemCtrl extends Controller {


    private ItemService itemService;

    private InventoryService inventoryService;

    private CarriageService carriageService;

    private VaryPriceService varyPriceService;

    private AdminSupplierService adminSupplierService;

    @Inject
    private ItemMiddle itemMiddle;

    @Inject
    Configuration configuration;

    @Inject
    private ActorSystem system;

    private static final int PAGE_SIZE = 50;

    @Inject
    public ItemCtrl(ItemService itemService, InventoryService inventoryService, CarriageService carriageService, VaryPriceService varyPriceService, AdminSupplierService adminSupplierService) {
        this.itemService = itemService;
        this.inventoryService = inventoryService;
        this.carriageService = carriageService;
        this.varyPriceService = varyPriceService;
        this.adminSupplierService = adminSupplierService;
    }

    /**
     * Ajax for get sub category.
     *
     * @return Result
     *
     */
    @Security.Authenticated(UserAuth.class)
    public Result getSubCategory() {
        DynamicForm form = Form.form().bindFromRequest();
        Long pcid = Long.parseLong(form.get("pcid"));
        HashMap<String, Long> hashMap = new HashMap<String, Long>();
        hashMap.put("parentCateId", pcid);
        return ok(Json.toJson(itemService.getSubCates(hashMap)));
    }

    /**
     * 商品查询
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemSearch(String lang){
        //查询页为sku信息
        Inventory inventory = new Inventory();
        inventory.setPageSize(-1);
        inventory.setOffset(-1);
        //首次查询只显示Y正常正常状态商品
        inventory.setState("Y");
        List<Inventory> inventoryList = inventoryService.invSearch(inventory);
        int countNum = inventoryList.size();//取总数
        int pageCount = countNum/PAGE_SIZE;//共分几页
        if (countNum%PAGE_SIZE!=0) {
            pageCount = countNum/PAGE_SIZE+1;
        }
        inventory.setPageSize(PAGE_SIZE);
        inventory.setOffset(0);
//        inventoryList = inventoryService.invSearch(inventory);
//        for(Inventory inv : inventoryList) {
//            String url = Json.parse(inv.getInvImg()).get("url")==null?"":Json.parse(inv.getInvImg()).get("url").asText();
//            inv.setInvImg(url);
//        }
        List<Brands> brandsList = itemService.getAllBrands();
        List<Cates> catesList = itemService.getCatesAll();
        Map<String,String> area = new ObjectMapper().convertValue(configuration.getObject("area"),HashMap.class);
        return ok(views.html.item.itemsearch.render(lang, SysParCom.IMAGE_URL,PAGE_SIZE,countNum,pageCount,brandsList,catesList,(User) ctx().args.get("user"), area));
    }

    /**
     * ajax分页查询
     * @param lang 语言
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemSearchAjax(String lang,int pageNum) {
        JsonNode json = request().body().asJson();
        Inventory inventory = Json.fromJson(json,Inventory.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*PAGE_SIZE;
            inventory.setPageSize(-1);
            inventory.setOffset(-1);
            List<Inventory> inventoryList = inventoryService.invSearch(inventory);
            int countNum = inventoryList.size();//取总数
            int pageCount = countNum/PAGE_SIZE;//共分几页
            if(countNum%PAGE_SIZE!=0){
                pageCount = countNum/PAGE_SIZE+1;
            }
            inventory.setPageSize(PAGE_SIZE);
            inventory.setOffset(offset);
            inventoryList = inventoryService.invSearch(inventory);
            for(Inventory inv : inventoryList) {
                String url = Json.parse(inv.getInvImg()).get("url")==null?"":Json.parse(inv.getInvImg()).get("url").asText();
                inv.setInvImg(url);
            }
            //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",inventoryList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",PAGE_SIZE);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * ajax商品分页查询           Added By Sunny Wu 2016.09.05
     * @param lang 语言
     * @param pageNum 当前页
     * @return json
     */
    @Security.Authenticated(UserAuth.class)
    public Result commSearchAjax(String lang,int pageNum) {
        JsonNode json = request().body().asJson();
        Item item = Json.fromJson(json,Item.class);
        if(pageNum>=1){
            //计算从第几条开始取数据
            int offset = (pageNum-1)*PAGE_SIZE;
            item.setPageSize(-1);
            item.setOffset(-1);
            List<Item> itemList = itemService.itemSearch(item);
            int countNum = itemList.size();//取总数
            int pageCount = countNum/PAGE_SIZE;//共分几页
            if(countNum%PAGE_SIZE!=0){
                pageCount = countNum/PAGE_SIZE+1;
            }
            item.setPageSize(PAGE_SIZE);
            item.setOffset(offset);
            itemList = itemService.itemSearch(item);
            List<Object[]> itList = new ArrayList<>();
            for(Item it : itemList) {
                Object[] object = new Object[4];
                object[0] = it.getId();//商品id
                object[1] = it.getItemTitle();//商品标题
                object[2] = itemService.getBrands(it.getBrandId()).getBrandNm();//品牌名称
                object[3] = itemService.getCate(it.getCateId()).getCateNm();//类别名称
                itList.add(object);

            }
                //组装返回数据
            Map<String,Object> returnMap=new HashMap<>();
            returnMap.put("topic",itList);
            returnMap.put("pageNum",pageNum);
            returnMap.put("countNum",countNum);
            returnMap.put("pageCount",pageCount);
            returnMap.put("pageSize",PAGE_SIZE);
            return ok(Json.toJson(returnMap));
        }
        else{
            return badRequest();
        }
    }

    /**
     * 商品录入
     * @param lang 语言
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemCreate(String lang) {
        List<AdminSupplier> adminSupplierList = adminSupplierService.getAllSuppliers();
        return ok(views.html.item.itemadd.render(lang,itemService.getAllBrands(),itemService.getParentCates(),SysParCom.IMG_UPLOAD_URL,SysParCom.IMAGE_URL,(User) ctx().args.get("user"), adminSupplierList));
    }

    /**
     *  由商品id获得单个商品及其库存信息展示在详情页面
     * @param lang 语言
     * @param id 商品id
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result findItemById(String lang,Long id) {
        Map<String,String> customs = new ObjectMapper().convertValue(configuration.getObject("customs"),HashMap.class);
        Map<String,String> area = new ObjectMapper().convertValue(configuration.getObject("area"),HashMap.class);
        List<AdminSupplier> adminSupplierList = adminSupplierService.getAllSuppliers();
        Item item = itemService.getItem(id);
        item.setItemFeatures(item.getItemFeatures().replace(" ", ""));
        Cates cates = itemService.getCate(item.getCateId());
        String pCateNm = "";
        if(null != cates.getPcateId()) {
            pCateNm = itemService.getCate(cates.getPcateId()).getCateNm();
        } else pCateNm = cates.getCateNm();
        HashMap<String, Long> hashMap = new HashMap<String, Long>();
        hashMap.put("parentCateId", cates.getCateId());
        if (null == cates.getPcateId() && itemService.getSubCates(hashMap).size()==0) {
            cates.setCateNm("");
        }
        Brands brands = itemService.getBrands(item.getBrandId());
        List<Inventory> inventories = inventoryService.getInventoriesByItemId(id);
        //包含modelName的库存列表
        List<Object[]> invList = new ArrayList<>();
        for(Inventory inventory : inventories) {
            Object[] object = new Object[25];
            object[0] = inventory.getOrMasterInv();
            object[1] = inventory.getId();
            object[2] = inventory.getItemSize();
            object[3] = inventory.getInvWeight();
            object[4] = inventory.getAmount();
            object[5] = inventory.getItemPrice();
            object[6] = inventory.getItemSrcPrice();
//            object[7] = inventory.getItemCostPrice();
            object[8] = inventory.getItemDiscount();
            object[9] = inventory.getRestrictAmount();
//            object[10] = inventory.getCarriageModelCode();
            //由库存表的carriageModelCode 得到 modelName
//            object[11] = carriageService.getModelName(inventory.getCarriageModelCode());
//            object[10] = inventory.getPostalTaxRate();
            object[11] = inventory.getPostalTaxCode();
            object[12] = inventory.getInvArea();
            object[13] = inventory.getInvCustoms();
            object[14] = Json.parse(inventory.getInvImg()).get("url")==null?"":Json.parse(inventory.getInvImg()).get("url").asText();
            object[15] = inventory.getItemPreviewImgs();
            object[16] = inventory.getState();
            object[17] = inventory.getRecordCode();
            object[18] = inventory.getRestAmount();
            object[19] = inventory.getInvCode();
            object[20] = inventory.getStartAt();
            object[21] = inventory.getEndAt();
            object[22] = inventory.getSoldAmount();
            object[23] = inventory.getOrVaryPrice();
            object[24] = "";
            if (object[23].toString().equals("true")) {
                VaryPrice varyPrice = new VaryPrice();
                varyPrice.setInvId(inventory.getId());
                String p_a = "";
                List<VaryPrice> varyPriceList = varyPriceService.getVaryPriceBy(varyPrice);
                for(VaryPrice vp : varyPriceList) {
//                    JsonNode jsonNode = Json.toJson(varyPriceList.get(i));
//                    VaryPrice vp = Json.fromJson(jsonNode, VaryPrice.class);
                    String status = vp.getStatus();
                    BigDecimal price = vp.getPrice();
                    Integer limitAmount = vp.getLimitAmount();
                    p_a += status + "," + price.toString() + "," + limitAmount.toString() + "_";
                }
                p_a = p_a.substring(0, p_a.length()-1);
                object[24] = p_a;
            }
            invList.add(object);
        }
        return ok(views.html.item.itemdetail.render(item,invList,cates,pCateNm,brands,SysParCom.IMAGE_URL,lang,(User) ctx().args.get("user"),customs,area,adminSupplierList));
    }

    /**
     * 由商品id获得单个商品及其库存信息展示在修改页面
     * @param lang 语言
     * @param id 商品id
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result updateItemById(String lang,Long id) {
        List<AdminSupplier> adminSupplierList = adminSupplierService.getAllSuppliers();
        Item item = itemService.getItem(id);
        item.setItemFeatures(item.getItemFeatures().replace(" ", ""));
        //由商品类别id获取类别
        Cates cates = itemService.getCate(item.getCateId());
        //父类别名称
        String pCateNm = "";
        if(null != cates.getPcateId()) {
            pCateNm = itemService.getCate(cates.getPcateId()).getCateNm();
        } else pCateNm = cates.getCateNm();
        //由商品品牌id获取品牌
        Brands brands = itemService.getBrands(item.getBrandId());
        //由商品id获取库存列表
        List<Inventory> inventories = inventoryService.getInventoriesByItemId(id);
        //包含modelName的库存列表
        List<Object[]> invList = new ArrayList<>();
        for(Inventory inventory : inventories) {
            Object[] object = new Object[26];
            object[0] = inventory.getId();
            object[1] = inventory.getItemId();
            object[2] = inventory.getOrMasterInv();
//            object[3] = inventory.getItemColor();
            object[4] = inventory.getItemSize();
            object[5] = inventory.getStartAt();
            object[6] = inventory.getEndAt();
            object[7] = inventory.getItemSrcPrice();
            object[8] = inventory.getItemPrice();
//            object[9] = inventory.getItemCostPrice();
//            object[10] = inventory.getItemDiscount();
            object[11] = inventory.getInvWeight();
            object[12] = inventory.getRestrictAmount();
            object[13] = inventory.getAmount();
            object[14] = inventory.getRestAmount();
            //由库存表的carriageModelCode 得到 modelName
//            object[15] = inventory.getCarriageModelCode();
//            object[23] = carriageService.getModelName(inventory.getCarriageModelCode());
            object[15] = inventory.getInvArea();
            object[16] = inventory.getInvCustoms();
//            object[17] = inventory.getPostalTaxRate();
            object[18] = inventory.getPostalTaxCode();
            object[19] = inventory.getRecordCode();
            object[20] = inventory.getInvImg();
            object[21] = inventory.getItemPreviewImgs();
            object[22] = inventory.getOrVaryPrice();
            object[23] = "";
            VaryPrice varyPrice = new VaryPrice();
            varyPrice.setInvId(inventory.getId());
            List<VaryPrice> vpList = varyPriceService.getVaryPriceBy(varyPrice);
            if (vpList.size()>0) {
                for(VaryPrice vp : vpList) {
                    object[23]=object[23] + vp.getId().toString()+",";
                    object[23]=object[23] + vp.getStatus()+",";
                    object[23]=object[23] + vp.getPrice().toString()+",";
                    object[23]=object[23] + vp.getLimitAmount().toString()+",";
                }
                object[23] = object[23].toString().substring(0,object[23].toString().length()-1);
            }
            object[24] = inventory.getInvCode();
            object[25] = inventory.getState();
            invList.add(object);
        }
        return ok(views.html.item.itemupdate.render(item,invList,cates,pCateNm,brands,SysParCom.IMAGE_URL,SysParCom.IMG_UPLOAD_URL,lang,itemService.getAllBrands(),itemService.getParentCates(),(User) ctx().args.get("user"), adminSupplierList));
    }

    /**
     * 添加商品
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemSave() {
        //操作人员的ip
        String operateIp = request().remoteAddress();
        String enNm = ((User) ctx().args.get("user")).enNm().get();
        JsonNode json = request().body().asJson();
        //--------------------数据验证------------------start
        Item item = new Item();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNow = sdf.format(now);//现在时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH,+6);
        String maxDate = sdf.format(calendar.getTime());
//        Logger.error("最大时间:"+maxDate);
        if (json.has("item")) {
            JsonNode jsonItem = json.findValue("item");
            if (jsonItem.has("publicity") && !"".equals(jsonItem.findValue("publicity").toString())) {
                ((ObjectNode) jsonItem).put("publicity",jsonItem.findValue("publicity").toString());
            }
            if (jsonItem.has("itemFeatures")) {
                ((ObjectNode) jsonItem).put("itemFeatures",jsonItem.findValue("itemFeatures").toString());
            }
            if (jsonItem.has("itemDetailImgs")) {
                ((ObjectNode) jsonItem).put("itemDetailImgs",jsonItem.findValue("itemDetailImgs").toString());
            }
            Form<Item> itemForm = Form.form(Item.class).bind(jsonItem);
            item = Json.fromJson(jsonItem,Item.class);
            //数据验证(若有详细则为json格式,商品参数为json格式,商品优惠信息为json格式)
            if (itemForm.hasErrors() || (null!=item.getItemDetailImgs() && !"".equals(item.getItemDetail()) && !Regex.isJason(item.getItemDetailImgs())) || !(Regex.isJason(item.getItemFeatures()))) {
                Logger.error("item 表单数据有误.....");
                return badRequest();
            }
        }
        if (json.has("inventories")) {
            for (final JsonNode jsonNode : json.findValue("inventories")) {
                Inventory inventory = new Inventory();
                if (jsonNode.has("inventory")) {
                    JsonNode jsonInv = jsonNode.findValue("inventory");
                    Form<Inventory> inventoryForm = Form.form(Inventory.class).bind(jsonInv);
                    inventory = Json.fromJson(jsonInv, Inventory.class);
                    String startAt = inventory.getStartAt();
                    String endAt = inventory.getEndAt();
                    //数据验证(商品价格,折扣,数量,重量等数据不能小于0, Y状态开始时间不能大于结束时间,结束时间不能小于现在时间,开始时间和结束时间不能超过当前时间6个月))
                    if (inventoryForm.hasErrors() || inventory.getItemPrice().compareTo(new BigDecimal(0.00))<0 || inventory.getItemSrcPrice().compareTo(new BigDecimal(0.00))<0
//                            || inventory.getItemCostPrice().compareTo(new BigDecimal(0.00))<0 || inventory.getItemDiscount().compareTo(new BigDecimal(0.00))<0 || inventory.getInvWeight()<0
                            || inventory.getRestrictAmount()<0 || inventory.getRestAmount()<0 || !(Regex.isJason(inventory.getItemPreviewImgs()))
                            || ("Y".equals(inventory.getState()) && (startAt.compareTo(endAt)>0 || endAt.compareTo(strNow)<0 || startAt.compareTo(maxDate)>0 || endAt.compareTo(maxDate)>0))) {
                        Logger.error(inventory.getInvCode() + " inventory 表单数据有误.....");
                        return badRequest();
                    }
                }
                if (jsonNode.has("varyPrices")) {
                    for (final JsonNode varyPriceNode : jsonNode.findValue("varyPrices")) {
                        Form<VaryPrice> varyPriceForm = Form.form(VaryPrice.class).bind(varyPriceNode);
                        VaryPrice varyPrice = Json.fromJson(varyPriceNode, VaryPrice.class);
                        //数据验证(价格和数量不能小于0)
                        if (varyPriceForm.hasErrors() || varyPrice.getPrice().compareTo(new BigDecimal(0.00))<0 || varyPrice.getLimitAmount()<0) {
                            Logger.error("varyPrice 表单数据有误.....");
                            return badRequest();
                        }
                    }
                }
            }
        }
        //--------------------数据验证------------------end
        List<Long> list = itemMiddle.itemSave(json, enNm, operateIp);
        return ok(list.toString());
    }

    /**
     * 添加商品, 弹窗商品添加规格       Added by Sunny.Wu
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemAddPop() {
        Map<String,String> customs = new ObjectMapper().convertValue(configuration.getObject("customs"),HashMap.class);
        Map<String,String> area = new ObjectMapper().convertValue(configuration.getObject("area"),HashMap.class);
//        Logger.error(customs.toString());
//        Logger.error(area.toString());
//        return ok(views.html.item.itemaddPop.render(carriageService.getModels(),SysParCom.IMG_UPLOAD_URL,SysParCom.IMAGE_URL,customs,area));
        return ok(views.html.item.itemaddPop.render(SysParCom.IMG_UPLOAD_URL,SysParCom.IMAGE_URL,customs,area));
    }

    /**
     * 新增运费模板
     * @param lang 语言
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result carrCreate(String lang) {
        Map<String,String> storeArea = new ObjectMapper().convertValue(configuration.getObject("area"),HashMap.class);
        Map<String,String> delivery = new ObjectMapper().convertValue(configuration.getObject("delivery"),HashMap.class);
        return ok(views.html.carriage.carrmodelAdd.render(lang, (User) ctx().args.get("user"), storeArea, delivery));
    }

    /**
     * 运费模板保存
     * @return Result
     */
    @Security.Authenticated(UserAuth.class)
    public Result carrSave() {
        JsonNode json = request().body().asJson();
        for(final JsonNode jsonNode : json) {
            Form<Carriage> carriageForm = Form.form(Carriage.class).bind(jsonNode);
            Carriage carriage  = Json.fromJson(jsonNode, Carriage.class);
            //数据验证
            if (carriageForm.hasErrors() || carriage.getFirstNum()<0 || carriage.getFirstFee().compareTo(new BigDecimal(0.00))<0 || carriage.getAddNum()<0 ||carriage.getAddFee().compareTo(new BigDecimal(0.00))<0) {
                Logger.error("carriage 表单数据有误.....");
                return badRequest();
            }
        }
        carriageService.carrModelSave(json);
        return ok("保存成功");

    }

    /**
     * 由modeCode得到该模板的所有运费计算方式
     * @param lang 语言
     * @param modelCode 模板代码
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result findModel(String lang,String modelCode) {
        Map<String,String> storeArea = new ObjectMapper().convertValue(configuration.getObject("area"),HashMap.class);
        Map<String,String> delivery = new ObjectMapper().convertValue(configuration.getObject("delivery"),HashMap.class);
        List<Carriage> carrList = carriageService.getCarrsByModel(modelCode);
        for(Carriage c : carrList) {
            c.setFirstNum(c.getFirstNum()/1000);
            c.setAddNum(c.getAddNum()/1000);
        }
        return ok(views.html.carriage.carrmodelUpdate.render(lang,carrList,(User) ctx().args.get("user"), storeArea, delivery));
    }

    /**
     * 有modelCode删除运费模板中所有数据
     * @param modelCode
     * @return Result
     */
    public Result delModel(String modelCode) {
        boolean bool = carriageService.delModelByCode(modelCode);
        if(bool)  return ok("true");
        else return ok("false");
    }

    /**
     * 运费模板列表
     * @param lang 语言
     * @return view
     */
    @Security.Authenticated(UserAuth.class)
    public Result carrModelSearch(String lang) {
        List<Carriage> modelList = carriageService.getModels();
        List<Carriage> carriageList = carriageService.getAllCarriage();
        for(Carriage c : carriageList) {
            c.setFirstNum(c.getFirstNum()/1000);
            c.setAddNum(c.getAddNum()/1000);
        }
        return ok(views.html.carriage.carrmodelList.render(lang,modelList,carriageList,(User) ctx().args.get("user")));
    }

    @Security.Authenticated(UserAuth.class)
    public Result carrPop() {
        return ok(views.html.carriage.cityPop.render());
    }


    /**
     * 品牌列表     Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result brandList(String lang) {
        return ok(views.html.item.brandsearch.render(lang,SysParCom.IMAGE_URL,itemService.getAllBrands(),(User) ctx().args.get("user")));
    }

    /**
     * 新增品牌     Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result brandAdd(String lang) {
        return ok(views.html.item.brandadd.render(lang,(User) ctx().args.get("user"),SysParCom.IMG_UPLOAD_URL,SysParCom.IMAGE_URL));
    }

    /**
     * 保存品牌    Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result brandSave(String lang) {
        JsonNode json = request().body().asJson();
        Form<Brands> brandsForm = Form.form(Brands.class).bind(json);
        //数据验证
        if (brandsForm.hasErrors()) {
            Logger.error("brand 表单数据有误.....");
            return badRequest();
        }
        itemService.insertBrands(json);
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 商品分类列表       Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateList(String lang) {

        List<Cates> catesList= itemService.getCatesAll();
        //含有父类名的分类列表
        List<Object[]> caList = new ArrayList<>();
        for(Cates cates : catesList){
            Object[] object = new Object[6];        //类别Id
            object[0] = cates.getCateId();          //类别名
            object[1] = cates.getCateNm();          //父类Id
            object[2] = cates.getPcateId();         //父类名
            if(itemService.getCate(cates.getPcateId()) != null) {
                object[3] = itemService.getCate(cates.getPcateId()).getCateNm();//类别描述
            }else{
                object[3] = "";//类别描述
            }
            object[4] = cates.getCateDesc();        //类别描述
            object[5] = cates.getCateCode();        //类别Code
            caList.add(object);
        }
        return ok(views.html.item.catesearch.render(lang,caList,(User) ctx().args.get("user")));
    }

    /**
     * 新增商品分类       Added by Tiffany Zhu
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateAdd(String lang) {
        return ok(views.html.item.cateadd.render(lang,itemService.getParentCates(),(User) ctx().args.get("user")));
    }

    /**
     * 保存商品分类
     * @param lang
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateSave(String lang) {
        JsonNode json = request().body().asJson();
        Form<Cates> catesForm = Form.form(Cates.class).bind(json);
        //数据验证
        if (catesForm.hasErrors()) {
            Logger.error("cates 表单数据有误.....");
            return badRequest();
        }
        itemService.catesSave(json);
        Logger.error(json.toString());
        return ok(Json.toJson(Messages.get(new Lang(Lang.forCode(lang)),"message.save.success")));
    }

    /**
     * 由id修改类别名称    Added By Sunny Wu 2016.06.24
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result cateNmUpdate() {
        JsonNode json = request().body().asJson();
        Form<Cates> catesForm = Form.form(Cates.class).bind(json);
        //数据验证
        if (catesForm.hasErrors()) {
            Logger.error("cates 表单数据有误.....");
            return badRequest();
        }
        Cates c = Json.fromJson(json,Cates.class);
        Cates cates = itemService.getCate(c.getCateId());
        cates.setCateNm(c.getCateNm());
        if (itemService.updateCateNm(cates))
            return ok("更新成功");
        else
            return ok("更新失败");

    }

    /**
     * 消息推送         Added By Sunny WU  2016.06.02
     * @param lang 语言
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result msgPush(String lang) {
        return ok(views.html.item.msgpush.render(lang, (User) ctx().args.get("user")));
    }

    /**
     * 消息推送保存      Added By Sunny WU  2016.06.02
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result msgPushSave() {
        JsonNode json = request().body().asJson();
        Form<PushMsg> msgForm = Form.form(PushMsg.class).bind(json);
        PushMsg msg = Json.fromJson(json, PushMsg.class);
        //数据验证
        if (msgForm.hasErrors()) {
            Logger.error("msg 表单数据有误.....");
            return badRequest();
        }
        msg.setAudience("all");
        if (null != msg.getUrl() && !"".equals(msg.getUrl()) && !"U".equals(msg.getTargetType())) {
            msg.setUrl(SysParCom.DEPLOY_URL+msg.getUrl());
        }
        Logger.info("推送消息:"+msg.toString());
        system.actorSelection(SysParCom.MSG_PUSH).tell(msg, ActorRef.noSender());
        return ok("推送成功");
    }

    /**
     * 类目选择弹窗        Added By Sunny Wu  2016.08.29
     * @return views
     */
    @Security.Authenticated(UserAuth.class)
    public Result catesPop() {
        //类别列表
        List<Cates> catesList = itemService.getCatesAll();
        if (catesList.size()>0) {
            return ok(views.html.item.catesPop.render(catesList));
        }
        else
            return ok("没有数据");
    }

    /**
     * 商品选择弹窗(选择item(主sku列表),sku,pin)       Added By Sunny Wu 2016.08.29
     * @return views
     */
    public Result goodsPop() {
        List<Skus> skusList = inventoryService.getAllSkus();
        List<Skus> goodsList = new ArrayList<>();
        for(Skus skus : skusList) {
            //商品列表为(除自定义价格和多样化价格的预售和正常商品)
            if (!skus.getSkuType().equals("customize")&&!skus.getSkuType().equals("vary")&&(skus.getSkuTypeStatus().equals("P")||skus.getSkuTypeStatus().equals("Y"))) {
                String url = Json.parse(skus.getSkuTypeImg()).get("url")==null?"":Json.parse(skus.getSkuTypeImg()).get("url").asText();
                skus.setSkuTypeImg(url);
                goodsList.add(skus);
            }
        }
        if (goodsList.size()>0) {
            return ok(views.html.item.itemPop.render(goodsList,SysParCom.IMAGE_URL));
        }
        else
            return ok("没有数据");
    }


    /**
     * 仅处理一次所有商品统一改为预售  谨慎操作!   Added By Sunny.Wu 2016.09.02
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result operateAllGoods() {
        itemMiddle.operateAllGoods();
        return ok("处理为预售完成");
    }

    /**
     * 批量导出商品数据     Added By Sunny.Wu 2016.09.12
     * @return
     */
    @Security.Authenticated(UserAuth.class)
    public Result itemExport() {
        //所有需要报关的商品信息
        List<Inventory> inventoryList = inventoryService.getAllCustomSku();
        //导出excel
        XSSFWorkbook wb = new XSSFWorkbook();//创建HSSFWorkbook对象(excel的文档对象)
        //输出Excel文件
        FileOutputStream output = null;
        String fileName = "KakaoGift报关商品信息表.xlsx";   //文件名
        Sheet sheet = wb.createSheet("需报关的商品信息");//建立新的sheet对象（excel的表单）
        Row row1 = sheet.createRow(0);//在sheet里创建第一行
        //创建单元格并设置单元格内容
        row1.createCell(0).setCellValue("商品ID");
        row1.createCell(1).setCellValue("SKU ID");
        row1.createCell(2).setCellValue("SKU编码");
        row1.createCell(3).setCellValue("商品标题");
        row1.createCell(4).setCellValue("规格");
        //在sheet里从第二行开始创建数据
        int r = 1;
        for(Inventory inv : inventoryList) {
            Row row = sheet.createRow(r);
            row.createCell(0).setCellValue(inv.getItemId());
            row.createCell(1).setCellValue(inv.getId());
            row.createCell(2).setCellValue(inv.getInvCode());
            row.createCell(3).setCellValue(inv.getInvTitle());
            row.createCell(4).setCellValue(inv.getItemColor() + " " +inv.getItemSize());
            r += 1;
        }
        try {

            File file = new File(fileName);
            output = new FileOutputStream(file);
            wb.write(output);
            output.close();

            // 设置response的Header
            response().setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes()));
//            response().setHeader("Content-Length", "" + file.length());

            return ok(file.getName());
        } catch (Exception e) {
            Logger.error(Throwables.getStackTraceAsString(e));
            return badRequest();
        }
//        return ok("导出成功");
    }

}



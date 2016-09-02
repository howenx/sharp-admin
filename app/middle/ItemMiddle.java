package middle;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Throwables;
import domain.*;
import domain.pingou.PinSku;
import modules.NewScheduler;
import play.Logger;
import play.libs.Json;
import scala.concurrent.duration.Duration;
import service.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sunny Wu on 16/1/14.
 * kakao china.
 *
 * controller 和 service 的中间层,为了解决一个modules只能注入一个数据库中的service,
 * 在同一个方法里注入不同库中的mapper来处理复杂的业务逻辑
 */
public class ItemMiddle {

    @Inject
    public  ItemService itemService;
    @Inject
    public  InventoryService inventoryService;
    @Inject
    public  VaryPriceService varyPriceService;
    @Inject
    public  DataLogService dataLogService;
    @Inject
    public  ItemStatisService itemStatisService;
    @Inject
    public  PingouService pingouService;
    @Inject
    public  SubjectPriceService subjectPriceService;

    @Inject
    private NewScheduler newScheduler;

    @Inject
    private ItemMiddle itemMiddle;

    @Inject
    @Named("inventoryAutoShelvesActor")
    private ActorRef inventoryAutoShelvesActor;

    @Inject
    @Named("pingouOffShelfActor")
    private ActorRef pingouOffShelfActor;


    /**
     * 录入或更新商品信息和库存信息, 同时录入日志信息
     * @param json 商品和库存信息json串
     * @param enNm 操作人员
     * @param operateIp 操作人员ip
     * @return
     */
    public  List<Long> itemSave(JsonNode json, String enNm, String operateIp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        Long nowTimes = now.getTime();
        String nowStr = sdf.format(now);
        List<Long> list = new ArrayList<>();
        Item item = new Item();
        //日志信息
        DataLog dataLog = new DataLog();
        dataLog.setOperateUser(enNm);
        dataLog.setOperateIp(operateIp);
        //统计信息
        ItemStatis itemStatis = new ItemStatis();
        //items表录入数据
        if (json.has("item")) {
            JsonNode jsonItem = json.findValue("item");
            item = Json.fromJson(jsonItem,Item.class);
            //更新商品信息
            if (jsonItem.has("id")) {
                Item originItem = itemService.getItem(item.getId());
                item.setOrDestroy(originItem.getOrDestroy());
                itemService.itemUpdate(item);
                //数据录入data_log表
                Long itemId = item.getId();
                dataLog.setOperateType("修改商品");
                dataLog.setLogContent("修改商品"+itemId);
                List<Inventory> originInv = inventoryService.getInventoriesByItemId(itemId);
                List<VaryPrice> originVP = new ArrayList<>();
                for(Inventory inventory : originInv) {
                    VaryPrice varyPrice = new VaryPrice();
                    varyPrice.setInvId(inventory.getId());
                    List<VaryPrice> varyPriceList = varyPriceService.getVaryPriceBy(varyPrice);
                    for(VaryPrice vp : varyPriceList) {
                        originVP.add(vp);
                    }
                }
                dataLog.setOriginData("{\"item\":"+Json.toJson(originItem).toString() + ",\"inventories\":"+Json.toJson(originInv).toString() + ",\"varyPrices\":"+Json.toJson(originVP).toString() + "}");
                dataLog.setNewData(json.toString());
                dataLogService.insertDataLog(dataLog);
            }
            //录入商品信息
            else {
                itemService.itemInsert(item);
                //数据录入data_log表
                dataLog.setOperateType("新增商品");
                dataLog.setLogContent("新增商品"+item.getId());
                dataLog.setOriginData("{}");
                dataLog.setNewData(json.toString());
                dataLogService.insertDataLog(dataLog);
            }
        }
        //往inventories表插入数据
        if (json.has("inventories")) {
            for(final JsonNode jsonNode : json.findValue("inventories")) {
                Inventory inventory = new Inventory();
                Inventory originInv = new Inventory();
                if (jsonNode.has("inventory")) {
                    JsonNode jsonInv = jsonNode.findValue("inventory");
                    inventory = Json.fromJson(jsonInv, Inventory.class);
                    inventory.setItemId(item.getId());
                    inventory.setPostalTaxRate("0");
                    inventory.setItemDiscount(inventory.getItemPrice().divide(inventory.getItemSrcPrice(),2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(10)));
                    String startAt = inventory.getStartAt();//现上架时间
                    String endAt = inventory.getEndAt();//现下架时间
                    Long startTimes = null;//现上架时间毫秒数
                    Long endTimes = null;//现下架时间毫秒数
                    try {
                        startTimes = sdf.parse(startAt).getTime();
                        endTimes = sdf.parse(endAt).getTime();
                    } catch (ParseException e) {
//                        e.printStackTrace();
                        Logger.error(Throwables.getStackTraceAsString(e));
                    }
                    //更新库存信息
                    if (jsonInv.has("id")) {
                        String state = inventory.getState();        //现sku状态
                        Integer orUpdate = 0;   //orUpdate状态为标识是否执行更新语句
                        originInv = inventoryService.getInventory(inventory.getId());
                        String originStartAt = originInv.getStartAt();//原上架时间
                        String originEndAt = originInv.getEndAt();//原下架时间
                        Long originStartTimes = null;//原上架时间毫秒数
                        Long originEndTimes = null;//原下架架时间毫秒数
                        try {
                            originStartTimes = sdf.parse(originStartAt).getTime();
                            originEndTimes = sdf.parse(originEndAt).getTime();
                        } catch (ParseException e) {
//                            e.printStackTrace();
                            Logger.error(Throwables.getStackTraceAsString(e));
                        }
                        String originState = originInv.getState();  //原sku状态
                        inventory.setSoldAmount(originInv.getSoldAmount());
                        inventory.setOrDestroy(originInv.getOrDestroy());
                        inventory.setInvTitle(item.getItemTitle());
                        inventory.setShareCount(originInv.getShareCount());
                        inventory.setCollectCount(originInv.getCollectCount());
                        inventory.setBrowseCount(originInv.getBrowseCount());
                        Integer restAmount = inventory.getRestAmount();//现剩余库存
                        Integer orginResAm = originInv.getRestAmount();//原剩余库存
                        //如果剩余库存量增加, 增加量累加到库存总量amount
                        if (restAmount > orginResAm) {
                            inventory.setAmount(originInv.getAmount() + (restAmount - orginResAm));
                        } else {
                            inventory.setAmount(originInv.getAmount());
                        }
                        //修改SKU
                        //如果修改时间,或时间状态都不修改,状态以时间为准
                        if (!originStartTimes.equals(startTimes) || !originEndTimes.equals(endTimes) || (originStartTimes.equals(startTimes) && originEndTimes.equals(endTimes) && originState.equals(state))) {
                            if (startTimes>nowTimes) {//上架时间比现在时间大为预售状态
                                inventory.setState("P");
                            }
                            if (startTimes<nowTimes && nowTimes<endTimes) {//现在时间介于上架和下架时间之间为正常状态
                                inventory.setState("Y");
                            }
                            if (endTimes<nowTimes) {//下架时间比当前时间小为下架状态
                                inventory.setState("D");
                            }
                        }
                        //如果时间不改 修改状态
                        else if (originStartTimes.equals(startTimes) && originEndTimes.equals(endTimes) && !originState.equals(state)) {
                            //P-->Y(上架时间改为现在)
                            if ("P".equals(originState) && "Y".equals(state)) {
                                inventory.setStartAt(nowStr);
                                startTimes = nowTimes;
                            }
                            // P-->D或Y-->D(下架时间改为现在)
                            if (("P".equals(originState) || "Y".equals(originState)) && "D".equals(state)){
                                inventory.setEndAt(nowStr);
                                endTimes = nowTimes;
                            }

                        }
                        //状态由预售到预售, 修改上架时间  ==> 修改上架schedule
                        if ("P".equals(originState) && "P".equals(state) && !originStartTimes.equals(startTimes)) {
                            Logger.info(inventory.getId()+" auto on shelves start...");
                            newScheduler.scheduleOnce(Duration.create(startTimes-nowTimes, TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
                        }
                        //状态由预售到正常, 修改上架时间  ==> 修改下架schedule
                        if ("P".equals(originState) && "Y".equals(state) && !originStartTimes.equals(startTimes)) {
                            orUpdate = 1;
                            inventoryService.updateInventory(inventory);
                            Logger.info(inventory.getId()+" auto off shelves start...");
                            newScheduler.scheduleOnce(Duration.create(endTimes-nowTimes, TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
                        }
                        //状态由预售/正常 到下架, 修改下架时间 ==> 停止schedule
                        if (("P".equals(originState) || "Y".equals(originState)) && "D".equals(state) && !originEndTimes.equals(endTimes)) {
                            orUpdate = 1;
                            inventoryService.updateInventory(inventory);
                            newScheduler.scheduleOnce(Duration.create(1000, TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());//停止scheduler
                        }
                        //状态由正常/下架 到预售, 修改上架时间 ==> 修改上架schedule
                        if (("Y".equals(originState) || "D".equals(originState)) && "P".equals(state) && !originStartTimes.equals(startTimes)) {
                            orUpdate = 1;
                            inventoryService.updateInventory(inventory);
                            Logger.info(inventory.getId()+" auto on shelves start...");
                            newScheduler.scheduleOnce(Duration.create(startTimes-nowTimes, TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
                        }
                        //状态由正常到正常, 修改下架时间 ==> 修改下架schedule
                        if ("Y".equals(originState) && "Y".equals(state) && !originEndTimes.equals(endTimes)) {
                            Logger.info(inventory.getId()+" auto off shelves start...");
                            newScheduler.scheduleOnce(Duration.create(endTimes-nowTimes, TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
                        }
                        //状态由下架到正常, 修改下架时间  ==> 修改下架schedule
                        if ("D".equals(originState) && "Y".equals(state) && !originEndTimes.equals(endTimes)) {
                            orUpdate = 1;
                            inventoryService.updateInventory(inventory);
                            Logger.info(inventory.getId()+" auto off shelves start...");
                            newScheduler.scheduleOnce(Duration.create(endTimes-nowTimes, TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
                        }
                        if (orUpdate.equals(0)) {
                            //以上没有执行修改语句时,此时修改数据
                            inventoryService.updateInventory(inventory);
                        }

                        if ("Y".equals(originState)) {
                            //修改pin_sku表中状态或时间
                            List<PinSku> pinSkuList = pingouService.getPinSkuByInvId(inventory.getId());
                            for (PinSku pinSku : pinSkuList) {
                                Date pinStartAt = new Date();
                                Date pinEndAt = new Date();
                                Long pinStartTimes = 0L;
                                Long pinEndTimes = 0L;
                                try {
                                    pinStartAt = sdf.parse(pinSku.getStartAt());
                                    pinEndAt = sdf.parse(pinSku.getEndAt());
                                    pinStartTimes = pinStartAt.getTime();
                                    pinEndTimes = pinEndAt.getTime();
                                } catch (ParseException e) {
//                                e.printStackTrace();
                                    Logger.error(Throwables.getStackTraceAsString(e));
                                }
                                //sku由正常到预售,修改sku上架时间  或 sku由正常到正常,修改下架时间且现下架时间<pin_sku上架时间 ==> pin_sku 直接下架,修改状态,上下架时间为当前时间,停止schedule
                                if (("P".equals(state) && !originStartTimes.equals(startTimes)) || ("Y".equals(state) && !originEndAt.equals(endAt) && endTimes < pinStartTimes)) {
                                    pinSku.setStatus("D");
                                    pinSku.setStartAt(sdf.format(now));
                                    pinSku.setEndAt(sdf.format(now));
                                    newScheduler.scheduleOnce(Duration.create(1000, TimeUnit.MILLISECONDS), pingouOffShelfActor, pinSku.getPinId());
                                    pingouService.updatePinSku(pinSku);
                                }
                                //sku由正常到正常,修改下架时间 且 pin_sku上架时间<现下架时间<pin_sku下架时间
                                if ("Y".equals(state) && !originEndAt.equals(endAt) && pinStartTimes < endTimes && endTimes < pinEndTimes) {
                                    //pin_sku原为预售,只修改pin_sku下架时间
                                    if ("P".equals(pinSku.getStatus())) {
                                        pinSku.setEndAt(endAt);
                                        pingouService.updatePinSku(pinSku);
                                    }
                                    //pin_sku原为正常,修改下架时间,修改pin_sku下架schedule
                                    if ("Y".equals(pinSku.getStatus())) {
                                        pinSku.setEndAt(endAt);
                                        newScheduler.scheduleOnce(Duration.create(endTimes - nowTimes, TimeUnit.MILLISECONDS), pingouOffShelfActor, pinSku.getPinId());
                                        pingouService.updatePinSku(pinSku);
                                    }
                                }
                                //由正常到下架,下架时间为当前时间,停止schedule
                                if ("D".equals(state) && !"D".equals(pinSku.getStatus())) {
                                    pinSku.setStatus("D");
                                    pinSku.setEndAt(sdf.format(now));
                                    newScheduler.scheduleOnce(Duration.create(1000, TimeUnit.MILLISECONDS), pingouOffShelfActor, pinSku.getPinId());
                                    pingouService.updatePinSku(pinSku);
                                }
                            }
                            if (!originInv.getState().equals(inventory.getState())) {
                                //修改subject_price表中状态(获取SubjectPrice,更新状态)
                                List<SubjectPrice> subjectPriceList = subjectPriceService.getSbjPriceByInvId(inventory.getId());
                                subjectPriceList.stream().filter(subjectPrice -> "Y".equals(subjectPrice.getState())).forEach(subjectPrice -> {
                                    subjectPrice.setState("D");
                                   subjectPriceService.sbjPriceUpd(subjectPrice);
                                });
                            }
                        }

                    }
                    //录入库存信息
                    else {
                        if (startTimes>nowTimes) {//上架时间比现在时间大为预售状态
                            inventory.setState("P");
                        }
                        if (startTimes<nowTimes && nowTimes<endTimes) {//现在时间介于上架和下架时间之间为正常状态
                            inventory.setState("Y");
                        }
                        if (endTimes<nowTimes) {//下架时间比当前时间小为下架状态
                            inventory.setState("D");
                        }
                        inventory.setAmount(inventory.getRestAmount());
                        inventory.setInvTitle(item.getItemTitle());
//                        Logger.error("sku信息:::::::"+inventory.toString());
                        inventoryService.insertInventory(inventory);
                        String createDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
                        itemStatis.setCreateDate(createDate);
                        itemStatis.setSkuId(inventory.getId());
                        itemStatis.setColor(inventory.getItemColor());
                        itemStatis.setSize(inventory.getItemSize());
                        itemStatisService.insertItemStatis(itemStatis);
                    }

                    //-- 创建Actor --//
                    //修改时,修改时间且下架时间大于现在时间 或 新增sku时 启动Actor
                    if (null==inventory.getId()) {
                        if (startTimes>nowTimes) {
                            //上架时间大于现在时间 启动上架schedule
                            Logger.info(inventory.getId()+" auto on shelves start...");
                            newScheduler.scheduleOnce(Duration.create(startTimes-nowTimes, TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
                        }
                        if (startTimes<nowTimes && endTimes>nowTimes ) {
                            //上架时间小于现在时间小于下架时间 启动下架scheduler
                            Logger.info(inventory.getId()+" auto off shelves start...");
                            newScheduler.scheduleOnce(Duration.create(endTimes-nowTimes, TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
                        }
                    }

                    list.add(inventory.getId());
                }
                //往vary_price表插入数据
                if (jsonNode.has("varyPrices")) {
                    for(final JsonNode varyPriceNode : jsonNode.findValue("varyPrices")) {
                        VaryPrice varyPrice = Json.fromJson(varyPriceNode, VaryPrice.class);
                        varyPrice.setInvId(inventory.getId());
                        //更新多样化价格信息
                        if (varyPriceNode.has("id")) {
                            //SKU状态改变,多样化价格状态相应变化
                            if ("P".equals(inventory.getState()) && "Y".equals(varyPrice.getStatus())) {
                                varyPrice.setStatus("P");
                            }
                            if ("Y".equals(inventory.getState()) && "P".equals(varyPrice.getStatus())) {
                                varyPrice.setStatus("Y");
                            }
                            if ("D".equals(inventory.getState()) && ("P".equals(varyPrice.getStatus())||"Y".equals(varyPrice.getStatus()))) {
                                varyPrice.setStatus("D");
                            }
//                            if ((originInv.getState().equals("P") || originInv.getState().equals("Y")) && inventory.getState().equals("D"))  {
//                                varyPrice.setStatus(inventory.getState());
//                            }
                            VaryPrice originVp = varyPriceService.getVaryPriceById(varyPrice.getId());
                            varyPrice.setSoldAmount(originVp.getSoldAmount());
                            varyPriceService.updateVaryPrice(varyPrice);
                        }
                        else {
                            varyPrice.setStatus(inventory.getState());
                            varyPriceService.insertVaryPrice(varyPrice);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 自动修改SkU状态(上架/下架)
     * @param invId 库存id
     */
    public void updateState(Long invId) {
        Inventory inventory = inventoryService.getInventory(invId);
        String state = inventory.getState();
        Date now = new Date();
        Long nowTimes = now.getTime();
        Long endTimes = 0l;
        try {
            endTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inventory.getEndAt()).getTime();
        } catch (ParseException e) {
//            e.printStackTrace();
            Logger.error(Throwables.getStackTraceAsString(e));
        }
        if (!"D".equals(state)) {
            if ("P".equals(state)) {
                inventory.setState("Y");
                //启动下架schedule
                Logger.info("sku "+inventory.getId()+"auto off shelves start...");
                try {
                    //等待上架schedule删除后创建下架schedule
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    Logger.error(Throwables.getStackTraceAsString(e));
                }
                newScheduler.scheduleOnce(Duration.create(endTimes-nowTimes,TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
            }
            if ("Y".equals(state)) {
                inventory.setState("D");
            }
            inventoryService.updateInventory(inventory);
            //修改多样化价格状态
            VaryPrice varyPrice = new VaryPrice();
            varyPrice.setInvId(invId);
            List<VaryPrice> varyPriceList = varyPriceService.getVaryPriceBy(varyPrice);
            for(VaryPrice vp : varyPriceList) {
                vp.setStatus(inventory.getState());
                varyPriceService.updateVaryPrice(vp);
            }
            //sku自动下架
            if ("D".equals(inventory.getState())) {
                //修改pin_sku的状态,正常状态的拼购商品置为下架
                List<PinSku> pinSkuList = pingouService.getPinSkuByInvId(inventory.getId());
                pinSkuList.stream().filter(pinSku -> "Y".equals(pinSku.getStatus())).forEach(pinSku -> {
                    pinSku.setStatus("D");
                    pinSku.setEndAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    pingouService.updatePinSku(pinSku);
                });
                //修改subject_price的状态,正常状态的自定义价格商品置为下架
                List<SubjectPrice> subjectPriceList = subjectPriceService.getSbjPriceByInvId(inventory.getId());
                subjectPriceList.stream().filter(subjectPrice -> "Y".equals(subjectPrice.getState())).forEach(subjectPrice -> {
                    subjectPrice.setState("D");
                    subjectPriceService.sbjPriceUpd(subjectPrice);
                });
            }
        }

    }

}

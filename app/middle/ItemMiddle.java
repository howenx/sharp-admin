package middle;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.*;
import entity.pingou.PinSku;
import modules.LevelFactory;
import modules.NewScheduler;
import play.Logger;
import play.libs.Json;
import scala.concurrent.duration.Duration;
import service.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
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
    private LevelFactory levelFactory;

    @Inject
    private ItemMiddle itemMiddle;

    @Inject
    @Named("inventoryAutoShelvesActor")
    private ActorRef inventoryAutoShelvesActor;


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
            if (jsonItem.has("publicity")) {
                ((ObjectNode) jsonItem).put("publicity",jsonItem.findValue("publicity").toString());
            }
            if (jsonItem.has("itemFeatures")) {
                ((ObjectNode) jsonItem).put("itemFeatures",jsonItem.findValue("itemFeatures").toString());
            }
            if (jsonItem.has("itemDetailImgs")) {
                ((ObjectNode) jsonItem).put("itemDetailImgs",jsonItem.findValue("itemDetailImgs").toString());
            }
            item = Json.fromJson(json.findValue("item"),Item.class);
//            Logger.error(item.toString());
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
//                Logger.error("log数据:"+dataLog.toString());
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
//                Logger.error("log数据:"+dataLog.toString());
                dataLogService.insertDataLog(dataLog);
            }
        }
        //往inventories表插入数据
        if (json.has("inventories")) {
            for(final JsonNode jsonNode : json.findValue("inventories")) {
//                Logger.error("库存数据:"+jsonNode);
                Inventory inventory = new Inventory();
                Inventory originInv = new Inventory();
                if (jsonNode.has("inventory")) {
                    JsonNode jsonInv = jsonNode.findValue("inventory");
                    inventory = Json.fromJson(jsonInv, Inventory.class);
                    inventory.setItemId(item.getId());
                    Timestamp startAt = inventory.getStartAt();
                    Timestamp endAt = inventory.getEndAt();
                    Long startTimes = startAt.getTime();
                    Long endTimes = endAt.getTime();
                    if (startTimes>nowTimes) {//上架时间比现在时间大为预售状态
                        inventory.setState("P");
                    }
                    if (startTimes<nowTimes && nowTimes<endTimes) {//现在时间介于上架和下架时间之间为正常状态
                        inventory.setState("Y");
                    }
                    if (endTimes<nowTimes) {//下架时间比当前时间小为下架状态
                        inventory.setState("D");
                    }
//                Logger.error(inventory.toString());
                    //更新库存信息
                    if (jsonInv.has("id")) {
                        originInv = inventoryService.getInventory(inventory.getId());
                        inventory.setSoldAmount(originInv.getSoldAmount());
                        inventory.setOrDestroy(originInv.getOrDestroy());
                        inventory.setInvTitle(originInv.getInvTitle());
                        inventory.setShareCount(originInv.getShareCount());
                        inventory.setCollectCount(originInv.getCollectCount());
                        inventory.setBrowseCount(originInv.getBrowseCount());
                        //修改SKU
                        //由预售或正常改为下架,下架时间改为当前时间
                        if (inventory.getState().equals("D") && (originInv.getState().equals("Y")||originInv.getState().equals("P"))) {
                            inventory.setEndAt(new Timestamp(nowTimes));
                        }
                        inventoryService.updateInventory(inventory);
                        if (!originInv.getState().equals(inventory.getState())) {
                            //修改pin_sku表中状态(获取PinSku,更新状态)
                            List<PinSku> pinSkuList = pingouService.getPinSkuByInvId(inventory.getId());
                            for(PinSku pinSku : pinSkuList) {
                                pinSku.setStatus(inventory.getState());
                                if (inventory.getState().equals("D") && (originInv.getState().equals("Y")||originInv.getState().equals("P"))) {
                                    pinSku.setEndAt(sdf.format(now));
                                }
                                pingouService.updatePinSku(pinSku);
                            }
                            //修改subject_price表中状态(获取SubjectPrice,更新状态)
                            List<SubjectPrice> subjectPriceList = subjectPriceService.getSbjPriceByInvId(inventory.getId());
                            for(SubjectPrice subjectPrice : subjectPriceList) {
//                            subjectPrice.setState(inventory.getState());
                                subjectPriceService.sbjPriceUpd(subjectPrice);
                            }
                        }
                    }
                    //录入库存信息
                    else {
                        inventory.setInvTitle(item.getItemTitle());
//                    Logger.error("sku信息:"+inventory);
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
                    if ((null!=inventory.getId() && (originInv.getStartAt()!=startAt||originInv.getEndAt()!=endAt) && endTimes>nowTimes) || null==inventory.getId()) {
                        if (((null!=inventory.getId()&&originInv.getStartAt()!=startAt) || null==inventory.getId()) && startTimes>nowTimes ) {
                            //上架时间大于现在时间 启动上架schedule
                            Logger.error("auto on shelves start...");
                            newScheduler.scheduleOnce(Duration.create(startTimes-nowTimes, TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
                        }
                        if (((null!=inventory.getId()&&originInv.getStartAt()!=startAt) || null==inventory.getId()) && startTimes<nowTimes && endTimes>nowTimes ) {
                            //上架时间小于现在时间小于下架时间 启动下架scheduler
                            Logger.error("auto off shelves start...");
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
//                        varyPrice.setStatus("Y");
                        //SKU状态修改,多样化价格和SKU状态保持一致
                        if (!originInv.getState().equals(inventory.getState())) {
                            varyPrice.setStatus(inventory.getState());
                        }
                        //更新多样化价格信息
                        if (varyPriceNode.has("id")) {
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
        Long endTimes = inventory.getEndAt().getTime();
        if (state.equals("P")) {
            inventory.setState("Y");
            //启动下架schedule
            Logger.error("auto off shelves start...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            newScheduler.scheduleOnce(Duration.create(endTimes-nowTimes,TimeUnit.MILLISECONDS), inventoryAutoShelvesActor, inventory.getId());
        }
        if (state.equals("Y")) {
            inventory.setState("D");

        }
        inventoryService.updateInventory(inventory);
        //修改pin_sku表中状态(获取PinSku,更新状态)
        List<PinSku> pinSkuList = pingouService.getPinSkuByInvId(inventory.getId());
        for(PinSku pinSku : pinSkuList) {
            pinSku.setStatus(inventory.getState());
            pingouService.updatePinSku(pinSku);
        }
        //修改subject_price表中状态(获取SubjectPrice,更新状态)
        List<SubjectPrice> subjectPriceList = subjectPriceService.getSbjPriceByInvId(inventory.getId());
        for(SubjectPrice subjectPrice : subjectPriceList) {
//      subjectPrice.setState(inventory.getState());
            subjectPriceService.sbjPriceUpd(subjectPrice);
        }
    }

}

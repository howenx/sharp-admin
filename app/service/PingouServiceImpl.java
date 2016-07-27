package service;

import domain.pingou.*;
import mapper.PinActivityMapper;
import mapper.PinSkuMapper;
import modules.NewScheduler;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tiffany on 16/1/20.
 */
public class PingouServiceImpl implements PingouService {

    @Inject
    PinSkuMapper pinSkuMapper;
    @Inject
    PinActivityMapper pinActivityMapper;

    @Inject
    private NewScheduler newScheduler;


    /**
     * 保存拼购商品和优惠券   Added by Tiffany Zhu 2016.01.20
     * @param pinSku
     */
    @Override
    public void pinSkuSave(PinSku pinSku) {
        //更新拼购
        if(pinSku.getPinId() != null) {
            //更新拼购
            pinSkuMapper.updatePinSku(pinSku);
        }
        //添加拼购
        else{
            pinSkuMapper.insertPinSku(pinSku);
        }


        /*
        PinSku pinSku = new PinSku();
        if(json.has("pinSku")){
            pinSku = Json.fromJson(json.findValue("pinSku"), PinSku.class);
        }
        //更新拼购
        if(pinSku.getPinId() != null){
            //更新拼购
            pinSkuMapper.updatePinSku(pinSku);
            //更新阶梯价格
            String updPriceId = "";
            List<PinTieredPrice> tieredPriceUpd = new ArrayList<>();
            List<PinTieredPrice> tieredPriceInst = new ArrayList<>();
            if(json.has("tieredPrice")){
                JsonNode tieredPriceJson = json.findValue("tieredPrice");
                if(tieredPriceJson.size()>0){
                    for(JsonNode price : tieredPriceJson){
                        PinTieredPrice pinTieredPrice = Json.fromJson(price,PinTieredPrice.class);
                        if(pinTieredPrice.getMasterCouponClass() == null || pinTieredPrice.getMasterCouponClass().equals("")){
                            pinTieredPrice.setMasterCouponClass(null);
                            pinTieredPrice.setMasterCoupon(null);
                            pinTieredPrice.setMasterCouponQuota(null);
                            pinTieredPrice.setMasterCouponStartAt(null);
                            pinTieredPrice.setMasterCouponEndAt(null);
                        }
                        if(pinTieredPrice.getMemberCouponClass() == null || pinTieredPrice.getMemberCouponClass().equals("")){
                            pinTieredPrice.setMemberCouponClass(null);
                            pinTieredPrice.setMemberCoupon(null);
                            pinTieredPrice.setMemberCouponQuota(null);
                            pinTieredPrice.setMemberCouponStartAt(null);
                            pinTieredPrice.setMemberCouponEndAt(null);
                        }
                        pinTieredPrice.setPinId(pinSku.getPinId());
                        //添加
                        if(pinTieredPrice.getId() == null){
                            tieredPriceInst.add(pinTieredPrice);
                        }
                        //更新
                        else{
                            updPriceId = updPriceId + "," + pinTieredPrice.getId().toString();
                            tieredPriceUpd.add(pinTieredPrice);
                        }
                    }
                    if(tieredPriceInst.size()>0){
                        pinSkuMapper.addTieredPrice(tieredPriceInst);
                    }
                    if (tieredPriceUpd.size()>0){
                        pinSkuMapper.updTieredPrice(tieredPriceUpd);
                    }
                    Logger.error(tieredPriceUpd.toString());
                    Logger.error(updPriceId);
                }
            }

            //删除阶梯价格
            if(json.has("beforeUpdPrice")){
                List<PinTieredPrice> delList = new ArrayList<>();
                JsonNode beforeUpdPriceJson = json.findValue("beforeUpdPrice");
                if(beforeUpdPriceJson.size() > 0){
                    for (JsonNode beforePrice : beforeUpdPriceJson){
                        PinTieredPrice beforeTieredPrice = Json.fromJson(beforePrice,PinTieredPrice.class);
                        if(!updPriceId.contains(beforeTieredPrice.getId().toString())){
                            delList.add(beforeTieredPrice);
                        }
                    }
                    pinSkuMapper.delTieredPrice(delList);
                }
            }
        }
        //添加拼购
        else{
            pinSkuMapper.insertPinSku(pinSku);
            List<PinTieredPrice> tieredPriceList = new ArrayList<>();
            if(json.has("tieredPrice")){
                JsonNode tieredPriceJson = json.findValue("tieredPrice");
                for(JsonNode price : tieredPriceJson){
                    PinTieredPrice pinTieredPrice = Json.fromJson(price,PinTieredPrice.class);
                    if(pinTieredPrice.getMasterCouponClass() == null || pinTieredPrice.getMasterCouponClass().equals("")){
                        pinTieredPrice.setMasterCouponClass(null);
                        pinTieredPrice.setMasterCoupon(null);
                        pinTieredPrice.setMasterCouponQuota(null);
                        pinTieredPrice.setMasterCouponStartAt(null);
                        pinTieredPrice.setMasterCouponEndAt(null);
                    }
                    if(pinTieredPrice.getMemberCouponClass() == null || pinTieredPrice.getMemberCouponClass().equals("")){
                        pinTieredPrice.setMemberCouponClass(null);
                        pinTieredPrice.setMemberCoupon(null);
                        pinTieredPrice.setMemberCouponQuota(null);
                        pinTieredPrice.setMemberCouponStartAt(null);
                        pinTieredPrice.setMemberCouponEndAt(null);
                    }
                    pinTieredPrice.setPinId(pinSku.getPinId());
                    tieredPriceList.add(pinTieredPrice);
                }
            }
            pinSkuMapper.addTieredPrice(tieredPriceList);
        }
*/
    }

    /**
     * 取得全部的拼购      Added by Tiffany Zhu 2016.01.21
     * @return
     */
    @Override
    public List<PinSku> getPinSkuAll() {
       return pinSkuMapper.getPinSkuAll();
    }

    /**
     * ajax 分页查询    Added by Tiffany Zhu 2016.01.21
     * @param pinSku
     * @return
     */
    @Override
    public List<PinSku> getPinSkuPage(PinSku pinSku) {
        return pinSkuMapper.getPinSkuPage(pinSku);
    }

    /**
     * 通过ID获取拼购    Added by Tiffany Zhu 2016.01.22
     * @param pinId
     * @return
     */
    @Override
    public PinSku getPinSkuById(Long pinId) {
        return pinSkuMapper.getPinSkuById(pinId);
    }

    /**
     * 通过PinId获取阶梯价格    Added by Tiffany Zhu 2016.01.28
     * @return
     */
    @Override
    public List<PinTieredPrice> getTieredPriceByPinId(Long pinId) {
        return pinSkuMapper.getTieredPriceByPinId(pinId);
    }

    /**
     * 添加主题ID       Added by Tiffany Zhu 2016.01.29
     * @param pinSku
     */
    @Override
    public void updPinThemeId(PinSku pinSku) {
        pinSkuMapper.updPinThemeId(pinSku);
    }

    /**
     * 通过阶梯价格ID获取阶梯价格       Added by Tiffany Zhu 2016.02.15
     * @param id
     * @return
     */
    @Override
    public PinTieredPrice getTieredPriceByTieredId(Long id) {
        return pinSkuMapper.getTieredPriceByTieredId(id);
    }

    /**
     * 手动添加拼购活动     Added by Tiffany Zhu 2016.02.15
     * @param pinActivity
     */
    @Override
    public void activityManualAdd(PinActivity pinActivity) {
        pinActivityMapper.activityManualAdd(pinActivity);
    }

    /**
     * 手动添加拼购活动的优惠券     Added by Tiffany Zhu 2016.02.15
     * @param pinCoupon
     */
    @Override
    public void activityManualAddCoupon(PinCoupon pinCoupon) {
        pinActivityMapper.activityManualAddCoupon(pinCoupon);
    }

    /**
     * 添加拼购用户        Added by Tiffany Zhu 2016.02.15
     * @param pinUser
     */
    @Override
    public void pinUserAdd(PinUser pinUser) {
        pinActivityMapper.pinUserAdd(pinUser);
    }

    /**
     * 获取全部的拼购活动        Added by Tiffany Zhu 2016.02.16
     * @return
     */
    @Override
    public List<PinActivity> getActivityAll() {
        return pinActivityMapper.getActivityAll();
    }

    /**
     * 拼购活动 ajax分页查询        Added by Tiffany Zhu 2016.02.16
     * @param pinActivity
     * @return
     */
    @Override
    public List<PinActivity> getPinActivityPage(PinActivity pinActivity) {
        return pinActivityMapper.getPinActivityPage(pinActivity);
    }

    /**
     * 通过ID获取拼购活动       Added by Tiffany Zhu 2016.02.17
     * @param id
     * @return
     */
    @Override
    public PinActivity getActivityById(Long id) {
        return pinActivityMapper.getActivityById(id);
    }

    /**
     * 通过拼购团ID获取优惠券     Added by Tiffany Zhu 2016.02.17
     * @param id
     * @return
     */
    @Override
    public PinCoupon getCouponByActivityId(Long id) {
        return pinActivityMapper.getCouponByActivityId(id);
    }

    /**
     * 通过拼购团ID获取参团团员     Added by Tiffany Zhu 2016.02.17
     * @param id
     * @return
     */
    @Override
    public List<PinUser> getUserByActivityId(Long id) {
        return pinActivityMapper.getUserByActivityId(id);
    }

    /**
     * 批量添加拼购团用户        Added by Tiffany Zhu 2016.02.17
     * @param list
     */
    @Override
    public void pinUserAddList(List list) {
        pinActivityMapper.pinUserAddList(list);
    }

    /**
     * 更新参加拼购活动的用户      Added by Tiffany Zhu 2016.02.18
     * @param hashMap
     */
    @Override
    public void updJoinPersonById(HashMap hashMap) {
        pinActivityMapper.updJoinPersonById(hashMap);
    }

    /**
     * 通过拼购ID获取所有的拼购团       Added by Tiffany Zhu 2016.02.18
     * @param pinId
     * @return
     */
    @Override
    public List<PinActivity> getActivityByPinId(Long pinId) {
        return pinActivityMapper.getActivityByPinId(pinId);
    }

    /**
     * 更新拼购商品状态         Added by Tiffany Zhu 2016.02.24
     */
    @Override
    public void updStatus() {
        pinSkuMapper.updStatus();
    }


    /**
     * 由invId获取拼购商品     Added By Sunny.Wu 2016.02.26
     * @param invId 库存Id
     * @return List of PinSku
     */
    @Override
    public List<PinSku> getPinSkuByInvId(Long invId) {
        return pinSkuMapper.getPinSkuByInvId(invId);
    }

    /**
     * 更新拼购             Added By Sunny.Wu 2016.02.26
     * @param pinSku
     */
    @Override
    public void updatePinSku(PinSku pinSku) {
        pinSkuMapper.updatePinSku(pinSku);
    }

    /**
     * 更新拼购下架       Added by Tiffany Zhu 2016.02.29
     * @param pinSku
     */
    @Override
    public void updStatusById(PinSku pinSku) {
        pinSkuMapper.updStatusById(pinSku);
    }

    @Override
    public void addTieredPrice(List list) {
        pinSkuMapper.addTieredPrice(list);
    }

    @Override
    public void updTieredPrice(List list) {
        pinSkuMapper.updTieredPrice(list);
    }

    @Override
    public void delTieredPrice(List list) {
        pinSkuMapper.delTieredPrice(list);
    }

    /**
     * 获取状态为"正常"和"预售"的拼购    Added by Tiffany Zhu 2016.03.01
     * @return
     */
    @Override
    public List<PinSku> getAvailablePingou() {
        return pinSkuMapper.getAvailablePingou();
    }

    /**
     * 查询成功收货的拼团活动的团长   Added By Sunny.Wu 2016.07.27
     * @return
     */
    @Override
    public List<PinActivity> getSuccActivityMaster() {
        return pinActivityMapper.getSuccActivityMaster();
    }

}

package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.pingou.PinCoupon;
import entity.pingou.PinSku;
import entity.pingou.PinTieredPrice;
import mapper.PinSkuMapper;
import play.Logger;
import play.libs.Json;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiffany on 16/1/20.
 */
public class PingouServiceImpl implements PingouService {

    @Inject
    PinSkuMapper pinSkuMapper;
    /**
     * 保存拼购商品和优惠券   Added by Tiffany Zhu 2016.01.20
     * @param json
     */
    @Override
    public void pinSkuSave(JsonNode json) {
        PinSku pinSku = new PinSku();
        if(json.has("pinSku")){
            pinSku = Json.fromJson(json.findValue("pinSku"), PinSku.class);
        }
        //更新拼购
        if(pinSku.getPinId() != null){
            pinSkuMapper.updatePinSku(pinSku);
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

    @Override
    public List<PinTieredPrice> getTieredPriceByPinId(Long pinId) {
        return pinSkuMapper.getTieredPriceByPinId(pinId);
    }
}

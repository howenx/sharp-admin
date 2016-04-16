package service;

import com.fasterxml.jackson.databind.JsonNode;
import domain.Carriage;
import mapper.CarriageMapper;
import play.Logger;
import play.libs.Json;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sunny Wu.
 */
public class CarriageServiceImpl implements CarriageService{

    @Inject
    private CarriageMapper carriageMapper;

    @Override
    public  void carrModelSave(JsonNode json) {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        Logger.error(json.toString());
        //标识是否为更新
        boolean orUpdate = false;
        for(final JsonNode jsonNode : json) {
            Carriage carriage = Json.fromJson(jsonNode, Carriage.class);
            // 若是更新模板 由modelCode得到现有数据库该模板的所有数据,删除数据库中该模板的数据
            if(jsonNode.has("modelCode")) {
                String modelCode = carriage.getModelCode();
                List<Carriage> carrList = carriageMapper.getCarrsByModel(modelCode);
                for(Carriage carr : carrList) {
                    Long id = carr.getId();
                    carriageMapper.delCarrById(id);
                }
                orUpdate = true;
                break;
            }
        }

        for(final JsonNode jsonNode : json) {
            Carriage carriage = Json.fromJson(jsonNode, Carriage.class);
            //更新模板
            if (orUpdate) {
                carriageMapper.insertCarriage(carriage);
            }
            //录入新的模板
            if (!orUpdate) {
                carriage.setModelCode(uuid);
                carriageMapper.insertCarriage(carriage);
            }
        }

    }

    /**
     * 录入一条运费信息
     * @param carriage 运费
     * @return id
     */
    @Override
    public Integer insertCarriage(Carriage carriage) {
        return carriageMapper.insertCarriage(carriage);
    }

    /**
     * 更新一条运费信息
     * @param carriage
     */
    @Override
    public void updateCarriage(Carriage carriage) {
        carriageMapper.updateCarriage(carriage);
    }

    /**
     * 由id删除一条运费信息
     * @param id
     */
    @Override
    public void delCarrById(Long id) {
        carriageMapper.delCarrById(id);
    }

    /**
     * 由modelCode删除运费模板的所有数据
     * @param modelCode
     */
    public boolean delModelByCode(String modelCode) {
        //由modelCode获得该运费模板的所有记录,由id逐个删除
        List<Carriage> carrList = carriageMapper.getCarrsByModel(modelCode);
        if (null != carrList && !"".equals(carrList)) {
            for(Carriage carr : carrList) {
                Long id = carr.getId();
                Logger.error(id.toString());
                carriageMapper.delCarrById(id);
            }
            return true;
        }
        return false;
    }
    /**
     * 根据modelCode获得模板名称
     * @param modelCode
     * @return modelName
     */
    @Override
    public String getModelName(String modelCode) {
        return carriageMapper. getModelName(modelCode);
    }

    /**
     * 获取所有的运费信息
     * @return list of Carriage
     */
    @Override
    public List<Carriage> getAllCarriage() {
        return carriageMapper.getAllCarriage();
    }

    /**
     * 获取运费模板列表(modelCode,modelName)
     * @return list of Carriage
     */
    @Override
    public List<Carriage> getModels(){
        return carriageMapper.getModels();
    }

    /**
     * 由modelCode获取该模板的运费信息
     * @param modelCode
     * @return list of Carriage
     */
    public List<Carriage> getCarrsByModel(String modelCode) {
        return carriageMapper.getCarrsByModel(modelCode);
    }

}

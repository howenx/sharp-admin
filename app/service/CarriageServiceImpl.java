package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import entity.Carriage;
import mapper.CarriageMapper;
import play.libs.Json;

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
        for(final JsonNode jsonNode : json) {
            Carriage carriage = Json.fromJson(jsonNode, Carriage.class);
            carriage.setModelCode(uuid);
            carriageMapper.insertCarriage(carriage);
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
     * 根据modelCode获取一条运费信息
     * @param modelCode
     * @return Carriage
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
     * 获取运费模板
     * @return list of Carriage
     */
    @Override
    public List<Carriage> getModels(){
        return carriageMapper.getModels();
    }

    public List<Carriage> getCarrsByModel(String modelCode) {
        return carriageMapper.getCarrsByModel(modelCode);
    }

}

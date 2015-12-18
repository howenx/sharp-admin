package service;

import com.fasterxml.jackson.databind.JsonNode;
import entity.Carriage;

import java.util.List;

/**
 * Created by Sunny Wu.
 */
public interface CarriageService {

    void carrModelSave(JsonNode json);

    Integer insertCarriage(Carriage carriage);

    void delCarrById(Long id);

    boolean delModelByCode(String modelCode);

    void updateCarriage(Carriage carriage);

    String getModelName(String modelCode);

    List<Carriage> getAllCarriage();

    /**
     * 获得所有的模板(modelCode,modelName)
     * @return list of carriage
     */
    List<Carriage> getModels();

    /**
     * 由modelCode 得到此模板下所有的计算方式
     * @return list of carriage
     */
    List<Carriage> getCarrsByModel(String modelCode);

}

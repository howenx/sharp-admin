package service;

import com.google.inject.Inject;
import entity.Carriage;
import mapper.CarriageMapper;

import java.util.List;

/**
 * Created by Sunny Wu.
 */
public class CarriageServiceImpl implements CarriageService{

    @Inject
    private CarriageMapper carriageMapper;

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
    public List<Carriage> getModel(){
        return carriageMapper.getModel();
    }

}

package service;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import entity.SystemParam;

/**
 * Created by tiffany on 15/12/19.
 */
public interface SysParamService {
    /**
     * 添加系统参数
     * @param json
     */
    void insertParam(JsonNode json);

    List<SystemParam> getParamAll();
}

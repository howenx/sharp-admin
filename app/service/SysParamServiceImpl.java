package service;

import com.fasterxml.jackson.databind.JsonNode;
import domain.SystemParam;
import mapper.SysParamMapper;
import play.Logger;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 15/12/19.
 */
public class SysParamServiceImpl implements SysParamService {
    @Inject
    SysParamMapper sysParamMapper;

    /**
     * 添加系统参数
     * @param json
     */
    @Override
    public void insertParam(JsonNode json) {
        Logger.error(json.toString());
        SystemParam systemParam = play.libs.Json.fromJson(json,SystemParam.class);
        sysParamMapper.insertParam(systemParam);
    }

    /**
     * 系统参数列表
     * @return
     */
    @Override
    public List<SystemParam> getParamAll() {
        return sysParamMapper.getParamAll();
    }
}

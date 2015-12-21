package mapper;

import entity.SystemParam;

import java.util.List;

/**
 * Created by tiffany on 15/12/19.
 */
public interface SysParamMapper {
    /**
     * 添加系统参数
     * @param param
     */
    void insertParam(SystemParam param);

    /**
     * 系统参数列表
     * @return
     */
    List<SystemParam> getParamAll();
}

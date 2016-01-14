package mapper;

import entity.DataLog;

import java.util.List;

/**
 * Created by Sunny Wu on 16/1/13.
 * kakao china.
 */
public interface DataLogMapper {

    /**
     * 录入一条日志记录
     * @param dataLog 日志信息
     * @return id
     */
    Long insertDataLog(DataLog dataLog);

    /**
     * 查看一条日志信息
     * @param id
     * @return DataLog
     */
    DataLog getDataLog(Long id);

    /**
     *  查询所有的日志记录
     * @return list of dataLog
     */
    List<DataLog> getAllDataLogs();

    /**
     *  查询一页日志记录
     * @param dataLog
     * @return list of dataLog
     */
    List<DataLog> getDataLogPage(DataLog dataLog);

}

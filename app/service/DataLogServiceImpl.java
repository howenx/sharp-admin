package service;

import domain.DataLog;
import mapper.DataLogMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/13.
 * kakao china.
 */
public class DataLogServiceImpl implements DataLogService {

    @Inject
    private DataLogMapper dataLogMapper;

    /**
     * 录入一条日志记录
     * @param dataLog 日志信息
     * @return id
     */
    @Override
     public Long insertDataLog(DataLog dataLog){
         return dataLogMapper.insertDataLog(dataLog);
     }

    /**
     * 查看一条日志信息
     * @param id
     * @return DataLog
     */
    @Override
    public DataLog getDataLog(Long id) {
        return dataLogMapper.getDataLog(id);
    }

    /**
     *  查询所有的日志记录
     * @return list of dataLog
     */
    @Override
    public List<DataLog> getAllDataLogs() {
        return dataLogMapper.getAllDataLogs();
    }

    /**
     *  查询一页日志记录
     * @param dataLog
     * @return list of dataLog
     */
    @Override
    public List<DataLog> getDataLogPage(DataLog dataLog) {
        return dataLogMapper.getDataLogPage(dataLog);
    }

}

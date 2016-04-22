package service;

import domain.UserLog;
import mapper.UserLogMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/4/19.
 * kakao china.
 */
public class UserLogServiceImpl implements UserLogService{

    @Inject
    private UserLogMapper userLogMapper;

    /**
     * 录入一条用户日志信息
     * @param userLog 用户日志
     * @return id
     */
    @Override
    public Long insertUserLog(UserLog userLog) {
        return userLogMapper.insertUserLog(userLog);
    }

    /**
     * 查看一条用户日志信息
     * @param id
     * @return UserLog
     */
    @Override
    public UserLog getUserLog(Long id) {
        return userLogMapper.getUserLog(id);
    }

    /**
     *  查询所有的用户日志
     * @return list of userLog
     */
    @Override
    public List<UserLog> getAllUserLogs() {
        return userLogMapper.getAllUserLogs();
    }

    /**
     *  查询一页用户日志
     * @param userLog
     * @return list of userLog
     */
    @Override
    public List<UserLog> getUserLogPage(UserLog userLog) {
        return userLogMapper.getUserLogPage(userLog);
    }

}

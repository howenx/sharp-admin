package service;

import domain.UserLog;

import java.util.List;

/**
 * Created by Sunny Wu on 16/4/19.
 * kakao china.
 */
public interface UserLogService {

    /**
     * 录入一条用户日志信息
     * @param userLog 用户日志
     * @return id
     */
    Long insertUserLog(UserLog userLog);

    /**
     * 查看一条用户日志信息
     * @param id
     * @return UserLog
     */
    UserLog getUserLog(Long id);

    /**
     *  查询所有的用户日志
     * @return list of userLog
     */
    List<UserLog> getAllUserLogs();

    /**
     *  查询一页用户日志
     * @param userLog
     * @return list of userLog
     */
    List<UserLog> getUserLogPage(UserLog userLog);
}

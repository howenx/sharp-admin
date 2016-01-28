package service;

import entity.AdminUser;
import mapper.AdminUserMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Sunny Wu on 16/1/27.
 * kakao china.
 */
public class AdminUserServiceImpl implements AdminUserService {

    @Inject
    private AdminUserMapper adminUserMapper;

    /**
     * 注册一个新用户
     * @param adminUser 用户
     * @return userId
     */
    @Override
    public Long insertUser(AdminUser adminUser) {
        return adminUserMapper.insertUser(adminUser);
    }

    /**
     * 修改一条用户信息
     * @param adminUser 用户
     */
    @Override
    public void updateUser(AdminUser adminUser) {
        adminUserMapper.updateUser(adminUser);
    }

    /**
     * 根据条件查询用户信息
     * @param adminUser 用户
     * @return List of AdminUser
     */
    @Override
    public List<AdminUser> getUserBy(AdminUser adminUser) {
        return adminUserMapper.getUserBy(adminUser);
    }

    /**
     * 查询所有的用户信息
     * @return List of AdminUser
     */
    @Override
    public List<AdminUser> getAllUsers() {
        return adminUserMapper.getAllUsers();
    }

}

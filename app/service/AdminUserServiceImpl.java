package service;

import domain.AdminUser;
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
     * @return Boolean
     */
    @Override
    public Boolean insertUser(AdminUser adminUser) {
        return adminUserMapper.insertUser(adminUser)>=0;
    }

    /**
     * 修改一条用户信息
     * @param adminUser 用户
     * @return Boolean
     */
    @Override
    public Boolean updateUser(AdminUser adminUser) {
        return adminUserMapper.updateUser(adminUser)>=0;
    }

    /**
     * 根据条件查询用户信息(包括登录)
     * @param adminUser 用户
     * @return List of AdminUser
     */
    @Override
    public AdminUser getUserBy(AdminUser adminUser) {
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

    /**
     * 根据id删除一条用户信息
     * @param id 用户id
     */
    public void delUserById(Long id) {
        adminUserMapper.delUserById(id);
    }

    /**
     * 修改密码
     * @param adminUser 用户
     * @return Boolean
     */
    public Boolean chgPwd(AdminUser adminUser) {
        return adminUserMapper.chgPwd(adminUser)>=0;
    }

}

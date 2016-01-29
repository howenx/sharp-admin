package service;

import entity.AdminUser;

import java.util.List;

/**
 * Created by Sunny Wu on 16/1/27.
 * kakao china.
 */
public interface AdminUserService {

    Long insertUser(AdminUser adminUser);

    void updateUser(AdminUser adminUser);

    AdminUser getUserBy(AdminUser adminUser);

    List<AdminUser> getAllUsers();

}

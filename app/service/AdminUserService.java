package service;

import domain.AdminUser;
import domain.IDAdmin;

import java.util.List;

/**
 * Created by Sunny Wu on 16/1/27.
 * kakao china.
 */
public interface AdminUserService {

    Boolean insertUser(AdminUser adminUser);

    Boolean updateUser(AdminUser adminUser);

    AdminUser getUserBy(AdminUser adminUser);

    List<AdminUser> getAllUsers();

    void delUserById(Long id);

    Boolean chgPwd(AdminUser adminUser);

    /**
     * 录入一条用户类型信息  Add By Sunny.Wu 2016.05.20
     * @param idAdmin
     */
    void insertIDAdmin(IDAdmin idAdmin);

}

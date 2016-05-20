package mapper;

import domain.AdminUser;
import domain.IDAdmin;

import java.util.List;

/**
 * Created by Sunny Wu on 16/1/27.
 * kakao china.
 */
public interface AdminUserMapper {

    Long insertUser(AdminUser adminUser);

    Long updateUser(AdminUser adminUser);

    AdminUser getUserBy(AdminUser adminUser);

    List<AdminUser> getAllUsers();

    void delUserById(Long id);

    Long chgPwd(AdminUser adminUser);

    /**
     * 录入一条用户类型信息  Add By Sunny.Wu 2016.05.20
     * @param idAdmin
     */
    void insertIDAdmin(IDAdmin idAdmin);
}

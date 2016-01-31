package mapper;

import entity.AdminUser;

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

}

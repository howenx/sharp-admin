package service;

import com.google.inject.Inject;
import entity.IDAdmin;
import mapper.IDAdminMapper;

/**
 * Created by Sunny Wu on 16/1/27.
 * kakao china.
 */
public class IDAdminServiceImpl implements IDAdminService {

    @Inject
    private IDAdminMapper idAdminMapper;

    /**
     * 录入一条用户角色信息
     * @param idAdmin
     */
    @Override
    public void insertIDAdmin(IDAdmin idAdmin) {
        idAdminMapper.insertIDAdmin(idAdmin);
    }

    /**
     * 修改用户角色信息
     * @param idAdmin
     */
    @Override
    public void updateIDAdmin(IDAdmin idAdmin) {
        idAdminMapper.updateIDAdmin(idAdmin);
    }

}

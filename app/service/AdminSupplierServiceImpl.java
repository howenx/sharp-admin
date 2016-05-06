package service;

import domain.AdminSupplier;
import mapper.AdminSupplierMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 16/4/19.
 */
public class AdminSupplierServiceImpl implements AdminSupplierService{
    @Inject
    AdminSupplierMapper adminSupplierMapper;

    /**
     * 录入一条供应商信息   Added By Sunny.Wu 2016.05.05
     * @param adminSupplier
     * @return boolean
     */
    @Override
    public boolean insertSupplier(AdminSupplier adminSupplier) {
        return adminSupplierMapper.insertSupplier(adminSupplier) > 0;
    }

    /**
     * 通过ID获取供应商信息      Added by Tiffany Zhu 2016.04.20
     * @param id
     * @return
     */
    @Override
    public AdminSupplier getSupplierByUserId(Long id) {
        return adminSupplierMapper.getSupplierByUserId(id);
    }

    /**
     * 查询所有的供应商                 Add By Sunny.Wu 2016.04.20
     * @return list of adminSupplier
     */
    public List<AdminSupplier> getAllSuppliers() {
        return adminSupplierMapper.getAllSuppliers();
    }

}

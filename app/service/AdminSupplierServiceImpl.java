package service;

import domain.AdminSupplier;
import mapper.AdminSupplierMapper;

import javax.inject.Inject;

/**
 * Created by tiffany on 16/4/19.
 */
public class AdminSupplierServiceImpl implements AdminSupplierService{
    @Inject
    AdminSupplierMapper adminSupplierMapper;

    /**
     * 通过ID获取供应商信息      Added by Tiffany Zhu 2016.04.20
     * @param id
     * @return
     */
    @Override
    public AdminSupplier getSupplierByUserId(Long id) {
        return adminSupplierMapper.getSupplierByUserId(id);
    }


}

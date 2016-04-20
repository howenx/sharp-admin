package service;

import domain.AdminSupplier;

/**
 * Created by tiffany on 16/4/19.
 */
public interface AdminSupplierService {
    /**
     * 通过ID获取供应商信息      Added by Tiffany Zhu 2016.04.20
     * @param id
     * @return
     */
    AdminSupplier getSupplierByUserId(Long id);
}

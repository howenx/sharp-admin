package mapper;

import domain.AdminSupplier;

import java.util.List;

/**
 * Created by tiffany on 16/4/19.
 */
public interface AdminSupplierMapper {

    /**
     * 通过ID获取供应商信息      Added by Tiffany Zhu 2016.04.20
     * @param id
     * @return
     */
    AdminSupplier getSupplierByUserId(Long id);

    /**
     * 查询所有的供应商                 Add By Sunny.Wu 2016.04.20
     * @return list of adminSupplier
     */
    List<AdminSupplier> getAllSuppliers();

}

package mapper;

import entity.Brands;

import java.util.List;

/**
 * BrandsMapper.xml for BrandsMapper interface.
 *
 * <p>
 *
 * @author Howen Xiong
 */
public interface BrandsMapper {

		/**
		 * get brand by brandId.
		 * @param brandId Long
		 * @return Brands entity
		 */
		Brands getBrands(Long brandId);

		/**
		 * get all brands.
		 * @return List of Brands entity
		 */
		List<Brands> getAllBrands();

}

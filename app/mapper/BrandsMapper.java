package mapper;

import entity.Brands;

import java.util.List;

/**
 * BrandsMapping.xml for BrandsMapper interface.
 *
 * <p>
 *
 * @author Howen Xiong
 */
public interface BrandsMapper {

		/**
		 * get brand by brandId.
		 * @param brandId Integer
		 * @return Brands entity
		 */
		Brands getBrands(Integer brandId);

		/**
		 * get all brands.
		 * @return List of Brands entity
		 */
		List<Brands> getAllBrands();
}

package mapper;

import domain.Brands;

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

		/**
		 *
		 * @return
		 */
		List<Brands> getBrandsPage(Brands brands);

		/**
		 * 新增品牌
		 * @param brands
		 */
		void insertBrands(Brands brands);

}

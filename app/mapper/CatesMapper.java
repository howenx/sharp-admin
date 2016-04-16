package mapper;

import domain.Cates;

import java.util.HashMap;
import java.util.List;

/**
 * CatesMapper.xml for CatesMapper interface.
 *
 * <p>
 *
 * @author Howen Xiong
 */
public interface CatesMapper {

		/**
		 * get all parents item categories.
		 * @return List cates entity.
		 */
		List<Cates> getParentCates();

		/**
		 * get category by cateId or parentCateId or cateId,parentCateId.
		 * @param hashMap key(String),value(Integer) (cateId,parentCateId).
		 * @return List cates entity.
		 */
		List<Cates> getSubCates(HashMap<String, Long> hashMap);

		/**
		 * 由 cateId 得到Cates
		 * @param cateId 类别id
		 * @return Cates
		 */
		Cates getCate(Long cateId);

		/**
		 * 新增cates
		 * @param cates
		 */
		void insertCates(Cates cates);

		/**
		 * 商品分类列表
		 * @return
		 */
		List<Cates> getCatesAll();

}

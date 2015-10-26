package mapper;

import entity.Cates;

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
		List<Cates> getSubCates(HashMap<String, Integer> hashMap);
}

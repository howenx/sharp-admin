package service;

import domain.Category;
import mapper.CategoryMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tiffany on 16/7/27.
 */
public class CategoryServiceImpl implements CategoryService {
    @Inject
    private CategoryMapper categoryMapper;
    /**
     * 获取全部的分类入口        Added by Tiffany Zhu 2016.07.27
     * @return
     */
    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }
}

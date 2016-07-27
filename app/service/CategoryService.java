package service;

import domain.Category;

import java.util.List;

/**
 * Created by tiffany on 16/7/27.
 */
public interface CategoryService {
    /**
     * 获取全部的分类入口        Added by Tiffany Zhu 2016.07.27
     * @return
     */
    List<Category> getAllCategory();
}

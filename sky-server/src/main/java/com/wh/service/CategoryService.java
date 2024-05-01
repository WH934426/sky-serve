package com.wh.service;

import com.wh.dto.CategoryDTO;

/**
 * 菜品分类接口
 */
public interface CategoryService {

    /**
     * 新增菜品分类
     * @param categoryDTO 新增菜品分类需要的数据
     */
    void addCate(CategoryDTO categoryDTO);
}

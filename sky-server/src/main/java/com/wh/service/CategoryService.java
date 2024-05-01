package com.wh.service;

import com.wh.dto.CategoryDTO;
import com.wh.dto.CategoryPageQueryDTO;
import com.wh.entity.CategoryEntity;
import com.wh.result.PageResult;

/**
 * 菜品分类接口
 */
public interface CategoryService {

    /**
     * 新增菜品分类
     * @param categoryDTO 新增菜品分类需要的数据
     */
    void addCate(CategoryDTO categoryDTO);

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO 菜品分类分页查询需要的条件
     * @return 分页后的查询结果
     */
    PageResult<CategoryEntity> queryCateByPage(CategoryPageQueryDTO categoryPageQueryDTO);
}

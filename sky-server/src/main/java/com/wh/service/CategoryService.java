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

    /**
     * 根据id删除菜品分类
     * @param id 菜品id
     */
    void delCateById(Long id);

    /**
     * 修改菜品分类
     * @param categoryDTO 修改菜品分类需要的dto
     */
    void updateCate(CategoryDTO categoryDTO);

    /**
     * 启用禁用菜品分类
     * @param status 分类状态
     * @param id 菜品分类id
     */
    void updateCateByType(Integer status,Long id);
}

package com.wh.mapper;

import com.github.pagehelper.Page;
import com.wh.dto.CategoryPageQueryDTO;
import com.wh.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品分类接口
 */
@Mapper
public interface CategoryMapper {

    /**
     * 新增菜品分类
     * @param category 菜品分类
     */
    void addCate(CategoryEntity category);

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO 菜品分类分页查询条件
     * @return 菜品分类分页结果
     */
    Page<CategoryEntity> queryCateByPage(CategoryPageQueryDTO categoryPageQueryDTO);
}

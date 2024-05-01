package com.wh.mapper;

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
}

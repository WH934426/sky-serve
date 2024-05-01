package com.wh.service.impl;

import com.wh.constant.StatusConstant;
import com.wh.context.BaseContext;
import com.wh.dto.CategoryDTO;
import com.wh.entity.CategoryEntity;
import com.wh.mapper.CategoryMapper;
import com.wh.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    // TODO 需要多个Mapper，因此需要使用构造器注入
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 新增菜品分类
     *
     * @param categoryDTO 新增菜品分类需要的数据
     */
    @Override
    public void addCate(CategoryDTO categoryDTO) {
        CategoryEntity category = new CategoryEntity();
        // 将categoryDTO的数据复制到category中
        BeanUtils.copyProperties(categoryDTO,category);
        // 将分类状态默认为禁用状态
        category.setStatus(StatusConstant.DISABLE);
        // 设置创建时间和更新时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        // 设置创建人和更新人
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        // 添加菜品分类
        categoryMapper.addCate(category);
    }
}

package com.wh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wh.constant.MessageConstant;
import com.wh.constant.StatusConstant;
import com.wh.dto.CategoryDTO;
import com.wh.dto.CategoryPageQueryDTO;
import com.wh.entity.CategoryEntity;
import com.wh.exception.DeletionNotAllowedException;
import com.wh.mapper.CategoryMapper;
import com.wh.mapper.DishMapper;
import com.wh.mapper.SetmealMapper;
import com.wh.result.PageResult;
import com.wh.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper, DishMapper dishMapper, SetmealMapper setmealMapper) {
        this.categoryMapper = categoryMapper;
        this.dishMapper = dishMapper;
        this.setmealMapper = setmealMapper;
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
        BeanUtils.copyProperties(categoryDTO, category);
        // 将分类状态默认为禁用状态
        category.setStatus(StatusConstant.DISABLE);
        // 添加菜品分类
        categoryMapper.addCate(category);
    }

    /**
     * 菜品分类分页查询
     *
     * @param categoryPageQueryDTO 菜品分类分页查询需要的条件
     * @return 分页后的查询结果
     */
    @Override
    public PageResult<CategoryEntity> queryCateByPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        try (Page<CategoryEntity> queryCateByPage = categoryMapper.queryCateByPage(categoryPageQueryDTO)) {
            return new PageResult<>(queryCateByPage.getTotal(), queryCateByPage.getResult());
        }
    }

    /**
     * 根据id删除菜品分类
     *
     * @param id 菜品id
     */
    @Override
    public void delCateById(Long id) {
        log.info("需要删除的菜品id：{}", id);
        // 需要判断该分类是否被菜品/套餐关联，如果关联则不能删除
        Integer count = dishMapper.countByCategoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        count = setmealMapper.countByCategoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.delCateById(id);
    }

    /**
     * 修改菜品
     *
     * @param categoryDTO 修改菜品分类需要的dto
     */
    @Override
    public void updateCate(CategoryDTO categoryDTO) {
        CategoryEntity category = new CategoryEntity();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.updateCate(category);
    }

    /**
     * 启用禁用菜品分类
     *
     * @param status 分类状态
     * @param id     菜品分类id
     */
    @Override
    public void updateCateByType(Integer status, Long id) {
        CategoryEntity category = CategoryEntity.builder()
                .id(id)
                .status(status)
                .build();
        categoryMapper.updateCate(category);
    }

    /**
     * 根据类型查询菜品分类
     *
     * @param type 菜品类型
     * @return 菜品分类后的列表数据
     */
    @Override
    public List<CategoryEntity> getListByType(Integer type) {
        return categoryMapper.getListByType(type);
    }
}

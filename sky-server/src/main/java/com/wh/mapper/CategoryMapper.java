package com.wh.mapper;

import com.github.pagehelper.Page;
import com.wh.annotation.AutoFill;
import com.wh.dto.CategoryPageQueryDTO;
import com.wh.entity.CategoryEntity;
import com.wh.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜品分类接口
 */
@Mapper
public interface CategoryMapper {

    /**
     * 新增菜品分类
     *
     * @param category 菜品分类
     */
    @AutoFill(value = OperationType.INSERT)
    void addCate(CategoryEntity category);

    /**
     * 菜品分类分页查询
     *
     * @param categoryPageQueryDTO 菜品分类分页查询条件
     * @return 菜品分类分页结果
     */
    Page<CategoryEntity> queryCateByPage(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据id删除菜品分类
     *
     * @param id 菜品id
     */
    @Delete("delete from category where id = #{id}")
    void delCateById(Long id);

    /**
     * 修改菜品分类
     *
     * @param category 修改菜品分类
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateCate(CategoryEntity category);

    /**
     * 根据类型查询菜品分类
     *
     * @param type 菜品类型
     * @return 菜品分类后的列表数据
     */
    List<CategoryEntity> getListByType(Integer type);
}

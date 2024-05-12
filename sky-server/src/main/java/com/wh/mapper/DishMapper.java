package com.wh.mapper;

import com.github.pagehelper.Page;
import com.wh.annotation.AutoFill;
import com.wh.dto.DishPageQueryDTO;
import com.wh.entity.DishEntity;
import com.wh.enumeration.OperationType;
import com.wh.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜品接口
 */
@Mapper
public interface DishMapper {

    /**
     * 新增菜品
     * @param dish 菜品
     */
    @AutoFill(value = OperationType.INSERT)
    void addDish(DishEntity dish);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 菜品分页查询条件
     * @return 分页后的菜品列表
     */
    Page<DishVO> queryDishByPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     *
     * @param id 菜品id
     * @return 菜品信息
     */
    @Select("select * from dish where id = #{id}")
    DishEntity getDishById(Long id);

    /**
     * 根据id删除菜品
     *
     * @param id 菜品id
     */
    @Delete("delete from dish where id = #{id}")
    void  delDishById(Long id);

    /**
     * 根据id动态修改菜品数据
     *
     * @param dish 菜品
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateDish(DishEntity dish);

    /**
     * 根据分类id查询菜品
     *
     * @param dish 菜品中存在的分类id
     * @return 菜品列表
     */
    List<DishEntity> listByCategoryId(DishEntity dish);
}

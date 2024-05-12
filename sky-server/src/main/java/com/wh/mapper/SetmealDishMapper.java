package com.wh.mapper;

import com.wh.entity.SetmealDishEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 套餐与菜品关系接口
 */
@Mapper
public interface SetmealDishMapper {

    /**
     * 批量添加套餐与菜品关系
     *
     * @param setmealDishList 套餐与菜品关系集合
     */
    void addSetmealDishByBatch(List<SetmealDishEntity> setmealDishList);

    /**
     * 根据套餐id删除套餐与菜品关系
     * @param setmealId 套餐id
     */
    @Delete("delete from setmeal_dish where setmeal_id=#{setmealId}")
    void deleteSetmealDishBySetmealId(Long setmealId);

    /**
     * 根据套餐id查询套餐与菜品关系
     *
     * @param setmealId 套餐Id
     * @return 套餐与菜品关系集合
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDishEntity> getSetmealDishBySetmealId(Long setmealId);

    /**
     * 根据菜品id查询对应的套餐id
     *
     * @param dishIds 菜品id集合
     * @return 套餐id集合
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}

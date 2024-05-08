package com.wh.mapper;

import com.wh.entity.DishFlavorEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜品口味接口
 */
@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     *
     * @param dishFlavors 菜品口味数据
     */
    void addDishFlavorByBatch(List<DishFlavorEntity> dishFlavors);

    /**
     * 根据菜品id删除菜品口味数据
     *
     * @param dishId 菜品id
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void delFavorByDishId(Long dishId);
}

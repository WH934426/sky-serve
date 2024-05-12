package com.wh.mapper;

import com.wh.entity.SetmealDishEntity;
import org.apache.ibatis.annotations.Mapper;

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
}

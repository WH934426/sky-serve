package com.wh.mapper;

import com.wh.annotation.AutoFill;
import com.wh.entity.DishEntity;
import com.wh.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

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
}

package com.wh.mapper;

import com.wh.entity.DishFlavorEntity;
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
}

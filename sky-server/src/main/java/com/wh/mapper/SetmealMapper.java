package com.wh.mapper;

import com.wh.annotation.AutoFill;
import com.wh.entity.SetmealEntity;
import com.wh.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 套餐接口
 */
@Mapper
public interface SetmealMapper {

    /**
     * 添加套餐
     * @param setmeal 套餐实体
     */
    @AutoFill(OperationType.INSERT)
    void addSetmeal(SetmealEntity setmeal);
}

package com.wh.service;

import com.wh.dto.SetmealDTO;

/**
 * 套餐业务层
 */
public interface SetmealService {

    /**
     * 添加套餐，同时添加套餐和菜品的关联关系
     * @param setmealDTO 套餐数据
     */
    void addSetmealWithDish(SetmealDTO setmealDTO);
}

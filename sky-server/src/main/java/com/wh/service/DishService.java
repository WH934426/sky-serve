package com.wh.service;

import com.wh.dto.DishDTO;

/**
 * 菜品接口
 */
public interface DishService {

    /**
     * 新增菜品和对应的口味
     *
     * @param dishDTO 新增菜品需要的dto类
     */
    void addDishWithFlavor(DishDTO dishDTO);
}

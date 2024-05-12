package com.wh.service.impl;

import com.wh.dto.SetmealDTO;
import com.wh.entity.SetmealDishEntity;
import com.wh.entity.SetmealEntity;
import com.wh.mapper.SetmealDishMapper;
import com.wh.mapper.SetmealMapper;
import com.wh.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 套餐 服务实现类
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;
    private final SetmealDishMapper setmealDishMapper;

    public SetmealServiceImpl(SetmealMapper setmealMapper, SetmealDishMapper setmealDishMapper) {
        this.setmealMapper = setmealMapper;
        this.setmealDishMapper = setmealDishMapper;
    }

    /**
     * 添加套餐，同时添加套餐和菜品的关联关系
     *
     * @param setmealDTO 套餐数据
     */
    @Transactional
    @Override
    public void addSetmealWithDish(SetmealDTO setmealDTO) {
        SetmealEntity setmeal = new SetmealEntity();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        // 将套餐实体添加到数据库
        setmealMapper.addSetmeal(setmeal);

        // 获取添加后套餐的ID
        Long setmealId = setmeal.getId();

        // 获取套餐DTO中的菜品列表，并为每个菜品设置套餐ID
        List<SetmealDishEntity> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });

        // 批量添加设置好套餐ID的菜品到数据库
        setmealDishMapper.addSetmealDishByBatch(setmealDishes);
    }
}

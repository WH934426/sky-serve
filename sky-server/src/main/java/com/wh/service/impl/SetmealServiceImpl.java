package com.wh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wh.constant.MessageConstant;
import com.wh.constant.StatusConstant;
import com.wh.dto.SetmealDTO;
import com.wh.dto.SetmealPageQueryDTO;
import com.wh.entity.SetmealDishEntity;
import com.wh.entity.SetmealEntity;
import com.wh.exception.DeletionNotAllowedException;
import com.wh.mapper.SetmealDishMapper;
import com.wh.mapper.SetmealMapper;
import com.wh.result.PageResult;
import com.wh.service.SetmealService;
import com.wh.vo.SetmealVO;
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

    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO 分页查询条件
     * @return 查询结果
     */
    @Override
    public PageResult<SetmealVO> querySetmealByPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        int page = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();

        PageHelper.startPage(page, pageSize);
        Page<SetmealVO> pageResult = setmealMapper.querySetmealByPage(setmealPageQueryDTO);
        return new PageResult<>(pageResult.getTotal(), pageResult.getResult());
    }

    /**
     * 批量删除套餐
     *
     * @param ids 套餐id集合
     */
    @Transactional
    @Override
    public void deleteSetmealByBatch(List<Long> ids) {
        ids.forEach(id -> {
            SetmealEntity setmeal = setmealMapper.getSetmealById(id);
            if (StatusConstant.ENABLE.equals(setmeal.getStatus())) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });
        ids.forEach(setmealId -> {
            setmealMapper.deleteSetmealById(setmealId);
            setmealDishMapper.deleteSetmealDishBySetmealId(setmealId);
        });
    }
}

package com.wh.service;

import com.wh.dto.SetmealDTO;
import com.wh.dto.SetmealPageQueryDTO;
import com.wh.result.PageResult;
import com.wh.vo.SetmealVO;

import java.util.List;

/**
 * 套餐业务层
 */
public interface SetmealService {

    /**
     * 添加套餐，同时添加套餐和菜品的关联关系
     * @param setmealDTO 套餐数据
     */
    void addSetmealWithDish(SetmealDTO setmealDTO);

    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO 分页查询条件
     * @return 查询结果
     */
    PageResult<SetmealVO> querySetmealByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除套餐
     * @param ids 套餐id集合
     */
    void deleteSetmealByBatch(List<Long> ids);

    /**
     * 根据id查询套餐和管理的菜品数据
     * @param id id
     * @return 套餐
     */
    SetmealVO getSetmealDishById(Long id);

    /**
     * 修改套餐
     * @param setmealDTO 套餐数据
     */
    void updateSetmeal(SetmealDTO setmealDTO);

    /**
     * 启用警用套餐状态
     * @param status 套餐状态
     * @param id 套餐id
     */
    void updateSetmealStatus(Integer status, Long id);
}

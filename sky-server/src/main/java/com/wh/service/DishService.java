package com.wh.service;

import com.wh.dto.DishDTO;
import com.wh.dto.DishPageQueryDTO;
import com.wh.result.PageResult;
import com.wh.vo.DishVO;

import java.util.List;

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

    /**
     * 根据条件查询菜品信息，并分页返回查询结果。
     *
     * @param dishPageQueryDTO 包含分页信息和查询条件的传输对象，其中包含了页码、页大小和可能的查询过滤条件。
     * @return PageResult<DishVO> 分页结果对象，包含了总记录数和当前页的菜品结果集。
     */
    PageResult<DishVO> queryDishByPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     *
     * @param ids 菜品id
     */
    void delDishBatch(List<Long> ids);

    /**
     * 根据id查询菜品和对应的口味数据
     *
     * @param id 菜品id
     * @return dishVO
     */
    DishVO getFlavorByDishId(Long id);

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     *
     * @param dishDTO 菜品dto
     */
    void updateDishWithFlavor(DishDTO dishDTO);
}

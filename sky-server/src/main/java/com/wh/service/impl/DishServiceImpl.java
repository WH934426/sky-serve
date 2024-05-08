package com.wh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wh.constant.MessageConstant;
import com.wh.constant.StatusConstant;
import com.wh.dto.DishDTO;
import com.wh.dto.DishPageQueryDTO;
import com.wh.entity.DishEntity;
import com.wh.entity.DishFlavorEntity;
import com.wh.exception.DeletionNotAllowedException;
import com.wh.mapper.DishFlavorMapper;
import com.wh.mapper.DishMapper;
import com.wh.result.PageResult;
import com.wh.service.DishService;
import com.wh.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final DishFlavorMapper dishFlavorMapper;

    public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper) {
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
    }

    /**
     * 新增菜品和对应的口味
     *
     * @param dishDTO 新增菜品需要的dto类
     */
    @Transactional
    @Override
    public void addDishWithFlavor(DishDTO dishDTO) {
        DishEntity dish = new DishEntity();
        BeanUtils.copyProperties(dishDTO, dish);
        // 向菜品表中添加数据
        dishMapper.addDish(dish);
        // 获取insert语句生成的主键值
        Long dishId = dish.getId();
        // 获取菜品 dto 中包含的所有口味
        List<DishFlavorEntity> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            // 对每个口味进行循环处理
            flavors.forEach(dishFlavorEntity -> {
                // 设置当前口味对应的主菜 id
                dishFlavorEntity.setDishId(dishId);
            });
            // 向口味表中插入多条数据
            dishFlavorMapper.addDishFlavorByBatch(flavors);
        }
    }

    /**
     * 根据条件查询菜品信息，并分页返回查询结果。
     *
     * @param dishPageQueryDTO 包含分页信息和查询条件的传输对象，其中包含了页码、页大小和可能的查询过滤条件。
     * @return PageResult<DishVO> 分页结果对象，包含了总记录数和当前页的菜品结果集。
     */
    @Override
    public PageResult<DishVO> queryDishByPage(DishPageQueryDTO dishPageQueryDTO) {
        // 使用PageHelper进行分页初始化，根据传入的页码和页大小准备进行分页查询
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        // 调用dishMapper查询满足条件的菜品信息，返回分页后的结果
        Page<DishVO> page = dishMapper.queryDishByPage(dishPageQueryDTO);
        // 构造并返回分页结果对象，包含总记录数和当前页的查询结果
        return new PageResult<>(page.getTotal(),page.getResult());
    }


    /**
     * 批量删除菜品
     *
     * @param ids 菜品id
     */
    @Transactional
    @Override
    public void delDishBatch(List<Long> ids) {
        //判断当前菜品是否能够删除---是否存在起售中的菜品
        for (Long id : ids) {
            DishEntity dishById = dishMapper.getDishById(id);
            if (Objects.equals(dishById.getStatus(), StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // TODO 判断当前菜品是否能够删除---是否被套餐关联了


        for (Long id : ids) {
            // 删除菜品表中的菜品数据
            dishMapper.delDishById(id);
            // 删除菜品口味表中的口味数据
            dishFlavorMapper.delFavorByDishId(id);
        }
    }
}

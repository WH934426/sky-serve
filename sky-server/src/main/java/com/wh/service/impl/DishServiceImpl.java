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
import com.wh.mapper.SetmealDishMapper;
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
    private final SetmealDishMapper setmealDishMapper;

    public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper, SetmealDishMapper setmealDishMapper) {
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
        this.setmealDishMapper = setmealDishMapper;
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
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        // 调用dishMapper查询满足条件的菜品信息，返回分页后的结果
        Page<DishVO> page = dishMapper.queryDishByPage(dishPageQueryDTO);
        // 构造并返回分页结果对象，包含总记录数和当前页的查询结果
        return new PageResult<>(page.getTotal(), page.getResult());
    }


    /**
     * 批量删除菜品
     *
     * @param ids 菜品id
     */
    @Transactional
    @Override
    public void delDishBatch(List<Long> ids) {
        // 判断当前菜品是否能够删除---是否存在起售中的菜品
        for (Long id : ids) {
            DishEntity dishById = dishMapper.getDishById(id);
            if (Objects.equals(dishById.getStatus(), StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 判断当前菜品是否能够删除---是否被套餐关联了
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            // 当前菜品被套餐关联了，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        for (Long id : ids) {
            // 删除菜品表中的菜品数据
            dishMapper.delDishById(id);
            // 删除菜品口味表中的口味数据
            dishFlavorMapper.delFavorByDishId(id);
        }
    }

    /**
     * 根据id查询菜品和对应的口味数据
     *
     * @param id 菜品id
     * @return vo对象
     */
    @Override
    public DishVO getFlavorByDishId(Long id) {

        // 根据id查询菜品
        DishEntity dish = dishMapper.getDishById(id);
        // 根据菜品id查询菜品口味
        List<DishFlavorEntity> dishFlavors = dishFlavorMapper.getFlavorByDishId(id);

        // 将查询到的数据封装到vo中
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     *
     * @param dishDTO 菜品dto
     */
    @Override
    public void updateDishWithFlavor(DishDTO dishDTO) {
        DishEntity dish = new DishEntity();
        BeanUtils.copyProperties(dishDTO, dish);

        // 修改菜品表基本信息
        dishMapper.updateDish(dish);

        // 删除原有的口味数据
        dishFlavorMapper.delFavorByDishId(dishDTO.getId());

        // 重新插入口味数据
        List<DishFlavorEntity> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            // 向口味表插入n条数据
            dishFlavorMapper.addDishFlavorByBatch(flavors);
        }
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return 菜品列表
     */
    @Override
    public List<DishEntity> listByCategoryId(Long categoryId) {
        DishEntity dish = DishEntity.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.listByCategoryId(dish);
    }
}

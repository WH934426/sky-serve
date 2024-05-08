package com.wh.service.impl;

import com.wh.dto.DishDTO;
import com.wh.entity.DishEntity;
import com.wh.entity.DishFlavorEntity;
import com.wh.mapper.DishFlavorMapper;
import com.wh.mapper.DishMapper;
import com.wh.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}

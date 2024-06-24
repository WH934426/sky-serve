package com.wh.controller.user;

import com.wh.constant.StatusConstant;
import com.wh.entity.DishEntity;
import com.wh.result.Result;
import com.wh.service.DishService;
import com.wh.vo.DishVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * C端菜品浏览控制器
 */
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    @Resource
    private DishService dishService;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return 菜品列表数据
     */
    @GetMapping("/list")
    public Result<List<DishVO>> getDishByCategoryId(Long categoryId) {
        DishEntity dish = new DishEntity();
        dish.setCategoryId(categoryId);
        // 设置查询的菜品状态为启用，过滤掉已禁用的菜品。
        dish.setStatus(StatusConstant.ENABLE);
        List<DishVO> dishVOList = dishService.listWithFlavor(dish);
        return Result.success(dishVOList);
    }
}

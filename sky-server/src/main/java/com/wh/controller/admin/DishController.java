package com.wh.controller.admin;

import com.wh.dto.DishDTO;
import com.wh.result.Result;
import com.wh.service.DishService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜品相关方法
 */
@Slf4j
@RequestMapping("/admin/dish")
@RestController
public class DishController {

    @Resource
    private DishService dishService;

    /**
     * 新增菜品
     *
     * @param dishDTO 新增菜品需要的dto
     * @return 提示信息
     */
    @PostMapping
    public Result<String> addDish(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        dishService.addDishWithFlavor(dishDTO);
        return Result.success();
    }
}

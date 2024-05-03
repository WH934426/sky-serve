package com.wh.controller.admin;

import com.wh.service.DishService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
}

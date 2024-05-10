package com.wh.controller.admin;

import com.wh.service.SetmealService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐请求路径
 */
@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {

    @Resource
    private SetmealService setmealService;
}

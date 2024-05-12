package com.wh.controller.admin;

import com.wh.dto.SetmealDTO;
import com.wh.result.Result;
import com.wh.service.SetmealService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 添加套餐
     *
     * @param setmealDTO 套餐dto
     * @return 提示信息
     */
    @PostMapping
    public Result<String> addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("套餐添加请求:{}", setmealDTO);
        setmealService.addSetmealWithDish(setmealDTO);
        return Result.success();
    }
}

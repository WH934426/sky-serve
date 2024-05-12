package com.wh.controller.admin;

import com.wh.dto.SetmealDTO;
import com.wh.dto.SetmealPageQueryDTO;
import com.wh.result.PageResult;
import com.wh.result.Result;
import com.wh.service.SetmealService;
import com.wh.vo.SetmealVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO 分页查询dto
     * @return 查询后的数据
     */
    @GetMapping("/page")
    public Result<PageResult<SetmealVO>> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询请求:{}", setmealPageQueryDTO);
        PageResult<SetmealVO> pageResult = setmealService.querySetmealByPage(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除套餐
     *
     * @param ids 套餐id集合
     * @return 提示信息
     */
    @DeleteMapping
    public Result<String> deleteSetmealByBatch(@RequestParam List<Long> ids) {
        log.info("套餐批量删除请求:{}", ids);
        setmealService.deleteSetmealByBatch(ids);
        return Result.success();
    }
}

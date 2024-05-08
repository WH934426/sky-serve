package com.wh.controller.admin;

import com.wh.dto.DishDTO;
import com.wh.dto.DishPageQueryDTO;
import com.wh.result.PageResult;
import com.wh.result.Result;
import com.wh.service.DishService;
import com.wh.vo.DishVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 分页查询需要的dto
     * @return 分页查询后的结果
     */
    @GetMapping("/page")
    public Result<PageResult<DishVO>> queryDishByPage(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询:{}", dishPageQueryDTO);
        PageResult<DishVO> pageResult = dishService.queryDishByPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除菜品
     *
     * @param ids 菜品id
     * @return 提示信息
     */
    @DeleteMapping
    public Result<String> delDishBatch(@RequestBody List<Long> ids) {
        log.info("批量删除菜品:{}", ids);
        dishService.delDishBatch(ids);
        return Result.success();
    }

    /**
     * 根据id查询菜品
     *
     * @param id 菜品id
     * @return vo对象
     */
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品：{}", id);
        DishVO dishVO = dishService.getFlavorByDishId(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     *
     * @param dishDTO 修改菜品需要的dto
     * @return 提示信息
     */
    @PutMapping
    public Result<DishDTO> update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);
        dishService.updateDishWithFlavor(dishDTO);
        return Result.success();
    }
}

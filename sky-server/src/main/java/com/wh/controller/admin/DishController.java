package com.wh.controller.admin;

import com.wh.dto.DishDTO;
import com.wh.dto.DishPageQueryDTO;
import com.wh.entity.DishEntity;
import com.wh.result.PageResult;
import com.wh.result.Result;
import com.wh.service.DishService;
import com.wh.vo.DishVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜品相关方法
 */
@Slf4j
@RequestMapping("/admin/dish")
@RestController
public class DishController {

    @Resource
    private DishService dishService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
        // 构造键值对并清理
        String key = "dish:" + dishDTO.getCategoryId();
        cleanCache(key);
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
    public Result<String> delDishBatch(@RequestParam List<Long> ids) {
        log.info("批量删除菜品:{}", ids);
        dishService.delDishBatch(ids);
        // 删除所有以 dish_开头的键值对
        cleanCache("dish_*");
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
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 菜品的起售停售
     *
     * @param status 菜品状态：1为起售，0为停售
     * @param id     菜品id
     * @return 提示信息
     */
    @PostMapping("/status/{status}")
    public Result<String> updateDishStatus(@PathVariable Integer status, Long id) {
        dishService.updateSetmealStatus(status, id);
        // 删除所有以 dish_开头的键值对
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return 菜品列表数据
     */
    @GetMapping("/list")
    public Result<List<DishEntity>> listByCategoryId(Long categoryId) {
        log.info("根据分类id：{}查询到的菜品", categoryId);
        List<DishEntity> list = dishService.listByCategoryId(categoryId);
        return Result.success(list);
    }

    /**
     * 清理缓存数据
     *
     * @param pattern 缓存键的模式匹配表达式，用于匹配需要清除的缓存键。
     */
    private void cleanCache(String pattern) {
        // 使用给定的模式从Redis中查找所有匹配的键
        Set<String> keys = redisTemplate.keys(pattern);
        // 检查找到的键集合是否非空
        if (keys != null && !keys.isEmpty()) {
            // 删除所有匹配的缓存键
            redisTemplate.delete(keys);
        }
    }
}

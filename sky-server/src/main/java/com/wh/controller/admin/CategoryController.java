package com.wh.controller.admin;

import com.wh.dto.CategoryDTO;
import com.wh.dto.CategoryPageQueryDTO;
import com.wh.entity.CategoryEntity;
import com.wh.result.PageResult;
import com.wh.result.Result;
import com.wh.service.CategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 新增菜品分类
     *
     * @param categoryDTO 菜品分类dto
     * @return 提示信息
     */
    @PostMapping
    public Result<String> addCate(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类:{}", categoryDTO);
        categoryService.addCate(categoryDTO);
        return Result.success();
    }

    /**
     * 菜品分类分页查询
     *
     * @param categoryPageQueryDTO 菜品分页查询需要的dto
     * @return 分页后的菜品结果
     */
    @GetMapping("/page")
    public Result<PageResult<CategoryEntity>> queryCateByPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("查询分类:{}", categoryPageQueryDTO);
        PageResult<CategoryEntity> pageResult = categoryService.queryCateByPage(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品分类
     *
     * @param id 菜品分类id
     * @return 提示信息
     */
    @DeleteMapping
    public Result<String> deleteById(Long id) {
        log.info("删除分类：{}", id);
        categoryService.delCateById(id);
        return Result.success();
    }

    /**
     * 修改菜品分类
     *
     * @param categoryDTO 修改菜品分类需要的dto
     * @return 提示信息
     */
    @PutMapping
    public Result<String> updateCate(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类:{}", categoryDTO);
        categoryService.updateCate(categoryDTO);
        return Result.success();
    }

    /**
     * 启用禁用菜品分类
     *
     * @param status 菜品分类状态
     * @param id     菜品分类id
     * @return 提示信息
     */
    @PostMapping("/status/{status}")
    public Result<String> updateCateByType(@PathVariable Integer status, Long id) {
        log.info("修改分类状态:{}", status);
        categoryService.updateCateByType(status, id);
        return Result.success();
    }
}

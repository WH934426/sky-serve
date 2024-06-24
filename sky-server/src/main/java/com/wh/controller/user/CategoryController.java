package com.wh.controller.user;

import com.wh.entity.CategoryEntity;
import com.wh.result.Result;
import com.wh.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * C端-分类接口
 */
@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 根据类型获取分类列表
     *
     * @param type 分类类型，可选参数，用于筛选特定类型的分类。
     * @return 返回一个包含分类实体的列表
     */
    @GetMapping("/list")
    public Result<List<CategoryEntity>> getCategoriesByType(Integer type) {
        List<CategoryEntity> list = categoryService.getListByType(type);
        return Result.success(list);
    }
}

package com.wh.controller.admin;

import com.wh.dto.CategoryDTO;
import com.wh.result.Result;
import com.wh.service.CategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 新增菜品分类
     * @param categoryDTO 菜品分类dto
     * @return 提示信息
     */
    @PostMapping
    public Result<String> addCate(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类:{}",categoryDTO);
        categoryService.addCate(categoryDTO);
        return Result.success();
    }
}

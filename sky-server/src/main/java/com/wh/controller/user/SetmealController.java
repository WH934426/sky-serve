package com.wh.controller.user;

import com.wh.constant.StatusConstant;
import com.wh.entity.SetmealEntity;
import com.wh.result.Result;
import com.wh.service.SetmealService;
import com.wh.vo.DishItemVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * C端-套餐浏览接口
 */
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
public class SetmealController {
    @Resource
    private SetmealService setmealService;


    /**
     * 根据分类ID查询套餐列表
     *
     * @param categoryId 分类ID
     * @return 套餐列表
     */
    @GetMapping("/list")
    public Result<List<SetmealEntity>> getSetmealByCategoryId(Integer categoryId) {
        SetmealEntity setmeal = new SetmealEntity();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        List<SetmealEntity> list = setmealService.getSetmealList(setmeal);
        return Result.success(list);
    }


    /**
     * 根据套餐ID查询套餐详情
     *
     * @param id 套餐ID
     * @return 套餐详情
     */
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> getDishItemsBySetmealId(@PathVariable Long id) {
        List<DishItemVO> list = setmealService.getDishItemBySetmealId(id);
        return Result.success(list);
    }
}

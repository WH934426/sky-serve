package com.wh.controller.admin;

import com.wh.result.Result;
import com.wh.service.WorkspaceService;
import com.wh.vo.BusinessDataVO;
import com.wh.vo.DishDataVO;
import com.wh.vo.OrderDataVO;
import com.wh.vo.SetmealDataVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 工作台控制器
 */
@RestController
@RequestMapping("/admin/workspace")
@Slf4j
public class WorkspaceController {

    @Resource
    private WorkspaceService workspaceService;

    /**
     * 获取当日运营数据
     *
     * @return 今日运营数据
     */
    @GetMapping("/businessData")
    public Result<BusinessDataVO> getBusinessData() {
        // 获取当天的开始时间与结束时间
        LocalDateTime beginTime = LocalDateTime.now().with(LocalDateTime.MIN);
        LocalDateTime endTime = LocalDateTime.now().with(LocalDateTime.MAX);
        // 获取当日运营数据
        BusinessDataVO businessDataVO = workspaceService.getBusinessData(beginTime, endTime);
        return Result.success(businessDataVO);
    }

    /**
     * 查询订单管理数据
     *
     * @return 订单管理数据
     */
    @GetMapping("/overviewOrders")
    public Result<OrderDataVO> getOrderData() {
        return Result.success(workspaceService.getOrderOverViewData());
    }

    /**
     * 查询菜品管理数据
     *
     * @return 菜品管理数据
     */
    @GetMapping("/overviewDishes")
    public Result<DishDataVO> getDishData() {
        return Result.success(workspaceService.getDishOverViewData());
    }

    /**
     * 查询套餐管理数据
     *
     * @return 套餐管理数据
     */
    @GetMapping("/overviewSetmeals")
    public Result<SetmealDataVO> getSetmealData() {
        return Result.success(workspaceService.getSetmealOverViewData());
    }
}

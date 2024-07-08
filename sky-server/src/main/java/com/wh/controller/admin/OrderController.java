package com.wh.controller.admin;

import com.wh.dto.OrderPageQueryDTO;
import com.wh.result.PageResult;
import com.wh.result.Result;
import com.wh.service.OrderService;
import com.wh.vo.OrdersVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务端订单管理
 */
@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 订单搜索
     *
     * @param orderPageQueryDTO 订单搜索条件
     * @return 分页结果
     */
    @GetMapping("/conditionSearch")
    public Result<PageResult<OrdersVO>> searchOrdersByCondition(OrderPageQueryDTO orderPageQueryDTO) {
        log.info("查询到的订单信息：{}", orderPageQueryDTO);
        PageResult<OrdersVO> pageResult = orderService.searchOrdersByCondition(orderPageQueryDTO);
        return Result.success(pageResult);
    }
}

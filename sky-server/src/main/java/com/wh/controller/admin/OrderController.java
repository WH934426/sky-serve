package com.wh.controller.admin;

import com.wh.dto.OrderPageQueryDTO;
import com.wh.dto.OrdersCancelDTO;
import com.wh.dto.OrdersConfirmDTO;
import com.wh.dto.OrdersRejectionDTO;
import com.wh.result.PageResult;
import com.wh.result.Result;
import com.wh.service.OrderService;
import com.wh.vo.OrderStatisticsVO;
import com.wh.vo.OrdersVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 各个状态的订单数量统计
     *
     * @return 订单数量统计
     */
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> statisticsOrder() {
        OrderStatisticsVO orderStatisticsVO = orderService.statisticsOrder();
        return Result.success(orderStatisticsVO);
    }

    /**
     * 订单详情
     *
     * @param id 订单id
     * @return 订单详情
     */
    @GetMapping("/details/{id}")
    public Result<OrdersVO> getOrderDetailById(@PathVariable Long id) {
        return Result.success(orderService.getOrderDetailById(id));
    }

    /**
     * 商家阶段
     *
     * @param ordersConfirmDTO 订单确认
     * @return 提示信息
     */
    @PutMapping("/confirm")
    public Result<Void> confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("商家是否接单：{}", ordersConfirmDTO.getStatus());
        orderService.confirmOrder(ordersConfirmDTO);
        return Result.success();
    }

    /**
     * 商家拒单
     *
     * @param ordersRejectionDTO 包含订单拒绝信息的数据传输对象，包括订单ID和拒绝原因。
     * @return 提示信息
     * @throws Exception 如果订单不存在或状态不正确，则抛出异常。
     */
    @PutMapping("/rejection")
    public Result<Void> rejectOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        orderService.rejectOrder(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * 商家取消订单
     *
     * @param ordersCancelDTO 包含订单取消信息的数据传输对象，包括订单ID和取消原因。
     * @return 提示信息
     * @throws Exception 如果订单不存在或状态不正确，则抛出异常。
     */
    @PutMapping("/cancel")
    public Result<Void> cancelOrder(@RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception {
        orderService.cancelOrder(ordersCancelDTO);
        return Result.success();
    }

    /**
     * 商家派送订单
     *
     * @param id 订单id
     * @return 提示信息
     */
    @PutMapping("/delivery/{id}")
    public Result<Void> deliveryOrder(@PathVariable Long id) {
        orderService.deliveryOrder(id);
        return Result.success();
    }

    /**
     * 订单完成
     *
     * @param id 订单id
     * @return 提示信息
     */
    @PutMapping("/complete/{id}")
    public Result<Void> completionOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return Result.success();
    }
}

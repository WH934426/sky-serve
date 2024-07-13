package com.wh.task;

import com.wh.entity.OrdersEntity;
import com.wh.mapper.OrderMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类 实现订单状态定时处理
 */
@Component
@Slf4j
public class OrdersTask {

    @Resource
    private OrderMapper orderMapper;


    /**
     * 定时任务，用于处理超时订单。
     * 通过CRON表达式每分钟执行一次，检查并处理15分钟前处于待支付状态的订单。
     * 将这些超时订单的状态更新为已取消，并记录取消原因和时间。
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void processTimeOutOrders() {
        log.info("定时处理超时订单：{}", LocalDateTime.now());

        // 计算15分钟前的时间点，用于查询待支付订单
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

        // 查询15分钟前仍未支付的订单
        List<OrdersEntity> orderList = orderMapper.getOrdersByStatusAndOrderTimeLT(OrdersEntity.PENDING_PAYMENT, time);
        // 遍历查询结果，更新超时订单状态
        if (orderList != null && !orderList.isEmpty()) {
            for (OrdersEntity orders : orderList) {
                // 设置订单为已取消状态，并记录取消原因和时间
                orders.setStatus(OrdersEntity.CANCELLED);
                orders.setCancelReason("订单超时，已取消");
                orders.setCancelTime(LocalDateTime.now());
                // 记录订单状态更新信息
                log.info("更新订单状态，订单号：{}", orders.getNumber());
                orderMapper.updateOrders(orders);
            }
        }
    }


    /**
     * 定时任务，用于处理处于派送中的订单。
     * 该任务每天在00:30触发，检查当天派送的订单是否已全部完成。
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void processDeliveryOrder() {
        log.info("定时处理处于派送中的订单：{}", LocalDateTime.now());

        // 计算30分钟前的时间点，用于查询30分钟前开始派送的订单
        LocalDateTime time = LocalDateTime.now().plusMinutes(-30);

        // 查询30分钟前处于派送中的订单
        List<OrdersEntity> orderList = orderMapper.getOrdersByStatusAndOrderTimeLT(OrdersEntity.DELIVERY_IN_PROGRESS, time);

        // 遍历查询到的订单，将状态更新为已完成
        if (orderList != null && !orderList.isEmpty()) {
            for (OrdersEntity orders : orderList) {
                orders.setStatus(OrdersEntity.COMPLETED);
                // 通过orderMapper更新订单状态
                orderMapper.updateOrders(orders);
            }
        }
    }
}

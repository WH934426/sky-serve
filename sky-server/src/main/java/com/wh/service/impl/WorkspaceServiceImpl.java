package com.wh.service.impl;


import com.wh.constant.StatusConstant;
import com.wh.entity.OrdersEntity;
import com.wh.mapper.DishMapper;
import com.wh.mapper.OrderMapper;
import com.wh.mapper.UserMapper;
import com.wh.service.WorkspaceService;
import com.wh.vo.BusinessDataVO;
import com.wh.vo.DishDataVO;
import com.wh.vo.OrderDataVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 工作台服务实现类
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final DishMapper dishMapper;


    public WorkspaceServiceImpl(OrderMapper orderMapper, UserMapper userMapper, DishMapper dishMapper) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.dishMapper = dishMapper;
    }

    /**
     * 获取营业数据
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 今日营业数据
     */
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime beginTime, LocalDateTime endTime) {
        // 初始化一个Map用于传递查询条件
        Map<String, Object> map = new HashMap<>();
        // 设置查询时间范围
        map.put("begin", beginTime);
        map.put("end", endTime);

        // 统计所有订单数量
        Integer totalOrderCount = orderMapper.sumOrderByMap(map);

        // 统计已完成订单的总金额
        map.put("status", OrdersEntity.COMPLETED);
        Double turnover = orderMapper.sumTurnoverByMap(map);
        // 如果总金额为null，则默认为0
        turnover = turnover == null ? 0.0 : turnover;

        // 统计已完成订单的数量
        Integer validOrderCount = orderMapper.sumOrderByMap(map);

        // 计算订单完成率和平均单价
        double unitPrice = 0.0;
        double orderCompletionRate = 0.0;
        if (totalOrderCount != 0 && validOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            unitPrice = turnover / validOrderCount;
        }

        // 统计新增用户数量
        Integer newUsers = userMapper.sumUserByMap(map);

        // 构建并返回业务数据统计VO对象
        return BusinessDataVO.builder()
                .newUsers(newUsers)
                .orderCompletionRate(orderCompletionRate)
                .turnover(turnover)
                .unitPrice(unitPrice)
                .validOrderCount(validOrderCount)
                .build();
    }

    /**
     * 获取订单数据
     *
     * @return 订单数据
     */
    @Override
    public OrderDataVO getOrderOverViewData() {
        // 初始化开始时间，用于统计所有订单
        LocalDateTime begin = LocalDateTime.now().with(LocalDateTime.MIN);
        Map<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("status", OrdersEntity.TO_BE_CONFIRMED);

        // 待接单
        Integer waitingOrders = orderMapper.sumOrderByMap(map);

        // 待派送
        map.put("status", OrdersEntity.CONFIRMED);
        Integer deliveredOrders = orderMapper.sumOrderByMap(map);

        // 已完成
        map.put("status", OrdersEntity.COMPLETED);
        Integer completedOrders = orderMapper.sumOrderByMap(map);

        // 已取消
        map.put("status", OrdersEntity.CANCELLED);
        Integer cancelledOrders = orderMapper.sumOrderByMap(map);

        // 全部订单
        map.put("status", null);
        Integer allOrders = orderMapper.sumOrderByMap(map);

        // 根据查询结果构建并返回订单概况数据对象
        return OrderDataVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    /**
     * 获取菜品数据
     *
     * @return 菜品数据
     */
    @Override
    public DishDataVO getDishOverViewData() {
        Map<String, Object> map = new HashMap<>();

        // 统计启用状态的菜品总数
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.sumDishByMap(map);

        // 统计停用状态的菜品总数
        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.sumDishByMap(map);

        return DishDataVO.builder()
                .discontinued(discontinued)
                .sold(sold)
                .build();
    }
}

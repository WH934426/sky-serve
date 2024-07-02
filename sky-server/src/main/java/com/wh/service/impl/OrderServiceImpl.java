package com.wh.service.impl;

import com.wh.mapper.OrderDetailMapper;
import com.wh.mapper.OrderMapper;
import com.wh.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * 订单service层实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    // TODO： 多个构造
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;

    public OrderServiceImpl(OrderMapper orderMapper, OrderDetailMapper orderDetailMapper) {
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
    }
}

package com.wh.mapper;

import com.wh.entity.OrdersEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单mapper接口
 */
@Mapper
public interface OrderMapper {

    /**
     * 添加订单数据
     *
     * @param orders 订单数据
     */
    void addOrders(OrdersEntity orders);
}

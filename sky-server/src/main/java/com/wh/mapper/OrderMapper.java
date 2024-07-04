package com.wh.mapper;

import com.wh.entity.OrdersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据订单号和用户id查询订单
     *
     * @param orderNumber 订单号
     * @param userId      用户id
     * @return 订单信息
     */
    @Select("select * from orders where number = #{orderNumber} and user_id = #{userId}")
    OrdersEntity getOrdersByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 修改订单
     *
     * @param orders 订单
     */
    void updateOrders(OrdersEntity orders);
}

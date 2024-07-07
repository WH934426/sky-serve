package com.wh.mapper;

import com.wh.entity.OrdersDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单明细mapper接口
 */
@Mapper
public interface OrderDetailMapper {

    /**
     * 批量新增订单明细
     *
     * @param ordersDetailList 订单明细列表
     */
    void addOrdersDetailBatch(List<OrdersDetailEntity> ordersDetailList);

    /**
     * 根据订单id查询订单明细
     *
     * @param ordersId 订单id
     * @return 订单明细列表
     */
    @Select("select * from order_detail where order_id = #{ordersId}")
    List<OrdersDetailEntity> getOrderDetailByOrderId(Long ordersId);
}

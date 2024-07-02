package com.wh.mapper;

import com.wh.entity.OrdersDetailEntity;
import org.apache.ibatis.annotations.Mapper;

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
}

package com.wh.vo;

import com.wh.entity.OrdersDetailEntity;
import com.wh.entity.OrdersEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 历史订单 VO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersVO extends OrdersEntity implements Serializable {
    /**
     * 订单菜品信息
     */
    private String orderDishes;
    /**
     * 订单详情
     */
    private List<OrdersDetailEntity> orderDetailList = new ArrayList<>();
}

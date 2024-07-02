package com.wh.service;

import com.wh.dto.OrderSubmitDTO;
import com.wh.vo.OrderSubmitVO;

/**
 * 订单service接口
 */
public interface OrderService {
    /**
     * 用户下单
     *
     * @param orderSubmitDTO 用户下单需要提交的数据
     * @return 订单提交成功返回的VO
     */
    OrderSubmitVO orderSubmit(OrderSubmitDTO orderSubmitDTO);
}

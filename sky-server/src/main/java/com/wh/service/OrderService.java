package com.wh.service;

import com.wh.dto.OrderSubmitDTO;
import com.wh.dto.OrdersPaymentDTO;
import com.wh.vo.OrderPaymentVO;
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

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO 订单支付需要提交的数据
     * @return 订单支付成功返回的VO
     * @throws Exception 订单支付失败
     */
    OrderPaymentVO orderPayment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo 订单号
     */
    void paySuccess(String outTradeNo);
}

package com.wh.service;

import com.wh.dto.OrderPageQueryDTO;
import com.wh.dto.OrderSubmitDTO;
import com.wh.dto.OrdersPaymentDTO;
import com.wh.result.PageResult;
import com.wh.vo.OrderPaymentVO;
import com.wh.vo.OrderStatisticsVO;
import com.wh.vo.OrderSubmitVO;
import com.wh.vo.OrdersVO;

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

    /**
     * 用户端历史订单分页查询
     *
     * @param orderPageQueryDTO 订单分页查询需要提交的数据
     * @return 订单分页查询结果
     */
    PageResult<OrdersVO> getHistoryOrders4User(OrderPageQueryDTO orderPageQueryDTO);

    /**
     * 根据id查询订单详情
     *
     * @param id 订单id
     * @return 订单详情
     */
    OrdersVO getOrderDetailById(Long id);

    /**
     * 用户取消订单
     *
     * @param id 订单id
     */
    void cancelOrderById(Long id) throws Exception;

    /**
     * 再来一单
     *
     * @param id 订单id
     */
    void repetitionOrder(Long id);

    /**
     * 根据条件查询订单
     *
     * @param orderPageQueryDTO 查询条件
     * @return 查询分页结果
     */
    PageResult<OrdersVO> searchOrdersByCondition(OrderPageQueryDTO orderPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     *
     * @return 各个状态的订单数量统计
     */
    OrderStatisticsVO statisticsOrder();
}

package com.wh.service;

import com.wh.dto.*;
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

    /**
     * 商家接单
     *
     * @param ordersConfirmDTO 订单确认需要提交的数据
     */
    void confirmOrder(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 商家拒单
     *
     * @param ordersRejectionDTO 包含订单拒绝信息的数据传输对象，包括订单ID和拒绝原因。
     * @throws Exception 如果订单不存在或状态不正确，则抛出异常。
     */
    void rejectOrder(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 商家取消订单
     *
     * @param ordersCancelDTO 包含订单取消信息的数据传输对象，包括订单ID和取消原因。
     * @throws Exception 如果订单不存在或状态不正确，则抛出异常。
     */
    void cancelOrder(OrdersCancelDTO ordersCancelDTO) throws Exception;

    /**
     * 商家派送订单
     *
     * @param id 订单id
     */
    void deliveryOrder(Long id);

    /**
     * 商家完成订单
     *
     * @param id 订单id
     */
    void completeOrder(Long id);

    /**
     * 用户催单
     *
     * @param id 订单id
     */
    void reminder4User(Long id);
}

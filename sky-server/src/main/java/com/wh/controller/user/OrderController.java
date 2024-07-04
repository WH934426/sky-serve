package com.wh.controller.user;

import com.wh.dto.OrderSubmitDTO;
import com.wh.dto.OrdersPaymentDTO;
import com.wh.result.Result;
import com.wh.service.OrderService;
import com.wh.vo.OrderPaymentVO;
import com.wh.vo.OrderSubmitVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * 用户端订单接口
 */
@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 用户下单
     *
     * @param orderSubmitDTO 用户下单需要传递的dto
     * @return 用户下单返回的vo数据
     */
    @PostMapping("/submit")
    public Result<OrderSubmitVO> orderSubmit(@RequestBody OrderSubmitDTO orderSubmitDTO) {
        log.info("用户下单:{}", orderSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.orderSubmit(orderSubmitDTO);
        return Result.success(orderSubmitVO);
    }


    /**
     * 处理订单支付请求。
     *
     * @param ordersPaymentDTO 包含订单支付信息的数据传输对象。
     * @return 返回订单支付结果的响应对象。
     * @throws Exception 如果支付过程中发生异常。
     */
    @PutMapping("/payment")
    public Result<OrderPaymentVO> orderPayment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        // 调用订单服务进行支付处理，并返回支付结果
        OrderPaymentVO orderPaymentVO = orderService.orderPayment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        // 返回支付结果的成功响应
        return Result.success(orderPaymentVO);
    }
}

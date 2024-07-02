package com.wh.controller.user;

import com.wh.dto.OrderSubmitDTO;
import com.wh.result.Result;
import com.wh.service.OrderService;
import com.wh.vo.OrderSubmitVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}

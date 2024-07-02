package com.wh.service.impl;

import com.wh.constant.MessageConstant;
import com.wh.context.BaseContext;
import com.wh.dto.OrderSubmitDTO;
import com.wh.entity.AddressBookEntity;
import com.wh.entity.OrdersDetailEntity;
import com.wh.entity.OrdersEntity;
import com.wh.entity.ShoppingCartEntity;
import com.wh.exception.AddressBookBusinessException;
import com.wh.exception.ShoppingCartBusinessException;
import com.wh.mapper.AddressBookMapper;
import com.wh.mapper.OrderDetailMapper;
import com.wh.mapper.OrderMapper;
import com.wh.mapper.ShoppingCartMapper;
import com.wh.service.OrderService;
import com.wh.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单service层实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    // TODO： 多个构造
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final AddressBookMapper addressBookMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    public OrderServiceImpl(OrderMapper orderMapper, OrderDetailMapper orderDetailMapper, AddressBookMapper addressBookMapper, ShoppingCartMapper shoppingCartMapper) {
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.addressBookMapper = addressBookMapper;
        this.shoppingCartMapper = shoppingCartMapper;
    }


    /**
     * 用户下单
     *
     * @param orderSubmitDTO 用户下单需要提交的数据
     * @return 订单提交成功返回的VO
     * @throws AddressBookBusinessException  如果地址信息不存在，则抛出地址簿业务异常。
     * @throws ShoppingCartBusinessException 如果购物车信息为空，则抛出购物车业务异常。
     */
    @Transactional
    @Override
    public OrderSubmitVO orderSubmit(OrderSubmitDTO orderSubmitDTO) {

        // 处理异常情况
        // 处理地址信息异常
        AddressBookEntity addressBook = addressBookMapper.getAddressBookById(Long.valueOf(orderSubmitDTO.getAddressBookId()));
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        // 获取用户id
        Long userId = BaseContext.getCurrentId();

        // 处理购物车数据为空异常
        ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
        shoppingCart.setUserId(userId);
        List<ShoppingCartEntity> shoppingCartList = shoppingCartMapper.queryShoppingCarts(shoppingCart);
        if (shoppingCartList == null || shoppingCartList.isEmpty()) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        // 构造订单对象
        OrdersEntity orders = new OrdersEntity();
        BeanUtils.copyProperties(orderSubmitDTO, orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(OrdersEntity.UN_PAID);
        orders.setStatus(OrdersEntity.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setAddress(addressBook.getDetail());
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(userId);
        // 向订单表插入1条数据
        orderMapper.addOrders(orders);

        // 构造订单明细对象
        List<OrdersDetailEntity> ordersDetailList = new ArrayList<>();
        for (ShoppingCartEntity cart : shoppingCartList) {
            OrdersDetailEntity ordersDetail = new OrdersDetailEntity();
            BeanUtils.copyProperties(cart, ordersDetail);
            // 设置当前订单明细关联的订单id
            ordersDetail.setOrderId(orders.getId());
            ordersDetailList.add(ordersDetail);
        }
        // 向订单明细表插入n条数据
        orderDetailMapper.addOrdersDetailBatch(ordersDetailList);

        // 提交订单后清空购物车数据
        shoppingCartMapper.deleteShoppingCartByUserId(userId);
        // 返回封装对象vo
        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderNumber(orders.getNumber())
                .orderAmount(new BigDecimal(orders.getAmount()))
                .build();
    }
}

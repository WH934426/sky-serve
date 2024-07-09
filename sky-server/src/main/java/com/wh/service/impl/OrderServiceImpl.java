package com.wh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wh.constant.MessageConstant;
import com.wh.context.BaseContext;
import com.wh.dto.*;
import com.wh.entity.*;
import com.wh.exception.AddressBookBusinessException;
import com.wh.exception.OrderBusinessException;
import com.wh.exception.ShoppingCartBusinessException;
import com.wh.mapper.*;
import com.wh.result.PageResult;
import com.wh.service.OrderService;
import com.wh.utils.WeChatPayUtil;
import com.wh.vo.OrderPaymentVO;
import com.wh.vo.OrderStatisticsVO;
import com.wh.vo.OrderSubmitVO;
import com.wh.vo.OrdersVO;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 订单service层实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final AddressBookMapper addressBookMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserMapper userMapper;
    private final WeChatPayUtil weChatPayUtil;

    public OrderServiceImpl(OrderMapper orderMapper, OrderDetailMapper orderDetailMapper, AddressBookMapper addressBookMapper, ShoppingCartMapper shoppingCartMapper, UserMapper userMapper, WeChatPayUtil weChatPayUtil) {
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.addressBookMapper = addressBookMapper;
        this.shoppingCartMapper = shoppingCartMapper;
        this.userMapper = userMapper;
        this.weChatPayUtil = weChatPayUtil;
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

    /**
     * 处理订单支付流程。
     *
     * @param ordersPaymentDTO 包含订单支付所需信息的DTO。
     * @return 返回订单支付结果的VO。
     * @throws Exception 如果订单已经支付，则抛出异常。
     */
    @Override
    public OrderPaymentVO orderPayment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        Long userId = BaseContext.getCurrentId();
        UserEntity user = userMapper.getUserById(userId);

        // 调用微信支付工具的支付方法，发起支付请求
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(),
                new BigDecimal("0.01"),
                "苍穹外卖订单",
                user.getOpenid()
        );
        // 检查支付结果，如果订单已支付，则抛出异常
        if (jsonObject.get("code") != null && jsonObject.get("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("订单已支付");
        }
        // 将支付结果JSON对象转换为OrderPaymentVO对象
        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        // 设置VO对象的packageStr属性，该属性可能在JSON对象中以字符串形式存在
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }


    /**
     * 处理支付成功后的订单状态更新。
     * 当支付系统通知本系统支付成功时，需要调用此方法来更新订单的状态，将其标记为已支付并记录支付时间。
     *
     * @param outTradeNo 商家订单号，用于唯一标识一个订单。
     */
    @Override
    public void paySuccess(String outTradeNo) {
        Long userId = BaseContext.getCurrentId();

        // 根据商家订单号和用户ID查询订单信息
        OrdersEntity ordersDB = orderMapper.getOrdersByNumberAndUserId(outTradeNo, userId);

        // 构建更新后的订单对象，状态改为待确认，支付状态改为已支付，并记录支付时间
        OrdersEntity orders = OrdersEntity.builder()
                .id(ordersDB.getId())
                .status(OrdersEntity.TO_BE_CONFIRMED)
                .payStatus(OrdersEntity.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        // 更新订单状态
        orderMapper.updateOrders(orders);
    }

    /**
     * 用户端历史订单分页查询
     *
     * @param orderPageQueryDTO 订单分页查询需要提交的数据
     * @return 订单分页查询结果
     */
    @Override
    public PageResult<OrdersVO> getHistoryOrders4User(OrderPageQueryDTO orderPageQueryDTO) {
        PageHelper.startPage(orderPageQueryDTO.getPage(), orderPageQueryDTO.getPageSize());
        orderPageQueryDTO.setUserId(BaseContext.getCurrentId());
        orderPageQueryDTO.setStatus(orderPageQueryDTO.getStatus());

        // 根据查询条件查询订单实体的分页结果
        Page<OrdersEntity> page = orderMapper.getHistoryOrders4User(orderPageQueryDTO);
        List<OrdersVO> list = new ArrayList<>();

        // 如果查询结果存在，则对每个订单实体进行转换成订单VO的操作
        if (page != null && page.getTotal() > 0) {
            for (OrdersEntity orders : page) {
                Long ordersId = orders.getId();
                // 根据订单ID查询对应的订单详情列表
                List<OrdersDetailEntity> orderDetailList = orderDetailMapper.getOrderDetailByOrderId(ordersId);
                // 创建订单VO，并将订单实体的属性复制到VO中
                OrdersVO ordersVO = new OrdersVO();
                BeanUtils.copyProperties(orders, ordersVO);
                // 设置订单的详情列表
                ordersVO.setOrderDetailList(orderDetailList);

                list.add(ordersVO);
            }
        }
        // 断言page不为null，确保后续使用page.getTotal()时不会出现空指针异常
        assert page != null;
        // 返回分页查询结果，包含订单列表和分页信息
        return new PageResult<>(page.getTotal(), list);
    }

    /**
     * 根据id查询订单详情
     *
     * @param id 订单id
     * @return 包含订单详细信息的VO对象
     */
    @Override
    public OrdersVO getOrderDetailById(Long id) {
        // 通过订单ID查询订单实体
        OrdersEntity orders = orderMapper.getOrdersById(id);
        // 根据订单ID查询对应的订单详情实体列表
        List<OrdersDetailEntity> orderDetailList = orderDetailMapper.getOrderDetailByOrderId(orders.getId());
        // 创建订单VO对象，并将订单实体的属性复制到VO对象中
        OrdersVO ordersVO = new OrdersVO();
        BeanUtils.copyProperties(orders, ordersVO);
        // 设置订单VO对象的订单详情列表
        ordersVO.setOrderDetailList(orderDetailList);

        return ordersVO;
    }

    /**
     * 用户取消订单
     *
     * @param id 订单id
     * @throws Exception 如果订单不存在或者订单状态错误，则抛出异常
     */
    @Override
    public void cancelOrderById(Long id) throws Exception {
        // 根据订单ID查询订单信息
        OrdersEntity ordersDB = orderMapper.getOrdersById(id);

        // 检查订单是否存在，如果不存在则抛出异常
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        // 检查订单状态，如果状态大于待确认，则不能取消订单
        if (ordersDB.getStatus() > OrdersEntity.TO_BE_CONFIRMED) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        // 创建一个新的订单对象，用于更新订单状态
        OrdersEntity orders = new OrdersEntity();
        orders.setId(ordersDB.getId());
        // 如果订单状态为待确认，执行退款操作，并将支付状态设置为已退款
        if (ordersDB.getStatus().equals(OrdersEntity.TO_BE_CONFIRMED)) {
            weChatPayUtil.refund(
                    ordersDB.getNumber(),
                    ordersDB.getNumber(),
                    new BigDecimal("0.01"),
                    new BigDecimal("0.01"));
            orders.setPayStatus(OrdersEntity.REFUND);
        }
        // 设置订单状态为已取消，并记录取消原因和时间
        orders.setStatus(OrdersEntity.CANCELLED);
        orders.setCancelReason("用户取消订单");
        orders.setCancelTime(LocalDateTime.now());
        // 更新订单信息到数据库
        orderMapper.updateOrders(orders);
    }

    /**
     * 根据订单ID复制订单商品至购物车
     * 该方法用于用户再次下单时，将之前订单的商品信息复制到购物车中，方便用户快速下单。
     *
     * @param id 订单ID，用于查询订单详情。
     */
    @Override
    public void repetitionOrder(Long id) {
        // 获取当前用户ID，用于设置购物车的商品所属用户。
        Long userId = BaseContext.getCurrentId();

        // 根据订单ID查询订单详情列表。
        List<OrdersDetailEntity> orderDetailList = orderDetailMapper.getOrderDetailByOrderId(id);

        // 将订单详情转换为购物车项，并批量添加到购物车。
        List<ShoppingCartEntity> shoppingCartList = orderDetailList.stream().map(x -> {
            ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
            // 复制订单详情信息到购物车项，排除ID字段。
            BeanUtils.copyProperties(x, shoppingCart, "id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());

            return shoppingCart;
        }).toList();

        // 批量添加购物车项到购物车表中。
        shoppingCartMapper.addShoppingCartBatch(shoppingCartList);
    }

    /**
     * 根据条件查询用户的订单信息并分页。
     *
     * @param orderPageQueryDTO 包含分页信息和查询条件的数据传输对象。
     * @return 返回分页结果，其中包含转换后的订单视图对象列表。
     */
    @Override
    public PageResult<OrdersVO> searchOrdersByCondition(OrderPageQueryDTO orderPageQueryDTO) {
        PageHelper.startPage(orderPageQueryDTO.getPage(), orderPageQueryDTO.getPageSize());
        // 根据条件查询用户的订单历史记录
        Page<OrdersEntity> page = orderMapper.getHistoryOrders4User(orderPageQueryDTO);
        // 部分订单状态，需要额外返回订单菜品信息，将Orders转化为OrderVO
        List<OrdersVO> ordersVOList = getOrderVOList(page);
        return new PageResult<>(page.getTotal(), ordersVOList);
    }

    /**
     * 各个状态的订单数量统计
     *
     * @return 各个状态的订单数量统计
     */
    @Override
    public OrderStatisticsVO statisticsOrder() {
        // 根据状态，分别查询出待接单，待派送，派送中的订单数量
        Integer toBeConfirmed = orderMapper.getOrderCountByStatus(OrdersEntity.TO_BE_CONFIRMED);
        Integer confirmed = orderMapper.getOrderCountByStatus(OrdersEntity.CONFIRMED);
        Integer deliveryInProgress = orderMapper.getOrderCountByStatus(OrdersEntity.DELIVERY_IN_PROGRESS);

        // 封装并返回统计结果
        return OrderStatisticsVO.builder()
                .toBeConfirmed(toBeConfirmed)
                .confirmed(confirmed)
                .deliveryInProgress(deliveryInProgress)
                .build();
    }

    /**
     * 商家接单
     *
     * @param ordersConfirmDTO 订单确认需要提交的数据
     */
    @Override
    public void confirmOrder(OrdersConfirmDTO ordersConfirmDTO) {
        OrdersEntity orders = OrdersEntity.builder()
                .id(ordersConfirmDTO.getId())
                .status(OrdersEntity.CONFIRMED)
                .build();
        orderMapper.updateOrders(orders);
    }

    /**
     * 拒绝订单。
     * <p>该方法用于处理订单的拒绝操作，它首先验证订单是否存在且状态为待确认，
     * 如果订单已支付，则尝试进行退款操作。最后，更新订单状态为已取消。</p>
     *
     * @param ordersRejectionDTO 包含订单拒绝信息的数据传输对象，包括订单ID和拒绝原因。
     * @throws Exception 如果订单不存在或状态不正确，则抛出异常。
     */
    @Override
    public void rejectOrder(OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        // 根据订单ID查询订单信息
        OrdersEntity orderDB = orderMapper.getOrdersById(ordersRejectionDTO.getId());
        // 验证订单是否存在且状态为待确认
        if (orderDB == null || !Objects.equals(orderDB.getStatus(), OrdersEntity.TO_BE_CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        // 检查订单支付状态，如果已支付，则进行退款操作
        Integer payStatus = orderDB.getPayStatus();
        if (Objects.equals(payStatus, OrdersEntity.PAID)) {
            // 调用微信支付工具进行退款，假设退款金额为0.01元
            String refund = weChatPayUtil.refund(
                    orderDB.getNumber(),
                    orderDB.getNumber(),
                    new BigDecimal("0.01"),
                    new BigDecimal("0.01"));
        }

        // 构建订单实体，更新订单状态为已取消
        OrdersEntity orders = OrdersEntity.builder()
                .id(ordersRejectionDTO.getId())
                .status(OrdersEntity.CANCELLED)
                .rejectReason(ordersRejectionDTO.getRejectionReason())
                .cancelTime(LocalDateTime.now())
                .build();
        // 更新订单信息
        orderMapper.updateOrders(orders);
    }


    /**
     * 根据分页对象转换为订单视图对象列表。
     * 该方法主要用于将数据库查询得到的订单实体对象转换为面向前端展示的订单视图对象。
     * 这种转换包括了基本属性的复制以及特定属性的加工，如将订单中的菜品信息汇总为字符串。
     *
     * @param page 分页对象，包含查询结果和分页信息。
     * @return 返回转换后的订单视图对象列表。
     */
    private List<OrdersVO> getOrderVOList(Page<OrdersEntity> page) {
        List<OrdersVO> ordersVOList = new ArrayList<>();
        List<OrdersEntity> orderList = page.getResult();
        // 当查询结果不为空时，进行转换处理
        if (!Collections.isEmpty(orderList)) {
            for (OrdersEntity orders : orderList) {
                OrdersVO ordersVO = new OrdersVO();
                // 将订单实体的基本信息转换到视图对象中
                BeanUtils.copyProperties(orders, ordersVO);
                String orderDishes = getDishesStr(orders);
                // 设置订单的菜品信息到视图对象中
                ordersVO.setOrderDishes(orderDishes);
                // 将处理后的订单视图对象添加到结果列表中
                ordersVOList.add(ordersVO);
            }
        }
        return ordersVOList;
    }

    /**
     * 根据订单ID获取订单中的菜品字符串。
     * 将订单中的每个菜品名称和数量拼接成字符串格式，多个菜品之间用逗号分隔。
     *
     * @param orders 订单实体，用于获取订单ID。
     * @return 返回拼接好的菜品字符串。
     */
    private String getDishesStr(OrdersEntity orders) {
        List<OrdersDetailEntity> orderDetailList = orderDetailMapper.getOrderDetailByOrderId(orders.getId());
        // 将订单中的每个菜品名称和数量拼接成字符串，并用逗号分隔
        List<String> orderDishList = orderDetailList.stream().map(x -> x.getName() + "*" + x.getNumber() + ";"
        ).toList();
        // 将所有菜品信息合并为一个字符串
        return String.join(",", orderDishList);
    }
}

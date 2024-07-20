package com.wh.mapper;

import com.github.pagehelper.Page;
import com.wh.dto.GoodsSalesDTO;
import com.wh.dto.OrderPageQueryDTO;
import com.wh.entity.OrdersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 订单mapper接口
 */
@Mapper
public interface OrderMapper {

    /**
     * 添加订单数据
     *
     * @param orders 订单数据
     */
    void addOrders(OrdersEntity orders);

    /**
     * 根据订单号和用户id查询订单
     *
     * @param orderNumber 订单号
     * @param userId      用户id
     * @return 订单信息
     */
    @Select("select * from orders where number = #{orderNumber} and user_id = #{userId}")
    OrdersEntity getOrdersByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 修改订单
     *
     * @param orders 订单
     */
    void updateOrders(OrdersEntity orders);

    /**
     * 用户端获取历史订单
     *
     * @param orderPageQueryDTO 分页查询条件
     * @return 分页数据
     */
    Page<OrdersEntity> getHistoryOrders4User(OrderPageQueryDTO orderPageQueryDTO);

    /**
     * 根据id查询订单
     *
     * @param id 订单id
     * @return 订单信息
     */
    @Select("select * from orders where id = #{id}")
    OrdersEntity getOrdersById(Long id);

    /**
     * 根据状态查询订单数量
     *
     * @param status 订单状态
     * @return 订单数量
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer getOrderCountByStatus(Integer status);

    /**
     * 根据状态和下单时间查询订单
     *
     * @param status    订单状态
     * @param orderTime 下单时间
     * @return 订单列表
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<OrdersEntity> getOrdersByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 根据条件查询营业额
     *
     * @param map 查询条件map集合
     * @return 营业额
     */
    Double sumTurnoverByMap(Map<String, Object> map);

    /**
     * 根据条件查询订单数量
     *
     * @param map 查询条件map集合
     * @return 订单数量
     */
    Integer sumOrderByMap(Map<String, Object> map);

    /**
     * 查询销量排名top10
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 销量排名前10的商品名和销量列表
     */
    List<GoodsSalesDTO> getSalesTop10ByTime(LocalDateTime begin, LocalDateTime end);
}

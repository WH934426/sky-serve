package com.wh.service;

import com.wh.vo.BusinessDataVO;
import com.wh.vo.DishDataVO;
import com.wh.vo.OrderDataVO;
import com.wh.vo.SetmealDataVO;

import java.time.LocalDateTime;

/**
 * 工作台服务层接口
 */
public interface WorkspaceService {

    /**
     * 获取营业数据
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 今日营业数据
     */
    BusinessDataVO getBusinessData(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 获取订单数据
     *
     * @return 订单数据
     */
    OrderDataVO getOrderOverViewData();

    /**
     * 获取菜品数据
     *
     * @return 菜品数据
     */
    DishDataVO getDishOverViewData();

    /**
     * 获取套餐数据
     *
     * @return 套餐数据
     */
    SetmealDataVO getSetmealOverViewData();
}

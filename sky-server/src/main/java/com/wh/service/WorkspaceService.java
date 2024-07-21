package com.wh.service;

import com.wh.vo.BusinessDataVO;

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
}

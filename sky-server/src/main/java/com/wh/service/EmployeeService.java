package com.wh.service;

import com.wh.dto.EmployeeLoginDTO;
import com.wh.entity.EmployeeEntity;

/**
 * 员工管理接口
 */
public interface EmployeeService {
    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工登录时传递的数据模型
     * @return 员工实体信息
     */
    EmployeeEntity empLogin(EmployeeLoginDTO employeeLoginDTO);
}

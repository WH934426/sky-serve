package com.wh.service.impl;

import com.wh.dto.EmployeeLoginDTO;
import com.wh.entity.EmployeeEntity;
import com.wh.mapper.EmployeeMapper;
import com.wh.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 员工管理实现类
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工登录时传递的数据模型
     * @return 员工实体信息
     */
    @Override
    public EmployeeEntity empLogin(EmployeeLoginDTO employeeLoginDTO) {
        // 从dto中获取用户传递的用户名与密码
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        // 从数据库中查询用户名
        EmployeeEntity employee = employeeMapper.getByUsername(username);


        return employee;
    }
}

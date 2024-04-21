package com.wh.service.impl;

import com.wh.constant.MessageConstant;
import com.wh.constant.StatusConstant;
import com.wh.dto.EmployeeLoginDTO;
import com.wh.entity.EmployeeEntity;
import com.wh.exception.AccountLockedException;
import com.wh.exception.AccountNotFoundException;
import com.wh.exception.PasswordErrorException;
import com.wh.mapper.EmployeeMapper;
import com.wh.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Objects;

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

        // 处理异常情况
        if (employee == null) {
            // 用户名不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 判断密码是否正确
        // 将用户输入的密码进行MD5加密
        // password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            // 密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        // 账号被禁用
        if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return employee;
    }
}

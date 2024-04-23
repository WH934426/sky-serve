package com.wh.service.impl;

import com.wh.constant.MessageConstant;
import com.wh.constant.PasswordConstant;
import com.wh.constant.StatusConstant;
import com.wh.context.BaseContext;
import com.wh.dto.EmployeeDTO;
import com.wh.dto.EmployeeLoginDTO;
import com.wh.entity.EmployeeEntity;
import com.wh.exception.AccountLockedException;
import com.wh.exception.AccountNotFoundException;
import com.wh.exception.PasswordErrorException;
import com.wh.mapper.EmployeeMapper;
import com.wh.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
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
        password = DigestUtils.md5DigestAsHex(password.getBytes());
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

    /**
     * 添加员工
     *
     * @param employeeDTO 添加员工需要的dto类
     */
    @Override
    public void addEmp(EmployeeDTO employeeDTO) {
        EmployeeEntity employee = new EmployeeEntity();
        // 对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);
        //设置账号的状态，默认正常状态 1表示正常 0表示锁定
        employee.setStatus(StatusConstant.ENABLE);
        // 设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置当前记录的创建时间和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //设置当前记录创建人id和修改人id
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.addEmp(employee);
    }
}

package com.wh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wh.constant.MessageConstant;
import com.wh.constant.PasswordConstant;
import com.wh.constant.StatusConstant;
import com.wh.dto.EmployeeDTO;
import com.wh.dto.EmployeeLoginDTO;
import com.wh.dto.EmployeePageQueryDTO;
import com.wh.entity.EmployeeEntity;
import com.wh.exception.AccountLockedException;
import com.wh.exception.AccountNotFoundException;
import com.wh.exception.PasswordErrorException;
import com.wh.mapper.EmployeeMapper;
import com.wh.result.PageResult;
import com.wh.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
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

        employeeMapper.addEmp(employee);
    }

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO 员工分页查询需要的dto
     * @return 分页结果类封装后的员工信息
     */
    @Override
    public PageResult<EmployeeEntity> queryEmpByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        // 开始分页，传入页码和每页大小
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        // 根据分页查询条件查询员工信息
        Page<EmployeeEntity> empByPage = employeeMapper.queryEmpByPage(employeePageQueryDTO);
        // 获取总记录数
        long total = empByPage.getTotal();
        // 获取分页查询结果
        List<EmployeeEntity> records = empByPage.getResult();
        // 返回分页结果
        return new PageResult<>(total, records);
    }

    /**
     * 启用或禁用员工账号
     *
     * @param status 员工账号状态
     * @param id     员工id
     */
    @Override
    public void startOrStopEmpAccount(Integer status, Long id) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setStatus(status);
        employee.setId(id);
        employeeMapper.updateEmp(employee);
    }

    /**
     * 根据员工id查询员工
     *
     * @param id 员工id
     * @return 员工信息
     */
    @Override
    public EmployeeEntity getEmpById(Long id) {
        EmployeeEntity empById = employeeMapper.getEmpById(id);
        // 手动将密码修改为*号
        empById.setPassword("****");
        return empById;
    }

    /**
     * 编辑员工信息
     *
     * @param employeeDTO 员工dto类
     */
    @Override
    public void updateEmp(EmployeeDTO employeeDTO) {
        EmployeeEntity employee = new EmployeeEntity();
        BeanUtils.copyProperties(employeeDTO,employee);
        employeeMapper.updateEmp(employee);
    }
}

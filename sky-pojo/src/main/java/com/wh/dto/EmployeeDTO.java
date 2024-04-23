package com.wh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 添加员工时需要的数据格式
 */
@Data
public class EmployeeDTO implements Serializable {
    /**
     * 员工id
     */
    private Long id;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 员工用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别
     */
    private String sex;
    /**
     * 身份证号
     */
    private String idNumber;
}

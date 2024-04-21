package com.wh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工登录需要传递的数据
 */
@Data
public class EmployeeLoginDTO implements Serializable {
    // 用户名
    private String username;
    // 密码
    private String password;
}

package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 员工登录后返回的数据
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginVO implements Serializable {
    // 主键值
    private Long id;
    // 员工用户名
    private String username;
    // 员工姓名
    private String name;
    // jwt token
    private String token;
}
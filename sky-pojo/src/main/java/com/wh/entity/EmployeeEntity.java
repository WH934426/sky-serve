package com.wh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
     * 员工密码
     */
    private String password;
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
    /**
     * 账号状态，1正常 0锁定
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人id
     */
    private Long createUser;
    /**
     * 最后修改人id
     */
    private Long updateUser;
}

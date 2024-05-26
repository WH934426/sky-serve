package com.wh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
public class UserLoginDTO implements Serializable {
    /**
     * 微信授权码
     */
    private String code;
}

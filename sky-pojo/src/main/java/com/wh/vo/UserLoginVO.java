package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * C端登录VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 微信童虎openid
     */
    private String openid;
    /**
     * 用户登录token
     */
    private String token;
}

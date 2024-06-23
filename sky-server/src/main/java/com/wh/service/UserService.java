package com.wh.service;

import com.wh.dto.UserLoginDTO;
import com.wh.entity.UserEntity;

/**
 * 用户接口
 */
public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO 微信登录信息
     * @return 用户信息
     */
    UserEntity wxLogin(UserLoginDTO userLoginDTO);
}

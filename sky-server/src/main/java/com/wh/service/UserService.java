package com.wh.service;

import com.wh.dto.UserLoginDTO;
import com.wh.vo.UserLoginVO;

/**
 * 用户接口
 */
public interface UserService {

    /**
     * 微信登录
     *
     * @param userLoginDTO 微信登录信息
     * @return 用户登录后返回的vo信息
     */
    UserLoginVO wxLogin(UserLoginDTO userLoginDTO);
}

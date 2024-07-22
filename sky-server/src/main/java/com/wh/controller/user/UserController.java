package com.wh.controller.user;

import com.wh.dto.UserLoginDTO;
import com.wh.result.Result;
import com.wh.service.UserService;
import com.wh.vo.UserLoginVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;
    
    /**
     * 处理微信登录请求。
     * 通过接收微信登录参数，验证并创建用户登录凭证。
     *
     * @param userLoginDTO 微信登录参数，包含用户的微信授权码。
     * @return 包含用户登录信息的结果对象，包括用户ID、微信OpenID和登录令牌（JWT）。
     */
    @PostMapping("/login")
    public Result<UserLoginVO> wxLogin(UserLoginDTO userLoginDTO) {
        log.info("微信登录参数:{}", userLoginDTO.getCode());
        // 根据微信登录参数调用userService的wxLogin方法进行登录处理
        UserLoginVO userLoginVO = userService.wxLogin(userLoginDTO);
        // 返回包含用户登录信息的结果对象
        return Result.success(userLoginVO);
    }
}

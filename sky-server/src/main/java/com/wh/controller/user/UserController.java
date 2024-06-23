package com.wh.controller.user;

import com.wh.constant.JwtClaimsConstant;
import com.wh.dto.UserLoginDTO;
import com.wh.entity.UserEntity;
import com.wh.properties.JwtProperties;
import com.wh.result.Result;
import com.wh.service.UserService;
import com.wh.utils.JwtUtil;
import com.wh.vo.UserLoginVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private JwtProperties jwtProperties;

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
        UserEntity user = userService.wxLogin(userLoginDTO);

        // 准备JWT的声明部分，包含用户ID
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());

        // 使用JWT工具和配置的秘钥及声明生成JWT令牌
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        // 构建用户登录VO，包含用户ID、微信OpenID和JWT令牌
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();

        // 返回包含用户登录信息的结果对象
        return Result.success(userLoginVO);
    }

}

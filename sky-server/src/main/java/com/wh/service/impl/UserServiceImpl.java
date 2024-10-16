package com.wh.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wh.constant.JwtClaimsConstant;
import com.wh.constant.MessageConstant;
import com.wh.dto.UserLoginDTO;
import com.wh.entity.UserEntity;
import com.wh.exception.LoginFailedException;
import com.wh.mapper.UserMapper;
import com.wh.properties.JwtProperties;
import com.wh.properties.WeChatProperties;
import com.wh.service.UserService;
import com.wh.utils.HttpClientUtil;
import com.wh.utils.JwtUtil;
import com.wh.vo.UserLoginVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户业务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    // 微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    private final UserMapper userMapper;
    private final WeChatProperties weChatProperties;
    private final JwtProperties jwtProperties;

    public UserServiceImpl(UserMapper userMapper, WeChatProperties weChatProperties, JwtProperties jwtProperties) {
        this.userMapper = userMapper;
        this.weChatProperties = weChatProperties;
        this.jwtProperties = jwtProperties;
    }

    /**
     * 处理微信登录请求。
     * 通过接收微信登录代码，获取用户OpenID，进而完成用户登录或注册流程。
     *
     * @param userLoginDTO 包含微信登录代码的用户登录DTO（数据传输对象）。
     * @return 成功登录或注册后的用户token信息
     * @throws LoginFailedException 如果无法获取OpenID或OpenID对应的用户已存在，则抛出登录失败异常。
     */
    @Override
    public UserLoginVO wxLogin(@RequestBody UserLoginDTO userLoginDTO) {
        // 根据登录代码获取微信OpenID
        String openid = getOpenid(userLoginDTO.getCode());
        // 检查OpenID是否获取成功，如果失败则抛出登录失败异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        // 根据OpenID查询数据库中是否存在对应的用户
        UserEntity user = userMapper.getUserByOpenid(openid);
        // 如果用户不存在，则创建新用户并添加到数据库
        if (user == null) {
            user = UserEntity.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.addUser(user);
        }
        // 准备JWT的声明部分，包含用户ID
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());

        // 使用JWT工具和配置的秘钥及声明生成JWT令牌
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        // 构建用户登录VO，包含用户ID、微信OpenID和JWT令牌
        return UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
    }

    /**
     * 根据微信小程序的code换取用户的openid。
     * 这是一个关键步骤，用于识别和验证用户身份。
     *
     * @param code 微信小程序登录时返回的code，用于换取openid。
     * @return 用户的openid，是用户在微信平台的唯一标识。
     */
    private String getOpenid(String code) {
        // 构建请求参数映射，包括appid、secret等必传参数
        Map<String, Object> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        // 发起HTTP GET请求，获取微信服务器返回的JSON数据
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        // 解析JSON数据，提取openid
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("openid");
    }

}

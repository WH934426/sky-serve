package com.wh.interceptor;

import com.wh.constant.JwtClaimsConstant;
import com.wh.context.BaseContext;
import com.wh.properties.JwtProperties;
import com.wh.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 前置拦截器
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理
     * @return True 放行 False 不放行
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            // 当前拦截到的不是动态方法，直接放行
            return true;
        }
        // 获取token
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 2、校验令牌
        try {
            log.info("token:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("当前员工id: {}", empId);
            BaseContext.setCurrentId(empId);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}

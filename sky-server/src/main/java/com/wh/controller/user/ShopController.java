package com.wh.controller.user;

import com.wh.result.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端店铺管理
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";
    private static final Integer DEFAULT_CLOSED_STATUS = -1; // 假设-1表示状态未找到或未知

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取店铺的营业状态
     *
     * @return 营业状态
     */
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Object statusObj = redisTemplate.opsForValue().get(KEY);
        int status = statusObj instanceof Integer ? (Integer) statusObj : DEFAULT_CLOSED_STATUS;

        String statusStr = status == 1 ? "营业中" : (status == DEFAULT_CLOSED_STATUS ? "状态未知" : "打烊中");
        log.info("获取到店铺的营业状态为：{}", statusStr);

        if (status == DEFAULT_CLOSED_STATUS) {
            status = 0;
        }

        return Result.success(status);
    }
}

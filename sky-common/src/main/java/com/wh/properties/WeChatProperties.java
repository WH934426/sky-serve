package com.wh.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件中微信相关配置属性
 */
@Component
@ConfigurationProperties(prefix = "sky.wechat")
@Data
public class WeChatProperties {
    private String appid;
    private String secret;
}

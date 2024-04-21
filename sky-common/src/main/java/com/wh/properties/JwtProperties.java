package com.wh.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件中的jwt配置信息
 */
@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;
}

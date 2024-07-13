package com.wh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement // 开启事务管理
@EnableCaching // 开启缓存
@EnableScheduling // 开启定时任务
public class SkyServeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyServeApplication.class, args);
        log.info("SkyServeApplication start success");
    }
}

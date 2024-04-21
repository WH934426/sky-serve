package com.wh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SkyServeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyServeApplication.class,args);
        log.info("SkyServeApplication start success");
    }
}

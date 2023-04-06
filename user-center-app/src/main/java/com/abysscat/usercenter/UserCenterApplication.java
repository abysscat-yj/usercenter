package com.abysscat.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 用户中心 SpringBoot 应用入口
 */
@SpringBootApplication(scanBasePackages = {"com.abysscat.usercenter"})
@EnableConfigurationProperties
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

}

package cn.yiidii.pigeon.auth;

import cn.yiidii.pigeon.common.security.annotation.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证服务
 *
 * @author: YiiDii Wang
 * @create: 2021-02-12 00:16
 */
@SpringBootApplication(scanBasePackages = {"cn.yiidii.pigeon"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.yiidii.pigeon.*.api"})
@EnableSecurity
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}

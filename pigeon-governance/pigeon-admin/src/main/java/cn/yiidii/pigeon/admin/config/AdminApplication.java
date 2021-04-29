package cn.yiidii.pigeon.admin.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SpringBoot Admin
 *
 * @author: YiiDii Wang
 * @create: 2021-04-29 19:30
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}

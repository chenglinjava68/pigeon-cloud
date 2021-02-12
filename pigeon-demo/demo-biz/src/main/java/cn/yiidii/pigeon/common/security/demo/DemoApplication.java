package cn.yiidii.pigeon.common.security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-11 13:36
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.yiidii.pigeon.*.api"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

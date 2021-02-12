package cn.yiidii.pigeon.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * rbac 启动类
 *
 * @author: YiiDii Wang
 * @create: 2021-02-11 16:01
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }
}

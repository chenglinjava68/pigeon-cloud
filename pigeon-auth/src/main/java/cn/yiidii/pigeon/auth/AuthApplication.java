package cn.yiidii.pigeon.auth;

import cn.yiidii.pigeon.common.core.constant.BizConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 认证服务
 *
 * @author: YiiDii Wang
 * @create: 2021-02-12 00:16
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({BizConstants.BUSINESS_PACKAGE})
@EnableFeignClients(value = BizConstants.BUSINESS_PACKAGE)
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}

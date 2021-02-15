package cn.yiidii.pigeon.demo;

import cn.yiidii.pigeon.common.core.constant.BizConstants;
import cn.yiidii.pigeon.common.knife4j.annotation.EnablePigeonKnife4j;
import cn.yiidii.pigeon.common.security.annotation.EnableResource;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-11 13:36
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({BizConstants.BUSINESS_PACKAGE})
@EnableFeignClients(value = BizConstants.BUSINESS_PACKAGE)
@EnableResource
@EnablePigeonKnife4j
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

package cn.yiidii.pigeon.demo;

import cn.yiidii.pigeon.common.core.constant.BizConstants;
import cn.yiidii.pigeon.common.feign.annotation.EnablePigeonFeign;
import cn.yiidii.pigeon.common.knife4j.annotation.EnablePigeonKnife4j;
import cn.yiidii.pigeon.common.security.annotation.EnableResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-11 13:36
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({BizConstants.BUSINESS_PACKAGE})
@EnablePigeonFeign
@EnableResource
@EnablePigeonKnife4j
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

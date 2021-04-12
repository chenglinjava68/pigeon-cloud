package cn.yiidii.pigeon.rbac;

import cn.yiidii.pigeon.common.core.constant.BizConstants;
import cn.yiidii.pigeon.common.feign.annotation.EnablePigeonFeign;
import cn.yiidii.pigeon.common.knife4j.annotation.EnablePigeonKnife4j;
import cn.yiidii.pigeon.common.security.annotation.EnableResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * rbac 启动类
 *
 * @author: YiiDii Wang
 * @create: 2021-02-11 16:01
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnablePigeonKnife4j
@ComponentScan({BizConstants.BUSINESS_PACKAGE})
@EnablePigeonFeign
@EnableResource
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }
}

package cn.yiidii.pigeon.file;

import cn.yiidii.pigeon.common.core.constant.BizConstants;
import cn.yiidii.pigeon.common.knife4j.annotation.EnablePigeonKnife4j;
import cn.yiidii.pigeon.common.security.annotation.EnableResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 文件服务
 *
 * @author: YiiDii Wang
 * @create: 2021-03-09 21:40
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnablePigeonKnife4j
@ComponentScan({BizConstants.BUSINESS_PACKAGE})
@EnableFeignClients(value = BizConstants.BUSINESS_PACKAGE)
@EnableResource
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}

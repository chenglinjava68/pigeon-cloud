package cn.yiidii.pigeon.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: YiiDii Wang
 * @create: 2021-03-27 18:29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "pigeon.social.vue")
public class SocialConfig {

    private String url;

}
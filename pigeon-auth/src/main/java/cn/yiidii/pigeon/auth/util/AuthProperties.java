package cn.yiidii.pigeon.auth.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-16 12:40
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "pigeon.auth")
public class AuthProperties {

    private Github github;
    private Gitee gitee;

    @Getter
    @Setter
    static class Github extends SuperAuthType {
    }

    @Getter
    @Setter
    static class Gitee extends SuperAuthType {
    }

}

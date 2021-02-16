package cn.yiidii.pigeon.auth.util;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthGithubScope;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthScopeUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-16 11:45
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthRequestHelper {

    @PostConstruct
    public void init() {
        log.info("===== AuthRequestHelper init...");
    }

    private final AuthProperties authProperties;

    /**
     * 根据具体的授权来源，获取授权请求工具类
     *
     * @param source 授权来源
     * @return
     */
    public AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;

        switch (source.toLowerCase()) {
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId(authProperties.getGithub().getClientId())
                        .clientSecret(authProperties.getGithub().getClientSecret())
                        .redirectUri(authProperties.getGithub().getRedirectUri())
                        .scopes(AuthScopeUtils.getScopes(AuthGithubScope.values()))
                        .build());
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw new AuthException("未获取到有效的Auth配置");
        }
        return authRequest;
    }

}

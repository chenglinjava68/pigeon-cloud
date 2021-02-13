package cn.yiidii.pigeon.auth.config;

import cn.yiidii.pigeon.common.security.config.TokenStoreConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.PostConstruct;

/**
 * AuthorizationServerConfig
 *
 * @author: YiiDii Wang
 * @create: 2021-02-12 09:44
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
@Import({
    TokenStoreConfig.class
})
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @PostConstruct
    public void init() {
        log.info("===== AuthorizationServerConfig init...");
    }


    //自定义的令牌储存规则
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    /**
     * 配置一
     * 配置客户端的详细信息，也就是需要认证的第三方服务
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //基于内存的客户端配置方式，以后改为数据库
        clients.inMemory()
                //客户端的ID
                .withClient("clientid_1")
                //客户端可以访问的资源列表
                .resourceIds("demo_resource_1", "demo_resource_2")
                //客户端的密钥
                .secret("123456")
                //oAuth2.0支持的五种授权类型
                .authorizedGrantTypes("password", "client_credentials", "authorization_code", "implicit", "refresh_token")
                //允许授权的范围
                .scopes("all")
                //false：授权码模式，授权的是后跳转到授权页面 ，true:不跳转，直接发送令牌
                .autoApprove(false)
                //验证回调地址
                .redirectUris("http://www.baidu.com");

    }

    /**
     * 令牌访问端点的安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //参数：premitAll() 公开；对应 oauth/token_key 的端点（提供公有密钥的接口url,如果你使用jwt令牌的话）
                .tokenKeyAccess("premitAll()")
                //参数：premitAll() 公开；对应 oauth/token_key 的端点 (资源服务访问的令牌解析校验接口的url)
                .checkTokenAccess("premitAll()")
                //允许表单认证，申请令牌
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置令牌访问端点
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //密码模式需要
                .authenticationManager(authenticationManager)
                //授权码需要
                .authorizationCodeServices(authorizationCodeServices)
                //自己配置的令牌管理
                .tokenServices(tokenServices())
                // 访问端点映射
                .pathMapping("/oauth/confirm_access","/custom/confirm_access")
                //允许get，post请求获取 token,及访问端点 oauth/token
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * 配置令牌的管理
     *
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices service = new DefaultTokenServices();
        //客户端信息
        service.setClientDetailsService(clientDetailsService);
        //是否产生令牌刷新
        service.setSupportRefreshToken(true);
        //token的储存策列，自己配置的
        service.setTokenStore(tokenStore);
        //令牌默认的有效时间2小时
        service.setAccessTokenValiditySeconds(7200);
        //刷新令牌默认有效期三天
        service.setRefreshTokenValiditySeconds(259200);
        return service;
    }

    /**
     * 配置授权码服务（授权码模式会用到）
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

}
